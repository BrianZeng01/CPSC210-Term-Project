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

    // EFFECTS: Displays the create world screen
    public CreateWorldPanel(Worlds worlds) {
        this.worlds = worlds;
        this.constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);
        constraints.insets = new Insets(10,10,10,10);
        createWorldHeader();
        textInputs();

        heroClassRadioButtons();
        difficultyRadioButtons();

        submitNewWorldButton();
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

    }

    // MODIFIES: this
    // EFFECTS: Creates radio buttons for difficulty options
    public void difficultyRadioButtons() {

    }

    // MODIFIES: this
    // EFFECTS: Creates a new world with given data if all fields filled
    public void submitNewWorldButton() {
        constraints.gridy = 4;
        JButton b = new JButton("tEST");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(worldName);
                System.out.println(heroName);
            }
        });
        add(b,constraints);
    }

    // Work around from :
    // EFFECTS: Returns the given text in a formatted manner
    public JTextField formatTextInput(JTextField text,String field) {
        text.setFont(new Font("inputs", Font.PLAIN,40));
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
