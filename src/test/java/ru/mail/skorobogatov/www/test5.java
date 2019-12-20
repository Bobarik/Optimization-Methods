Double[] B = {20000., 30000.};
Double[] C = {-1., 4., 30000., -20000., 30000., 20000.};
Double[][] A = {{30000., 0., -20000., 10000., 20000., 10000.},
{10000., 10000., 40000., 20000., 30000., 10000.}};

setSimplex(A, B, C);
while(!gauss(findB(), findIMin()));

System.out.printf("The answer is: %.5f", simplexTable[simplexTable.length - 1][0]);
