Double[] B = {1000., 0.};
Double[] C = {100., 100., 10., 0., 10., 1000.};
Double[][] A = {{99., 999., 1., 9., 999., 99.},
{100., 100., 100., 200., 300., 100.}};

setSimplex(A, B, C);
while(!gauss(findB(), findIMin()));

System.out.printf("The answer is: %.5f", simplexTable[simplexTable.length - 1][0]);
