package hr.fer.oprpp1.hw02.prob1;

public class Token {
    TokenType type;
    Object value;

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public TokenType getType() {
        return this.type;
    }
}

// TODO document ALL IN HW02/PROB1
