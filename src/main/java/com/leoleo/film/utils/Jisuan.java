package com.leoleo.film.utils;//package io.renren.modules.generator.utils;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class Jisuan {
//    public static void main(String[] args) {
//        int a = 68;
//        boolean b = true;
//        int last = 0;
//        int big = 100;
//        int small = 0;
//        while (b) {
//            Scanner input = new Scanner(System.in);
//            System.out.println("请输入数字");
//            int contect = input.nextInt();
//            //先判断输入是否与结果相等,如果相等直接结束
//            if (contect == a) {
//                System.out.println("答对了");
//                b = false;
//            }
//            if (contect > 100) {
//                System.out.println("不能超过100,重新输入");
//                b = false;
//                break;
//            }
//            if (contect>big&&contect<small){
//                contect=big;
//            }
//            if (contect > a) {
//                System.out.println("大了");
//                if (contect >= last) {
//                    if (last > a) {
//                        System.out.println("范围是: " + small + "~" + contect);
//                    }
//                    if (last < a) {
//                        System.out.println("范围是: " + last + "~" + contect);
//                        small = last;
//                    }
//                }
//                if (contect < last) {
//                    System.out.println("范围是: " + small + "~" + contect);
//                }
//                big = contect;
//            }
//            if (contect < a) {
//                System.out.println("小了");
//                if (contect >= last) {
//                    System.out.println("范围是:" + contect + "~" + big);
//                }
//                if (contect < last) {
//                    if (last < a) {
//                        System.out.println("范围是: " + last + "~" + big);
//                    }
//                    if (last > a) {
//                        System.out.println("范围是: " + contect + "~" + last);
//                        big = last;
//                    }
//                }
//                small = contect;
//            }
//            last = contect;
//        }
//    }
//}
