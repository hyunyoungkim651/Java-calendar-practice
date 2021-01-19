import java.util.Scanner;

public class Sumnumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("달을 입력하세요.");
        int month = sc.nextInt();
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        System.out.println(month+"월은 " + days[month - 1] + "일까지 있습니다.");

    }
}
