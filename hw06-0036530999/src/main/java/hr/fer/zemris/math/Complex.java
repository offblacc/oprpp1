package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing complex numbers.
 */
public class Complex {
    /**
     * Real part.
     */
    private double re;

    /**
     * Imaginary part.
     */
    private double im;

    /**
     * Constant representing zero.
     */
    public static final Complex ZERO = new Complex(0, 0);  // TODO should u use this bro?

    /**
     * Constant representing one.
     */
    public static final Complex ONE = new Complex(1, 0);

    /**
     * Constant representing negative one.
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);

    /**
     * Constant representing imaginary unit.
     */
    public static final Complex IM = new Complex(0, 1);

    /**
     * Constant representing negative imaginary unit.
     */
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Default constructor, setting real and imaginary part to zero.
     */
    public Complex() {
        this(0, 0);
    }

    /**
     * Constructor setting real and imaginary part to given values.
     *
     * @param re real part
     * @param im imaginary part
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Returns module of this complex number.
     *
     * @return module of this complex number
     */
    public double module() {
        return Math.sqrt(re * re + im * im);
    }

    /**
     * Returns new complex number which is multiplication of this and given complex number.
     *
     * @param c complex number to multiply with
     * @return new complex number which is multiplication of this and given complex number
     */
    public Complex multiply(Complex c) {
        if (c == null) {
            throw new IllegalArgumentException("Given complex number must not be null.");
        }
        return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }

    /**
     * Returns new complex number which is division of this and given complex number.
     *
     * @param c complex number to divide with
     * @return new complex number which is division of this and given complex number
     */
    public Complex divide(Complex c) {
        if (c == null) {
            throw new IllegalArgumentException("Given complex number must not be null.");
        }
        if (c.equals(ZERO)) {
            throw new IllegalArgumentException("Given complex number must not be zero.");
        }
        Complex numerator = this.multiply(new Complex(c.re, -c.im)); // equal to this * c conjugated
        double denominator = c.re * c.re + c.im * c.im;
        return new Complex(numerator.re / denominator, numerator.im / denominator);
    }

    /**
     * Returns new complex number which is addition of this and given complex number.
     *
     * @param c complex number to add with
     * @return new complex number which is addition of this and given complex number
     */
    public Complex add(Complex c) {
        if (c == null) {
            throw new IllegalArgumentException("Given complex number must not be null.");
        }
        return new Complex(re + c.re, im + c.im);
    }

    /**
     * Returns new complex number which is subtraction of this and given complex number.
     *
     * @param c complex number to subtract with
     * @return new complex number which is subtraction of this and given complex number
     */
    public Complex sub(Complex c) {
        if (c == null) {
            throw new IllegalArgumentException("Given complex number must not be null.");
        }
        return new Complex(re - c.re, im - c.im);
    }

    /**
     * Returns new complex number which is a negative of this complex number.
     *
     * @return new complex number which is a negative of this complex number
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Returns new complex number which is a power of this complex number.
     *
     * @param n power
     * @return new complex number which is a power of this complex number
     */
    public Complex power(int n) { // TODO test with ^0
        if (n < 0) {
            throw new IllegalArgumentException("Given power must be non-negative integer.");
        }
        double module = Math.pow(module(), n);
        double angle;
        if (re == 0) {
            angle = im > 0 ? Math.PI / 2 : -Math.PI / 2;
        } else {
            angle = Math.atan(im / re);
            if (re < 0) {
                angle += Math.PI;
            }
        }
        angle *= n;
        return new Complex(module * Math.cos(angle), module * Math.sin(angle));

    }


    // returns n-th root of this, n is positive integer
    @SuppressWarnings("DuplicatedCode")
    public List<Complex> root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Given root must be positive integer.");
        }
        double module = Math.pow(module(), 1.0 / n);
        double angle;
        if (re == 0) {
            angle = im > 0 ? Math.PI / 2 : -Math.PI / 2;
        } else {
            angle = Math.atan(im / re);
            if (re < 0) {
                angle += Math.PI;
            }
        }
        angle /= n;
        List<Complex> roots = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            roots.add(new Complex(module * Math.cos(angle), module * Math.sin(angle)));
            angle += 2 * Math.PI / n;
        }
        return roots;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (re != 0) {
            sb.append(re);
        }
        if (im != 0) {
            if (im > 0) {
                if (re != 0) {
                    sb.append("+");
                }
            } else {
                sb.append("-");
            }
            if (Math.abs(im) != 1) {
                sb.append(Math.abs(im));
            }
            sb.append("i");
        }
        if (sb.length() == 0) {
            sb.append("0.0");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return Double.compare(complex.re, re) == 0 && Double.compare(complex.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }
}
