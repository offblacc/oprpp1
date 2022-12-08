package hr.fer.oprpp1.hw05.shell;

import parser.MyShellParser;

import static java.lang.System.exit;

public class MyShell {
    public static void main(String[] args) {
        ShellStatus shellStatus = ShellStatus.CONTINUE;
        Environment env = createEnvironment();
        MyShellParser parser = new MyShellParser(env);

        try {
            env.writeln("Welcome to MyShell v 1.0");
        } catch (ShellIOException e) {
            System.out.println("Error while writing to output stream.");
            exit(1);
        }

        while(shellStatus != ShellStatus.TERMINATE) { // todo change this later, while state is not terminated
            try {
                // trimming - this way the parser knows that if the line contains a \n there is a multiline command
                String line = env.readLine().trim();
                parser.parse(line);
                String commandName = parser.getCommandName();
                String arguments = parser.getArguments();


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
