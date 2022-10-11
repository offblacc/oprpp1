package hr.fer.oprpp1.hw02.prob1;

public class Lexer { // TODO docs
    private char[] data; // ulazni tekst
    private Token token; // trenutni token
    private int currentIndex; // indeks prvog neobrađenog znaka
    // konstruktor prima ulazni tekst koji se tokenizira

    public Lexer(String text) {
        data = text.toCharArray();
        currentIndex = 0;
        token = null;
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

        StringBuilder sb = new StringBuilder();
        if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
            while (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
                if (data[currentIndex] == '\\') {
                    currentIndex++;
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
            return new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
        }

        return token;
    }

    // vraća zadnji generirani token; može se pozivati
    // više puta; ne pokreće generiranje sljedećeg tokena
    public Token getToken() {
        return token;
    }
}