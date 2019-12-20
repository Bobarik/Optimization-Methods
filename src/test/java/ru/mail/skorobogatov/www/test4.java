Double[] B = {0., 0.};
Double[] C = {0., 0., 0., 0., 0., 0.};
Double[][] A = {{0., 0., 0., 0., 0., 0.},
{1., 0., 0., 0., 0., 0.}};

setSimplex(A, B, C);
while(!gauss(findB(), findIMin()));

System.out.printf("The answer is: %.5f", simplexTable[simplexTable.length - 1][0]);
