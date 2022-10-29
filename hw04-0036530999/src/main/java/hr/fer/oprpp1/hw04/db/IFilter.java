package hr.fer.oprpp1.hw04.db;

/**
 * A functional interface that represents a filter for student records.
 */
public interface IFilter {
    boolean accepts(StudentRecord record);
}
