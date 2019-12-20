package ru.mail.skorobogatov.www;

import java.util.Arrays;
import java.util.stream.Collectors;

public class App {
    public static double epsilon = 0.00001;
    public static int iFunc = 0;
    public static int iN = 0;

    public static double fib(int n) {
        return (1.0 / Math.sqrt(5.0) * (Math.pow((1 + Math.sqrt(5.0)) / 2, n) - Math.pow((1 - Math.sqrt(5.0)) / 2, n)));
    }

    public static double func(double x) {
        return (Math.pow(Math.exp(1), x) - x);
    }

    public static void diсhotomy(double a, double b) {
        double aVar = a;
        double bVar = b;
        double delta = epsilon / 5;
        double left;
        double right;
        int i = 1;

        System.out.println("Dichotomy for a = " + a + "; b = " + b);

        while (Math.abs(aVar - bVar) > epsilon) {
            left = (bVar + aVar) / 2 - delta;
            right = (bVar + aVar) / 2 + delta;
            if (func(left) < func(right)) {
                bVar = right;
            } else {
                aVar = left;
            }
            System.out.format("%7d %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f%n", i, aVar, bVar, Math.abs(aVar - bVar),
                    left, right, func(left), func(right));
            i++;
        }
    }

    public static void golden(double a, double b) {
        double aVar = a;
        double bVar = b;
        double left;
        double right;
        int i = 1;

        System.out.println("Golden ration for a = " + a + "; b = " + b);

        while (Math.abs(aVar - bVar) > epsilon) {
            left = aVar + (3 - Math.sqrt(5)) / 2 * (bVar - aVar);
            right = aVar + (Math.sqrt(5) - 1) / 2 * (bVar - aVar);
            if (func(left) < func(right)) {
                bVar = right;
            } else {
                aVar = left;
            }
            System.out.format("%7d %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f%n", i, aVar, bVar, Math.abs(aVar - bVar),
                    left, right, func(left), func(right));
            i++;
        }
    }

    public static void fibonaсci(double a, double b) {
        double aVar = a;
        double bVar = b;
        double left;
        double right;
        int i = 1;
        int n;

        System.out.println("Fibonaсci for a = " + a + "; b = " + b);

        double deltan = (b - a) / epsilon;
        for (n = 1; fib(n + 2) <= deltan; n++)
            ;

        left = aVar + (fib(n) / fib(n + 2) * (bVar - aVar));
        right = aVar + (fib(n + 1) / fib(n + 2) * (bVar - aVar));

        while (i <= n) {
            if (func(left) < func(right)) {
                bVar = right;
                right = left;
                left = aVar + bVar - right;
            } else {
                aVar = left;
                left = right;
                right = bVar - (left - aVar);
            }
            System.out.format("%7d %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f%n", i, aVar, bVar, Math.abs(aVar - bVar),
                    left, right, func(left), func(right));
            i++;
        }
    }

    public static void fmin() {
        double delta = epsilon / 3;
        double x0 = Math.random() * 10 - 5;
        double h;
        double x1, x2, xprev;
        if (func(x0) > func(x0 + delta)) {
            h = delta;
            x1 = x0 + delta;
        } else {
            h = -delta;
            x1 = x0 - delta;
        }

        xprev = x0;
        x2 = x1 + h;

        while (func(x1) > func(x2)) {
            xprev = x1;
            x1 = x2;
            h *= 2;
            x2 = x1 + h;

            System.out.format("\t%7.5f %7.5f %7.5f %7.5f %7.5f%n", h, x1, x2, func(x1), func(x2));
        }

        if (xprev > x2) {
            double a = xprev;
            xprev = x2;
            x2 = a;
        }

        golden(xprev, x2);
    }

    
    public static Double argFunc(Double[] x) {
        iFunc++;
        return Math.pow(x[1] - Math.pow(x[0], 2), 2) + 100 * Math.pow((1 - x[0]), 2);
    }


    public static Double[] grad(Double[] xs, boolean isAnalyt) {
        if (isAnalyt) {
            Double [] x = new Double[2];
            double delta = Math.pow(10,-9);
            Double [] y = {xs[0] - delta, xs[1]};
            Double [] z = {xs[0], xs[1] - delta};
            x[0] = (argFunc(y) - argFunc(xs)) / delta;
            x[1] = (argFunc(z) - argFunc(xs)) / delta;
            return x;
        } else {
            Double [] y = {2 * (200 * Math.pow(xs[0], 3) - 200 * xs[0] * xs[1] + xs[0] - 1),
                            200 * (xs[1] - Math.pow(xs[0], 2))};
            return y;
        }
    }

    public static double direction(Double[] args, Double[] vector, double lambd){
        Double[] params = new Double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            double p = args[i] + lambd * vector[i];
            params[i] = p;
        }
        return argFunc(params);
    }

    public static Double goldenMany(double a, double b, Double[] vector, Double[] starterx) {
        double aVar = a;
        double bVar = b;
        double left = 0;
        double right = 0;

        //System.out.println("Golden ration for a = " + a + "; b = " + b);

        while (Math.abs(aVar - bVar) > epsilon) {
            left = aVar + (3 - Math.sqrt(5)) / 2 * (bVar - aVar);
            right = aVar + (Math.sqrt(5) - 1) / 2 * (bVar - aVar);
            if (direction(starterx, vector, left) < direction(starterx, vector, right)) {
                bVar = right;
            } else {
                aVar = left;
            }
            //System.out.format("%7d %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f %7.5f%n", i, aVar, bVar, Math.abs(aVar - bVar),
            //        left, right, direction(starterx, vector, left), direction(starterx, vector, right));
        }

        return left;
    }

    public static Double fminMany(Double[] fPos,  Double[] grad) {
        double delta = epsilon/3; 
        double x, xprev, xprevprev;
        x = 0;
        if (direction(fPos, grad, 0) < direction(fPos, grad, delta)) {
            delta = -delta;
            x += delta;
        }

        double val = direction(fPos, grad, x);
        double valN = direction(fPos, grad, x + delta);

        xprev = x;
        x += delta;
        xprevprev = 0;

        while (val > valN) {
            xprevprev = xprev;
            xprev = x;
            delta *= 2;
            x += delta;
            val = valN;
            valN = direction(fPos, grad, x + delta);
        }

        if (xprevprev > x) {
            double a = xprevprev;
            xprevprev = x;
            x = a;
        }
        return goldenMany(xprevprev, x, grad, fPos);
    }

    public static void goingDown(Double [] stPoint, boolean isAnalyt, double epsilon) {
        boolean needToTerminate = false;
        Double [] cuPoint = stPoint;
        iFunc = 0;
        iN = 0;
        while(true) {
            iN++;
            Double [] grad = Arrays.stream(grad(cuPoint, isAnalyt)).map(x -> (-x)).collect(Collectors.toList()).toArray(new Double[0]);
            Double result = fminMany(cuPoint, grad);
            Double[] x = new Double[cuPoint.length];
            for (int i = 0; i < cuPoint.length; i++) {
                x[i] = cuPoint[i] + grad[i] * result;
            }
            //System.out.println("Cupoint is" + cuPoint[0] + "   " + cuPoint[1]);
            //for (int i = 0; i < x.length; i++) {
            //    needToTerminate = needToTerminate || Math.abs(x[i] - cuPoint[i]) < epsilon; 
            //}
            if(Math.abs(argFunc(x) - argFunc(cuPoint)) < epsilon || needToTerminate) {
                break;
            }
            cuPoint = x;
        }
        System.out.println("Function was called: " + iFunc);
        System.out.println("Number of iterations: " + iN);
        System.out.println("POINT IS " + argFunc(cuPoint));
        System.out.println("AT ");
        Arrays.stream(cuPoint).forEach(System.out::println);
        System.out.println("\n");
    }

    public static void main( String[] args ) {
        /* x^3 - x , x belongs to [0;1] */
        //diсhotomy(0, 1);
        //System.out.println("\n\n");
        //golden(0, 1);
        //System.out.println("\n\n");
        //fibonaсci(0, 1);
        //System.out.println("\n\n");
        //fmin();
        //System.out.println("\n\n");
        Double[] starter = {-100.0, 100.0};
        for (int i = 0; i < 5; i++) {
            goingDown(starter, true, epsilon);
            epsilon /= 10;
        }
    }
}