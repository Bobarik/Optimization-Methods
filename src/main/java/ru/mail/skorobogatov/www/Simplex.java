package ru.mail.skorobogatov.www;

public class Simplex {

    public static Double[][] simplexTable;

    public static void setSimplex(Double[][] A, Double[] B, Double[]C) {
        simplexTable = new Double[A.length+1][A[0].length + A.length + 2];

        for (int i = 0; i < simplexTable.length; i++) {
            for (int j = 0; j < simplexTable[0].length; j++) {
                simplexTable[i][j] = 0.0;
            }
        }

        /*for (int i = 0; i < simplexTable.length - 1; i++) {
            simplexTable[i][simplexTable[0].length - 1] = 0.;
        }

        for (int i = 0; i < A.length + 1; i++) {
            simplexTable[simplexTable.length - 1][i + A[0].length] = 0.;
        }*/

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                simplexTable[i][j+1] = A[i][j];
            }
        }
        
        for (int i = 0; i < B.length; i++) {
            simplexTable[i][0] = B[i];
        }

        for (int i = 0; i < C.length; i++) {
            simplexTable[simplexTable.length-1][i+1] = -C[i];
        }

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                simplexTable[i][j + A[0].length + 1] = (i==j?1.:0.);
            }
        }

        for (Double[] i : simplexTable) {
            for (Double j : i) {
                System.out.printf("%-8.4f ", j);
            }
            System.out.println();
        }System.out.println();
    }

    public static int findB() {
        int mindex = 1;
        for (int i = 0; i < simplexTable[0].length - 2; i++) {
            if(simplexTable[simplexTable.length - 1][i + 1] < simplexTable[simplexTable.length - 1][mindex]) {
                mindex = i + 1;
            }
        }

        for (int i = 0; i < simplexTable.length - 1; i++) {
            double a = simplexTable[i][0] / simplexTable[i][mindex];
            simplexTable[i][simplexTable[0].length - 1] = (a > 0?a:Double.POSITIVE_INFINITY);
        }

        return mindex;
    }

    public static int findIMin() {
        int iMin = 0;

        for (int i = 0; i < simplexTable.length - 1; i++) {
            if(simplexTable[i][simplexTable[0].length - 1] < simplexTable[iMin][simplexTable[0].length - 1]) {
                iMin = i;
            }
        }

        return iMin;
    }

    public static boolean gauss(int mindex, int iMin) {
        System.out.printf("[%d; %d]%n", iMin, mindex);
        double c = simplexTable[iMin][mindex];
        Double[][] simplexUnchanged = simplexTable.clone();
        for (int i = 0; i < simplexUnchanged.length; i++) {
            simplexUnchanged[i] = simplexUnchanged[i].clone();
        }

        for (int i = 0; i < simplexTable.length; i++) {
            for (int j = 0; j < simplexTable[0].length - 1; j++) {
                if(simplexTable[i][j] == null) {
                    continue;
                }
                if(i == iMin) {
                    simplexTable[i][j] /= c;
                } else {
                    if(j == mindex) {
                        simplexTable[i][j] = 0.;
                    } else {
                        simplexTable[i][j] -= simplexUnchanged[i][mindex] * simplexUnchanged[iMin][j] / c;
                    }
                }
            }
        }

        for (Double[] i : simplexTable) {
            for (Double j : i) {
                System.out.printf("%-8.4f ", j);
            }
            System.out.println();
        }System.out.println();


        boolean isPositive = true;
        for (int i = 1; i < simplexTable[0].length - 2 && isPositive; i++) {
            isPositive = (simplexTable[simplexTable.length - 1][i] >= 0);
        }

        return isPositive;
    }

    public static void main(String[] args) {
        
        Double[] B = {1., 2.};
        Double[] C = {-2., 3., 4., -1., 2., 1.};
        Double[][] A = {{2., 1., -1., 0., 2., 1.},
                        {2., 0.,  3., 0., 1., 1.}};
                                
        setSimplex(A, B, C);
        while(!gauss(findB(), findIMin()));

        System.out.printf("The answer is: %.5f", simplexTable[simplexTable.length - 1][0]);
    }
}