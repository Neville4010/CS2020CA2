package Budget;
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



// Swing imports
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.awt.*;
import java.util.Stack;


// class definition
public class BudgetBase extends JPanel 
{    // based on Swing JPanel
    Stack<GUIState> stateStack;
    GUIState previousState;
    GUIState currentState;
    // high level UI stuff
    JFrame topLevelFrame;  // top-level JFrame
    GridBagConstraints layoutConstraints = new GridBagConstraints(); // used to control layout

    // widgets which may have listeners and/or values
    private JButton calculateButton;   // Calculate button
    private JButton exitButton;        // Exit button
    private JButton undoButton;        // Undo button
    private JComboBox<String> comboTimePeriod;  // JComboBox for time period selection
    private String[] timePeriod = new String[] {"Weekly", "Monthly","Annually"};    // Selection values for combobox
    private JTextField wagesField;     // Wages text field
    private JTextField loansField;     // Loans text field
    private JTextField otherIncomeField;     // Other income text field
    private JTextField foodField;      // Food text field
    private JTextField rentField;      // Rent text field
    private JTextField otherSpendingField;     // Other spending field
    private JTextField totalSpendingField;     // Total spending field
    private JTextField totalIncomeField; // Total Income field
    private JTextField totalAmountField;       // Total amount field
    private JTextField weeklyTotalField; // Weekly total amount field
    private JTextField monthlyTotalField; // Monthly total amount field
    private JTextField yearlyTotalField; // Yearly total amount field
    private double totalSpending;   // total spending amount double value
    private double totalIncome; // total income amount double value

    // constructor - create UI  (dont need to change this)
    public BudgetBase(JFrame frame) 
    {
        initComponents();  // initalise components
    }

    // initialise componenents
    // Note that this method is quite long.  Can be shortened by putting Action Listener stuff in a separate method
    // will be generated automatically by IntelliJ, Eclipse, etc
    private void initComponents() 
    {
        // initialising stack of states of GUI from GUIState Class
        stateStack = new Stack<GUIState>();
        // setting value of the previous GUI state
        previousState = new GUIState();
        // setting value of the current GUI state
        currentState = new GUIState();

        // Top row (0) - "INCOME" label
        JLabel incomeLabel = new JLabel("INCOME");
        addComponent(incomeLabel, 0, 0);

        // Row 1 - Wages label followed by wages textbox
        JLabel wagesLabel = new JLabel("Wages");
        addComponent(wagesLabel, 1, 0);

        // set up text field for entering wages
        wagesField = new JTextField("0", 10);   // blank initially, with 10 columns
        wagesField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(wagesField, 1, 1);

        // Row 2 - Loans label followed by loans textbox
        JLabel loansLabel = new JLabel("Loans");
        addComponent(loansLabel, 2, 0);
        
        // set up text box for entering loans
        loansField = new JTextField("0", 10);   // blank initially, with 10 columns
        loansField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(loansField, 2, 1);

        // Row 3 - Other label followed by wages textbox
        JLabel otherLabel = new JLabel("Other");
        addComponent(otherLabel, 3, 0);
        
        // set up text box for entering other income
        otherIncomeField = new JTextField("0", 10);   // blank initially, with 10 columns
        otherIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        addComponent(otherIncomeField, 3, 1);

        // Row 4 - Expenditure label
        JLabel expenditureLabel = new JLabel("Expenditures");
        addComponent(expenditureLabel, 4, 0);

        //Row 5 - Food outgoing label, followed by food outgoing field
        JLabel foodOutgoingLabel = new JLabel("Food");
        addComponent(foodOutgoingLabel, 5, 0);

        //Set up textbox to obtain food value
        foodField = new JTextField("0", 10);
        foodField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(foodField, 5, 1);

        //Row 6 - Rent outgoing label, followed by rent outgoing field
        JLabel rentOutgoingLabel = new JLabel("Rent");
        addComponent(rentOutgoingLabel, 6, 0);

        //Set up textbox to obtain rent value
        rentField = new JTextField("0", 10);
        rentField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(rentField, 6, 1);

        //Row 7 - Other outgoings label, followed by other outgoings field
        JLabel otherOutgoingsLabel = new JLabel("Other");
        addComponent(otherOutgoingsLabel, 7, 0);

        //Set up textbox to obtain rent value
        otherSpendingField = new JTextField("0", 10);
        otherSpendingField.setHorizontalAlignment(JTextField.RIGHT);
        addComponent(otherSpendingField, 7, 1);

        // Row 8 - Total Income label followed by total income field
        JLabel totalIncomeLabel = new JLabel("Total Income");
        addComponent(totalIncomeLabel, 8, 0);

        // set up text box for displaying total income.  Users can view, but cannot directly edit it
        totalIncomeField = new JTextField("0", 10);   // 0 initially, with 10 columns
        totalIncomeField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalIncomeField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalIncomeField, 8, 1);  

        // Row 9 - Total Spending label followed by total spending field
        JLabel totalSpendingLabel = new JLabel("Total Spending");
        addComponent(totalSpendingLabel, 9, 0);

        // set up text box for displaying total spending.  Users can view, but cannot directly edit it
        totalSpendingField = new JTextField("0.00", 10);   // 0 initially, with 10 columns
        totalSpendingField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalSpendingField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalSpendingField, 9, 1); 

        // Row 10 - Total Amount label followed by total income field
        JLabel totalAmountLabel = new JLabel("Total Amount");
        addComponent(totalAmountLabel, 10, 0);

        // set up text box for displaying total amount.  Users can view, but cannot directly edit it
        totalAmountField = new JTextField("0.00", 10);   // 0 initially, with 10 columns
        totalAmountField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        totalAmountField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(totalAmountField, 10, 1);

        // Row 11 - Total weekly amount label followed by total weekly amount field
        JLabel weeklyTotalLabel = new JLabel("Weekly Total");
        addComponent(weeklyTotalLabel, 11, 0);

        // set up text box for displaying weekly total amount.  Users can view, but cannot directly edit it
        weeklyTotalField = new JTextField("0.00", 10);   // 0 initially, with 10 columns
        weeklyTotalField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        weeklyTotalField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(weeklyTotalField, 11, 1);

        // Row 12 - Total month amount label followed by total monthly amount field
        JLabel monthlyTotalLabel = new JLabel("Monthly Total");
        addComponent(monthlyTotalLabel, 12, 0);

        // set up text box for displaying monthly total amount.  Users can view, but cannot directly edit it
        monthlyTotalField = new JTextField("0.00", 10);   // 0 initially, with 10 columns
        monthlyTotalField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        monthlyTotalField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(monthlyTotalField, 12, 1); 

        // Row 13 - Total yearly amount label followed by total yearly amount fieldd
        JLabel yearlyTotalLabel = new JLabel("Annual Total");
        addComponent(yearlyTotalLabel, 13, 0);

        // set up text box for displaying yearly total amount.  Users can view, but cannot directly edit it
        yearlyTotalField = new JTextField("0.00", 10);   // 0 initially, with 10 columns
        yearlyTotalField.setHorizontalAlignment(JTextField.RIGHT) ;    // number is at right end of field
        yearlyTotalField.setEditable(false);    // user cannot directly edit this field (ie, it is read-only)
        addComponent(yearlyTotalField, 13, 1); 

        // Row 14 - Calculate Button
        calculateButton = new JButton("Calculate");
        addComponent(calculateButton, 14, 0);  

        // Row 14 - Undo Button
        undoButton = new JButton("Undo");
        addComponent(undoButton, 14, 1);

        // Row 14 - Exit Button
        exitButton = new JButton("Exit");
        addComponent(exitButton, 14, 2);  

        // set up  listeners (in a spearate method)
        initListeners();
    }

    // set up listeners
    // initially just for buttons, can add listeners for text fields
    public void initListeners() {

        // exitButton - exit program when pressed
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // calculateButton - call calculateTotalIncome() when pressed
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTotalSpending();
                calculateTotalIncome();
                calculateTotal();
            }
        });

        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentState = stateStack.pop();

                // function to check if the state to undo to is different to what the field contains (to reduce doing an insert update again!!!!)
                if(Double.parseDouble(currentState.wages)  != getTextFieldValue(wagesField))
                {
                    wagesField.setText(currentState.wages);
                }

                else if(Double.parseDouble(currentState.loans)  != getTextFieldValue(loansField))
                {
                    loansField.setText(currentState.loans);
                }

                else if(Double.parseDouble(currentState.otherIncome)  != getTextFieldValue(otherIncomeField))
                {
                    otherIncomeField.setText(currentState.otherIncome);
                }

                else if(Double.parseDouble(currentState.rent)  != getTextFieldValue(rentField))
                {
                    rentField.setText(currentState.rent);
                }

                else if(Double.parseDouble(currentState.food)  != getTextFieldValue(foodField))
                {
                    foodField.setText(currentState.food);
                }

                else if(Double.parseDouble(currentState.otherSpending)  != getTextFieldValue(otherSpendingField))
                {
                    otherSpendingField.setText(currentState.otherSpending);
                }

            }
        });

        comboTimePeriod = new JComboBox<String>(timePeriod);
        comboTimePeriod.setMaximumRowCount(4);
        addComponent(comboTimePeriod, 0, 0);
        add(comboTimePeriod);

        comboTimePeriod.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event){
                calculateTimePeriodValues();
            }
        });
        
        DocumentListener textFieldChangeListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent event) {
                calculateTotalIncome();
                calculateTotalSpending();
                calculateTotal();
                calculateTimePeriodValues();
                
                // make function to check if any state is "dirty/has been edited"
                if(isStateChanged())
                {
                    GUIState temp = new GUIState(currentState);
                    stateStack.push(temp);

                    // create update state function that updates the current state to be inline with what's in the text field
                    currentState.wages = wagesField.getText();
                    currentState.loans = loansField.getText();
                    currentState.otherIncome = otherIncomeField.getText();
                    currentState.food = foodField.getText();
                    currentState.rent = rentField.getText();
                    currentState.otherSpending = otherSpendingField.getText();
                }
            }
            @Override
            public void removeUpdate(DocumentEvent event) {
                calculateTotalIncome();
                calculateTotalSpending();
                calculateTotal();
                calculateTimePeriodValues();
                if(Double.parseDouble(currentState.rent) != getTextFieldValue(rentField))
                {
                    previousState.rent = currentState.rent;
                }
            }
            @Override
            public void changedUpdate(DocumentEvent event) {
                calculateTotalIncome();
                calculateTotalSpending();
                calculateTotal();
                calculateTimePeriodValues();
                if(Double.parseDouble(currentState.rent) != getTextFieldValue(rentField))
                {
                    previousState.rent = currentState.rent;
                }
            }
        };

        wagesField.getDocument().addDocumentListener(textFieldChangeListener);
        loansField.getDocument().addDocumentListener(textFieldChangeListener);
        otherIncomeField.getDocument().addDocumentListener(textFieldChangeListener);
        foodField.getDocument().addDocumentListener(textFieldChangeListener);
        rentField.getDocument().addDocumentListener(textFieldChangeListener);
        otherSpendingField.getDocument().addDocumentListener(textFieldChangeListener);
        ((JTextField)comboTimePeriod.getEditor().getEditorComponent()).getDocument().addDocumentListener(textFieldChangeListener);



    }

    public boolean isStateChanged()
    {
        boolean pp = false;

        pp |= Double.parseDouble(currentState.wages) != getTextFieldValue(wagesField);
        pp |= Double.parseDouble(currentState.loans) != getTextFieldValue(loansField);
        pp |= Double.parseDouble(currentState.otherIncome) != getTextFieldValue(otherIncomeField);
        pp |= Double.parseDouble(currentState.food) != getTextFieldValue(foodField);
        pp |= Double.parseDouble(currentState.rent) != getTextFieldValue(rentField);
        pp |= Double.parseDouble(currentState.otherSpending) != getTextFieldValue(otherSpendingField);

        return pp;
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
    
        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(wages) || Double.isNaN(loans) || Double.isNaN(otherIncome)) {
            totalIncomeField.setText("");  // clear total income field
            wages = 0.0;
            return wages;   // exit method and do nothing
        }

        // otherwise calculate total income and update text field
        totalIncome = wages + otherIncome + loans;
        totalIncomeField.setText(String.format("%.2f",totalIncome));  // format with 2 digits after the .
        if (totalIncome < 0){
            totalIncomeField.setForeground(Color.RED);
        }
        return totalIncome;
    }

    public double calculateTotalSpending() {
        double rent = getTextFieldValue(rentField);
        double food = getTextFieldValue(foodField);
        double otherSpending = getTextFieldValue(otherSpendingField);

        if (Double.isNaN(rent) || Double.isNaN(food) || Double.isNaN(otherSpending)){
          totalSpendingField.setText("");
          rent = 0.0;
          return rent;
        }
        totalSpending = rent + food + otherSpending;
        totalSpendingField.setText(String.format("%.2f",totalSpending));  // format with 2 digits after the .

        return totalSpending;
    }

    public double calculateTotal() {
        // clear total field and return if any value is NaN (error)
        if (Double.isNaN(totalSpending) || Double.isNaN(totalIncome)) {
            totalAmountField.setText("");  // clear overall total field
            totalSpending = 0.0;
            return totalSpending;   // exit method and do nothing
        }

        // otherwise calculate overall total and update text field
        double overallTotal = totalIncome - totalSpending;
        totalAmountField.setText(String.format("%.2f",overallTotal));  // format with 2 digits after the .
        if (overallTotal < 0){
            totalAmountField.setForeground(Color.RED);
        }

        return overallTotal;
    }

    public void calculateTimePeriodValues() {
        String dateSelected = (String) comboTimePeriod.getSelectedItem();
        if (dateSelected.equals("Weekly")) {
            comboTimePeriod.setForeground(Color.GREEN);
            weeklyTotalField.setText(String.format("%.2f",(calculateTotal())));
            monthlyTotalField.setText(String.format("%.2f",(calculateTotal() * 4.333333)));
            yearlyTotalField.setText(String.format("%.2f",(calculateTotal() * 52)));
        }
        else if (dateSelected.equals("Monthly")){
            comboTimePeriod.setForeground(Color.GREEN);
            weeklyTotalField.setText(String.format("%.2f",(calculateTotal() / 4.333333)));
            monthlyTotalField.setText(String.format("%.2f",(calculateTotal())));
            yearlyTotalField.setText(String.format("%.2f",(calculateTotal() * 12)));
        }
        else if (dateSelected.equals("Annually")){
            comboTimePeriod.setForeground(Color.GREEN);
            weeklyTotalField.setText(String.format("%.2f",(calculateTotal() / 52)));
            monthlyTotalField.setText(String.format("%.2f",(calculateTotal() / 12)));
            yearlyTotalField.setText(String.format("%.2f",(calculateTotal())));
        }

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