package domain;

import data.Data;
import data.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Presenter implements PresenterContract {
    private final List<String> models = new ArrayList<>();
    private final List<File> datas;

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
    public void runModel(String model, String dataFileName) {
        //  Run the model
        Controller ctl = new Controller(model);
        ctl.readDataFrom(Data.DATA_SOURCE.getPath() + "/" + dataFileName).runModel();
        String res= ctl .getResultsAsTsv();
        System.out.println(res);
    }
}
