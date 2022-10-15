package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementFunction;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

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
    
    
    // {$ FOR i-1.35bbb"1" $} -> {$ FOR i -1.35 bbb "1" $}
     

    @Test
    public void testWithComplexEchoTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is text {$=1.23 @sin\"0.000\"$} još neki tekst.");
        
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

        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("0.000", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals(" još neki tekst.", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }


    @Test
    public void testAnotherEcho() {
        SmartScriptLexer lexer = new SmartScriptLexer("Banana split: \" onda do Rovinja \\{");
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("Banana split: \" onda do Rovinja {", lexer.getToken().getValue());
    }

    // test sa { bez $, ponaša li se normalno ako ne slijedi $?
    @Test
    public void testHalfTagWithoutDollarSign() {
        SmartScriptLexer lexer = new SmartScriptLexer("Testiram nasumični tekst { je usred njega i ovo je sve jedan $ test.");
        
        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("Testiram nasumični tekst { je usred njega i ovo je sve jedan $ test.", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testWithTagAndEndTagAfter() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$=1$}{$END$}");
        
        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.ECHO, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("1", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementConstantInteger);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.END, lexer.nextToken().getType());
        assertEquals("END", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testWithTagAndEndTagAfterAndWhitespace() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$  =    1   $}{$  END \t $}");
        
        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.ECHO, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BASIC, lexer.nextToken().getType());
        assertEquals("1", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementConstantInteger);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("{$", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.END, lexer.nextToken().getType());
        assertEquals("END", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.TAG, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.BOUND, lexer.nextToken().getType());
        assertEquals("$}", lexer.getToken().getValue());
        assertEquals(SmartScriptLexerState.BASIC, lexer.getState());
        assertTrue(lexer.getToken().getElement() instanceof ElementString);

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

}