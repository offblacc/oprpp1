package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.scripting.nodes.*;
import hr.fer.oprpp1.custom.scripting.parser.*;

public class TestParserManually {

    public static void main(String[] args) {
        String docBody = "Bok Patriče \\{$Macola {$= kakoJe \"string \\\"test\\\"  \\\\dalje\"$} legendo {$ FOR i 1 10 1 $} dijete for-a {$END$}";
        SmartScriptParser parser = new SmartScriptParser(docBody);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        // now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(same);
    }
}
