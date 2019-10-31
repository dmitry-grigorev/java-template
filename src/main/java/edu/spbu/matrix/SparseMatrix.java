package edu.spbu.matrix;


import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
/**
 * Разряженная матрица
 */
public class SparseMatrix implements Matrix
{
  public HashMap<Point, Double> SMatr;
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
      SMatr=new HashMap<>();
      String[] dividedcurrln;
      String strrepcurrln=bufR.readLine();
      int length=0,height=0;
      double element;
      while(strrepcurrln!=null)
      {
        dividedcurrln = strrepcurrln.split(" ");
        length = dividedcurrln.length;
        for (int i = 0; i < length; i++) {
          if(!dividedcurrln[0].isEmpty()) {
            element = Double.parseDouble(dividedcurrln[i]);
            if(element!=0) {
                Point p=new Point(height,i);
                SMatr.put(p, element);
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

  public SparseMatrix(HashMap<Point,Double> SMatr,int nrows,int ncols)
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
    else if(o instanceof DenseMatrix)
    {
        return mul((DenseMatrix)o);
    }
    else throw new RuntimeException("Применяемый операнд является представителем класса иного происхождения");
  }

  public SparseMatrix transpose()
  {
      HashMap<Point,Double> transposedSMtx=new HashMap<>();
      Point p;
      for(Point k:SMatr.keySet())
      {
          p=new Point(k.y,k.x);
          transposedSMtx.put(p,SMatr.get(k));
      }
      return new SparseMatrix(transposedSMtx,nc,nr);
  }

  public SparseMatrix mul(SparseMatrix SMtx)
  {
      if(nc==0||SMtx.nr==0||SMatr==null||SMtx.SMatr==null) return null;
    if(nc==SMtx.nr)
    {
        HashMap<Point,Double> result=new HashMap<>();
      SparseMatrix tSMtx=SMtx.transpose();
      for(Point k: SMatr.keySet())
      {
        for(int i=0;i<tSMtx.nr;i++)
        {
            //if(k.y==l.y)
            Point p1=new Point(i,k.y);
            if(tSMtx.SMatr.containsKey(p1))
            {
                Point p2=new Point(k.x,i);
                {
                    double buf;
                    if(result.containsKey(p2))
                    {
                        buf = result.get(p2) + SMatr.get(k) * tSMtx.SMatr.get(p1);
                        if (buf == 0) result.remove(p2);
                        else result.put(p2, buf);
                    } else {
                        buf = SMatr.get(k) * tSMtx.SMatr.get(p1);
                        result.put(p2, buf);
                    }
                }
            }
        }
      }
      return new SparseMatrix(result,nr,SMtx.nc);
    }
    else throw new RuntimeException("Размеры матриц не отвечают матричному уможению.");
  }

    public DenseMatrix mul(DenseMatrix DMtx){
        if(nc==DMtx.nr&&SMatr!=null&&DMtx.DMatr!=null)
        {
            double[][] res=new double[nr][DMtx.nc];
            DenseMatrix tDMtx=DMtx.transpose();
            for(Point p:SMatr.keySet())
            {
                for(int j=0;j<tDMtx.nr;j++)
                {
                    for(int k=0;k<nc;k++)
                    {
                        if(p.y==k)
                        {
                            res[p.x][j]+=SMatr.get(p)*tDMtx.DMatr[j][k];
                        }
                    }
                }
            }
            return new DenseMatrix(res);
        }else throw new RuntimeException("Размеры матриц не отвечают матричному уможению.");
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
      if(o instanceof DenseMatrix)
      {
          DenseMatrix DMtx=(DenseMatrix) o;
          if (SMatr == null || DMtx.DMatr == null) return false;
          if (nr == DMtx.nr && nc == DMtx.nc) {
              int nonzeros=0;
              for(int i=0;i<DMtx.nr;i++)
              {
                  for(int j=0;j<DMtx.nc;j++)
                  {
                      if(DMtx.DMatr[i][j]!=0)
                      {
                          nonzeros++;
                      }
                  }
              }
              if(nonzeros!=SMatr.size()) return false;
              for (Point k: SMatr.keySet()) {
                  if(DMtx.DMatr[k.x][k.y]==0)
                      return false;
                  if (DMtx.DMatr[k.x][k.y]!=SMatr.get(k)) {
                      return false;
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
          if (this.hashCode() != SMtx.hashCode()) return false;
          if (nr != SMtx.nr || nc != SMtx.nc) return false;
          if (SMatr.size()!=SMtx.SMatr.size())return false;
          for (Point p:SMatr.keySet()) {
              if (SMatr.get(p)-(SMtx.SMatr.get(p))!=0)
                  return false;
          }return true;


      }
    return false;
  }

  @Override
  public int hashCode() {
      int hsh=Objects.hash(nr,nc);
      for(Point p:SMatr.keySet())
      {
          hsh+=(SMatr.get(p).hashCode()<<2)+31;
      }

    return hsh;
  }

  @Override public String toString() {
      if(SMatr==null) throw new RuntimeException("Встречена пустая матрица");
      StringBuilder resBuilder = new StringBuilder();
      resBuilder.append('\n');
      for (int i = 0; i < nr; i++) {
        resBuilder.append('[');
        for (int j = 0; j < nc; j++) {
            Point p=new Point(i,j);
          if (SMatr.containsKey(p)) {
            resBuilder.append(SMatr.get(p));
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
