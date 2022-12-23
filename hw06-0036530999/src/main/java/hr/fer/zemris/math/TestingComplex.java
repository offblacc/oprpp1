package hr.fer.zemris.math;

public class TestingComplex {
    public static void main(String[] args) {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
        );
        ComplexPolynomial cp = crp.toComplexPolynomial();
        System.out.println(crp);
        System.out.println(cp);
        System.out.println(cp.derive());
    }
}
