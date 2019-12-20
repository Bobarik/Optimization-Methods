package ru.mail.skorobogatov.www;

public class Newton {
    public static int iteration;
    public static double func(double[] x) {
        return Math.pow(x[1] - Math.pow(x[0], 2), 2) + 100 * Math.pow((1 - x[0]), 2);
    }

    public static double[] grad(double[] xs, boolean isAnalyt) {
        if (!isAnalyt) {
            double [] x = new double[2];
            x[0] = dx(xs, false);
            x[1] = dy(xs, false);
            return x;
        } else {
            double [] y = {dx(xs, true),
                           dy(xs, true)};
            return y;
        }
    }

    public static double dx (double[] xs, boolean isAnalyt) {
        if(isAnalyt) {
            return 4 * (-50 + Math.pow(xs[0], 3) - xs[0] * (xs[1] - 50));
        } else {
            double x;
            double delta = Math.pow(10, -10);
            double [] y = {xs[0] - delta, xs[1]};
            x = (func(xs) - func(y)) / delta;
            return x;
        }
    }

    public static double dy (double[] xs, boolean isAnalyt) {
        if(isAnalyt) {
            return 2 * (xs[1] - Math.pow(xs[0], 2));
        } else {
            double x;
            double delta = Math.pow(10, -10);
            double [] z = {xs[0], xs[1] - delta};
            x = (func(xs) - func(z)) / delta;
            return x;
        }
    }

    public static double dxdx (double[] xs, boolean isAnalyt) {
        if(isAnalyt) {
            return 4 * (3 * Math.pow(xs[0], 2) - xs[1] + 50);
        } else {
            double x;
            double delta = Math.pow(10, -10);
            double [] y = {xs[0] - delta, xs[1]};
            x = (dx(xs, true) - dx(y, true)) / delta;
            return x;
        }
    }

    public static double dxdy(double xs[], boolean isAnalyt) {
        if(isAnalyt) {
            return -4 * xs[0];
        } else {
            double x;
            double delta = Math.pow(10, -10);
            double [] z = {xs[0], xs[1] - delta};
            x = (dx(xs, true) - dx(z, true)) / delta;
            return x;
        }
    }

    public static double dydy(double xs[], boolean isAnalyt) {
        if(isAnalyt) {
            return 2;
        } else {
            double x;
            double delta = Math.pow(10, -10);
            double [] z = {xs[0], xs[1] - delta};
            x = (dy(xs, true) - dy(z, true)) / delta;
            return x;
        }
    }

    public static double[][] hess(double[] xs, boolean isAnalyt) {
        double [][] x = {{dxdx(xs, isAnalyt), dxdy(xs, isAnalyt)},{dxdy(xs, isAnalyt), dydy(xs, isAnalyt)}};
        return x;
    }

    public static double hessian(double[][] x) {
        return x[0][0] * x[1][1] - x[0][1] * x[1][0];
    }

    public static double[][] oHess(double[][] hess) {
        double[][] ohess = {{hess[1][1], -hess[1][0]}, {-hess[0][1], hess[0][0]}};
        return ohess;
    }

    public static double absGrad(double[]x, boolean isAnalyt) {
        return Math.pow((Math.pow(dx(x, isAnalyt),2) + Math.pow(dy(x, isAnalyt),2)), 0.5);
    }

    public static double[] newton(double[] x, double eps, boolean isAnalyt) {
        System.out.println("Epsilon = " + eps + ". Derivative is " + (isAnalyt?"Analytical":"Numerical"));
        iteration = 0;
        while (absGrad(x, isAnalyt) > eps) {
            iteration++;
            double hessianm1 = 1/hessian(hess(x, isAnalyt));
            double[] change = multiplyMatrix(oHess(hess(x, isAnalyt)), grad(x, isAnalyt));
            for (int i = 0; i < 2; i++) {
                change[i] *= hessianm1;
            }
            for (int i = 0; i < 2; i++) {
                x[i] -= change[i];
            }
            //System.out.println(x[0] + "   " + x[1]);
        }
        System.out.println("Number of iterations: " + iteration);
        System.out.printf("x, y = %.10f %.10f%n", x[0], x[1]);
        System.out.println("abs(grad) = " + absGrad(x, isAnalyt));
        System.out.println("Minimum of the function = " + func(x));
        System.out.println("\n");
        return x;
    }

    public static double[] multiplyMatrix(double[][] hess, double [] grad) {
        double[] x = new double[2];
        x[0] = hess[0][0] * grad[0] + hess[0][1] * grad[1];
        x[1] = hess[1][0] * grad[0] + hess[1][1] * grad[1];
        return x;
    }

    public static void main(String[] args) {
        double[] starter = {-100., 100.};
        double eps = 0.1;
        for (int i = 0; i < 3; i++) {
            newton(starter, eps, false);
            starter[0] = -4; starter[1] = 6;

            newton(starter, eps, true);
            starter[0] = -4; starter[1] = 6;
            eps /= 100;
        }
    }
}