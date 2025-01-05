package ui.tableandscripts;

import javax.swing.*;
import java.awt.*;

public class TableAndScriptsPanel extends JPanel {
    private final TableAndScriptsCallback callback;
    private JPanel tablePanel = new JPanel();
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
        JButton createAndRunADHocScriptButton = new JButton("Create and run ad-hoc script");

        scriptsPanel.add(runScriptButton);
        scriptsPanel.add(createAndRunADHocScriptButton);
    }

    public void updateTable(String sourceTable, String dataFileName) {
        String[] lines = sourceTable.trim().split("\n");
        Object[][] tableData = new Object[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split("\\t");
            tableData[i] = parts;
        }

        JTable table = new JTable(tableData, callback.getYears(dataFileName));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        tablePanel.removeAll();
        tablePanel.add(new JScrollPane(table));
        tablePanel.revalidate();
        tablePanel.repaint();

    }
}
