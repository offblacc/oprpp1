package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;

public class SmartScriptParser {
    private SmartScriptLexer lexer;
    private DocumentNode documentNode;
    private ObjectStack stack;
    private SmartScriptToken token;
    private SmartScriptParserState parserState;

    public SmartScriptParser(String text) {
        documentNode = new DocumentNode();
        lexer = new SmartScriptLexer(text);
        stack = new ObjectStack();
        parserState = SmartScriptParserState.INIT;
        parse();
    }

    public void parse() {
        stack.push(documentNode);
        while ((token = lexer.nextToken()).getType() != SmartScriptTokenType.EOF) {
            if (parserState == SmartScriptParserState.INIT) {
                if (token.getType() == SmartScriptTokenType.BASIC) {
                    ((Node) stack.peek()).addChildNode(new TextNode((ElementString) token.getElement()));
                } else if (token.getType() == SmartScriptTokenType.BOUND) {
                    parserState = SmartScriptParserState.EXPECT_TAG_NAME; // switch state and go on to the next token
                } else {
                    throw new SmartScriptParserException("Unknown parsing exception.");
                }
            } else if (parserState == SmartScriptParserState.EXPECT_TAG_NAME) {
                processTag();
            } else if (parserState == SmartScriptParserState.UNCLOSED_FOR_TAG) {
                if (token.getValue().equals("$}")) {
                    parserState = SmartScriptParserState.INIT;
                } else {
                    throw new SmartScriptParserException("Unclosed for loop tag.");
                }
            }
        }
        if (parserState != SmartScriptParserState.INIT) {
            throw new SmartScriptParserException("Parsing error. Possible unclosed tags.");
        }
    }

    private void processTag() {
        String tagName = token.getValue().toString();
        if (isForTag(tagName)) {
            processForLoop();
        } else if (isValidTagName(tagName)) {
            if (isEndTag(tagName)) {
                processEndTag();
            } else if (tagName.equals("=")) {
                processNamedTag();
            } else {
                throw new UnsupportedOperationException("Undefined behaviour for tag name: " + tagName);
            }
        }
    }

    private void processNamedTag() {
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        while (!token.getValue().equals("$}")) {
            throwExceptionIfEOF(token);
            elements.add(token.getElement());
            token = lexer.nextToken();
        }

        Element[] elementsArray = new Element[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            elementsArray[i] = (Element) elements.get(i);
        }

        EchoNode echoNode = new EchoNode(elementsArray);
        ((Node) stack.peek()).addChildNode(echoNode);
        parserState = SmartScriptParserState.INIT;
    }

    private void processForLoop() {
        ElementVariable var = (ElementVariable) (token = lexer.nextToken()).getElement();
        throwExceptionIfEOF(token);
        throwExceptionIfEndBound(var);
        throwExceptionIfInvalidVariableName(var.asText());

        Element startExpression = (token = lexer.nextToken()).getElement();
        throwExceptionIfEOF(token);
        throwExceptionIfEndBound(startExpression);
        throwExceptionIfInvalidForLoopParameter(startExpression);

        Element endExpression = (token = lexer.nextToken()).getElement();
        throwExceptionIfEOF(token);
        throwExceptionIfEndBound(endExpression);
        throwExceptionIfInvalidForLoopParameter(endExpression);

        Element stepExpression = (token = lexer.nextToken()).getElement();
        throwExceptionIfEOF(token);
        throwExceptionIfInvalidStepExpression(stepExpression);
        if (isEndBound(stepExpression)) {
            parserState = SmartScriptParserState.INIT; // because we exited the for loop tag
            stepExpression = null;
        } else {
            parserState = SmartScriptParserState.UNCLOSED_FOR_TAG;
        }

        ForLoopNode forLoopNode = new ForLoopNode(var, startExpression, endExpression, stepExpression);
        ((Node) stack.peek()).addChildNode(forLoopNode);
    }

    private void processEndTag() {
        try {
            stack.pop();
        } catch (EmptyStackException ex) {
            throw new SmartScriptParserException("There are extra END tags.");
        }

        while (token.getValue() != "$}") {
            if ((token = lexer.nextToken()).getType() == SmartScriptTokenType.EOF) {
                throw new SmartScriptParserException("End tag has no right bound.");
            }
        }
        parserState = SmartScriptParserState.INIT;
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
        return isValidVariableName(variableName) || (variableName.length() == 1 && variableName.equals("="));
    }

    private static boolean isForTag(String tagName) {
        return tagName.equalsIgnoreCase("for");
    }

    private static boolean isEndTag(String tagName) {
        return tagName.equalsIgnoreCase("end");
    }

    private static boolean isEndBound(Element element) {
        return element.asText().equals("$}");
    }

    private static boolean isValidForLoopParameter(Element element) {
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

    private static void throwExceptionIfEndBound(Element element) {
        if (isEndBound(element)) {
            throw new SmartScriptParserException("Not enough for loop parameters.");
        }
    }

    private static void throwExceptionIfInvalidForLoopParameter(Element element) {
        if (!isValidForLoopParameter(element)) {
            throw new SmartScriptParserException("Invalid for loop parameter");
        }
    }

    private static void throwExceptionIfInvalidVariableName(String name) {
        if (!isValidVariableName(name)) {
            throw new SmartScriptParserException(
                    String.format("Invalid variable name %s inside for loop", name));
        }
    }

    private static void throwExceptionIfEOF(SmartScriptToken token) {
        if (token.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptParserException("Unexpected end of file.");
        }
    }

    private static void throwExceptionIfInvalidStepExpression(Element element) {
        if (!isStepExpressionOrEndBound(element)) {
            throw new SmartScriptParserException("Invalid for loop parameter");
        }
    }

    private static boolean isStepExpressionOrEndBound(Element element) {
        return isValidForLoopParameter(element) || isEndBound(element);
    }

    /**
     * Used for testing purposes
     * 
     * param node - root node, count all children
     * 
     * @return
     */
    public int countAllNodesRecursively(Node node) {
        int nodes = 0;
        for (int i = 0; i < node.numberOfChildren(); i++) {
            nodes += countAllNodesRecursively(node.getChild(i));
        }
        return node.numberOfChildren();
    }

    public DocumentNode getDocumentNode() {
        return documentNode;
    }

    public int countTextNodesRecursively(Node node) {
        int textNodes = 0;
        for (int i = 0; i < node.numberOfChildren(); i++) {
            textNodes += countTextNodesRecursively(node.getChild(i));
        }
        if (node instanceof TextNode) {
            textNodes++;
        }
        return textNodes;
    }
}
