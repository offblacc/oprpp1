package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.List;

public class LSCommand implements ShellCommand {
    public static final String NAME = "ls";
    public static final List<String> DESCRIPTION = List.of(
            "Command takes a single argument – directory – and writes a directory listing (not recursive)."
    );
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        return null;
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
