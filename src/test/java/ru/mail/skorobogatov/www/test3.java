Double[] B = {666., 333.};
Double[] C = {-999., 666., 333., -666., 333., 999.};
Double[][] A = {{3., 0., -2., 1., 2., 1.},
{1., 1., 4., 2., 3., 1.}};

setSimplex(A, B, C);
while(!gauss(findB(), findIMin()));

System.out.printf("The answer is: %.5f", simplexTable[simplexTable.length - 1][0]);
