package com.leoleo.film.utils;//package io.renren.modules.generator.utils;
//
//import com.google.common.cache.*;
//
//import java.util.concurrent.TimeUnit;
//
//public class GuavaCacheBuilderTest {
//    public static void main(String[] args) throws Exception {
//        GuavaCacheBuilderTest cache = new GuavaCacheBuilderTest();
//        cache.getNameLoadingCache("bixiao");
//    }
//
//    public void getNameLoadingCache(String name) throws Exception {
//        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(20)//设置大小，条目数
//                .expireAfterWrite(20, TimeUnit.SECONDS)//设置失效时间，创建时间
//                .expireAfterAccess(20, TimeUnit.HOURS) //设置时效时间，最后一次被访问
//                .removalListener(new RemovalListener<String, String>() { //移除缓存的监听器
//                    public void onRemoval(RemovalNotification<String, String> notification) {
//                        System.out.println("有缓存数据被移除了");
//                    }
//                }).build(new CacheLoader<String, String>() { //通过回调加载缓存
//                    @Override
//                    public String
//                    load(String name) throws Exception {
//                        return name + "-" + "iamzhongyong";
//                    }
//                });
//        System.out.println(cache.get(name));
//        //cache.invalidateAll();
//    }
//}
