package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
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
}