package teamthree.twodo.logic.commands;

import teamthree.twodo.commons.core.EventsCenter;
import teamthree.twodo.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String COMMAND_WORD_SECOND = "quit";
    public static final String COMMAND_WORD_FAST = "x";
    public static final String COMMAND_WORD_FAST_SECOND = "q";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TaskList as requested ...";

    public static final String MESSAGE_USAGE = ": Exits 2Do.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
