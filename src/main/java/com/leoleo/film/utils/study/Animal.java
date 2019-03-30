package com.leoleo.film.utils.study;

import lombok.Data;

@Data
public class Animal {
    private int id ;
    private String name;

    public Animal(int id , String name){
        id=id;
        name=name;
    }
    void eat(){
        System.out.println("吃的方法");
    }



}
