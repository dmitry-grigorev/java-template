package edu.spbu.matrix;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.io.*;
/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix
{
  public double[][] DMatr=new double[3][3];//матрица
  public int nr;//количество строк
  public int nc;//количество столбцов

    /**
   * загружает матрицу из файла
   * @param fileName
   */
  public DenseMatrix(String fileName) {
      File src=new File(fileName);
      try{
          Scanner sc=new Scanner(src);
          String[] buf;
          int i=0,j=0,length=0;
          while(sc.hasNextLine())
          {
              buf=sc.nextLine().split(" ");
              length=buf.length;
              for(;j<length;j++)
              {
                  DMatr[i][j]=Double.parseDouble(buf[j]);
              }
              j=0;
              i++;
          }
          nr=length;
          nc=i;
      }
      catch(FileNotFoundException e)
      {
          e.printStackTrace();
      }
      catch(NullPointerException e)
      {

      }
  }

  public DenseMatrix(double[][] input)
  {
      if (input.length > 0 )
      {
          DMatr=input;
          nc=input.length;
          nr=input[0].length;
      }
  }
  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o)
  {
      DenseMatrix DMtx=(DenseMatrix)o;
      if(nc==DMtx.nr&&DMatr!=null&&DMtx.DMatr!=null)
      {
          double[][] res=new double[this.nr][DMtx.nc];
          for(int i=0;i<nr;i++)
          {
              for(int j=0;j<DMtx.nc;j++)
              {
                  for(int k=0;k<nr;k++)
                  {
                      res[i][j]+=DMatr[i][k]*DMtx.DMatr[k][j];
                  }
              }
          }
          return new DenseMatrix(res);
      }
      else return null;
  }

  /**
   * многопоточное умножение матриц
   *
   * @param o
   * @return
   */
  @Override public Matrix dmul(Matrix o)
  {
    return null;
  }

  @Override
    public int hashCode() {
        return Arrays.hashCode(DMatr);
  }

    /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */

  @Override public boolean equals(Object o) {

    DenseMatrix DMtx=(DenseMatrix)o;
    if(DMtx.DMatr==DMatr) return true;
    if(DMatr==null||DMtx.DMatr==null) return false;
    if(DMatr.hashCode()!=DMtx.DMatr.hashCode())
    if(nr==DMtx.nr&&nc==DMtx.nc) {
        for(int i=0;i<nr;i++)
        {
          for(int j=0;j<nc;j++)
          {
            if(DMatr[i][j]!=DMtx.DMatr[i][j])
            {
              return false;
            }
          }
        }
        return true;
    }
    return false;
  }

}
