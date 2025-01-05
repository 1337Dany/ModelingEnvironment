package domain;

import data.Model;
import data.annotations.Bind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Controller {
    private Map<String, double[]> scriptVariables = new LinkedHashMap<>();
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
                            //throw new RuntimeException("Данные для поля '" + field.getName() + "' отсутствуют в входных данных.");
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
                            formatted = formatted.replace(",", ".");
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
                for (int i = 1; i < parts.length; i++) {
                    years[i] = parts[i];
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return years;
    }
}
