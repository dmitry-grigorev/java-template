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
  public TreeMap <Integer,Double> Array;
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
      Array=new TreeMap<>();
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
            Array.put(key0 + i, element);
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

  public SparseMatrix(TreeMap<Integer,Double> array,int nrows,int ncols)
  {
    this.Array=array;
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
    if(nc==SMtx.nr&&Array!=null&&SMtx.Array!=null)
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
            if(Array.containsKey(i*nc+k)&&SMtx.Array.containsKey(k* SMtx.nc+j))
            {
              buf+=Array.get(i*nc+k)*(SMtx.Array.get(k*SMtx.nc+j));
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

  /**
   * спавнивает с обоими вариантами
   * @param o
   * @return
   */
  @Override public boolean equals(Object o) {
    return false;
  }

  @Override public String toString() {
      StringBuilder resBuilder = new StringBuilder();
      resBuilder.append('\n');
      for (int i = 0; i < nr; i++) {
        resBuilder.append('[');
        for (int j = 0; j < nc; j++) {
          if (Array.containsKey(i * nc + j)) {
            resBuilder.append(Array.get(i * nc + j));
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
