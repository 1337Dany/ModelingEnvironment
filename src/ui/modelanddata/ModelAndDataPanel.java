package ui.modelanddata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModelAndDataPanel extends JPanel {
    private final JPanel choosePanel = new JPanel();
    private final JButton chooseButton = new JButton("Run model");
    private final JPanel modelPanel = new JPanel();
    private final JScrollPane modelScrollPane = new JScrollPane(modelPanel);
    private final JPanel dataPanel = new JPanel();
    private final JScrollPane dataScrollPane = new JScrollPane(dataPanel);
    private static final Dimension modelAndDataSize = new Dimension(100, 50);

    public ModelAndDataPanel() {
        configureModelPanel();
        configureDataPanel();
        configureChoosePanel();
        configure();
    }

    private void configure() {
        setLayout(new BorderLayout());
        setBounds(0, 0, 300, 300);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel selectModeAndDataLabel = new JLabel("Select Model and Data");
        selectModeAndDataLabel.setBounds(0, 0, 200, 25);

        add(selectModeAndDataLabel, BorderLayout.NORTH);
        add(choosePanel, BorderLayout.CENTER);
        add(chooseButton, BorderLayout.SOUTH);
    }

    private void configureChoosePanel() {
        choosePanel.setLayout(new BoxLayout(choosePanel, BoxLayout.X_AXIS));
        choosePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        choosePanel.add(modelScrollPane);
        choosePanel.add(Box.createRigidArea(new Dimension(10, 10)));
        choosePanel.add(dataScrollPane);
    }

    private void configureModelPanel() {
        modelPanel.setLayout(new BoxLayout(modelPanel, BoxLayout.Y_AXIS));
        modelPanel.setBackground(Color.WHITE);
        modelPanel.setPreferredSize(modelAndDataSize);
        modelPanel.setMinimumSize(modelAndDataSize);

        //test
        for (int i = 1; i <= 50; i++) {
            addModel("Model " + i);
        }

        modelScrollPane.getVerticalScrollBar().setOpaque(false);
        modelScrollPane.setOpaque(false);
        modelScrollPane.getViewport().setOpaque(false);
    }

    public void addModel(String modelName) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(modelName);
            modelPanel.add(label);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    label.setBackground(Color.ORANGE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    label.setBackground(Color.WHITE);
                }
            });

            modelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            modelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            modelPanel.revalidate();
            modelPanel.repaint();
        });
    }

    private void configureDataPanel() {
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setPreferredSize(modelAndDataSize);
        dataPanel.setMinimumSize(modelAndDataSize);

        //test
        for (int i = 1; i <= 50; i++) {
            addData("Data " + i);
        }

        dataScrollPane.getVerticalScrollBar().setOpaque(false);
        dataScrollPane.setOpaque(false);
        dataScrollPane.getViewport().setOpaque(false);
    }

    public void addData(String fileName) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(fileName);
            dataPanel.add(label);

            dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            dataScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            dataPanel.revalidate();
            dataPanel.repaint();
        });
    }
}
