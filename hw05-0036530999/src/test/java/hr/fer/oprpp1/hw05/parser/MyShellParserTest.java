package hr.fer.oprpp1.hw05.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyShellParserTest {

    // tests if filenames contianing quotes and backslashes will work
    @Test
    void parseArgumentsSupportingQuotes() {
        String arguments = "\"C:\\\\Users\\\\user\\\\Desktop\\\\test.txt\" \"C:\\\\Users\\\\user\\\\Desktop\\\\test2.txt\"";
        List<String> args = MyShellParser.parseArgumentsSupportingQuotes(arguments);
        assertEquals(2, args.size());
        assertEquals("C:\\Users\\user\\Desktop\\test.txt", args.get(0));
        assertEquals("C:\\Users\\user\\Desktop\\test2.txt", args.get(1));
    }

    @Test
    void processArguments() {
        // quotes are treated like normal characters
        String arguments = "neki \"argumenti\" \"sa\" \"navodnicima\"";
        List<String> args = MyShellParser.parseArgumentsSupportingQuotes(arguments);
        assertEquals(4, args.size());
        assertEquals("neki", args.get(0));
        assertEquals("argumenti", args.get(1));
        assertEquals("sa", args.get(2));
        assertEquals("navodnicima", args.get(3));
    }

    @Test
    void processArguments2() {
        String args = " PROMPT #";
        List<String> arguments = MyShellParser.processArguments(args);
        assertEquals(2, arguments.size());
        assertEquals("PROMPT", arguments.get(0));
        assertEquals("#", arguments.get(1));
    }
}