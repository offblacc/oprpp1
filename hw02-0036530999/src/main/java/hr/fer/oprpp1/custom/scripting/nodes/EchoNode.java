package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Class that represents a command which generates some textual output
 * dynamically, inheriting Node.
 */
public class EchoNode extends Node {
    private Element[] elements;

    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ECHO ");
        for (Element element : elements) {
            sb.append(element.asText() + " ");
        }
        return sb.toString();
    }

    

    
}