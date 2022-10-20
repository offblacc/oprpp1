package hr.fer.oprpp1.demo;

import hr.fer.oprpp1.custom.collections.*;

public class z1p6Demo {
    public static void main(String[] args) {
        List col1 = new ArrayIndexedCollection();
        List col2 = new LinkedListIndexedCollection();
        col1.add("Ivana");
        col2.add("Jasna");
        Collection col3 = col1;
        Collection col4 = col2;
        col1.get(0);
        col2.get(0);
        // col3.get(0); // neće se prevesti! Razumijete li zašto? -> Collection ne zna za get(int), stavimo li List umjesto Collection, prevede se
        // col4.get(0); // neće se prevesti! Razumijete li zašto? -> Collection ne zna za get(int), stavimo li List umjesto Collection, prevede se
        col1.forEach(System.out::println); // Ivana
        col2.forEach(System.out::println); // Jasna
        col3.forEach(System.out::println); // Ivana
        col4.forEach(System.out::println); // Jasna
    }
}
