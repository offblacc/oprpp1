package hr.fer.oprpp1.hw02.prob1;

public class Lexer { // TODO docs
    private char[] data; // ulazni tekst
    private Token token; // trenutni token
    private int currentIndex; // indeks prvog neobrađenog znaka
    LexerState state;
    // konstruktor prima ulazni tekst koji se tokenizira

    public Lexer(String text) {
        data = text.toCharArray();
        currentIndex = 0;
        token = null;
        state = LexerState.BASIC;
    }

    // generira i vraća sljedeći token
    // baca LexerException ako dođe do pogreške
    public Token nextToken() { // TODO lexer exceptions, syntax errors!
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException("No more tokens available.");
        }
        if (currentIndex >= data.length) {
            token = new Token(TokenType.EOF, null);
            return token;
        }
        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            return nextToken();
        }

        /*
         * This block is triggered either when we're in LexerState.BASIC state and need
         * to process # as a symbol and switch states, or when we were in extended state
         * and the following block did not increase currentIndex after finding '#', so
         * we return the WORD we processed in EXTENDED state, then switch states and,
         * since we didn't increase our index, move on to the else block next time this
         * method is called and tokenize '#' as a symbol
         */
        if (state == LexerState.EXTENDED && data[currentIndex] != '#') {
            StringBuilder sb = new StringBuilder();
            while (data[currentIndex] != ' ' && data[currentIndex] != '#') {
                sb.append(data[currentIndex++]);
            }
            if (data[currentIndex] == '#') {
                flipLexerState();
            }
            return new Token(TokenType.WORD, sb.toString());

        } else {

            StringBuilder sb = new StringBuilder();
            if (currentIndex == data.length - 1 && data[currentIndex] == '\\') {
                throw new LexerException("Invalid escape ending.");
            }
            if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
                while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
                    if (data[currentIndex] == '\\') {
                        currentIndex++;
                        if (Character.isLetter(data[currentIndex])) {
                            throw new LexerException("Invalid escape sequence");
                        }
                    }
                    sb.append(data[currentIndex]);
                    if (++currentIndex >= data.length) {
                        break;
                    }
                }
                return new Token(TokenType.WORD, sb.toString());
            } else if (Character.isDigit(data[currentIndex])) { // TODO what if double dot ?
                while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
                    sb.append(data[currentIndex]);
                    if (++currentIndex >= data.length) {
                        break;
                    }
                }
                try {
                    long num = Long.parseLong(sb.toString());
                    return new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
                } catch (NumberFormatException ex) {
                    throw new LexerException("Number cannot be represented as Long");
                }
            } else { // meaning it is a TokenType.SYMBOL
                if (data[currentIndex] == '#') {
                    flipLexerState();
                }
                return new Token(TokenType.SYMBOL, data[currentIndex++]);
            }
        }
    }

    // vraća zadnji generirani token; može se pozivati
    // više puta; ne pokreće generiranje sljedećeg tokena
    public Token getToken() {
        return token;
    }

    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException("Lexer state cannot be null");
        }
        this.state = state;
    }

    private void flipLexerState() {
        if (state == LexerState.BASIC) {
            state = LexerState.EXTENDED;
        } else {
            state = LexerState.BASIC;
        }
    }
}