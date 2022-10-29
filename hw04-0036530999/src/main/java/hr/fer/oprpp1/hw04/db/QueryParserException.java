package hr.fer.oprpp1.hw04.db;

public class QueryParserException extends RuntimeException {
    public QueryParserException(String message) {
        super(message);
    }

    public QueryParserException() {
        super();
    }
}
