package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class TestParserManually {

    public static void main(String[] args) {
        String docBody = "Neki random test {$ FOR i 1 10 1 $} text {$END$} {$= i $} {$ FOR i 0 10 2 $} text {$END$} {$= i $}";
        docBody = "{$ FOR aaaaaa 1 10 1 $} text {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        System.out.println(docBody);
        System.out.println("-------------------------------------------------");
        System.out.println(originalDocumentBody);
        // TODO also test with escape
    }
}
