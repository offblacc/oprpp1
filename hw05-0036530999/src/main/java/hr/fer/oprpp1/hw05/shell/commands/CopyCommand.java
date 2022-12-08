package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

public class CopyCommand implements ShellCommand {
    public static final String NAME = "copy";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "The copy command takes two arguments: source file name and destination file name (i.e. paths and names).",
            "If destination file exists prompts the user whether it should be overwritten.",
            "If the second argument is directory, it is assumed the user wants to copy the",
            "original file into this directory using the original file name.",
            "This command works only with files and does not work with directories."
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
