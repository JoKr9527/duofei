package com.duofei.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapStu {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 1063000; i++) {
            map.put(UUID.randomUUID().toString() , i);
        }
        System.out.println(map.size());
    }
}
