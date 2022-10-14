package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.Element;

public class SmartScriptToken {
    Element element;
    SmartScriptTokenType type;

    public SmartScriptToken(Element element, SmartScriptTokenType type) {
        this.element = element;
        this.type = type;
    }

    public SmartScriptTokenType getType() {
        return type;
    }

    public Element getElement() {
        return element;
    }

    public Object getValue() {
        return element.asText();
    }
    
}
