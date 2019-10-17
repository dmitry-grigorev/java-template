package edu.spbu.matrix;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * Разряженная матрица
 */
public class SparseMatrix implements Matrix
{
  public TreeMap <Integer, Double> SMatr;
  public int nr;
  public int nc;
  /**
   * загружает матрицу из файла
   * @param fileName
   */
  public SparseMatrix(String fileName) {
    try {
      FileReader rdr = new FileReader(fileName);
      BufferedReader bufR = new BufferedReader(rdr);
      SMatr=new TreeMap<>();
      String[] dividedcurrln;
      String strrepcurrln=bufR.readLine();
      int length=0,key0,height=0;
      double element;
      while(strrepcurrln!=null)
      {
        dividedcurrln = strrepcurrln.split(" ");
        length = dividedcurrln.length;
        key0=height*length;
        for (int i = 0; i < length; i++) {
          if(!dividedcurrln[0].isEmpty()) {
            element = Double.parseDouble(dividedcurrln[i]);
            SMatr.put(key0 + i, element);
          }
        }
        height++;
        strrepcurrln=bufR.readLine();
      }
      rdr.close();
      nr=height;
      nc=length;
    }
    catch(FileNotFoundException e) {
      System.out.println("File not found");
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

  public SparseMatrix(TreeMap<Integer,Double> SMatr,int nrows,int ncols)
  {
    this.SMatr=SMatr;
    this.nr=nrows;
    this.nc=ncols;
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
    if(o instanceof SparseMatrix)
    {
      return mul((SparseMatrix)o);
    }
    return null;
  }

  public SparseMatrix mul(SparseMatrix SMtx)
  {
    if(nc==SMtx.nr&&SMatr!=null&&SMtx.SMatr!=null)
    {
      TreeMap<Integer,Double> result=new TreeMap<>();
      double buf=0;
      for(int i=0;i<nr;i++)
      {
        for(int j=0;j<SMtx.nc;j++)
        {
          buf=0;
          for(int k=0;k<nc;k++)
          {
            if(SMatr.containsKey(i*nc+k)&&SMtx.SMatr.containsKey(k* SMtx.nc+j))
            {
              buf+=SMatr.get(i*nc+k)*(SMtx.SMatr.get(k*SMtx.nc+j));
            }
          }
          result.put(i*SMtx.nc+j,buf);
        }
      }
      return new SparseMatrix(result,nr,SMtx.nc);
    }
    return null;
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
  public double getEL(int i,int j)
  {
      if(!SMatr.containsKey(i*nc+j))
        return 0;
      else return SMatr.get(i*nc+j);
  }
  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
      if(o instanceof DenseMatrix)
      {
          DenseMatrix DMtx=(DenseMatrix) o;
          if (SMatr == null || DMtx.DMatr == null) return false;
          System.out.println("expected: " + this.toString());
          System.out.println("actual: " + DMtx.toString());
          if (nr == DMtx.nr && nc == DMtx.nc) {
              for (int i = 0; i < nr; i++) {
                  for (int j = 0; j < nc; j++) {
                      if (DMtx.DMatr[i][j]!=this.getEL(i,j)) {
                          return false;
                      }
                  }
              }
              return true;
          }
      }
      else if(o instanceof SparseMatrix)
      {
          SparseMatrix SMtx=(SparseMatrix)o;
          if (SMatr == null || SMtx.SMatr == null) return false;
          if (SMtx.SMatr == SMatr) return true;
          //System.out.println("expected: " + this.toString());
         // System.out.println("actual: " + SMtx.toString());
          //if (this.hashCode() == SMtx.hashCode())
              if (nr == SMtx.nr && nc == SMtx.nc) {
                  for (int i = 0; i < nr; i++) {
                      for (int j = 0; j < nc; j++) {
                          if(SMtx.getEL(i,j)!=this.getEL(i,j))
                              return false;
                      }
                  }

                  return true;
              }
      }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(SMatr, nr, nc);
  }

  @Override public String toString() {
      StringBuilder resBuilder = new StringBuilder();
      resBuilder.append('\n');
      for (int i = 0; i < nr; i++) {
        resBuilder.append('[');
        for (int j = 0; j < nc; j++) {
          if (SMatr.containsKey(i * nc + j)) {
            resBuilder.append(SMatr.get(i * nc + j));
          } else {
            resBuilder.append(0.0);
          }
          if (j < nc - 1)
            resBuilder.append(" ");
        }
        resBuilder.append("]\n");
      }
      return resBuilder.toString();
  }
}
