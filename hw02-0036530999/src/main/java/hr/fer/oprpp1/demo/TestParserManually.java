package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParser;

public class TestParserManually {
    public static void main(String[] args) {
        SmartScriptParser p = new SmartScriptParser("Ovo je sve jedan text node");
        p.printDocumentNode();
    }
}
