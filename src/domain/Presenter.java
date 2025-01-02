package domain;

import data.Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Presenter implements PresenterContract {
    private final List<Model> models = new ArrayList<>();
    private final List<File> datas;

    public Presenter() {
        //  Models initialization
        models.add(new Model("Model 1"));

        //  Data initialization
        datas = Arrays.asList(Objects.requireNonNull(new File("src/resources/datasource").listFiles()));
    }

    @Override
    public List<Model> getModels() {
        return models;
    }

    @Override
    public List<File> getDatas() {
        return datas;
    }

    @Override
    public void runModel(Model model, String dataFileName) {
        //  Run the model
    }
}
