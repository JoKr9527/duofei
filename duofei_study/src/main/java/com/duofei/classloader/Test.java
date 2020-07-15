package com.duofei.classloader;

import sun.misc.Unsafe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

    static final Unsafe unsafe = getUnsafe();

    public static void main(String[] args) throws Exception {
        Class<Test> testClzz = (Class<Test>) Test.class.getClassLoader().loadClass("com.duofei.classloader.Test");
        printAddresses("testClzz", testClzz);
         Test test = testClzz.newInstance();
        test.test();
        Thread.sleep(15000);
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if (name.startsWith("java.lang")) {
                    return getParent().loadClass(name);
                }
                String fileName = name.substring(name.lastIndexOf('.') + 1) + ".class";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                if (inputStream == null || name.contains("$")) {
                    return getParent().loadClass(name);
                }
                byte[] bytes = new byte[2048 * 5];
                int len = 2048 * 5;
                try {
                    len = inputStream.read(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return defineClass(name, bytes, 0, len);
            }
        };
        Class<Test> test1Clzz = (Class<Test>) classLoader.loadClass("com.duofei.classloader.Test");
        printAddresses("test1Clzz", test1Clzz);
        Method invoke = test1Clzz.getMethod("test");
        invoke.invoke(test1Clzz.newInstance());

    }

    @SuppressWarnings("deprecation")
    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ":         0x");
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = 8;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last){
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    }
                    else{
                        System.out.print(", -" + Long.toHexString(last - i2));
                    }
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public void test() {
        System.out.println(3);
    }

}