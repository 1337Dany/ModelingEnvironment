package ui.tableandscripts;

import domain.SettingsSetter;

import javax.swing.*;
import java.awt.*;

public class AdHocScriptCreator extends JFrame {
    private final AdHocScriptCreatorCallback callback;
    private final JTextArea scriptTextArea = new JTextArea();
    private final JPanel buttonPanel = new JPanel();
    private static final Dimension AdHocScriptCreatorFrameSize = new Dimension(600, 300);
    {new SettingsSetter(this);}

    public AdHocScriptCreator(AdHocScriptCreatorCallback callback) {
        this.callback = callback;

        configure();
        configureTextArea();
        configureButtonPanel();

        SettingsSetter.setParametersToObjects(this);
    }

    private void configureTextArea(){
        JScrollPane scrollPane = new JScrollPane(scriptTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }
    private void configureButtonPanel(){
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton runScriptButton = new JButton("Run script");
        runScriptButton.addActionListener(e -> {
            callback.onRunAdHocScriptButton(scriptTextArea.getText());
            dispose();});
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(runScriptButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void configure(){
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        setTitle("Ad-Hoc Script Creator");
        setSize(AdHocScriptCreatorFrameSize);
        setLocationRelativeTo(null);   // center the window
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

    }
}
