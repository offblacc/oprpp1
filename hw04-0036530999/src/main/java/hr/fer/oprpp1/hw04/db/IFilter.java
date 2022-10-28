package hr.fer.oprpp1.hw04.db;

/**
 * A functional nterface that represents a filter for student records.
 */
public interface IFilter {
    public boolean accepts(StudentRecord record);
}
