package pl.edu.wat.wcy.pz.view;

import de.javasoft.plaf.synthetica.*;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {
    private MainWindow mainWindow;

    CustomPanel buttonsPanel;
    JPanel tabbedPanePanel;
    JPanel okAndCancelPanel;
    JTabbedPane basicOptionsTabbedPane;
    JTabbedPane searchOptionsTabbedPane;
    JTabbedPane customizeGuiTabbedPane;

    private JComboBox lafComboBox;
    private String[] lafTitles = {"Alu Oxide"
                                , "Black Eye"
                                , "Black Moon"
                                , "Black Star"
                                , "Blue Ice"
                                , "Blue Light"
                                , "Blue Steel"
                                , "Classy"
                                , "Green Dream"
                                , "Mauve Metallic"
                                , "Orange Metallic"
                                , "Plain"
                                , "Silver Moon"
                                , "Simple 2D"
                                , "Sky Metallic"
                                , "White Vision"
                                 };

    public SettingsDialog(MainWindow mainWindow) {
        super(mainWindow, "Settings", true);
        this.mainWindow = mainWindow;
        setMyValues();
        setLayout(new BorderLayout());
        generateButtonsPanel();
        generateTabbedPanePanel();
        generateOkAndCancelPanel();

        add(buttonsPanel, BorderLayout.WEST);
        add(tabbedPanePanel, BorderLayout.CENTER);
        add(okAndCancelPanel, BorderLayout.SOUTH);
        setVisible(true);
    }


    private void generateOkAndCancelPanel() {
        okAndCancelPanel = new JPanel();
        okAndCancelPanel.setLayout(new MigLayout());

        JButton okButton = new JButton(new ImageIcon("src/images/okIcon.png"));
        okButton.setText("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okAndCancelPanel.add(okButton, "gapleft 210");

        JButton cancelButton = new JButton(new ImageIcon("src/images/cancelIcon.png"));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        okAndCancelPanel.add(cancelButton, "gapleft 4");
    }

    private void generateTabbedPanePanel() {
        tabbedPanePanel = new JPanel();
        tabbedPanePanel.setLayout(new BorderLayout());

        generateBasicOptionsTabbedPane();
        generateSearchOptionsTabbedPane();
        generateCustomizeGuiTabbedPane();

        tabbedPanePanel.add(basicOptionsTabbedPane);
    }

    private void generateCustomizeGuiTabbedPane() {
        customizeGuiTabbedPane = new JTabbedPane();
        JPanel lookAndFeel = new JPanel(new BorderLayout());

        JPanel lafPanel = new JPanel(new MigLayout());
        lafPanel.setBorder(BorderFactory.createTitledBorder("Look And Feel"));
        JLabel choseLaf = new JLabel("Chose Look and Feel");
        lafComboBox = new JComboBox(lafTitles);
        lafComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setLookAndFeel();
            }
        });
        lafComboBox.setSelectedIndex(0);



        lafPanel.add(choseLaf, "wrap");
        lafPanel.add(lafComboBox);
        lookAndFeel.add(lafPanel);

        customizeGuiTabbedPane.addTab("LookAndFeel", lookAndFeel);
    }

    private void setLookAndFeel() {

        try {
            UIManager.removeAuxiliaryLookAndFeel(UIManager.getLookAndFeel());
            SyntheticaLookAndFeel.setWindowsDecorated(false);
            UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());

                switch (lafComboBox.getSelectedIndex()) {
                    case 0:
                        UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
                        break;
                    case 1:
                        UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
                        break;
                    case 2:
                        UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
                        break;
                    case 3:
                        UIManager.setLookAndFeel(new SyntheticaBlackStarLookAndFeel());
                        break;
                    case 4:
                        UIManager.setLookAndFeel(new SyntheticaBlueIceLookAndFeel());
                        break;
                    case 5:
                        UIManager.setLookAndFeel(new SyntheticaBlueLightLookAndFeel());
                        break;
                    case 6:
                        UIManager.setLookAndFeel(new SyntheticaBlueSteelLookAndFeel());
                        break;
                    case 7:
                        UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
                        break;
                    case 8:
                        UIManager.setLookAndFeel(new SyntheticaGreenDreamLookAndFeel());
                        break;
                    case 9:
                        UIManager.setLookAndFeel(new SyntheticaMauveMetallicLookAndFeel());
                        break;
                    case 10:
                        UIManager.setLookAndFeel(new SyntheticaOrangeMetallicLookAndFeel());
                        break;
                    case 11:
                        UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
                        break;
                    case 12:
                        UIManager.setLookAndFeel(new SyntheticaSilverMoonLookAndFeel());
                        break;
                    case 13:
                        UIManager.setLookAndFeel(new SyntheticaSimple2DLookAndFeel());
                        break;
                    case 14:
                        UIManager.setLookAndFeel(new SyntheticaSkyMetallicLookAndFeel());
                        break;
                    case 15:
                        UIManager.setLookAndFeel(new SyntheticaWhiteVisionLookAndFeel());
                        break;
                }
            SwingUtilities.updateComponentTreeUI(mainWindow);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateSearchOptionsTabbedPane() {
        searchOptionsTabbedPane = new JTabbedPane();
        JPanel searchOptions = new JPanel();
        searchOptionsTabbedPane.addTab("Search Options", searchOptions);
    }

    private void generateBasicOptionsTabbedPane() {
        basicOptionsTabbedPane = new JTabbedPane();
        JPanel licensePanel = new JPanel();
        basicOptionsTabbedPane.addTab("License", licensePanel);


    }

    private void generateButtonsPanel() {
        buttonsPanel = new CustomPanel("src/images/officeImage.jpg");
        buttonsPanel.setLayout(new MigLayout());

        Dimension buttonsSize = new Dimension(70,70);

        JButton basicOptions = new JButton(" Basic Options  ");
        basicOptions.setIcon(new ImageIcon("src/images/settings64Icon.png"));
        basicOptions.setVerticalTextPosition(SwingConstants.BOTTOM);
        basicOptions.setHorizontalTextPosition(SwingConstants.CENTER);
        basicOptions.setPreferredSize(buttonsSize);
        basicOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBasicOptionsTabbedPane();
            }
        });
        buttonsPanel.add(basicOptions, "wrap");

        JButton searchOptions = new JButton("Search Options");
        searchOptions.setIcon(new ImageIcon("src/images/findIcon.png"));
        searchOptions.setVerticalTextPosition(SwingConstants.BOTTOM);
        searchOptions.setHorizontalTextPosition(SwingConstants.CENTER);
        searchOptions.setPreferredSize(buttonsSize);
        searchOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSearchOptionsTabbedPane();
            }
        });
        buttonsPanel.add(searchOptions, "wrap");

        JButton customizeGui = new JButton("Customize GUI ");
        customizeGui.setIcon(new ImageIcon("src/images/guiIcon.png"));
        customizeGui.setVerticalTextPosition(SwingConstants.BOTTOM);
        customizeGui.setHorizontalTextPosition(SwingConstants.CENTER);
        customizeGui.setPreferredSize(buttonsSize);
        customizeGui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCustomizeGuiTabbedPane();
            }
        });
        buttonsPanel.add(customizeGui);

    }

    private void setCustomizeGuiTabbedPane() {
        tabbedPanePanel.removeAll();
        tabbedPanePanel.add(customizeGuiTabbedPane);
        tabbedPanePanel.revalidate();
        tabbedPanePanel.repaint();
    }

    private void setSearchOptionsTabbedPane() {
        tabbedPanePanel.removeAll();
        tabbedPanePanel.add(searchOptionsTabbedPane);
        tabbedPanePanel.revalidate();
        tabbedPanePanel.repaint();

    }

    private void setBasicOptionsTabbedPane() {
        tabbedPanePanel.removeAll();
        tabbedPanePanel.add(basicOptionsTabbedPane);
        tabbedPanePanel.revalidate();
        tabbedPanePanel.repaint();
    }


    private void setMyValues() {
        setSize(400,500);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
