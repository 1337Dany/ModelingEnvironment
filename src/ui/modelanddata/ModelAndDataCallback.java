package ui.modelanddata;

import java.io.File;
import java.util.List;

public interface ModelAndDataCallback {
    List<String> getModels();
    List<File> getDatas();
    void runModel(String model, String dataFileName);
}
