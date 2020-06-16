package com.generic;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.*;

public class Generic {
    static class  Pair<K,V>{
        private K key;
        private V value;
        public Pair(K k, V v){
            this.key = k;
            this.value = v;
        }
    }
    public static void main(String ...args){

//        var hashtable = new Hashtable<String ,String>();
//        Pair<String,String> pair = new Pair<String, String>("1","2");
//        Queue<String> queue1 = new LinkedList<>();
//        Queue<String> queue2 = new ArrayDeque<>();
        int[] arr ;
        arr = new int[]{2,1,4,6,3,5,7,9,10,8};
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>=arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public final void e1(){

    }
}
