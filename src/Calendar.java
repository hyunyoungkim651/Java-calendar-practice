import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Formattable;
import java.util.HashMap;
import java.util.Scanner;

public class Calendar {
    private static final int[] DAYS = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] LEEP_DAYS = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final String[] WEEK_DAYS = {"일", "월", "화", "수", "목", "금", "토"};
    private static final String SAVE_FILE = "calendar.dat";
    private HashMap<Date, PlanItem> planItemHashMap;

    public Calendar() {
        planItemHashMap = new HashMap<Date, PlanItem>();
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            return;
        }
        try {
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String line = s.nextLine();
                String[] words = line.split(",");
                String date = words[0];
                String detail = words[1].replaceAll("\"", "");
                System.out.println(date + ":" + detail);
                PlanItem planItem = new PlanItem(date, detail);
                planItemHashMap.put(planItem.getPlanDate(), planItem);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean isLeepYear(int year) {
        if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public int getMaxDaysOfMonth(int year, int month) {
        if (isLeepYear(year)) {
            return LEEP_DAYS[month - 1];
        } else {
            return DAYS[month - 1];
        }
    }

    public void printCalendar(int year, int month) {
        System.out.println("     <<" + year + "년 " + month + "월>>");
        System.out.println("  일 월 화 수 목 금 토");
        System.out.println("---------------------");

        int weekday = getWeekDay(year, month, 1);

        //print blank space
        for (int i = 0; i < weekday; i++) {
            System.out.print("   ");
        }

        int maxDay = getMaxDaysOfMonth(year, month);
        int count = 7 - weekday;
        int delim = (count < 7) ? count : 0;


        //print first line
        for (int i = 1; i <= count; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();

        //print second line
        for (int i = count + 1; i <= maxDay; i++) {
            System.out.printf("%3d", i);
            if (i % 7 == delim) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private int getWeekDay(int year, int month, int day) {
        // 기준 년도
        int syear = 1970;
        final int STANDARD_WEEKDAY = 4; // 1970/1/1 = thursday, 목요일

        int count = 0;

        for (int i = syear; i < year; i++) {
            int delta = isLeepYear(i) ? 366 : 365;
            count += delta;
        }

        for (int i = 1; i < month; i++) {
            int delta = getMaxDaysOfMonth(year, i);
            count += delta;
        }

        // 1월 1일은 더해줄 필요 없다.
        count += day - 1;

        int weekday = (count + STANDARD_WEEKDAY) % 7;

        return weekday;
    }

    public void registerPlan(String date, String text) {
        PlanItem p = new PlanItem(date, text);
        planItemHashMap.put(p.getPlanDate(), p);

        File file = new File(SAVE_FILE);
        String item = p.saveString();
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(item);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlanItem searchPlan(String strdate) {
        Date date = PlanItem.getDatefromString(strdate);
        return planItemHashMap.get(date);
    }

    public static void main(String[] args) {
        Prompt p = new Prompt();
        p.runPrompt();
    }
}

