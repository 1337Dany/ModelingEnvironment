package ui.tableandscripts;

import javax.swing.*;
import java.awt.*;

public class TableAndScriptsPanel extends JPanel{
    private final TableAndScriptsCallback callback;
    private final JPanel tablePanel = new JPanel();
    private final JPanel scriptsPanel = new JPanel();

    public TableAndScriptsPanel(TableAndScriptsCallback callback) {
        this.callback = callback;

        configureTablePanel();
        configureScriptsPanel();

        configure();
    }

    private void configure() {
        setLayout(new BorderLayout());

        add(tablePanel, BorderLayout.CENTER);
        add(scriptsPanel, BorderLayout.SOUTH);
    }

    private void configureTablePanel() {
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void configureScriptsPanel() {
        scriptsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        scriptsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton runScriptButton = new JButton("Run script from file");
        runScriptButton.addActionListener(e -> callback.runScriptFromFile());
        JButton createAndRunADHocScriptButton = new JButton("Create and run ad-hoc script");
        createAndRunADHocScriptButton.addActionListener(e -> callback.createAndRunAdHocScript());

        scriptsPanel.add(runScriptButton);
        scriptsPanel.add(createAndRunADHocScriptButton);
    }

    public void updateTable(String sourceTable) {
        String[] lines = sourceTable.trim().split("\n");
        Object[][] tableData = new Object[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split("\\t");
            tableData[i] = parts;
        }

        JTable table = new JTable(tableData, callback.getYears());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        tablePanel.removeAll();
        tablePanel.add(new JScrollPane(table));
        tablePanel.revalidate();
        tablePanel.repaint();
    }
}
