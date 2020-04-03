package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.ActivityManager;
import team.easytravel.model.trip.TripManager;

/**
 * Clears the address book.
 */
public class ClearActivityCommand extends Command {

    /**
     * The constant COMMAND_WORD.
     */
    public static final String COMMAND_WORD = "clearactivity";
    /**
     * The constant MESSAGE_SUCCESS.
     */
    public static final String MESSAGE_SUCCESS = "The activity List has been clear";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.setActivityManager(new ActivityManager());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
