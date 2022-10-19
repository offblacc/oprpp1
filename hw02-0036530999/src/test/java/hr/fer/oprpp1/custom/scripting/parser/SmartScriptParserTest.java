package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;

public class SmartScriptParserTest {
    private String readExample(int n) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
            if (is == null)
                throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch (IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

    // ----------------------------------------------------------
    // this part of tests only asserts if there is 1 text node
    // ----------------------------------------------------------
    // actual parser functionality will be tested later
    // ----------------------------------------------------------
    @Test
    public void testExample1() {
        String docBody = readExample(1);
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertEquals(1, parser.countTextNodesRecursively(parser.getDocumentNode()));
    }

    @Test
    public void testExample2() {
        String docBody = readExample(2);
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertEquals(1, parser.countTextNodesRecursively(parser.getDocumentNode()));
    }

    @Test
    public void testExample3() {
        String docBody = readExample(3);
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertEquals(1, parser.countTextNodesRecursively(parser.getDocumentNode()));
    }

    @Test
    public void testExample4() {
        String docBody = readExample(4);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));

    }

    @Test
    public void testExample5() {
        String docBody = readExample(5);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));

    }

    @Test
    public void testExample6() {
        String docBody = readExample(6);
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertEquals(1, parser.countTextNodesRecursively(parser.getDocumentNode()));
    }

    @Test
    public void testExample7() {
        String docBody = readExample(7);
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertEquals(1, parser.countTextNodesRecursively(parser.getDocumentNode()));
    }

    @Test
    public void testExample8() {
        String docBody = readExample(8);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testExample9() {
        String docBody = readExample(9);
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    // ----------------------------------------------------------

    @Test
    public void testForLoopNode() {
        String docBody = "{$ FOR i 1 10 1 $} text {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        ForLoopNode forLoopNode = (ForLoopNode) parser.getDocumentNode().getChild(0);
        assertEquals("i", forLoopNode.getVariable().asText());
        assertEquals("1", forLoopNode.getStartExpression().asText());
        assertEquals("10", forLoopNode.getEndExpression().asText());
        assertEquals("1", forLoopNode.getStepExpression().asText());
        assertEquals(" text ", forLoopNode.getChild(0).toString());
    }

    @Test
    public void testForLoopNodeTooManyArguments() {
        String docBody = "{$ FOR i 1 10 1 1 $} text {$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testForLoopNodeTooFewArguments() {
        String docBody = "{$ FOR i 1 $} text {$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testForLoopNodeWithStringArguments() {
        String docBody = "{$ FOR i \"1\" \"10\" \"1\" $} text {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        ForLoopNode forLoopNode = (ForLoopNode) parser.getDocumentNode().getChild(0);

        assertEquals("i", forLoopNode.getVariable().asText());
        assertTrue(forLoopNode.getVariable() instanceof ElementVariable);

        assertEquals("1", forLoopNode.getStartExpression().asText());
        assertTrue(forLoopNode.getStartExpression() instanceof ElementConstantInteger);

        assertEquals("10", forLoopNode.getEndExpression().asText());
        assertTrue(forLoopNode.getStartExpression() instanceof ElementConstantInteger);

        assertEquals("1", forLoopNode.getStepExpression().asText());
        assertTrue(forLoopNode.getStartExpression() instanceof ElementConstantInteger);

        assertEquals(" text ", forLoopNode.getChild(0).toString());
    }

    @Test
    public void testForLoopWithNumberAsVariable() {
        String docBody = "{$ FOR 1 1 10 1 $} text {$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testForLoopWithNoEndTag() {
        String docBody = "{$ FOR i 1 10 1 $} text";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testForLoopTwoEndTags() {
        String docBody = "{$ FOR i 1 10 1 $} text {$END$}{$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testOnlyEndTag() {
        String docBody = "{$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));

    }

    @Test
    public void testUnclosedForTag() {
        String docBody = "{$ FOR i 1 10 1 $}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));

    }

    @Test
    public void testUnclosedForTagAndText() {
        String docBody = "{$ FOR i 1 10 1 $} text";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testUnclosedForTagAndTextBeforeAndAfterTag() {
        String docBody = "neki tekst {$ FOR i 1 10 1 $} text";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testUnclosedEchoTag() {
        String docBody = "{$= i";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testEchoTagWithEndTag() {
        String docBody = "{$= i $}{$END$}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testEndTagWithText() {
        String docBody = "{$END$} text";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testEndTagWithEchoTag() {
        String docBody = "{$END$}{$= i $}";
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(docBody));
    }

    @Test
    public void testEscapePartialTag() {
        String docBody = "\\{= i $}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertEquals("\\{= i $}", parser.getDocumentNode().toString());
    }

    @Test
    public void testEscapeForTag() {
        String docBody = "\\{$ FOR i 1 10 1 $} text";
        assertEquals("\\{$ FOR i 1 10 1 $} text", new SmartScriptParser(docBody).getDocumentNode().toString());
    }

    @Test
    public void testEscapeEchoTag() {
        String docBody = "\\{$= i $}";
        assertEquals("\\{$= i $}", new SmartScriptParser(docBody).getDocumentNode().toString());
    }

    @Test
    public void testExamplePDF() {
        String docBody = "This is sample text.\n{$ FOR i 1 10 1 $}\n This is {$= i $}-th time this message is generated.\n{$END$}\n{$FOR i 0 10 2 $}\n sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n{$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        System.out.println(parser.getDocumentNode().toString());
        System.out.println("----------");
        assertTrue(parser.getDocumentNode().toString().equals(new SmartScriptParser(parser.getDocumentNode().toString()).getDocumentNode().toString()));}

    //test out of bounds checks for { tag, if it starts a tag or is just plain text

    @Test
    public void testFakeTagOpenThoroughly1() {
        String docBody = "{{This is {oh, it's, not, { again, not {";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertTrue(parser.getDocumentNode().toString().equals(new SmartScriptParser(parser.getDocumentNode().toString()).getDocumentNode().toString()));
    }

    @Test
    public void testFakeTagOpenThoroughly() {
        String docBody = "{{This is {oh, it's, not, { again, not {{{";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        assertTrue(parser.getDocumentNode().toString().equals(new SmartScriptParser(parser.getDocumentNode().toString()).getDocumentNode().toString()));
    }

    
}
