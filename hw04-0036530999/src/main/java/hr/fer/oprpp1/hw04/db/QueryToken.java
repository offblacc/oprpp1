package hr.fer.oprpp1.hw04.db;

public class QueryToken {
    String value;
    QueryTokenType type;

    public QueryToken(String value, QueryTokenType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public QueryTokenType getType() {
        return type;
    }
}
