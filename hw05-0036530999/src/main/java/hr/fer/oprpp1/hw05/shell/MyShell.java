package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.parser.MyShellParser;

import java.util.SortedMap;

import static java.lang.System.exit;

public class MyShell {
    public static void main(String[] args) {
        ShellStatus shellStatus = ShellStatus.CONTINUE;
        Environment env = createEnvironment();
        MyShellParser parser = new MyShellParser(env);
        SortedMap<String, ShellCommand> commands = env.commands();

        try {
            env.writeln("Welcome to MyShell v1.0");
        } catch (ShellIOException e) {
            System.out.println("Error while writing to output stream.");
            exit(1);
        }

        while(shellStatus != ShellStatus.TERMINATE) { // todo change this later, while state is not terminated
            try {
                env.write(env.getPromptSymbol() + " ");
                // trimming - this way the parser knows that if the line contains a \n there is a multiline command
                String line = env.readLine().trim();
                parser.parse(line);
                String commandName = parser.getCommandName();
                String arguments = parser.getArguments();
                ShellCommand command = commands.get(commandName);
                if (command == null) {
                    env.writeln("Command not found.");
                } else {
                    shellStatus = command.executeCommand(env, arguments);
                }
            } catch (ShellIOException e) {
                System.out.println("Error while reading from input stream. Terminating shell.");
                exit(1);
            }
        }
    }


    private static Environment createEnvironment() {
        return new MyShellEnvironment();
    }
}
