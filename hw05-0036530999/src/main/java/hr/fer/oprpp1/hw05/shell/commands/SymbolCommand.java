package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.parser.MyShellParser;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;

public class SymbolCommand implements ShellCommand {
    public static final String NAME = "symbol";
    public static final List<String> DESCRIPTION = Arrays.asList(
            "Used to change environment symbols.",
            "Currently supports changing prompt, multiline and morelines symbols."
    );

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        List<String> args = MyShellParser.processArguments(arguments);
        if (args.size() != 2) {
            try {
                env.writeln("Expected 2 arguments.");
            } catch (ShellIOException e) {
                exit(1);
            }
            return ShellStatus.CONTINUE;
        }

        int symbolIndex = 0;
        if (List.of(new String[]{"MORELINES", "PROMPT", "MULTILINE"}).contains(args.get(0))) {
            symbolIndex = 1; // the symbol is at index one
        }

        switch (args.get(1 - symbolIndex)) {
            case "PROMPT":
                env.setPromptSymbol(args.get(symbolIndex).charAt(0));
            case "MORELINES":
                env.setMorelinesSymbol(args.get(symbolIndex).charAt(0));
            case "MULTILINE":
                env.setMultilineSymbol(args.get(symbolIndex).charAt(0));
        }

        try {
            env.writeln("Symbol for " + args.get(1 - symbolIndex) + " changed to '" + args.get(symbolIndex) + "'");
        } catch (ShellIOException e) {
            exit(1);
        }

        return ShellStatus.CONTINUE;
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
