package ui;

import domain.Presenter;
import domain.PresenterContract;
import domain.SettingsSetter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class View extends JFrame implements ViewCallback {
    private final PresenterContract presenter = new Presenter();
    private final ModelAndDataPanel modelAndDataPanel = new ModelAndDataPanel(this);
    private final TableAndScripts tableAndScripts = new TableAndScripts();
    private static final Dimension frameSize = new Dimension(1000, 600);
    private final SettingsSetter settingsSetter = new SettingsSetter(this);

    public void openMainJFrame() {
        configure();
        openMenu();

        SettingsSetter.setParametersToObjects(this);
    }

    private void configure() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(frameSize);
        this.setLocationRelativeTo(null);   // center the window
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
    }

    private void openMenu() {
        add(modelAndDataPanel, BorderLayout.WEST);
        add(tableAndScripts, BorderLayout.CENTER);

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
        presenter.runModel(model, dataFileName);
    }
}
