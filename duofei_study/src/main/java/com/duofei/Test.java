package com.duofei;

import com.spire.xls.CellRange;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;
import com.sun.deploy.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author duofei
 * @date 2020/6/5
 */
public class Test {
    public static void main(String[] args) {
        /*String a = "1";
        String b = null;
        if (a1(a) || b1(b) && b2(b)) {
            System.out.println("end");
        }*/
        //operExcel();
        // 使用 Lambda 表达式
        Arrays.asList(1, 2, 3).forEach( element -> System.out.println(element));
        // 使用匿名内部类
        Arrays.asList(1, 2, 3).forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer);
            }
        });

        // 完整形式
        Arrays.asList(1, 2, 3).stream().allMatch((Integer element) -> {
            return element > 0;
        });

        // 省略 数据类型，()
        Arrays.asList(1, 2, 3).stream().allMatch(element -> {
            return element > 0;
        });

        // 省略 数据类型，() 以及 {}, return 和 ;
        Arrays.asList(1, 2, 3).stream().allMatch(element -> element > 0);

        // 外部定义 Lambda 表达式
        Predicate<Integer> predicate = element -> element > 0;
        Arrays.asList(1, 2, 3).stream().allMatch(predicate);


        String name = "zhang3";
        // 如果 数组 中有任何一个 name = "zhang3" , isLegal 将为 true
        boolean isLegal = Arrays.asList(new Student("zhang3"), new Student("li4")).stream().map(Student::getName)
                .anyMatch(name::equals);
        System.out.println(isLegal);
        String[] names = {"zhang3", "li4"};
        // 将 names 转换为 student 的集合
        List<Student> allStudents = Arrays.stream(names).map(Student::newInstance).collect(Collectors.toList());
        allStudents.forEach(System.out::println);
    }


    public static boolean a1(String a1) {
        System.out.println("a1");
        return a1.equals("");
    }

    public static boolean b1(String b1) {
        System.out.println("b1");
        return b1 != null;
    }

    public static boolean b2(String b2) {
        System.out.println("b2");
        return b2.equals("");
    }

    public static void operExcel() {
        String file = "C:\\Users\\Administrator\\Desktop\\1_(11月)员工刷卡记录表.xls";
        //创建Workbook对象
        Workbook workbook = new Workbook();

        //加载Excel文档
        workbook.loadFromFile(file);

        //获取第一个工作表
        Worksheet sheet = workbook.getWorksheets().get(0);

        int rowEnd = 63;

        int colEnd = 31;

        int currentRow = 5, currentCol = 2;

        int userNameCol = 12;

        Map<String, List<List<String>>> result = new HashMap<>();
        while (currentRow < rowEnd){
            String userName = null;
            String ableUserName = sheet.get(currentRow, userNameCol).getText();
            if (ableUserName != null && !ableUserName.contains("11") && !ableUserName.contains(":")){
                userName = ableUserName;
                result.put(userName, new ArrayList<>());
            }
            currentRow = currentRow + 1;
            int cr = currentRow;
            int max = cr;
            currentCol = 2;
            while (currentCol < colEnd){
                List<String> dates = new ArrayList<>();
                while (true && cr < rowEnd) {
                    String au = sheet.get(cr, userNameCol).getText();
                    if (au != null && !au.equals("") && !au.contains("11") && !au.contains(":")){
                        break;
                    }
                    String text = null;
                    try {
                        text = sheet.get(cr, currentCol).getText();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (text != null && text.contains(":")){
                        String[] rs = text.split("\n");
                        for (String r : rs) {
                            if(r != null && !r.trim().equals("")){
                                dates.add(r);
                            }
                        }
                    }
                    cr = cr + 1;
                }
                currentCol += 1;
                if (cr > max) {
                    max = cr;
                }
                cr = currentRow;
                result.get(userName).add(dates);
            }
            currentRow = max;
        }
        String handle = handle(result);
        File file1 = new File("C:\\Users\\Administrator\\Desktop\\结果.txt");
        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\结果.txt"), handle.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String handle(Map<String, List<List<String>>> result){
        StringBuilder sb = new StringBuilder();

        result.forEach((name,temp) -> {
            sb.append("----------- " + name + "-------------").append("\r\n");
            List<Integer> one = new ArrayList<>();
            List<Integer> early = new ArrayList<>();
            List<Integer> lately = new ArrayList<>();
            int total = 0;
            int sort = 1;
            for (List<String> rs : temp) {
                if(rs.size() == 0 || rs.size() == 1){
                    one.add(sort);
                    sort += 1;
                    break;
                }
                LocalTime lt1 = LocalTime.parse(rs.get(0));
                LocalTime lt2 = LocalTime.parse(rs.get(rs.size() - 1));
                if (lt1.isBefore( LocalTime.parse("05:00"))){
                    early.add(sort);
                }
                if (lt2.isAfter( LocalTime.parse("18:00"))){
                    lately.add(sort);
                    LocalTime localTime = lt2.minusMinutes(LocalTime.parse("18:00").getMinute());
                    total += localTime.getMinute();
                }
                sort += 1;
            }
            sb.append("一天只打了一次或0次卡的号数：");
            one.forEach(o -> {
                sb.append(o).append(",");
            });
            sb.append("\r\n");
            sb.append("一天早于早上 5 点打卡的号数：");
            early.forEach(o -> {
                sb.append(o).append(",");
            });
            sb.append("\r\n");
            sb.append("一天晚于六点打卡的号数：");
            lately.forEach(o -> {
                sb.append(o).append(",");
            });
            sb.append(", 总计：").append(total).append(" 分钟");
            sb.append("\r\n");
        });
        return sb.toString();
    }
}
