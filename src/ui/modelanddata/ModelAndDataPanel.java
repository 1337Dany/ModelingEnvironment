package ui.modelanddata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ModelAndDataPanel extends JPanel {
    private final ModelAndDataCallback callback;
    private final JPanel choosePanel = new JPanel();
    private final JButton chooseButton = new JButton("Run model");
    private final JPanel modelPanel = new JPanel();
    private final JScrollPane modelScrollPane = new JScrollPane(modelPanel);
    private final JPanel dataPanel = new JPanel();
    private final JScrollPane dataScrollPane = new JScrollPane(dataPanel);
    private JLabel selectedModel;
    private JLabel selectedData;

    public ModelAndDataPanel(ModelAndDataCallback callback) {
        this.callback = callback;

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
        selectModeAndDataLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(selectModeAndDataLabel, BorderLayout.NORTH);
        add(choosePanel, BorderLayout.CENTER);

        chooseButton.addActionListener(e -> {
            if (selectedModel != null && selectedData != null) {
               callback.runModel(selectedModel.getText(), selectedData.getText());
            }
        });

        add(chooseButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
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
        modelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        //  adding models
        for(String model : callback.getModels()) {
            addModel(model);
        }
    }

    public void addModel(String model) {
        SwingUtilities.invokeLater(() -> {
            JLabel modelLable = new JLabel(model);
            modelLable.setBackground(new Color(0, 191, 255));
            modelPanel.add(modelLable);

            modelLable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (selectedModel != null) {
                        selectedModel.setOpaque(false);
                        selectedModel.repaint();
                    }
                    modelLable.setOpaque(true);
                    selectedModel = modelLable;

                    modelLable.repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selectedModel != modelLable) {
                        modelLable.setOpaque(true);
                    }
                    modelLable.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (selectedModel != modelLable) {
                        modelLable.setOpaque(false);
                    }
                    modelLable.repaint();
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
        dataPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));

        for(File file : callback.getDatas()) {
            addData(file.getName());
        }
    }

    public void addData(String fileName) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(fileName);
            label.setBackground(new Color(0, 191, 255));
            dataPanel.add(label);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedData != null) {
                        selectedData.setOpaque(false);
                        selectedData.repaint();
                    }
                    label.setOpaque(true);
                    selectedData = label;

                    label.repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (selectedData != label) {
                        label.setOpaque(true);
                    }
                    label.repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (selectedData != label) {
                        label.setOpaque(false);
                    }
                    label.repaint();
                }
            });

            dataScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            dataScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            dataPanel.revalidate();
            dataPanel.repaint();
        });
    }
}
