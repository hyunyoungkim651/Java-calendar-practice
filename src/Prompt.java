import java.util.Scanner;

public class Prompt {
    public int parseDay(String week) {
        if (week.equals("일")) {
            return 0;
        } else if (week.equals("월")) {
            return 1;
        } else if (week.equals("화")) {
            return 2;
        }else if (week.equals("수")) {
            return 3;
        }else if (week.equals("목")) {
            return 4;
        }else if (week.equals("금")) {
            return 5;
        } else if (week.equals("토")) {
            return 6;
        } else {
            return 0;
        }
    }


    public void runPrompt() {
        printMenu();
        Scanner sc = new Scanner(System.in);
        Calendar cal = new Calendar();

        boolean isLoop = true;
        while (isLoop) {
            System.out.println("명령 (1, 2, 3, h, q)");
            System.out.print("> ");
            String cmd = sc.next();
            switch (cmd) {
                case "1":
                    cmdRegister(sc, cal);
                    break;
                case "2":
                    cmdSearch(sc, cal);
                    break;
                case "3":
                    cmdCal(sc, cal);
                    break;
                case "h":
                    printMenu();
                    break;
                case "q":
                    isLoop = false;
                    break;
            }
        }
//            System.out.println("첫번째 요일을 입력하세요. (일, 월, 화, 수, 목, 금, 토)");
//            System.out.print("WEEKDAY> ");

//            String weekday = sc.next();
//            int weekdays = parseDay(weekday);
    }

    private void cmdCal(Scanner sc, Calendar cal) {
        System.out.println("년도를 입력하시오.");
        System.out.print("YEAR> ");
        int year = sc.nextInt();

        System.out.println("달을 입력하시오.");
        System.out.print("MONTH> ");
        int month = sc.nextInt();

        if (month > 12 || month < 1) {
            System.out.println("잘못된 입력입니다.");
            return;
        }

        cal.printCalendar(year, month);
    }

    private void cmdSearch(Scanner sc, Calendar cal) {
        System.out.println("[일정 검색]");
        System.out.println("[날짜를 입력해 주세요(yyyy-MM--dd).]");
        String date = sc.next();
        PlanItem planItem;
        planItem = cal.searchPlan(date);
        if (planItem != null) {
            System.out.println(planItem.detail);
        } else {
            System.out.println("일정이 없습니다.");
        }
    }

    private void cmdRegister(Scanner sc, Calendar cal) {
        System.out.println("[새 일정 등록]");
        System.out.println("[날짜를 입력해 주세요(yyyy-MM--dd).]");
        String date = sc.next();
        String text = "";
        sc.nextLine(); // Enter 값을 없애주기 위해
        System.out.println("일정을 입력해 주세요.");
        text = sc.nextLine();

        cal.registerPlan(date, text);
    }

    private void printMenu() {
        System.out.println("+----------------------+\n" +
                "| 1. 일정 등록           \n" +
                "| 2. 일정 검색           \n" +
                "| 3. 달력 보기\n" +
                "| h. 도움말 q. 종료\n" +
                "+----------------------+");
    }

}
