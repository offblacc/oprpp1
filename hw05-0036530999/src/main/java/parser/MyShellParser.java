package parser;

import hr.fer.oprpp1.hw05.shell.Environment;

import java.util.List;

public class MyShellParser {
    Environment env;
    String commandName;
    String arguments;

    public MyShellParser(Environment env) {
        this.env = env;
    }

    /**
     * Parses the text into command name and arguments. Converts text to a single line, parsing
     * multiline commands into a single line correspondingly to the environment settings.
     *
     * @param text the text to be parsed
     */
    public void parse(String text) {
        commandName = null; // reset on new parse() call, parsing a new line with the same parser object
        arguments = null;
        // this first StringBuilder usage is to build the command name
        StringBuilder sb = new StringBuilder();
        int i;

        // reading the command name
        for (i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ') {
                commandName = sb.toString();
                sb = new StringBuilder(); // this SB purpose is to transform a multiline command into a single line text
                break;
            } else {
                sb.append(text.charAt(i));
            }
        }
        int argumentsStartingIndex = i + 1;

        // converting to a single line text
        if (text.indexOf('\n') != -1) {
            while (i < text.length()) {
                // the current char is always appended, in case of newline it's the next two that need to be skipped
                // so the only difference between there being a newline or not is the `i` increment
                sb.append(text.charAt(i));
                if (i < text.length() - 2) {
                    if (text.charAt(i) == ' ' && text.charAt(i + 1) == env.getMorelinesSymbol() && text.charAt(i + 2) == '\n') {
                        i += 3;
                    }
                } else {
                    i++;
                }
            }
        }
        arguments = sb.substring(argumentsStartingIndex);
    }

    /**
     * Returns the command name.
     *
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Returns the arguments.
     *
     * @return the arguments
     */
    public String getArguments() {
        return arguments;
    }
}
