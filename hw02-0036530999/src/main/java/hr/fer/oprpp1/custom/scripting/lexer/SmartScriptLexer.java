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

        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            return nextToken();
        }

        if (state == SmartScriptLexerStates.BASIC) {
            return nextTokenBasic();
        } else {
            throw new UnknownError("nexttoken tag state not implemented");
            // return null;
            // return nextTokenSpecial();
        }
    }

    private SmartScriptToken nextTokenBasic() {
        if (data[currentIndex] == '{') {
            if (data[currentIndex + 1] == '$') {
                state = SmartScriptLexerStates.TAG;
                currentIndex += 2;
                token = new SmartScriptToken(new ElementString("{$"), SmartScriptTokenType.BOUND);
                return token;
            } else {
                throw new SmartScriptLexerException("Invalid tag bound.");
            }
        }

        // pročitaj sljedeći token
        StringBuilder sb = new StringBuilder();
        while (currentIndex < data.length && data[currentIndex] != '{' && !Character.isWhitespace(data[currentIndex])) {
            sb.append(data[currentIndex]);
            currentIndex++;
        }

        // ustanovi što je sljedeći token
        try {
            double d = Double.parseDouble(sb.toString());
            token = new SmartScriptToken(new ElementConstantDouble(d), SmartScriptTokenType.BASIC);
            return token;
        } catch (NumberFormatException ex) {
            try {
                int i = Integer.parseInt(sb.toString());
                token = new SmartScriptToken(new ElementConstantInteger(i), SmartScriptTokenType.BASIC);
                return token;
            } catch (NumberFormatException ex2) {
                token = new SmartScriptToken(new ElementString(sb.toString()), SmartScriptTokenType.BASIC);
                return token;
            } catch (Exception ex3) {
                throw new UnknownError();
            }
        } catch (Exception ex4) {
            throw new UnknownError();
        }
    }
}
