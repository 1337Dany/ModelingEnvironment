package ui.tableandscripts;

public interface TableAndScriptsCallback {
    String[] getYears();
    void runScriptFromFile();
    void createAndRunAdHocScript();
}
