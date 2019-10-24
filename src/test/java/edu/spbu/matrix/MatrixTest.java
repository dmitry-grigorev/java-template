package edu.spbu.matrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MatrixTest
{
  /**
   * ожидается 4 таких теста
   */
  //Тест умножения плотных матриц. Ожидается успех.
  @Test
  public void mulDDEx1() {
    Matrix m1 = new DenseMatrix("m1.txt");
    Matrix m2 = new DenseMatrix("m2.txt");
    Matrix actual=m1.mul(m2);
    Matrix expected = new DenseMatrix("result.txt");
    System.out.println("expected:"+(expected).toString());
    System.out.println("actual:"+((DenseMatrix)actual).toString());
    assertEquals(expected, actual);
  }
  
    //Тест умножения плотных матриц. Ожидается успех.
  @Test
  public void mulDDEx2() {
      Matrix m1 = new DenseMatrix("m3.txt");
      Matrix m2 = new DenseMatrix("m4.txt");
      Matrix actual=m1.mul(m2);
      Matrix expected = new DenseMatrix("result2.txt");
      System.out.println("expected:"+(expected).toString());
      System.out.println("actual:"+((DenseMatrix)actual).toString());
      assertEquals(expected, actual);
  }

  //Тест умножения разреженных матриц. Ожидается успех.
  @Test
  public void mulSSEx1() {
      Matrix m1 = new SparseMatrix("SparseA1.txt");
      Matrix m2 = new SparseMatrix("SparseA2.txt");
      Matrix actual=m1.mul(m2);
      Matrix expected=new SparseMatrix("ResA1xA2.txt");
      System.out.println("expected:"+(expected).toString());
      System.out.println("actual:"+((SparseMatrix)actual).toString());
      assertEquals(expected, actual);
  }

  //Тест умножения разреженных матриц. Ожидается успех.
    @Test
    public void mulSSEx2() {
        Matrix m1 = new SparseMatrix("SparseA3.txt");
        Matrix m2 = new SparseMatrix("SparseA4.txt");
        Matrix actual=m1.mul(m2);
        Matrix expected=new SparseMatrix("ResA3xA4.txt");
        System.out.println("expected:"+(expected).toString());
        System.out.println("actual:"+((SparseMatrix)actual).toString());
        assertEquals(expected, actual);
    }

    //Тест умножения больших разреженных матриц.
    @Test
    public void mulSSGiant() {
        Matrix m1 = new SparseMatrix("SparseGiant1.txt");
        Matrix m2 = new SparseMatrix("SparseGiant2.txt");
        Matrix expected=new SparseMatrix("ResGiant.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //Тест умножения разреженной матрицы и плотной. Ожидается успех.
    @Test
    public void mulSDEx1() {
        Matrix m1 = new SparseMatrix("PermutationMatrix.txt");
        Matrix m2 = new DenseMatrix("m1.txt");
        Matrix actual=m1.mul(m2);
        Matrix expected=new DenseMatrix("PermutationResult1.txt");
        System.out.println("expected:"+(expected).toString());
        System.out.println("actual:"+((DenseMatrix)actual).toString());
        assertEquals(expected, actual);
    }

    //Тест умножения плотной матрицы на разреженную. Ождиается успех.
    @Test
    public void mulDSEx1() {
        Matrix m1 = new DenseMatrix("m1.txt");
        Matrix m2 = new SparseMatrix("PermutationMatrix.txt");
        Matrix actual=m1.mul(m2);
        Matrix expected=new DenseMatrix("PermutationResult2.txt");
        System.out.println("expected:"+(expected).toString());
        System.out.println("actual:"+((DenseMatrix)actual).toString());
        assertEquals(expected, m1.mul(m2));
    }

    //Тест на неподходящие размеры множителей. Ожидается успех.
    @Test(expected=RuntimeException.class)
    public void Failmul1() {
        Matrix m1 = new SparseMatrix("SparseA1.txt");
        Matrix m2 = new SparseMatrix("FailedTest1.txt");
        Matrix expected = new SparseMatrix("ResA1xA2.txt");
        assertEquals(expected, m1.mul(m2));
    }

    //Тест на наличие ненулевого элемента в неожидаемой позиции. Ожидается успех.
    @Test
    public void Failmul2() {
        Matrix m1 = new SparseMatrix("SparseA1.txt");
        Matrix m2 = new SparseMatrix("SparseA2.txt");
        Matrix actual=m1.mul(m2);
        Matrix expected = new SparseMatrix("FailedTest2.txt");
        System.out.println("expected:"+(expected).toString());
        System.out.println("actual:"+((SparseMatrix)actual).toString());
        assertNotEquals(expected, m1.mul(m2));
    }

    @Test(expected=NullPointerException.class)
    public void Failmul3() {
        Matrix m1 = new SparseMatrix(null,0,0);
        Matrix m2 = new SparseMatrix(null,0,0);
        Matrix actual=m1.mul(m2);
        System.out.println("actual:"+((SparseMatrix)actual).toString());
    }
}
