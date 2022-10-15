package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantDouble;
import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.hw02.prob1.LexerException;
import hr.fer.oprpp1.hw02.prob1.Token;

public class SmartScriptLexer {
    private char[] data;
    private int currentIndex;
    private SmartScriptToken token;
    private SmartScriptLexerStates state;

    public SmartScriptLexer(String text) {
        if (text == null) {
            throw new SmartScriptLexerException("Text cannot be null.");
        }
        data = text.toCharArray();
        currentIndex = 0; // first unread char
        token = null;
        state = SmartScriptLexerStates.BASIC;
    }

    public SmartScriptToken getToken() {
        return token;
    }

    public SmartScriptToken nextToken() {
        if (token != null && token.getType() == SmartScriptTokenType.EOF) { // if last generated token was type EOF
            throw new SmartScriptLexerException("No more tokens available.");
        }
        if (currentIndex >= data.length) {
            token = new SmartScriptToken(null, SmartScriptTokenType.EOF);
            return token;
        }

        if (state == SmartScriptLexerStates.BASIC) {
            return nextTokenBasic();
        } else {
            throw new UnknownError("next token tag state not implemented");
            // return null;
            // return nextTokenSpecial();
        }
    }

    // \\ treat as \
    // \{ treat as {
    private SmartScriptToken nextTokenBasic() {
        // case if we are in basic state and read a tag - go to tag state return tag
        // token with tag bound type
        StringBuilder sb = new StringBuilder();
        if ((data[currentIndex] == '{')) {
            if (data[currentIndex + 1] == '$') {
                state = SmartScriptLexerStates.TAG;
                currentIndex += 2;
                token = new SmartScriptToken(new ElementString("{$"), SmartScriptTokenType.BOUND);
                return token;
            }
        }

        while (currentIndex < data.length) {
            if (data[currentIndex] == '\\') {
                if (currentIndex + 1 == data.length) {
                    throw new SmartScriptLexerException("Invalid escape sequence.");
                }
                if (data[currentIndex + 1] == '{' || data[currentIndex + 1] == '\\') {
                    sb.append(data[currentIndex + 1]);
                    currentIndex += 2;
                } else {
                    throw new SmartScriptLexerException("Invalid escape sequence.");
                }
            } else {
                if (data[currentIndex] == '{') {
                    if (currentIndex + 1 == data.length) {
                        throw new SmartScriptLexerException("Invalid escape sequence.");
                    }
                    if (data[currentIndex + 1] == '$') { // tag encountered, but let the next token handle it, here it
                                                         // just means end of reading this token
                        token = new SmartScriptToken(new ElementString(sb.toString()), SmartScriptTokenType.BASIC);
                        return token;
                    }
                }
                sb.append(data[currentIndex]);
                currentIndex++;
            }
        }
        token = new SmartScriptToken(new ElementString(sb.toString()), SmartScriptTokenType.BASIC);
        return token;
    }

    private SmartScriptToken nextTokenSpecial() {
        StringBuilder sb = new StringBuilder();
        if (!Character.isDigit(sb.toString().charAt(0))) {
            token = new SmartScriptToken(new ElementString(sb.toString()), SmartScriptTokenType.BASIC);
            return token;
        } else {
            int dotCount = 0;
            for (int i = 0; i < sb.length(); i++) {
                if (sb.toString().charAt(i) == '.') {
                    dotCount++;
                }
            }
            if (dotCount == 1) {
                double d = Double.parseDouble(sb.toString());
                token = new SmartScriptToken(new ElementConstantDouble(d), SmartScriptTokenType.BASIC);
                return token;
            } else if (dotCount == 0) {
                int i = Integer.parseInt(sb.toString());
                token = new SmartScriptToken(new ElementConstantInteger(i), SmartScriptTokenType.BASIC);
                return token;
            } else {
                token = new SmartScriptToken(new ElementString(sb.toString()), SmartScriptTokenType.BASIC);
                return token;
            }

        }
        // ustanovi što je sljedeći token
        // TODO in case of a double with a dot and then a dot at the end, fix!
    }
}
