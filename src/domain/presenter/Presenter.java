package domain.presenter;

import data.Data;
import domain.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Presenter implements PresenterContract {
    private final List<String> models = new ArrayList<>();
    private final List<File> datas;
    private String currentModel;
    private String currentData;

    public Presenter() {
        //  Models initialization
        models.add("Model1");

        //  Data initialization
        datas = Arrays.asList(Objects.requireNonNull(new File(Data.DATA_SOURCE.getPath()).listFiles()));
    }

    @Override
    public List<String> getModels() {
        return models;
    }

    @Override
    public List<File> getDatas() {
        return datas;
    }

    @Override
    public String runModel(String model, String dataFileName) {
        currentModel = model;
        currentData = Data.DATA_SOURCE.getPath() + "/" + dataFileName;

        //  Run the model
        Controller ctl = new Controller(model);
        ctl.readDataFrom(currentData).runModel();
        String res = ctl.getResultsAsTsv();
        System.out.println(res);

        return res;
    }

    @Override
    public String[] getYears() {
        //  Get the years from the data
        Controller ctl = new Controller(currentModel);
        return ctl.getYears(currentData);
    }

    @Override
    public String runScriptFromFile(String filePath) {
        Controller ctl = new Controller(currentModel);
        ctl.readDataFrom(currentData)
                .runModel()
                .runScriptFromFile(filePath);
        String res= ctl .getResultsAsTsv();
        System.out.println(res);

        return res;
    }

    @Override
    public String createAndRunAdHocScript(String scriptCode) {
        Controller ctl = new Controller(currentModel);
        ctl.readDataFrom(currentData)
                .runModel()
                .runScript(scriptCode);
        String res= ctl .getResultsAsTsv();
        System.out.println(res);

        return res;
    }
}
