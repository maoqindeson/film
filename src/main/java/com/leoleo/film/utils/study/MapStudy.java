package com.leoleo.film.utils.study;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStudy {
    public static void main(String[] args) {
        Dog dog= new Dog();
        //数组定义即马上初始化里面的元素
        int[] arr = {1,3,4,6,6};
        //取出数组中第三个元素,因为索引是从
        int a = arr[0];

        //List定义方法
        List<Dog> dogList = new ArrayList<>();
        //添加一个狗对象进list
        dogList.add(dog);
        //取出一只狗
        Dog dog1 = dogList.get(0);

        //Map 定义方法
        Map<String,Dog> dogMap = new HashMap<>();
        //向map里面存一个东西的时候,需要指定key,也就是指定他的名字
        dogMap.put("first dog", dog);
        //根据名字取出对应的元素
        Dog firstDog = dogMap.get("first dog");
    }
}
