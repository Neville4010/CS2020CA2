// base code for student budget assessment
// Students do not need to use this code in their assessment, fine to junk it and do something different!
//
// Your submission must be a maven project, and must be submitted via Codio, and run in Codio
//
// user can enter in wages and loans and calculate total income
//
// run in Codio 
// To see GUI, run with java and select Box Url from Codio top line menu
//
// Layout - Uses GridBag layout in a straightforward way, every component has a (column, row) position in the UI grid
// Not the prettiest layout, but relatively straightforward
// Students who use IntelliJ or Eclipse may want to use the UI designers in these IDEs , instead of GridBagLayout
package Budget;

// Swing imports
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// class definition
public class BudgetBase extends JPanel {    // based on Swing JPanel

    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JTextField wagesField;     // Wages text field
    private JTextField otherIncomeField;     // Other income text field
    private JTextField loansField;     // Loans text field
    private JTextField foodField;      // Food text field
    private JTextField rentField;      // Rent text field
    private JTextField otherSpendingField;     // Other spending field
    private JTextField totalIncomeField; // Total Income field
    private String[] timePeriod = new String[] {"Weekly", "Monthly","Annually"};

    // constructor - create UI  (dont need to change this)
    public BudgetBase(JFrame frame) {
        initComponents();  // initalise components
    }

    // initialise componenents
    // Note that this method is quite long.  Can be shortened by putting Action Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() {

        JComboBox<String> comboTimePeriod = new JComboBox<String>(timePeriod);
        comboTimePeriod.setMaximumRowCount(4);
        addComponent(comboTimePeriod, 0, 0);
        add(comboTimePeriod);

        comboTimePeriod.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event){
                JComboBox<String> comboTimePeriod = (JComboBox<String>) event.getSource();
                String dateSelected = (String) comboTimePeriod.getSelectedItem();
                if (dateSelected.equals("Weekly")) {
                    System.out.println("Weekly view has been selected.");
                    comboTimePeriod.setForeground(Color.GREEN);
                    System.out.printf("Weekly amount: " + calculateTotalIncome());
                    System.out.printf("Monthly amount: " + (calculateTotalIncome() * 4));
                    System.out.printf("Yearly amount: " + (calculateTotalIncome() * 52));
                }
                else if (dateSelected.equals("Monthly")){
                    System.out.println("Monthly view has been selected.");
                    comboTimePeriod.setForeground(Color.GREEN);
                    System.out.printf("Weekly amount: " + (calculateTotalIncome() / 4));
                    System.out.printf("Monthly amount: " + calculateTotalIncome());
                    System.out.printf("Yearly amount: " + (calculateTotalIncome() * 12));
                }
                else if (dateSelected.equals("Annually")){
                    System.out.println("Annual view has been selected.");
                    comboTimePeriod.setForeground(Color.GREEN);
                    System.out.printf("Weekly amount: " + (calculateTotalIncome() / 52));
                    System.out.printf("Monthly amount: " + (calculateTotalIncome() / 12));
                    System.out.printf("Yearly amount: " + calculateTotalIncome());
                }

            }
        });

        // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 1, 0);

        // Row 1 - Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 2, 0);

        // Row 2 - Other label followed by wages textbox
        JLabel otherLabel = new JLabel("Other");
        addComponent(otherLabel, 3, 0);


        // set up text field for entering wages
        // Could create method to do below (since this is done several times)
        wagesField = new JTextField("", 10);   // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(wagesField, 2, 1);  
        
        // Row 2 - Other income label with input textbox following
        otherIncomeField = new JTextField("", 10);   // blank initially, with 10 columns
        otherIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(otherIncomeField, 3, 1);

        // Row 3 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 4, 0);

        // set up text box for entering loans
        loansField = new JTextField("", 10);   // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(loansField, 4, 1);

        // Row 4 - Expenditure label
        JLabel expenditureLabel = new JLabel("Expenditures");
        addComponent(expenditureLabel, 6, 0);

        //Row 5 - Food outgoing label, followed by food outgoing field
        JLabel foodOutgoingLabel = new JLabel("Food");
        addComponent(foodOutgoingLabel, 7, 0);

        //Set up textbox to obtain food value
        foodField = new JTextField("", 10);
        foodField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(foodField, 7, 1);

        //Row 6 - Rent outgoing label, followed by rent outgoing field
        JLabel rentOutgoingLabel = new JLabel("Rent");
        addComponent(rentOutgoingLabel, 8, 0);

        //Set up textbox to obtain rent value
        rentField = new JTextField("", 10);
        rentField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(rentField, 8, 1);

        //Row 7 - Other outgoings label, followed by other outgoings field
        JLabel otherOutgoingsLabel = new JLabel("Other");
        addComponent(otherOutgoingsLabel, 9, 0);

        //Set up textbox to obtain rent value
        otherSpendingField = new JTextField("", 10);
        otherSpendingField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherSpendingField, 9, 1);

        // Row 8 - Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 10, 0);

        // set up text box for displaying total income.  Users cam view, but cannot directly edit it
        totalIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 10, 1);  

        // Row 9 - Calculate Button
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 11, 0);  

        // Row 10 - Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 11, 1);  

        // set up  listeners (in a spearate method)
        initListeners();
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    private void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // calculateButton - call calculateTotalIncome() when pressed
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotalIncome();
            }
        });

    }

    // add a component at specified row and column in UI.  (0,0) is top-left corner
    private void addComponent(Component component, int gridrow, int gridcol) {
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;   // always use horixontsl filll
        layoutConstraints.gridx = gridcol;
        layoutConstraints.gridy = gridrow;
        add(component, layoutConstraints);

    }

    // update totalIncomeField (eg, when Calculate is pressed)
    // use double to hold numbers, so user can type fractional amounts such as 134.50
    public double calculateTotalIncome() {

        // get values from income text fields.  value is NaN if an error occurs
        double wages = getTextFieldValue(wagesField);
        double otherIncome = getTextFieldValue(otherIncomeField);
        double loans = getTextFieldValue(loansField);
        double food = getTextFieldValue(foodField);
        double rent = getTextFieldValue(rentField);
        double otherSpending = getTextFieldValue(otherSpendingField);


        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans)) {
            totalIncomeField.setText("");  // clear total income field
            wages = 0.0;
            return wages;   // exit method and do nothing
        }

        // otherwise calculate total income and update text field
        double totalIncome = ((wages + otherIncome + loans) - (food + rent + otherSpending));
        totalIncomeField.setText(String.format("%.2f",totalIncome));  // format with 2 digits after the .
        if (totalIncome < 0){
            totalIncomeField.setForeground(Color.RED);
        }
        return totalIncome;
    }

    // return the value if a text field as a double
    // --return 0 if field is blank
    // --return NaN if field is not a number
    private double getTextFieldValue(JTextField field) {

        // get value as String from field
        String fieldString = field.getText();  // get text from text field

        if (fieldString.isBlank()) {   // if text field is blank, return 0
            return 0;
        }

        else {  // if text field is not blank, parse it into a double
            try {
                return Double.parseDouble(fieldString);  // parse field number into a double
             } catch (java.lang.NumberFormatException ex) {  // catch invalid number exception
                JOptionPane.showMessageDialog(topLevelFrame, "Please enter a valid number");  // show error message
                return Double.NaN;  // return NaN to show that field is not a number
            }
        }
    }


// below is standard code to set up Swing, which students shouldnt need to edit much
    // standard mathod to show UI
    private static void createAndShowGUI() {
 
        //Create and set up the window.
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        BudgetBase newContentPane = new BudgetBase(frame);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);

    }
    // standard main class to set up Swing UI
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


}