package com.Collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Main {
    public static void main(String ...args){
//        String str1 = new String("1")+new String("5");
//        str1.intern();
//        String str2 = "15";
//        System.out.println(str1 == str2);

        String str3 = new String("1")+new String("2");
        str3.intern();
        String str4 = "12";
        System.out.println(str3 == str4);
    }
}