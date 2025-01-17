package ui.tableandscripts;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Arrays;

public class TableAndScriptsPanel extends JPanel {
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
        String[][] tableData = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].split("\\t");
            tableData[i] = parts;
            tableData[i] = Arrays.stream(tableData[i]).map(this::formatValue).toArray(String[]::new);
        }

        JTable table = new JTable(tableData, callback.getYears());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);

        // Create a custom cell renderer that aligns text to the right
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        // Apply the custom renderer to all columns except the first one
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        tablePanel.removeAll();
        tablePanel.add(new JScrollPane(table));
        tablePanel.revalidate();
        tablePanel.repaint();
    }

    private String formatValue(String value) {
        try {
            double number = Double.parseDouble(value);

            DecimalFormat formatter = new DecimalFormat("#,###.##");
            return formatter.format(number).replace(",", " ");
        } catch (NumberFormatException e) {
            return value;
        }

    }
}
