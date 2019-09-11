package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
public class IntSort {
  public static void sort (int array[]) {
    int n=array.length;
    quickSort(array,0,n-1);
  }

  //метод,выполняющий быструю сортировку массива array
  //left  -- номер элемента-левой границы рассматриваемого фрагмента массива
  //right -- номер элемента-правой границы рассматриваемого фрагмента
  //beg   -- номер рассматриваемого элемента в левой части массива
  //end   -- номер рассматриваемого элемента в правой части массива
  //med   -- значение опорного элемента
  //buf   -- вспомогательная переменная
  public static void quickSort(int array[],int left,int right){
    int beg=left,end=right,med=array[(left+right)/2];
    int buf;
    while(beg<=end)
    {
      for(;array[beg]<med;beg++);
      for(;array[end]>med;end--);
      if(beg<=end)
      {
        buf=array[beg];
        array[beg]=array[end];
        array[end]=buf;
        beg++;
        end--;
      }
    }
    if(left<end) quickSort(array,left,end);
    if(beg<right) quickSort(array,beg,right);


  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}
