package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class TestParserManually {

    public static void main(String[] args) {
        String docBody = "Ovo je OK {$ = \"String ide\nu više redaka\nčak tri\" $}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        Node node = parser.getDocumentNode();
        node.printChildrenRecursively(null, node);
    }
}
