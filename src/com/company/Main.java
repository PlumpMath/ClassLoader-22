package com.company;

        import tmp.Test;

        import javax.tools.JavaCompiler;
        import javax.tools.ToolProvider;
        import java.io.File;
        import java.net.URL;
        import java.net.URLClassLoader;
        import java.nio.charset.StandardCharsets;
        import java.nio.file.Files;

public class Main {
    public static final String CODE = "package tmp;" +
            "import com.company.BomberInterface;"+

            "public class Test extends TestSuperClass implements BomberInterface{\n" +
            "    public Test() {\n" +
            "        System.out.println(\"construct test1\");\n" +
            "    }\n" +
            "\n" +
            "    public String run(){\n" +
            "        return \"right\";\n" +
            "    }\n" +
            "}";

    public static final String CODE1 = "package tmp;" +
            "import com.company.BomberInterface;"+

            "public class Test extends TestSuperClass implements BomberInterface{\n" +
            "    public Test() {\n" +
            "        System.out.println(\"construct test2\");\n" +
            "    }\n" +
            "\n" +
            "    public String run(){\n" +
            "        return \"left\";\n" +
            "    }\n" +
            "}";

    public static void main(String[] args) {

        try {
            File file = new File("D:\\Projects\\Java\\ClassLoader\\src\\tmp\\Test.java");
            File classFile = new File("D:\\Projects\\Java\\ClassLoader\\out\\production\\ClassLoader\\tmp\\Test.class");
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            Files.write(file.toPath(), CODE.getBytes(StandardCharsets.UTF_8));
            compiler.run(null, null, null, file.getPath());

            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classFile.toURI().toURL()});
            Test newInstance = (Test) classLoader.loadClass("tmp.Test").newInstance(); //(Test) Class.forName("tmp.Test", true, classLoader).newInstance();
            System.out.println(newInstance.run());

            Files.write(file.toPath(), CODE1.getBytes(StandardCharsets.UTF_8));
            compiler.run(null, null, null, file.getPath());

            URLClassLoader classLoader1= URLClassLoader.newInstance(new URL[]{classFile.toURI().toURL()});
            Test newInstance1 = (Test) classLoader1.loadClass("tmp.Test").newInstance();// Class.forName("tmp.Test", true, classLoader1).newInstance();
            System.out.println(newInstance1.run());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    public static void main(String[] args) throws
//            ClassNotFoundException,
//            IllegalAccessException,
//            InstantiationException {
//
//        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
//        MyClassLoader classLoader = new MyClassLoader(parentClassLoader);
//        Class myObjectClass = classLoader.loadClass("tmp.Test");
//
//        BomberInterface       object1 =
//                (BomberInterface) myObjectClass.newInstance();
//        BomberInterface       object3 =
//                (BomberInterface) myObjectClass.newInstance();
//
//        TestSuperClass object2 =
//                (TestSuperClass) myObjectClass.newInstance();
//
//        //create new class loader so classes can be reloaded.
//        classLoader = new MyClassLoader(parentClassLoader);
//        myObjectClass = classLoader.loadClass("tmp.Test");
//        object1 = (BomberInterface) myObjectClass.newInstance();
//
//        File file = new File("D:\\Projects\\Java\\ClassLoader\\src\\tmp\\Test.java");
//        try {
//            Files.write(file.toPath(), CODE.getBytes(StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        object3 = (BomberInterface) myObjectClass.newInstance();
//        object2 = (TestSuperClass) myObjectClass.newInstance();
////
//    }

}