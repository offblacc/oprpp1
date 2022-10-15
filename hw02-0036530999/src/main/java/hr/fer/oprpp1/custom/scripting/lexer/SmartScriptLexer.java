package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.elems.*;

public class SmartScriptLexer {
    private char[] data;
    private int currentIndex;
    private SmartScriptToken token;
    private SmartScriptLexerState state;

    public SmartScriptLexer(String text) {
        if (text == null) {
            throw new SmartScriptLexerException("Text cannot be null.");
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
        if (token != null && token.getType() == SmartScriptTokenType.EOF) { // if last generated token was type EOF
            throw new SmartScriptLexerException("No more tokens available.");
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

        if (Character.isDigit(word.charAt(0))) {
            return parsePotentialNumber(word); // might even just paste that logic here - better put things below in
                                               // methods
        } else {
            if (token.getType() == SmartScriptTokenType.BOUND) {
                if (word.equalsIgnoreCase("FOR")) {
                    token = new SmartScriptToken(new ElementString("FOR"), SmartScriptTokenType.FOR);
                } else if (word.equals("=")) {
                    token = new SmartScriptToken(new ElementString("="), SmartScriptTokenType.ECHO);
                } else if (word.equalsIgnoreCase("END")) {
                    token = new SmartScriptToken(new ElementString("END"), SmartScriptTokenType.END);
                } else {
                    token = new SmartScriptToken(new ElementString(word), SmartScriptTokenType.BASIC);
                }
            } else if (token.getType() == SmartScriptTokenType.FOR || token.getType() == SmartScriptTokenType.ECHO) {
                token = new SmartScriptToken(new ElementVariable(word), SmartScriptTokenType.BASIC);
            } else if (token.getType() == SmartScriptTokenType.BASIC) {
                if (word.charAt(0) == '@') {
                    token = new SmartScriptToken(new ElementFunction(word), SmartScriptTokenType.BASIC);
                } else {
                    if (word.charAt(0) == '"' && word.charAt(word.length() - 1) == '"') {
                        token = new SmartScriptToken(new ElementString(word.substring(1, word.length() - 1)),
                                SmartScriptTokenType.BASIC);
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
        if (data[currentIndex] == '\\') {
            // check if escape seq is valid
            if (currentIndex + 1 == data.length) {
                throw new SmartScriptLexerException("Invalid escape sequence.");
            }
            if (data[currentIndex + 1] == '\"' || data[currentIndex + 1] == '\\') {
                sb.append(data[currentIndex + 1]);
                currentIndex += 2;
            } else {
                throw new SmartScriptLexerException("Invalid escape sequence.");
            }
        }

        if (data[currentIndex] == '"') {
            sb.append(data[currentIndex++]);
            while (currentIndex < data.length && data[currentIndex] != '"') {
                sb.append(data[currentIndex++]);
            }
            if (currentIndex == data.length) {
                throw new SmartScriptLexerException("Invalid string.");
            }
            if (data[currentIndex] == '"') {
                sb.append(data[currentIndex++]);
            }
        } else {
            while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])
                    && data[currentIndex] != '\"') {
                sb.append(data[currentIndex++]);
                if (currentIndex + 1 < data.length) {
                    if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
}
