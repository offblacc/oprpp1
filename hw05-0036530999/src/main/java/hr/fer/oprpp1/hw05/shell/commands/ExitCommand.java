package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.List;

public class ExitCommand implements ShellCommand {
    public static final String NAME = "exit";
    public static final List<String> DESCRIPTION = List.of(
            "Terminates the shell."
    );
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return ShellStatus.TERMINATE;
    }

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public List<String> getCommandDescription() {
        return DESCRIPTION;
    }
}
