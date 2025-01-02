package ui;

import data.Model;
import domain.Presenter;
import domain.PresenterContract;
import domain.SettingsSetter;
import ui.modelanddata.ModelAndDataPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class View extends JFrame implements ViewCallback {
    private final PresenterContract presenter = new Presenter();
    private final ModelAndDataPanel modelAndDataPanel = new ModelAndDataPanel(this);
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

    }

    @Override
    public List<Model> getModels() {
        return presenter.getModels();
    }

    @Override
    public List<File> getDatas() {
        return presenter.getDatas();
    }

    @Override
    public void runModel(Model model, String dataFileName) {
        presenter.runModel(model, dataFileName);
    }
}
