package domain.presenter;

import java.io.File;
import java.util.List;

public interface PresenterContract {
    List<String> getModels();
    List<File> getDatas();
    String runModel(String model, String dataFileName);
    String[] getYears();
    String runScriptFromFile(String filePath);
    String createAndRunAdHocScript(String scriptCode);
}
