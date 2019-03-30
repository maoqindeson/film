package com.leoleo.film.utils.study;

public class Farm {
    public static void main(String[] args) {
        int[] arr = {6, 3, 8, 2, 9, 1};     //定义了数组
//        System.out.println("冒泡排序前的数组为:");   //系统输出
//        for (int num : arr) {                          //循环输出数组中的数值
//            System.out.print(num + " ");
//        }
//        for (int i = 0; i < arr.length-1; i++) {//外层循环控制排序趟数
//            for (int j = 0; j < arr.length -1 - i; j++) {//内层循环控制每一趟排序多少次
//                if (arr[j] > arr[j + 1]) {
//                    int temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
//            }
//        }
//        System.out.println();
//        System.out.println("排序后的数组为：");
//        for (int num : arr) {
//            System.out.print(num + " ");
//        }
//        for (int i = 0 ; i < arr.length;i++){
        for (int i = 0 ; i <= arr.length-1;i++){
            System.out.println(arr[i]);
        }
    }
}
