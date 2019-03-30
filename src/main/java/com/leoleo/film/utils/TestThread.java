package com.leoleo.film.utils;//package io.renren.modules.generator.utils;
//
//import io.renren.modules.generator.service.CodeService;
//import org.apache.velocity.runtime.directive.contrib.For;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//@Configuration
//public class TestThread {
//    public static List<String> createData(int length){
//        List<String> list = new ArrayList<>();
//        for (int i =0 ; i<length;i++){
//            list.add(StringTools.getRandomString(4));
//        }
//        System.out.println("得到一个list元素数量为： "+list.size());
//        System.out.println("得到一个list元素： "+list.toString());
//        //以上得到数据源，有100个字符串，接下来把这100个字符串分割成10份，插入数据库
//        return list;
//    }
//}
