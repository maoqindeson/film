package com.leoleo.film.utils;




import com.leoleo.film.utils.DTO.ImgSearchData;

import java.util.*;

public class ListUtil {
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    // 删除ArrayList中重复元素，保持顺序
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
//
//    //按时间逆序排序
//    public static void sort(List<PetEntity> list) {
//        Collections.sort(list, new Comparator<PetEntity>() {
//            @Override
//            public int compare(PetEntity o1, PetEntity o2) {
//                try {
//                    Date dt1 = o1.getCreatedDate();
//                    Date dt2 = o2.getCreatedDate();
//                    if (dt1.getTime() < dt2.getTime()) {
//                        return 1;
//                    } else if (dt1.getTime() > dt2.getTime()) {
//                        return -1;
//                    } else {
//                        return 0;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return 0;
//            }
//        });
//    }
    public static List<ImgSearchData> DataListfenye(List<ImgSearchData> list, Integer pageIndex, Integer pageSize) {
        //如果有传页码参数则分页
            if (null==pageIndex||0 == pageIndex) {
                pageIndex = 1;
            }
            if (null==pageSize||0 == pageSize) {
                pageSize = 10;
            }
            if (pageSize >= list.size()) {
                pageSize = list.size();
            }
            List<ImgSearchData> newlist = new ArrayList<>();
            int currIdx = (pageIndex > 1 ? (pageIndex - 1) * pageSize : 0);
            for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
                ImgSearchData memberArticleBean = list.get(currIdx + i);
                newlist.add(memberArticleBean);
            }
            return newlist;

    }
//    public static List<StoreOrderUrlResultOrder> StoreOrderListfenye(List<StoreOrderUrlResultOrder> list,int pageNumber,int pageSize){
//        List<StoreOrderUrlResultOrder> newlist = new ArrayList<>();
//        int currIdx = (pageNumber > 1 ? (pageNumber -1) * pageSize : 0);
//        for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
//            StoreOrderUrlResultOrder memberArticleBean = list.get(currIdx + i);
//            newlist.add(memberArticleBean);
//        }
//        return newlist;
//    }
}

