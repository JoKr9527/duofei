package com.duofei.classloader;

import com.duofei.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author duofei
 * @date 2020/7/6
 */
public class ClassLoaderTest {

    protected static Test1 test1 ;

    public static void main(String[] args) throws Exception {

        test11();
        ClassLoader classLoader = new ClassLoader(){
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                if(name.startsWith("java.lang")){
                    return getParent().loadClass(name);
                }
                String fileName = name.substring(name.lastIndexOf('.') + 1) + ".class";
                InputStream inputStream = getClass().getResourceAsStream(fileName);
                if(inputStream == null || name.contains("$")){
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
        Class<ClassLoaderTest> clt1 = (Class<ClassLoaderTest>) classLoader.loadClass("com.duofei.classloader.ClassLoaderTest");
        System.out.println("clt1.newInstance instanceof ClassLoaderTest : " + (clt1.newInstance() instanceof ClassLoaderTest));
        System.out.println("clt1.equals(ClassLoaderTest.class) : " + clt1.equals(ClassLoaderTest.class));
        test1 = (Test1) ClassLoaderTest.class.getClassLoader().loadClass("com.duofei.classloader.ClassLoaderTest$Test1").newInstance();
        Method invoke = clt1.getMethod("invokeTest1");
        invoke.invoke(clt1.newInstance());
    }

    public void invokeTest1(){
        test1.invoke();
        System.out.println("test1 invoke complete!");
    }

    public static class Test1{
        public void invoke(){
            System.out.println("i am invoked!");
        }
    }

    public static void test11() throws Exception{
        ClassLoader classLoader = new ClassLoader(){
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return getParent().loadClass(name);
            }
        };
        Class<ClassLoaderTest> clt1 = (Class<ClassLoaderTest>) classLoader.loadClass("com.duofei.classloader.ClassLoaderTest");
        ClassLoader classLoader2 = new ClassLoader(){
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return getParent().loadClass(name);
            }
        };
        Class<ClassLoaderTest> clt2 = (Class<ClassLoaderTest>) classLoader2.loadClass("com.duofei.classloader.ClassLoaderTest");
        System.out.println();
    }
}
