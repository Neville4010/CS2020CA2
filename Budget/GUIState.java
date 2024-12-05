package Budget;

public class GUIState {
    // String rent food loan... 
    String rent, food, loans, otherSpending, otherIncome, wages;


    // can use either of these constructors to make an object,
    // Default constructor (GUIState()) will set values to 0
    // Copy Constructor (GUIState(GUIState state)) will set values to the object you want to copy to
    GUIState()
    {
        wages = "0";
        loans = "0";
        otherIncome = "0";
        rent = "0";
        food = "0";
        otherSpending = "0";
    }

    GUIState(GUIState state)
    {
        this.wages = state.wages;
        this.loans = state.loans;
        this.otherIncome = state.otherIncome;
        this.rent = state.rent;
        this.food = state.food;
        this.otherSpending = state.otherSpending;
    }

}
