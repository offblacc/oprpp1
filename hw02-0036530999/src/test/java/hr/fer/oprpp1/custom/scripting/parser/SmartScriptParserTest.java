package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptParserTest {
    // TODO za primjere{1, 2, 3, 4, 5, 6, 7, 8, 9} assertaj točno jedan TextNode
    // TODO je li END case sensitive?

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
        assertEquals(1, forLoopNode.getStartExpression().asText());
        assertEquals(10, forLoopNode.getEndExpression().asText());
        assertEquals(1, forLoopNode.getStepExpression().asText());
    }

}
