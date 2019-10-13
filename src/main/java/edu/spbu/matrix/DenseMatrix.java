package edu.spbu.matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
/**
 * Плотная матрица
 */
public class DenseMatrix implements Matrix
{
  private double[][] DMatr;//матрица
  private int nr;//количество строк
  private int nc;//количество столбцов

    /**
   * загружает матрицу из файла
   * @param fileName
   */
  public DenseMatrix(String fileName) {
      try{
          FileReader rdr=new FileReader(fileName);
          BufferedReader bufR=new BufferedReader(rdr);

          String[] dividedcurrln;
          String strrepcurrln=bufR.readLine();
          double[] currln;
          ArrayList<double[]> L= new ArrayList<>();
          int height=0,length=0;
          while(strrepcurrln!=null)
          {
              dividedcurrln=strrepcurrln.split(" ");
              length=dividedcurrln.length;
                currln=new double[length];
              for(int j=0;j<length;j++)
              {
                  currln[j]=Double.parseDouble(dividedcurrln[j]);
              }
              L.add(currln);
              height++;
              strrepcurrln=bufR.readLine();
          }
          rdr.close();
          double[][]res=new double[height][length];
          for(int i=0;i<height;i++)
          {
              for(int k=0;k<length;k++)
              {
                  res[i][k]=L.get(i)[k];
              }
          }
          DMatr=res;
          nc=length;
          nr=height;
      }
      catch(FileNotFoundException e)
      {
          System.out.println("File not found");
          e.printStackTrace();
      }
       catch (IOException e) {
          e.printStackTrace();
      }
  }

  private DenseMatrix(double[][] input)
  {
      if (input.length > 0 )
      {
          DMatr=input;
          nr=input.length;
          nc=input[0].length;
      }
  }

  private DenseMatrix Transpose()
  {
      double[][] transposedDMtx=new double[nc][nr];
      for(int i=0;i<nc;i++)
      {
          for(int j=0;j<nr;j++)
          {
              transposedDMtx[i][j]=DMatr[j][i];
          }
      }
      return new DenseMatrix(transposedDMtx);
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
          double[][] res=new double[nr][DMtx.nc];
          DenseMatrix tDMtx=DMtx.Transpose();
          for(int i=0;i<nr;i++)
          {
              for(int j=0;j<tDMtx.nr;j++)
              {
                  for(int k=0;k<nc;k++)
                  {
                      res[i][j]+=DMatr[i][k]*tDMtx.DMatr[j][k];
                  }
              }
          }
          return new DenseMatrix(res);
      }
      else
      {
          System.out.println("Данные не отвечают правилам матричного умножения");
          return null;
      }
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
        int result = Objects.hash(nr, nc);
        result = 31 * result + Arrays.deepHashCode(DMatr);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder resBuilder=new StringBuilder();
        resBuilder.append('\n');
        for(int i=0;i<nr;i++) {
            resBuilder.append('[');
            for (int j = 0; j < nc; j++) {
                resBuilder.append(DMatr[i][j]);
                if (j < nc - 1)
                    resBuilder.append(" ");
            }
            resBuilder.append("]\n");

        }
        return resBuilder.toString();
    }

    /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */

  @Override public boolean equals(Object o) {

    DenseMatrix DMtx=(DenseMatrix)o;
    if(DMatr==null||DMtx.DMatr==null) return false;
    if(DMtx.DMatr==DMatr) return true;
    System.out.println("expected: "+this.toString());
    System.out.println("actual: " +DMtx.toString());
    if(this.hashCode() == DMtx.hashCode())
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
