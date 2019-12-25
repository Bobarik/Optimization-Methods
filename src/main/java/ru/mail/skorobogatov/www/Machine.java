package ru.mail.skorobogatov.www;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Machine {

    public final static double leftBorder = 0;
    public final static double rightBorder = 3;
    public final static double epsilon = 0.0000001;
    public final static double diff = rightBorder - leftBorder;
    public final static int datasetSize = 800;

    public static double func(double x) {
        return Math.pow(1.1, x) - 2;
    }

    public static Double[] grad(Double[] xs) {
        Double [] y = {0., 0.};
        for (int i = 0; i < dataset.size(); i++) {
            y[0] -= 2 * (dataset.get(i).get(1) - xs[0] - xs[1] * dataset.get(i).get(0));
            y[1] -= 2 * (dataset.get(i).get(1) - xs[0] - xs[1] * dataset.get(i).get(0)) * dataset.get(i).get(0);
        }
        return y;
    }

    public static List<ArrayList<Double>> dataset;

    public static double makeNoise(double y) {
        Random random = new Random();
        return random.nextGaussian() * 0.03 + y;
    }

    public static double mseFunc(Double[] x) {
        double emp = 0;
        for (int i = 0; i < dataset.size(); i++) {
            emp += Math.pow(dataset.get(i).get(1) - (x[0] + x[1] * dataset.get(i).get(0)), 2);
        }
        return emp;
    }

    public static void createDataset(int datasetMax) {
        dataset = new ArrayList<ArrayList<Double>>();
        Random random = new Random();
        for (int i = 0; i < datasetMax; i++) {
            double x = random.nextDouble() * diff;
            double y = makeNoise(func(x));
            ArrayList<Double> newArraylist = new ArrayList<Double>();
            newArraylist.add(x);
            newArraylist.add(y);
            dataset.add(newArraylist);
        }
    }

    public static double direction(Double[] args, Double[] vector, double lambd){
        Double[] params = new Double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            double p = args[i] + lambd * vector[i];
            params[i] = p;
        }
        return mseFunc(params);
    }

    public static Double goldenMany(double a, double b, Double[] vector, Double[] starterx) {
        double aVar = a;
        double bVar = b;
        double left = 0;
        double right = 0;

        while (Math.abs(aVar - bVar) > epsilon) {
            left = aVar + (3 - Math.sqrt(5)) / 2 * (bVar - aVar);
            right = aVar + (Math.sqrt(5) - 1) / 2 * (bVar - aVar);
            if (direction(starterx, vector, left) < direction(starterx, vector, right)) {
                bVar = right;
            } else {
                aVar = left;
            }
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

    public static Double[] goingDown(Double [] stPoint, boolean isAnalyt, double epsilon) {
        boolean needToTerminate = false;
        Double [] cuPoint = stPoint;
        while(true) {
            Double [] grad = Arrays.stream(grad(cuPoint)).map(x -> (-x)).collect(Collectors.toList()).toArray(new Double[0]);
            Double result = fminMany(cuPoint, grad);
            Double[] x = new Double[cuPoint.length];
            for (int i = 0; i < cuPoint.length; i++) {
                x[i] = cuPoint[i] + grad[i] * result;
            }
            
            if(Math.abs(mseFunc(x) - mseFunc(cuPoint)) < epsilon || needToTerminate) {
                break;
            }
            cuPoint = x;
            System.out.println("POINT IS " + mseFunc(cuPoint));
            System.out.println("AT ");
            Arrays.stream(cuPoint).forEach(System.out::println);
            System.out.println("\n");
        }
        return cuPoint;
    }

    public static void main(String[] args) {
        createDataset(datasetSize);

        Double[] starter = {0.0, 0.0};
        Double[] point = goingDown(starter, true, epsilon);

        double up = 0;
        double down = 0;
        for (int i = 0; i < dataset.size(); i++) {
            double x = dataset.get(i).get(0);
            up += Math.pow(func(x) - point[1] * x - point[0], 2);
            down += Math.pow(dataset.get(i).get(1) - point[1] * x - point[0], 2);;
        }
        System.out.println("DETERMINATION COEFFICIENT IS " + (1 - (up/down)));

        final Plotter plotter = new Plotter("Regression");
        plotter.plotterStart(dataset, point);
    }
}