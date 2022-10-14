package hr.fer.oprpp1.custom.scripting.lexer;

import static hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType.*;

import hr.fer.oprpp1.hw02.prob1.LexerException;
import hr.fer.oprpp1.hw02.prob1.Token;

public class SmartScriptLexer {
    private char[] data;
    private int currentIndex;
    private SmartScriptToken token;
    private SmartScriptLexerStates state;

    public SmartScriptLexer(String text) {
        // if (text == null) { // TODO not sure if needed
        // throw new IllegalArgumentException("Text cannot be null.");
        // }
        data = text.toCharArray();
        currentIndex = 0; // first unread char
        token = null;
        state = SmartScriptLexerStates.BASIC;

    }

    public SmartScriptToken getToken() {
        return token;
    }

    public SmartScriptToken nextToken() {
        if (token != null && token.getType() == SmartScriptTokenType.EOF) {
            throw new SmartScriptLexerException("No more tokens available!");
        }
        if (currentIndex >= data.length) {
            return new SmartScriptToken(SmartScriptTokenType.EOF, null);
        }
        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            return nextToken();
        }

        if (data[currentIndex] == '{' || data[currentIndex] == '$') {
            if ((data[currentIndex] == '{' && data[currentIndex + 1] == '$')
                    || (data[currentIndex] == '$' && data[currentIndex + 1] == '}')) {
                token = new SmartScriptToken(TAG,
                        new String(new char[] { data[currentIndex], data[currentIndex + 1] }));
                state = SmartScriptLexerStates.TAG;
                currentIndex += 2;
            } else {
                throw new SmartScriptLexerException("Invalid tag bound");
            }
        } else {
            if (state == SmartScriptLexerStates.BASIC) {
                token = new SmartScriptToken(TEXT, readText());
            } else {
                token = new SmartScriptToken(TAG, readTag());
            }
        }
        return token;
    }

    private String readText() { // TODO are {$}$ legal outside of tags? if so, handle it
        StringBuilder sb = new StringBuilder();
        while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
            if (data[currentIndex] == '{') {
                break;
            }
            sb.append(data[currentIndex++]);
        }
        return sb.toString();
    }

    private String readTag() {
        String id = readText();
        String idLower = id.toLowerCase();
        if (idLower.toLowerCase().equals("for")) {
            state = SmartScriptLexerStates.FOR_LOOP_VAR;
        } else if (id.toLowerCase().equals("=")) {
            state = SmartScriptLexerStates.ECHO;
        }
        return id;
    }
}

// readForLoop()
// read= ili tako nešto
// ima još nešto u special načinu rada?