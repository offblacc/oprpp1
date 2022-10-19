package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Class that represents an entire document, inheriting Node.
 */
public class DocumentNode extends Node {

    @Override
    public String toString() {
        return buildDocument(this);
    }

    private String buildDocument(Node node) {
        StringBuilder sb = new StringBuilder();

        if (node instanceof TextNode) {
            sb.append(addEscapeCharacterToTextNode(((TextNode) node).getText()));
        } else if (node instanceof ForLoopNode) {
            sb.append(buildForLoopNode((ForLoopNode) node));
        } else if (node instanceof EchoNode) {
            sb.append(buildEchoNode((EchoNode) node));
        }

        for (int i = 0; i < node.numberOfChildren(); i++) {
            sb.append(buildDocument(node.getChild(i)));
        }
        if (node instanceof ForLoopNode) {
            sb.append("{$END$}");
        }
        return sb.toString();
    }

    private String buildEchoNode(EchoNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append("{$");
        for (Element element : node.getElements()) {
            if (element instanceof ElementString && ((ElementString) element).isTagString())
                sb.append("\"");
            sb.append(addEscapeCharacterToTagString(element));
            if (element instanceof ElementString && ((ElementString) element).isTagString())
                sb.append("\"");
            sb.append(" ");
            
        }
        sb.append("$}");

        return sb.toString();
    }

    private String buildForLoopNode(ForLoopNode node) {
        StringBuilder sb = new StringBuilder();
        sb.append("{$ FOR ");
        sb.append(addEscapeCharacterToTagString(node.getVariable()) + " ");
        sb.append(addEscapeCharacterToTagString(node.getStartExpression()) + " ");
        sb.append(addEscapeCharacterToTagString(node.getEndExpression()) + " ");
        if (node.getStepExpression() != null) {
            sb.append(node.getStepExpression().asText() + " ");
        }
        sb.append("$}");

        return sb.toString();
    }

    private String addEscapeCharacterToTextNode(Element element) {
        if (!(element instanceof ElementString)) {
            return element.asText();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < element.asText().length(); i++) {
            if (element.asText().charAt(i) == '\\') {
                sb.append("\\\\");
            } else if (element.asText().charAt(i) == '{' && i + 1 < element.asText().length()
                    && element.asText().charAt(i + 1) == '$') {
                sb.append("\\");
                sb.append(element.asText().charAt(i));
            } else {
                sb.append(element.asText().charAt(i));
            }
        }
        return sb.toString();

    }

    private String addEscapeCharacterToTagString(Element element) {
        if (!(element instanceof ElementString)) {
            return element.asText();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < element.asText().length(); i++) {
            if (element.asText().charAt(i) == '\\') {
                sb.append("\\\\");
            } else if (element.asText().charAt(i) == '"') {
                sb.append("\\");
                sb.append(element.asText().charAt(i));
            } else {
                sb.append(element.asText().charAt(i));
            }
        }
        return sb.toString();
    }

    // override equals
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DocumentNode documentNode = (DocumentNode) o;
        if (this.numberOfChildren() != documentNode.numberOfChildren())
            return false;
        for (int i = 0; i < this.numberOfChildren(); i++) {
            if (!this.getChild(i).equals(documentNode.getChild(i))) {
                return false;
            }
        }
        return true;
    }
}
