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
        Matrix m2 = new SparseMatrix("SparseA2.txt");
        Matrix expected=new SparseMatrix("ResA1xA2.txt");
        assertEquals(expected, m1.mul(m2));
    }

    @Test
    public void mulSSEx2() {
        Matrix m1 = new SparseMatrix("SparseA3.txt");
        Matrix m2 = new SparseMatrix("SparseA4.txt");
        Matrix expected=new SparseMatrix("ResA3xA4.txt");
        assertEquals(expected, m1.mul(m2));
    }

    @Test
    public void mulSSGiant() {
        Matrix m1 = new SparseMatrix("SparseGiant1.txt");
        Matrix m2 = new SparseMatrix("SparseGiant2.txt");
        Matrix expected=new SparseMatrix("ResGiant.txt");
        assertEquals(expected, m1.mul(m2));
    }

    /*@Test
    public void mulSSEx3() {
        Matrix m1 = new DenseMatrix("m1.txt");
        Matrix m2 = new SparseMatrix("m1.txt");
        assertEquals(m1, m2);
    }*/
}
