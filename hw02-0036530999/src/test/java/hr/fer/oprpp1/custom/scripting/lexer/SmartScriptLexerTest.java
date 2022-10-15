package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;

public class SmartScriptLexerTest {

    // assert eof
    @Test
    public void testEmptyText() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testBasicText() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This is sample text.", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testBasicTestWithNumbers() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text 123 1.23.");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This is sample text 123 1.23.", lexer.getToken().getValue());

    }

    @Test
    public void testEscapeCharacters() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text \\{ \\\\");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This is sample text { \\", lexer.getToken().getValue());
    }

    @Test
    public void testInvalidEscapeCharacter() {
        var lexer = new SmartScriptLexer("Hello this is text with invalid escape seq \\");
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
        var lexer2 = new SmartScriptLexer("Hello this is text with in\\valid escape seq");
        assertThrows(SmartScriptLexerException.class, () -> lexer2.nextToken());
    }

    @Test
    public void testTextWithOpeningTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text {$");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This is sample text ", lexer.getToken().getValue());
        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
    }

    @Test
    public void testTextWithEchoTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is sample text {$=1$}");

        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This is sample text ", lexer.getToken().getValue());

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.ECHO, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());


        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("1", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementConstantInteger);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);
    }
    
    // TODO FIRST WHEN YOU COME BACK
    /*
     * {$ FOR i-1.35bbb"1" $} -> {$ FOR i -1.35 bbb "1" $}
     * that should be BOUND FOR VAR DOUBLE STRING STRING BOUDN
     * fix (and find out) -> are all strings inside ECHO tag variables or what?
     * and in for tag aswell, how to treat it?
     * the very best of luck
     */

    // new test not yet satisfied, first figure out how strings and vars are treated
    @Test
    public void testWithComplexEchoTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is text {$=1.23 @sin \"0.000\"$}");
        
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("This is text ", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.ECHO, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("1.23", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementConstantDouble);
        
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("@sin", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementFunction);


    }

}