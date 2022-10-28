package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.List;

public class StudentDatabase {
    private ArrayList<StudentRecord> records;
    private HashMap<String, StudentRecord> jmbagMap;

    public StudentDatabase(ArrayList<String> lines) {
        records = new ArrayList<>();
        jmbagMap = new HashMap<>();

        for (String line : lines) {
            String[] rows = line.split("\t");
            if (rows.length != 4) {
                throw new IllegalArgumentException("Invalid number of arguments for line: " + line);
            }
            if (!checkGrade(rows[3])) {
                throw new IllegalArgumentException("Invalid grade for line: " + line);
            }
            if (jmbagMap.containsKey(rows[0])) {
                throw new IllegalArgumentException("Jmbag is not unique for line: " + line);
            }
            StudentRecord record = new StudentRecord(rows[0], rows[1], rows[2], Double.parseDouble(rows[3]));
            records.add(record);
            jmbagMap.put(rows[0], record);
        }
    }

    /**
     * Returns the student record with the given jmbag.
     * @param jmbag - jmbag of the student
     * @return student record with the given jmbag
     */
    public StudentRecord forJMBAG(String jmbag) {
        return jmbagMap.getOrDefault(jmbag, null);
    }

    /**
     * Returns a list of all records that satisfy the given filter.
     * @param filter - filter to be satisfied
     * @return - list of all records that satisfy the given filter
     */
    public List<StudentRecord> filter(IFilter filter) {
        return records.stream().filter(filter::accepts).collect(Collectors.toList());
    }

    /**
     * Checks if the provided grade is valid.
     * @param grade - the grade to check
     * @return - true if the grade is valid, false otherwise
     */
    private boolean checkGrade(Object grade) {
        try {
            int gradeInt = Integer.parseInt(grade.toString());
            return gradeInt >= 1 && gradeInt <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
