package ui;

import domain.Presenter;
import domain.PresenterContract;
import domain.SettingsSetter;
import ui.modelanddata.ModelAndDataCallback;
import ui.modelanddata.ModelAndDataPanel;
import ui.tableandscripts.TableAndScriptsCallback;
import ui.tableandscripts.TableAndScriptsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class View extends JFrame implements ViewContract, ModelAndDataCallback, TableAndScriptsCallback {
    private final PresenterContract presenter = new Presenter();
    private final ModelAndDataPanel modelAndDataPanel = new ModelAndDataPanel(this);
    private final TableAndScriptsPanel tableAndScriptsPanel = new TableAndScriptsPanel(this);
    private static final Dimension frameSize = new Dimension(1000, 600);
    private final SettingsSetter settingsSetter = new SettingsSetter(this);

    public void openMainJFrame() {
        configure();
        openMenu();

        SettingsSetter.setParametersToObjects(this);
    }

    private void configure() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Model Environment");
        this.setSize(frameSize);
        this.setLocationRelativeTo(null);   // center the window
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
    }

    private void openMenu() {
        add(modelAndDataPanel, BorderLayout.WEST);
        add(tableAndScriptsPanel, BorderLayout.CENTER);

    }

    @Override
    public List<String> getModels() {
        return presenter.getModels();
    }

    @Override
    public List<File> getDatas() {
        return presenter.getDatas();
    }

    @Override
    public void runModel(String model, String dataFileName) {
        tableAndScriptsPanel.updateTable(presenter.runModel(model, dataFileName), dataFileName);
    }

    @Override
    public String[] getYears(String dataFileName) {
        return presenter.getYears(dataFileName);
    }
}
