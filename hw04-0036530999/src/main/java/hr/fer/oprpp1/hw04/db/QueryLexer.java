package hr.fer.oprpp1.hw04.db;

import java.util.NoSuchElementException;

public class QueryLexer {
    char[] data;
    int currentIndex;
    QueryToken currentToken;
    String word; // current word

    public QueryLexer(String query) {
        data = query.toCharArray();
        currentIndex = 0;
    }

    public QueryToken nextToken() {
        if (currentToken != null && currentToken.getType() == QueryTokenType.EOF) {
            throw new NoSuchElementException("No more tokens.");
        }

        if (currentIndex >= data.length) {
            currentToken = new QueryToken(null, QueryTokenType.EOF);
            return currentToken;
        }
        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            return nextToken();
        }
        word = readNextWord();
        return new QueryToken(word, determineTokenType());

    }

    /**
     * Reads and returns the next word (essentially token's value), whether an
     * operator, a string or a command.
     * 
     * @return the next token's value
     */
    private String readNextWord() {
        StringBuilder sb = new StringBuilder();
        if (Character.isLetter(data[currentIndex])) {
            while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
                sb.append(data[currentIndex++]);
            }
        } else if (data[currentIndex] == '=') {
            sb.append(data[currentIndex++]);
        } else if (data[currentIndex] == '"') {
            sb.append(data[currentIndex++]);
            while (data[currentIndex] != '"') {
                sb.append(data[currentIndex++]);
            }
            sb.append(data[currentIndex++]);
        } else {
            while (!Character.isWhitespace(data[currentIndex])) {
                sb.append(data[currentIndex++]);
            }
        }
        return sb.toString();
    }

    /**
     * After the current word is read by readNextWord() method, this determines the
     * token type based on that word and returns it.
     * 
     * @return - token type based on the current word
     */
    private QueryTokenType determineTokenType() {
        if (word.length() == 1 || word.length() == 2 || word.equals("LIKE")) {
            if (!Character.isLetter(word.charAt(0))) {
                return QueryTokenType.COMPARISION_OPERATOR;
            } else if (word.equals("LIKE")) {
                return QueryTokenType.LIKE;
            }
            throw new UnsupportedOperationException("What broke me bruh");
        } else if (word.equals("query")) {
            return QueryTokenType.QUERYKW;
        } else if (word.startsWith("\"") && word.endsWith("\"")) {
            return QueryTokenType.STRING;
        } else if (word.equals("jmbag")) {
            return QueryTokenType.JMBAG;
        } else if (word.equals("AND")) {
            return QueryTokenType.AND;
        } else if (word.equals("lastName")) {
            return QueryTokenType.LASTNAME;
        } else if (word.equals("firstName")) {
            return QueryTokenType.FIRSTNAME;
        } else if (word.equals("exit")) {
            return QueryTokenType.EXIT;
        }
        throw new UnsupportedOperationException("Could not determine token type.");
    }
}
