package domain;

import data.Model;

import java.io.File;
import java.util.List;

public interface PresenterContract {
    List<String> getModels();
    List<File> getDatas();
    void runModel(String model, String dataFileName);
}
