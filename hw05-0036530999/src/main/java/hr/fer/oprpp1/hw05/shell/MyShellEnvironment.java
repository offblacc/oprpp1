package hr.fer.oprpp1.hw05.shell;

import java.io.*;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyShellEnvironment implements Environment {
    private SortedMap<String, ShellCommand> commands;
    private char multilineSymbol;
    private char promptSymbol;
    private char moreLinesSymbol;
    private final char DEFAULT_PROMPT = '>';
    private final char DEFAULT_MORELINES = '\\';
    private final char DEFAULT_MULTILINE = '|';

    public MyShellEnvironment() {
        this.multilineSymbol = DEFAULT_MULTILINE;
        this.promptSymbol = DEFAULT_PROMPT;
        this.moreLinesSymbol = DEFAULT_MORELINES;
    }


    /**
     * Reads a single line from the standard input. If the line ends with the
     * morelines symbol, the method will continue reading until the line ends
     * without the multiline symbol.
     * @return the line read from the standard input
     * @throws ShellIOException if an error occurs while reading from the standard input
     */
    @Override
    public String readLine() throws ShellIOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();
            sb.append(line);
            while (line.endsWith(String.valueOf(moreLinesSymbol))) {
                line = reader.readLine();
                sb.append(line);
            }
        } catch (Exception e) {
            throw new ShellIOException("Error while reading from input stream.");
        }
        return sb.toString();
    }

    @Override
    public void write(String text) throws ShellIOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(System.out)) {
            writer.write(text);
            writer.flush();
        } catch (Exception e) {
            throw new ShellIOException("Error while writing to output stream.");
        }
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(System.out);) {
            writer.write(text);
            writer.write(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            throw new ShellIOException("Error while writing to output stream.");
        }
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        TreeMap<String, ShellCommand> commands = new TreeMap<>();
        return null; // TODO this is there you stopped in this class
    }

    @Override
    public Character getMultilineSymbol() {
        return null;
    }

    @Override
    public void setMultilineSymbol(Character symbol) {

    }

    @Override
    public Character getPromptSymbol() {
        return null;
    }

    @Override
    public void setPromptSymbol(Character symbol) {

    }

    @Override
    public Character getMorelinesSymbol() {
        return null;
    }

    @Override
    public void setMorelinesSymbol(Character symbol) {

    }
}
