package ui;

import domain.SettingsSetter;
import ui.modelanddata.ModelAndDataPanel;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements ViewCallback {
    private final ModelAndDataPanel modelAndDataPanel = new ModelAndDataPanel();
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
}
