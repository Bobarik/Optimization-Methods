package ru.mail.skorobogatov.www;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SimplexTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        Double[] B = {2., 3.};
        Double C[] = {-1., 4., 3., -2., 3., 2.};
        Double A[][] = {{3., 0., -2., 1., 2., 1.},
                        {1., 1., 4., 2., 3., 1.}};

        Simplex.setSimplex(A, B, C);
        while(!Simplex.gauss(Simplex.findB(), Simplex.findIMin()));
        assertTrue( true );

    }


}

