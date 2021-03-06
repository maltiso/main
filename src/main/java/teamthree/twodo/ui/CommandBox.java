package teamthree.twodo.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import teamthree.twodo.commons.core.LogsCenter;
import teamthree.twodo.commons.events.logic.NewUserInputEvent;
import teamthree.twodo.commons.events.ui.NewResultAvailableEvent;
import teamthree.twodo.logic.Logic;
import teamthree.twodo.logic.commands.CommandResult;
import teamthree.twodo.logic.commands.exceptions.CommandException;
import teamthree.twodo.logic.parser.exceptions.ParseException;

// Implements the commandbox user interface for the user to interact with the app
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ArrayList<String> previousUserInput;
    private int index;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        this.previousUserInput = logic.getCommandHistory().getHistory();
        index = previousUserInput.size();

    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    //@@author A0162253M
    @FXML
    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode().equals(KeyCode.UP)) {
            accessPreviousCommand();
        } else if (e.getCode().equals(KeyCode.DOWN)) {
            accessNextCommand();
        }
    }

    // Displays the previous command input on the command box if it is available
    private void accessPreviousCommand() {
        if (index > 0) {
            index--;
            commandTextField.clear();
            commandTextField.appendText(previousUserInput.get(index));
        }
    }

    private void accessNextCommand() {
        if (index < previousUserInput.size() - 1) {
            index++;
            commandTextField.clear();
            commandTextField.appendText(previousUserInput.get(index));
        }
    }
    public void setPreviousUserInput(ArrayList<String> newUserInputList) {
        previousUserInput = newUserInputList;
    }

    //Sets the command box style to indicate a successful command.
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    //Sets the command box style to indicate a failed command.
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    public void handleNewUserInputEvent(NewUserInputEvent e) {
        this.setPreviousUserInput(e.userInput);
        index = e.userInput.size();
    }

}
