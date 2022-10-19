package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class TestParserManually {

    public static void main(String[] args) {
        String docBody = "Bok Patriče \\{$Macola {$= kakoJe $} legendo {$ FOR i 1 10 1 $} dijete for-a {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        System.out.println(docBody);
        System.out.println("-------------------------------------------------");
        System.out.println(originalDocumentBody);
        // TODO also test with escape
    }
}
