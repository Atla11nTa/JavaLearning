package com.Collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Main {
    public static void main(String ...args){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        System.out.println(arrayList.size());
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        System.out.println(arrayList.toString()+", "+arrayList2.toString());
    }
}
