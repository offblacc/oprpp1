package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import javax.swing.text.Document;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;

public class SmartScriptParser {
    private SmartScriptLexer lexer;
    private DocumentNode documentNode;
    private Node currentNode;
    private ObjectStack stack;
    private SmartScriptToken token; // current token

    public SmartScriptParser(String text) {
        System.out.println("Pozvan konstruktor");
        documentNode = new DocumentNode();
        lexer = new SmartScriptLexer(text);
        stack = new ObjectStack();
        parse();
    }
    
    public void parse() {
        while ((token = lexer.nextToken()).getType() != SmartScriptTokenType.EOF) {
            if (token.getType() == SmartScriptTokenType.BASIC && token.getElement() instanceof ElementString) {
                currentNode = new TextNode((ElementString) token.getElement());
                documentNode.addChildNode(currentNode);
            }
        }
    }

    public void printDocumentNode() {
        documentNode.printElementsRecursively();
    }
}
