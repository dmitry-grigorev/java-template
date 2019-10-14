package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixTest
{
  /**
   * ожидается 4 таких теста
   */
  @Test
  public void mulDDEx1() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix expected = new DenseMatrix("result.txt");
    assertEquals(expected, m1.mul(m2));
  }

  @Test
  public void mulDDEx2() {
        Matrix m1 = new DenseMatrix("m3.txt");
        Matrix m2 = new DenseMatrix("m4.txt");
        Matrix expected = new DenseMatrix("result2.txt");
        assertEquals(expected, m1.mul(m2));
  }

    @Test
    public void mulSSEx1() {
        Matrix m1 = new SparseMatrix("SparseA1.txt");
        System.out.println(m1.toString());
    }

    @Test
    public void mulSSEx2() {
        Matrix m1 = new SparseMatrix("SparseA2.txt");
        Matrix m2 = new SparseMatrix("SparseA3.txt");
        System.out.println(m1.mul(m2).toString());
    }
}
