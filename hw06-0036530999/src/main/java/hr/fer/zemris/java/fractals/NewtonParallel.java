package hr.fer.zemris.java.fractals;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.exit;

public class NewtonParallel {
    public static void main(String[] args) {
        HashMap<String, Integer> params = parseArguments(args);
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        int numOfThreads = params.get("workers");
        int numOfTracks = params.get("tracks");
        System.out.println("Using " + numOfThreads + " threads and " + numOfTracks + " tracks.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
        int i = 1;
        Scanner sc = new Scanner(System.in);
        String line;
        List<Complex> complexRoots = new ArrayList<>();
        do {
            System.out.printf("Root " + i++ + " > ");
            line = sc.nextLine();
            try {
                Complex complex = Complex.parse(line);
                complexRoots.add(complex);
            } catch (IllegalArgumentException e) {
                if (!line.equals("done")) {
                    System.out.println("Invalid input, try again.");
                    i--;
                }
            }
        } while (!line.equals("done"));
        if (complexRoots.size() < 2) {
            System.out.println("You must enter at least two roots.");
            exit(1);
        }
        sc.close();
        System.out.println("Image of fractal will appear shortly. Thank you.");
        FractalViewer.show(new MyProducer(complexRoots.toArray(new Complex[0]), numOfThreads, numOfTracks));
    }

    public static class CalculationJob implements Runnable {
        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int m;
        short[] data;
        Complex[] roots;
        AtomicBoolean cancel;
        ComplexRootedPolynomial rootedPolynomial;
        ComplexPolynomial complexPolynomial;
        ComplexPolynomial derived;
        public static CalculationJob NO_JOB = new CalculationJob();

        public CalculationJob() {}

        public CalculationJob(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin, int yMax, int m, short[] data, AtomicBoolean cancel, Complex[] roots) {
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.cancel = cancel;
            this.roots = roots;
            this.rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
            this.complexPolynomial = rootedPolynomial.toComplexPolynomial();
            this.derived = complexPolynomial.derive();
        }

        @Override
        public void run() {
            int offset = 0;
            for (int y = 0; y < height; y++) {
                if (cancel.get()) {
                    System.out.println("Izracun prekinut.");
                    return;
                }
                for (int x = 0; x < width; x++) {
                    double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
                    double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
                    Complex zn = new Complex(cre, cim);
                    int iter = 0;
                    double module;
                    do {
                        Complex numerator = rootedPolynomial.apply(zn);
                        Complex denominator = derived.apply(zn);
                        Complex znold = zn;
                        Complex fraction = numerator.divide(denominator);
                        zn = zn.sub(fraction);
                        module = znold.sub(zn).module();
                        iter++;
                    } while (module > 1E-3 && iter < m);
                    int index = rootedPolynomial.indexOfClosestRootFor(zn, 0.002);
                    data[offset++] = (short) (index + 1);
                }
            }
        }
    }

    public static class MyProducer implements IFractalProducer {
        private int numOfThreads;
        private int numOfTracks;
        private Complex[] roots;

        public MyProducer(Complex[] roots, int numOfThreads, int numOfTracks) {
            this.numOfThreads = numOfThreads;
            this.numOfTracks = numOfTracks;
            this.roots = roots;
        }

        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Zapocinjem izracun...");
            int m = 16 * 16 * 16;
            short[] data = new short[width * height];

            final BlockingQueue<CalculationJob> queue = new LinkedBlockingQueue<>();

            Thread[] workers = new Thread[numOfThreads];
            for (int i = 0; i < numOfThreads; i++) {
                workers[i] = new Thread(() -> {
                    while (true) {
                        CalculationJob job = null;
                        try {
                            job = queue.take();
                            if (job == CalculationJob.NO_JOB) break;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        job.run();
                    }
                });
            }

            for (int i = 0; i < numOfThreads; i++) {
                workers[i].start();
            }

            System.out.println("Number of tracks: " + numOfTracks);
            for (int i = 0; i < numOfTracks; i++) {
                int yMin = i * height / numOfTracks;
                int yMax = (i + 1) * height / numOfTracks - 1;
                if (i == numOfTracks - 1) {
                    yMax = height - 1;
                }
                CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel, roots);
                try {
                    queue.put(job);
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0; i < workers.length; i++) {
                while(true) {
                    try {
                        queue.put(CalculationJob.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for(int i = 0; i < workers.length; i++) {
                while(true) {
                    try {
                        workers[i].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("Izracun gotov...");
            observer.acceptResult(data, (short) (roots.length + 1), requestNo);
        }
    }


    public static HashMap<String, Integer> parseArguments(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-w") || args[i].startsWith("--workers=")) {
                boolean shortForm = args[i].equals("-w");
                if (shortForm && i + 1 < args.length) {
                    try {
                        map.put("workers", Integer.parseInt(args[i + 1]));
                        i++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of workers.");
                        exit(1);
                    }
                } else {
                    try {
                        map.put("workers", Integer.parseInt(args[i].substring(10)));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of workers.");
                        exit(1);
                    }
                }
            } else if (args[i].equals("-t") || args[i].startsWith("--tracks=")) {
                boolean shortForm = args[i].equals("-t");
                if (shortForm && i + 1 < args.length) {
                    try {
                        map.put("tracks", Integer.parseInt(args[i + 1]));
                        i++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of tracks.");
                        exit(1);
                    }
                } else {
                    try {
                        map.put("tracks", Integer.parseInt(args[i].substring(9)));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number of tracks.");
                        exit(1);
                    }
                }
            } else {
                System.out.println("Unknown parameter: " + args[i]);
                exit(1);
            }
        }
        if (!map.containsKey("workers")) {
            map.put("workers", Runtime.getRuntime().availableProcessors());
        }
        if (!map.containsKey("tracks")) {
            map.put("tracks", 4 * map.get("workers"));
        }
        return map;
    }
}
