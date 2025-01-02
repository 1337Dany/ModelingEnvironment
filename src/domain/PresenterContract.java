package domain;

import data.Model;

import java.io.File;
import java.util.List;

public interface PresenterContract {
    List<Model> getModels();
    List<File> getDatas();
    void runModel(Model model, String dataFileName);
}
