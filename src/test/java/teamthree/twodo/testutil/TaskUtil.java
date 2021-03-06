package teamthree.twodo.testutil;

import static teamthree.twodo.logic.parser.CliSyntax.PREFIX_DEADLINE_END;
import static teamthree.twodo.logic.parser.CliSyntax.PREFIX_DEADLINE_START;
import static teamthree.twodo.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static teamthree.twodo.logic.parser.CliSyntax.PREFIX_NAME;
import static teamthree.twodo.logic.parser.CliSyntax.PREFIX_TAG;

import teamthree.twodo.logic.commands.AddCommand;
import teamthree.twodo.model.task.Task;
import teamthree.twodo.model.task.TaskWithDeadline;

/**
 * A utility class for Task.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(Task task) {
        return AddCommand.COMMAND_WORD + " " + gettaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    private static String gettaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription().value + " ");
        task.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        if (task instanceof TaskWithDeadline) {
            sb.append(PREFIX_DEADLINE_START + task.getDeadline().get().getStartDate().toString() + " "
                    + PREFIX_DEADLINE_END + task.getDeadline().get().getEndDate().toString() + " ");
        }
        return sb.toString();
    }
}
