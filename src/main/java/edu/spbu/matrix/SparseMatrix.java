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
        if(strrepcurrln.isBlank());
        {
          dividedcurrln = strrepcurrln.split(" ");
          length = dividedcurrln.length;
          key0=height*length;
          for (int i = 0; i < length; i++) {
            element = Double.parseDouble(dividedcurrln[i]);
              if (element != 0) {

                if (Array != null) {
                  Array.put(key0 + i, element);
                }
              }


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
  /**
   * однопоточное умнджение матриц
   * должно поддерживаться для всех 4-х вариантов
   *
   * @param o
   * @return
   */
  @Override public Matrix mul(Matrix o)
  {
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
