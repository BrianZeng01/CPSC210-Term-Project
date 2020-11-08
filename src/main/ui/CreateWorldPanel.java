package ui;

import model.Worlds;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateWorldPanel extends JPanel {
    private GridBagConstraints constraints;
    protected Worlds worlds;
    protected String worldName = "Placeholder World Name";
    protected String heroName = "Placeholder Hero Name";
    protected String selectedHeroClass;
    protected String selectedDifficulty;

    // EFFECTS: Displays the create world screen with some default inputs
    public CreateWorldPanel(Worlds worlds, int width, int height) {
        this.selectedHeroClass = "warrior";
        this.selectedDifficulty = "easy";
        this.worlds = worlds;
        this.constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.DARK_GRAY);
        constraints.insets = new Insets(10,10,10,10);
        createWorldHeader();
        textInputs();

        heroClassRadioButtons();
        difficultyRadioButtons();

        submitNewWorldButton();
        backToMainMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: Displays the title for create world screen
    public void createWorldHeader() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel label = new JLabel("Creating New World");
        label.setFont(new Font("title",3,64));
        label.setForeground(new Color(178,102,255));
        label.setBackground(Color.BLACK);
        add(label,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays text fields
    public void textInputs() {
        constraints.gridy = 1;
        JTextField worldName = formatTextInput(new JTextField("World Name"), "worldName");
        add(worldName,constraints);
        constraints.gridy = 2;
        JTextField heroName = formatTextInput(new JTextField("Hero Name"), "heroName");
        add(heroName,constraints);
    }

    // RadioButton format from :https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
    // MODIFIES: this
    // EFFECTS: Creates radio buttons for hero options
    public void heroClassRadioButtons() {
        constraints.gridy = 3;
        JLabel label = new JLabel("Select A Class:");
        label.setFont(new Font("title", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        add(label,constraints);

        JRadioButton warriorButton = formatRadio(new JRadioButton("warrior"), "heroClass");
        warriorButton.setSelected(true);
        JRadioButton archerButton = formatRadio(new JRadioButton("archer"), "heroClass");
        JRadioButton mageButton = formatRadio(new JRadioButton("mage"), "heroClass");

        ButtonGroup heroRadioButtons = new ButtonGroup();
        heroRadioButtons.add(warriorButton);
        heroRadioButtons.add(archerButton);
        heroRadioButtons.add(mageButton);

        constraints.gridy = 4;
        add(warriorButton,constraints);
        constraints.gridy = 5;
        add(archerButton,constraints);
        constraints.gridy = 6;
        add(mageButton,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Creates radio buttons for difficulty options
    public void difficultyRadioButtons() {
        constraints.gridy = 7;
        JLabel label = new JLabel("Select A Difficulty:");
        label.setFont(new Font("title", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        add(label,constraints);

        JRadioButton easyButton = formatRadio(new JRadioButton("easy"), "difficulty");
        easyButton.setSelected(true);
        JRadioButton mediumButton = formatRadio(new JRadioButton("medium"), "difficulty");
        JRadioButton hardButton = formatRadio(new JRadioButton("hard"), "difficulty");

        ButtonGroup difficultyButtons = new ButtonGroup();
        difficultyButtons.add(easyButton);
        difficultyButtons.add(mediumButton);
        difficultyButtons.add(hardButton);

        constraints.gridy = 8;
        add(easyButton,constraints);
        constraints.gridy = 9;
        add(mediumButton,constraints);
        constraints.gridy = 10;
        add(hardButton,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Creates a new world with given data if all fields filled
    public void submitNewWorldButton() {
        constraints.gridy = 12;
        JButton b = new JButton("Save");
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("submit", Font.BOLD, 32));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setFont(new Font("submit", 3, 32));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setFont(new Font("submit", Font.BOLD, 32));
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createWorld();
            }
        });
        add(b,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Displays the button to go back to main menu
    public void backToMainMenuButton() {
        constraints.gridy = 13;
        JButton b = new JButton("Back to Main Menu");
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(new Font("main", Font.BOLD, 32));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backToMainMenu();
            }
        });
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                b.setFont(new Font("main", 3, 32));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                b.setFont(new Font("main", Font.BOLD, 32));
            }
        });
        add(b,constraints);
    }

    // MODIFIES: this
    // EFFECTS: Creates a new world or displays an error message if fields are missing
    public void createWorld() {
        if (heroName.equals("") || worldName.equals("")) {
            constraints.gridy = 14;
            JLabel error = new JLabel("All fields must be filled");
            error.setFont(new Font("error", Font.BOLD, 24));
            error.setForeground(Color.RED);
            add(error,constraints);
        } else {
            worlds.createWorld(worldName,heroName,selectedHeroClass,selectedDifficulty);
            backToMainMenu();
        }
    }

    // MODIFIES: this
    // EFFECTS: Goes back to the main menu
    public void backToMainMenu() {
        removeAll();
        MainMenuPanel main = new MainMenuPanel(worlds, getWidth(), getHeight());
        add(main.getPanel());
        updateUI();
    }

    // MODIFIES: this, radio
    // EFFECTS: Formats the given radio button and returns it
    public JRadioButton formatRadio(JRadioButton radio, String field) {
        radio.setBackground(Color.DARK_GRAY);
        radio.setForeground(Color.WHITE);
        radio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field.equals("heroClass")) {
                    selectedHeroClass = radio.getText();
                } else {
                    selectedDifficulty = radio.getText();
                }
            }
        });

        return radio;
    }

    // Work around from :https://stackoverflow.com/questions/28913312/change-listener-for-a-jtextfield
    // MODIFIES: text
    // EFFECTS: Returns the given text in a formatted manner
    public JTextField formatTextInput(JTextField text,String field) {
        text.setPreferredSize(new Dimension(300,60));
        text.setFont(new Font("inputs", Font.PLAIN,34));
        text.setForeground(Color.WHITE);
        text.setBackground(new Color(153,51,255));
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateField(e, text, field);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateField(e, text, field);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateField(e, text, field);
            }
        });
        return text;
    }

    // MODIFIES: this
    // EFFECTS: Keeps track of the textField being changed
    public void updateField(DocumentEvent e, JTextField text, String field) {
        if (field.equals("worldName")) {
            this.worldName = text.getText();
        } else {
            this.heroName = text.getText();
        }
    }
}
