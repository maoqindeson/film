package com.leoleo.film.utils.study;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjectLesson {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        List<Integer> newList = new ArrayList<>();
        for (int a=1;a <=100;a++){
                list.add(a);
        }
        Iterator it = list.iterator();
        while(it.hasNext()) {
            Integer i = (Integer) it.next();
            int b = i%3;
            if (0==b){
                it.remove();
                newList.add(i);
            }
        }
//        for (Integer i : list){
//            int b = i%3;
//            if (0==b){
////                list.remove(i);
//                newList.add(i);
//            }
//        }
        System.out.println("原数组为: "+list.toString());
        System.out.println("3倍数数组为: "+newList.toString());
    }
}
