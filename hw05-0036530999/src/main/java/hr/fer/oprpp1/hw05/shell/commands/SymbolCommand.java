package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

public class SymbolCommand implements ShellCommand {
    public static final String NAME = "symbol";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "Used to change environment symbols.",
            "Currently supports changing prompt, multiline and morelines symbols."
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
