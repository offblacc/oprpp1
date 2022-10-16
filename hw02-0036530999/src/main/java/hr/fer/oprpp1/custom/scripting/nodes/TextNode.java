package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.*;

/**
 * Class that represents a text node, inheriting Node.
 */
public class TextNode extends Node {
    private ElementString text;

    public TextNode(ElementString text) {
        this.text = text;
    }

    public ElementString getText() {
        return text;
    }

    @Override
    public String toString() {
        return text.asText();
    }

    
}
