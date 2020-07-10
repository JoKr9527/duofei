import java.io.*;
import java.lang.reflect.*;
public class Test{
	
	public static void main(String[] args) throws Exception{
		Class<Test> testClzz = (Class<Test>)Test.class.getClassLoader().loadClass("Test");
		System.out.println(System.identityHashCode(testClzz));
		Test test = testClzz.newInstance();
		test.test();
		Thread.sleep(15000);
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
		Class<Test> test1Clzz = (Class<Test>)classLoader.loadClass("Test");
		System.out.println(System.identityHashCode(test1Clzz));
		Method invoke = test1Clzz.getMethod("test");
		invoke.invoke(test1Clzz.newInstance());

	}
	
	public void test(){
		System.out.println(3);
	}

}