package ru.mail.skorobogatov.www;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Machine {

    public final double leftBorder = -1.;
    public final double rightBorder = 0.5;
    public final double diff = rightBorder - leftBorder;

    public double func(double x) {
        return Math.pow(x, 2.) - 1; 
    }

    public Map<Double, Double> dataset = new HashMap<Double, Double>();

    public double makeNoise(double x, double dif) {
        
    }

    public void createDataset(int datasetMax) {
        Random random = new Random();
        for (int i = 0; i < datasetMax; i++) {
            double x = random.nextDouble() * diff - 1;
            double y = func(x);
            dataset.put(x, y);
        }
    }
}