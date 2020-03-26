package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.Budget;

/**
 * Edits the details of an existing fixed expense.
 */
public class EditBudgetCommand extends Command {

    public static final String COMMAND_WORD = "editbudget";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the budget of the current trip"
            + "\nValues must be a number.\n"
            + "Example: " + COMMAND_WORD + " 1000 ";

    public static final String MESSAGE_EDIT_BUDGET_SUCCESS = "Edited Budget: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Budget should not be blank";
    public static final String MESSAGE_WRONG_FORMAT = "Budget should be an integer";

    private final String newBudget;

    /**
     * @param budget details to edit the current budget with
     */
    public EditBudgetCommand(String budget) {
        requireNonNull(budget);
        this.newBudget = budget.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Integer amount;
        try {
            amount = Integer.parseInt(newBudget);

        } catch (NumberFormatException e) {
            return new CommandResult(String.format(MESSAGE_USAGE));
        }

        if (!Budget.isValidBudget(amount)) {
            return new CommandResult(String.format(Budget.MESSAGE_CONSTRAINTS));
        }

        Budget editedBudget = new Budget(amount);
        model.getTripManager().setBudget(editedBudget);
        return new CommandResult(String.format(MESSAGE_EDIT_BUDGET_SUCCESS, amount));
    }


}