package teamthree.twodo.logic.commands;

import teamthree.twodo.commons.core.Messages;
import teamthree.twodo.logic.CommandHistory;
import teamthree.twodo.logic.UndoCommandHistory;
import teamthree.twodo.logic.commands.exceptions.CommandException;
import teamthree.twodo.model.Model;
import teamthree.twodo.model.category.CategoryManager;

/**
 * Represents a command with hidden internal logic and the ability to be
 * executed.
 */
public abstract class Command {
    protected Model model;
    protected CategoryManager catMan;
    protected CommandHistory history;
    protected UndoCommandHistory undoHistory;

    /**
     * Constructs a feedback message to summarize an operation that displayed a
     * listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException
     *             If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides dependencies that the command requires.
     * @param undoHistory, history, model
     */
    public void setData(Model model, CommandHistory history,
            UndoCommandHistory undoHistory, CategoryManager catMan) {
        this.model = model;
        this.history = history;
        this.undoHistory = undoHistory;
        this.catMan = catMan;
    }
    public void setData(Model model, CommandHistory history,
            UndoCommandHistory undoHistory) {
        this.model = model;
        this.history = history;
        this.undoHistory = undoHistory;
        this.catMan = null;
    }
}
