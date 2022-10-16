package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptLexer {
    private char[] data;
    private int currentIndex;
    private SmartScriptToken token;
    private SmartScriptLexerState state;
    private boolean isString = false; // used to flag numbers represented as a string to not be parsed as numbers

    public SmartScriptLexer(String text) {
        if (text == null) {
            throw new SmartScriptParserException("Text cannot be null.");
        }
        data = text.toCharArray();
        currentIndex = 0; // first unread char
        token = null;
        state = SmartScriptLexerState.BASIC;
    }

    public Object getState() {
        return state;
    }

    public SmartScriptToken getToken() {
        return token;
    }

    public SmartScriptToken nextToken() {
        isString = false;
        if (token != null && token.getType() == SmartScriptTokenType.EOF) { // if last generated token was type EOF
            throw new SmartScriptParserException("No more tokens available.");
        }
        if (currentIndex >= data.length) {
            token = new SmartScriptToken(null, SmartScriptTokenType.EOF);
            return token;
        }

        if (state == SmartScriptLexerState.BASIC) {
            return nextTokenBasic();
        } else {
            return nextTokenTag();
        }
    }

    private SmartScriptToken nextTokenTag() {
        String word = readNextToken();
        if (word.equals("$}")) {
            state = SmartScriptLexerState.BASIC;
            token = new SmartScriptToken(new ElementString("$}"), SmartScriptTokenType.BOUND);
            return token;
        }

        if ((Character.isDigit(word.charAt(0)) || word.charAt(0) == '-') && !isString) {
            return parsePotentialNumber(word);
        } else {
            if (token.getType() == SmartScriptTokenType.BOUND) {
                if (word.equalsIgnoreCase("END")) { // TODO end should also be only of tag type
                    token = new SmartScriptToken(new ElementString("END"), SmartScriptTokenType.END);
                } else {
                    token = new SmartScriptToken(new ElementString(word), SmartScriptTokenType.TAG);
                }
            } else if (token.getType() == SmartScriptTokenType.TAG) {
                token = new SmartScriptToken(new ElementString(word), SmartScriptTokenType.BASIC);
            } else if (token.getType() == SmartScriptTokenType.BASIC) {
                if (word.charAt(0) == '@') {
                    token = new SmartScriptToken(new ElementFunction(word), SmartScriptTokenType.BASIC);
                } else {
                    if (isString) {
                        token = new SmartScriptToken(new ElementString(word), SmartScriptTokenType.BASIC);
                    } else {
                        token = new SmartScriptToken(new ElementVariable(word), SmartScriptTokenType.BASIC);
                    }
                }
            }
            return token;
        }
    }

    private SmartScriptToken parsePotentialNumber(String word) {
        int dotCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.toString().charAt(i) == '.') {
                dotCount++;
            }
        }
        if (dotCount == 1) {
            double d = Double.parseDouble(word);
            token = new SmartScriptToken(new ElementConstantDouble(d), SmartScriptTokenType.BASIC);
            return token;
        } else if (dotCount == 0) {
            int i = Integer.parseInt(word);
            token = new SmartScriptToken(new ElementConstantInteger(i), SmartScriptTokenType.BASIC);
            return token;
        } else {
            token = new SmartScriptToken(new ElementString(word), SmartScriptTokenType.BASIC);
            return token;
        }
    }

    // \\ treat as \
    // \{ treat as {
    private SmartScriptToken nextTokenBasic() {
        StringBuilder sb = new StringBuilder();
        if ((data[currentIndex] == '{')) {
            if (data[currentIndex + 1] == '$') {
                state = SmartScriptLexerState.TAG;
                currentIndex += 2;
                token = new SmartScriptToken(new ElementString("{$"), SmartScriptTokenType.BOUND);
                return token;
            }
        }

        while (currentIndex < data.length) {
            if (data[currentIndex] == '\\') {
                if (currentIndex + 1 == data.length) {
                    throw new SmartScriptParserException("Invalid escape sequence.");
                }
                if (data[currentIndex + 1] == '{' || data[currentIndex + 1] == '\\') {
                    sb.append(data[currentIndex + 1]);
                    currentIndex += 2;
                } else {
                    throw new SmartScriptParserException("Invalid escape sequence.");
                }
            } else {
                if (data[currentIndex] == '{') {
                    if (currentIndex + 1 == data.length) {
                        throw new SmartScriptParserException("Invalid escape sequence.");
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

    // Tollerable escape seqs
    // \\ is a single \
    // \" is a single ", and should not be treated as string start/stop
    // any other escaping should result in an exception
    private String readNextToken() {
        while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }

        if (currentIndex + 1 < data.length) {
            if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
                currentIndex += 2;
                return "$}";
            }
        }

        if (data[currentIndex] == '=') {
            currentIndex++;
            return "=";
        }
        StringBuilder sb = new StringBuilder();

        while (currentIndex < data.length) {
            if (data[currentIndex] == '@') {
                while (!Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '"') {
                    sb.append(data[currentIndex++]);
                }
                return sb.toString();
            } else if (data[currentIndex] == '"') {
                isString = true;
                currentIndex++;
                while (data[currentIndex] != '"') {
                    if (data[currentIndex] == '\\') {
                        // ----- escape seq inside string ----------
                        if (currentIndex < data.length - 1
                                && (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '"')) {
                            sb.append(data[currentIndex + 1]);
                            currentIndex += 2;
                        } else {
                            throw new SmartScriptParserException("Invalid escape sequence.");
                        }
                        // -----------------------------------------
                    } else {
                        sb.append(data[currentIndex++]);
                    }
                }
                currentIndex++; // preskoči izlazeći navodnik
                // ako sam ušao u ovaj if, kad sam na ovoj liniji string je završen i treba ga
                // vratiti
                return sb.toString();
            } else if (data[currentIndex] == '\\') {
                throw new SmartScriptParserException("Illegal character.");
            } else {
                while (!Character.isWhitespace(data[currentIndex]) && data[currentIndex] != '$'
                        && data[currentIndex] != '"') {
                    sb.append(data[currentIndex]);
                    currentIndex++;
                }
                return sb.toString();
            }
        }
        throw new UnknownError("Missed something here!");
        // return sb.toString();
    }
}
