package hr.fer.zemris.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexRootedPolynomialTest {
    @Test
    public void indexOfClosestRootForTest() {
        ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
        assertEquals(0, polynomial.indexOfClosestRootFor(Complex.ONE, 0.1));
        assertEquals(1, polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
        assertEquals(2, polynomial.indexOfClosestRootFor(Complex.IM, 0.1));
        assertEquals(3, polynomial.indexOfClosestRootFor(Complex.IM_NEG, 0.1));
        assertEquals(0, polynomial.indexOfClosestRootFor(Complex.ONE, 0.1));
        assertEquals(1, polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
        assertEquals(2, polynomial.indexOfClosestRootFor(Complex.IM, 0.1));
        assertEquals(3, polynomial.indexOfClosestRootFor(Complex.IM_NEG, 0.1));
        assertEquals(0, polynomial.indexOfClosestRootFor(Complex.ONE, 0.1));
        assertEquals(1, polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
        assertEquals(2, polynomial.indexOfClosestRootFor(Complex.IM, 0.1));
        assertEquals(3, polynomial.indexOfClosestRootFor(Complex.IM_NEG, 0.1));
        assertEquals(0, polynomial.indexOfClosestRootFor(Complex.ONE, 0.1));
        assertEquals(1, polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
        assertEquals(2, polynomial.indexOfClosestRootFor(Complex.IM, 0.1));
        assertEquals(3, polynomial.indexOfClosestRootFor(Complex.IM_NEG, 0.1));
        assertEquals(0, polynomial.indexOfClosestRootFor(Complex.ONE, 0.1));
        assertEquals(1, polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
        assertEquals(2, polynomial.indexOfClosestRootFor(Complex.IM, 0.1));
        assertEquals(3, polynomial.indexOfClosestRootFor(Complex.IM_NEG, 0.1));
        assertEquals(0, polynomial.indexOfClosestRootFor(Complex.ONE, 0.1));
        assertEquals(1, polynomial.indexOfClosestRootFor(Complex.ONE_NEG, 0.1));
        assertEquals(2, polynomial.indexOfClosestRootFor(Complex.IM, 0.1));
    }
}