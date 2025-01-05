package domain;

import data.models.Model;
import data.annotations.Bind;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controller {
    private final Map<String, double[]> scriptVariables = new LinkedHashMap<>();
    private final Model model;

    public Controller(String modelName) {
        try {
            this.model = (Model) Class.forName("data.models." + modelName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Controller readDataFrom(String fname) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            Map<String, double[]> data = new HashMap<>();
            String line;
            int LL = 0;     // number of years of simulation

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("LATA")) {
                    String[] parts = line.split("\\s+");
                    LL = parts.length - 1;
                    data.put("LL", new double[]{LL});
                } else {
                    String[] parts = line.split("\\s+");
                    String varName = parts[0];
                    double[] values = new double[parts.length - 1];
                    for (int i = 1; i < parts.length; i++) {
                        values[i - 1] = Double.parseDouble(parts[i]);
                    }
                    data.put(varName, values);
                }
            }

            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Bind.class)) {
                    field.setAccessible(true);
                    if (field.getName().equals("LL")) {
                        field.set(model, LL);
                    } else if (field.getType().isArray() && field.getType().getComponentType() == double.class) {
                        double[] values = data.get(field.getName());
                        if (values == null) {
                            values = new double[LL];
                        } else if (values.length < LL) {
                            double[] extended = new double[LL];
                            System.arraycopy(values, 0, extended, 0, values.length);
                            for (int i = values.length; i < LL; i++) {
                                extended[i] = values[values.length - 1];
                            }
                            values = extended;
                        }
                        field.set(model, values);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return this;
    }

    public Controller runModel() {
        model.run();
        return this;
    }

    public String getResultsAsTsv() {
        StringBuilder sb = new StringBuilder();

        try {
            for (Field field : model.getClass().getDeclaredFields()) {
                if (field.getType().isArray()) {
                    field.setAccessible(true);

                    String fieldName = field.getName();

                    if (field.getType().getComponentType() == double.class) {
                        double[] values = (double[]) field.get(model);
                        sb.append(fieldName).append("\t");
                        for (double value : values) {
                            String formatted = String.format("%.2f", value);
                            formatted = formatted.replace(",", ".");    // replace comma with dot for decimal numbers
                            sb.append(formatted).append("\t");
                        }
                    } else if (field.getType().getComponentType() == int.class) {
                        int[] intValues = (int[]) field.get(model);
                        sb.append(fieldName).append("\t");
                        for (int value : intValues) {
                            sb.append(value).append("\t");
                        }
                    }

                    sb.append("\n");
                }
            }

            //Made for checking script vars in case of adding additional ad-hoc scripts
            for (Map.Entry<String, double[]> entry : scriptVariables.entrySet()) {
                sb.append(entry.getKey()).append("\t");
                for (double value : entry.getValue()) {
                    String formatted = String.format("%.2f", value).replace(",", ".");
                    sb.append(formatted).append("\t");
                }
                sb.append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error formatting TSV: " + e.getMessage());
        }

        return sb.toString();
    }

    public String[] getYears(String fname) {
        String[] years = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(fname))) {
            String line;
            line = reader.readLine();
            line = line.trim();
            if (line.startsWith("LATA")) {
                String[] parts = line.split("\\s+");
                int LL = parts.length;
                years = new String[LL];
                years[0] = "LATA";
                System.arraycopy(parts, 1, years, 1, parts.length - 1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return years;
    }

    public Controller runScriptFromFile(String scriptPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptPath))) {
            StringBuilder scriptBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                scriptBuilder.append(line).append(System.lineSeparator());
            }
            // Выполняем скрипт, переданный в виде строки, с помощью метода runScript
            return this.runScript(scriptBuilder.toString());
        } catch (Exception e) {
            throw new RuntimeException("Error reading script file: " + scriptPath + ". " + e.getMessage());
        }
    }

    public Controller runScript(String script){
        // Create a Groovy Binding
        Binding binding = new Binding();

        // Bind all fields annotated with @Bind to the Groovy script
        Field[] fields = model.getClass().getDeclaredFields();
        ArrayList<String> bindedFieldNames = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Bind.class)) {
                field.setAccessible(true);
                try {
                    binding.setVariable(field.getName(), field.get(model));
                    bindedFieldNames.add(field.getName());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // Bind fields created in scripts
        for (Map.Entry<String, double[]> entry : scriptVariables.entrySet()){
            binding.setVariable(entry.getKey(), entry.getValue());
        }

        // Create and run the Groovy script
        GroovyShell shell = new GroovyShell(binding);
        shell.evaluate(script);

        for (Object obj : binding.getVariables().entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) obj;

            if(entry.getKey().length() < 2 && entry.getKey().matches("[a-z]"))
                continue;

            if(bindedFieldNames.contains(entry.getKey()))
                continue;

            scriptVariables.put(entry.getKey(), (double[])entry.getValue());
        }
        //executes the script code specified as a string
        return this;
    }
}
