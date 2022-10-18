package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import hr.fer.oprpp1.custom.collections.ObjectStack; // TODO will need others
import hr.fer.oprpp1.custom.scripting.elems.*;

public class SmartScriptParser {
    private SmartScriptLexer lexer;
    private DocumentNode documentNode;
    private Node currentNode;
    private ObjectStack stack;
    private SmartScriptToken token; // current token
    private SmartScriptParserState parserState;
    private String currentTagName;

    public SmartScriptParser(String text) {
        System.out.println("Pozvan konstruktor");
        documentNode = new DocumentNode();
        lexer = new SmartScriptLexer(text);
        stack = new ObjectStack();
        parserState = SmartScriptParserState.INIT;
        parse();
    }

    public void parse() {
        stack.push(documentNode);
        while ((token = lexer.nextToken()).getType() != SmartScriptTokenType.EOF) {
            if (parserState == SmartScriptParserState.INIT) { // INIT state - outside of tags; expecting text or {$ (tag
                                                              // opening)
                if (token.getType() == SmartScriptTokenType.BASIC) {
                    ((Node) stack.peek()).addChildNode(new TextNode((ElementString) token.getElement()));
                } else if (token.getType() == SmartScriptTokenType.BOUND) {
                    parserState = SmartScriptParserState.EXPECT_TAG_NAME; // switch state and go on to the next token
                } else {
                    throw new SmartScriptParserException("Unknown parsing exception.");
                }
            } else if (parserState == SmartScriptParserState.EXPECT_TAG_NAME) {
                String tagName = token.getValue().toString();
                if (isValidTagName(tagName)) {
                    if (isForTag(tagName)) {
                        processForLoop();
                    } else {
                        processNamedTag();
                    }
                }
            }
        }
        if (parserState != SmartScriptParserState.INIT) {
            throw new SmartScriptParserException("Parsing error. Possible unclosed tags.");
        }
    }

    private void processForLoop() {
        ElementVariable var = (ElementVariable) (token = lexer.nextToken()).getElement();

        if (!isValidVariableName(var.asText())) {
            throw new SmartScriptParserException(
                    String.format("Invalid variable name %s inside for loop", var.asText()));
        }

        if (!canBeForLoopParameter((token = lexer.nextToken()).getElement())) {
            throw new SmartScriptParserException("For loop starting integer parsing error.");
        }
        int startInteger = (int) token.getValue();
        
        if (!canBeForLoopParameter((token = lexer.nextToken()).getElement())) {
            throw new SmartScriptParserException("For loop end integer parsing error.");
        }
        int endInteger = (int) token.getValue();

        if (!canBeForLoopParameter((token = lexer.nextToken()).getElement())) {
            if (token.getValue() != "$}") {
                throw new SmartScriptParserException("For loop end integer parsing error.");
            } else {
                parserState = SmartScriptParserState.INIT;
            }
        }
        int stepInteger = (int) token.getValue();

        parserState = SmartScriptParserState.UNCLOSED_FOR_TAG; // will check if next token is closing tag bound, else
                                                               // exception, too many arguments

    }

    private void processNamedTag() {
        throw new UnsupportedOperationException("processNamedTag not implemented"); // TODO implement
    }

    private static boolean isValidVariableName(String variableName) {
        if (variableName.length() == 0)
            return false;
        if (!Character.isLetter(variableName.charAt(0)))
            return false;
        for (int i = 1; i < variableName.length(); i++) {
            char c = variableName.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidTagName(String variableName) {
        if (variableName.length() == 1 && variableName.equals("="))
            return true;
        return isValidTagName(variableName);
    }

    private static boolean isForTag(String tagName) {
        return tagName.equalsIgnoreCase("for");
    }

    private static boolean canBeForLoopParameter(Element element) {
        if (element instanceof ElementVariable)
            return true;
        if (element instanceof ElementConstantInteger)
            return true;
        if (element instanceof ElementConstantDouble)
            return true;
        if (element instanceof ElementString)
            return true;
        return false;
    }
}
