package ru.mail.skorobogatov.www;

import java.util.ArrayList;
import java.util.Random;

public class Machine {

    public final double leftBorder = -3.;
    public final double rightBorder = 5;
    public final double diff = rightBorder - leftBorder;

    public double func(double x) {
        return x/100 + 1; 
    }

    public ArrayList<ArrayList<Double>> dataset = new ArrayList<ArrayList<Double>>();

    public double makeNoise(double x) {
        Random random = new Random();
        return random.nextGaussian() + x;
    }

    public void createDataset(int datasetMax) {
        Random random = new Random();
        for (int i = 0; i < datasetMax; i++) {
            double x = random.nextDouble() * diff - 1;
            double y = makeNoise(func(x));
            ArrayList<Double> newArraylist = new ArrayList<Double>();
            newArraylist.add(x);
            newArraylist.add(y);
            dataset.add(newArraylist);
        }
    }
}