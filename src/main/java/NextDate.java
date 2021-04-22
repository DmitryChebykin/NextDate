import java.time.LocalDate;
import java.util.Scanner;

public class NextDate {
    /**
     * Class NextDate is for getting the value of the next date.
     * The date to be examined is entered from the console by the user.
     * Valid year values range from 2021 to 9999.
     * If correct input data, the date of the next day and additionally the current system date will be displayed.
     */

    public static final int MAXIMUM_DAY_OF_MONTH = 31;
    public static final int MAXIMUM_MONTH_OF_YEAR = 12;
    public static final int[] NON_LEAP_YEAR_DAY_OF_MONTH_QUANTITY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final int[] LEAP_YEAR_DAY_OF_MONTH_QUANTITY = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        int day = getNumber("Введите день месяца:", "Неверный ввод. День - целое число в диапазоне от 1 до 31", 1, MAXIMUM_DAY_OF_MONTH);
        int month = getNumber("Введите номер месяца:", "Неверный ввод. Месяц - целое число в диапазоне от 1 до 12", 1, MAXIMUM_MONTH_OF_YEAR);
        int year = getNumber("Введите значение года (от 2021 до 9999):", "Неверный ввод. Год - целое число в диапазоне от 2021 до 9999)", 2021, 9999);
        printDatesInfo(day, month, year);
    }

    private static int getNumber(String inputMessage, String errorMessage, int minValue, int maxValue) {
        boolean isRightInput = false;

        Scanner scanner = new Scanner(System.in);

        int inputNumber = 0;

        while (!isRightInput) {
            System.out.println(inputMessage);

            if (scanner.hasNextInt()) {
                inputNumber = scanner.nextInt();

                if (inputNumber < minValue || inputNumber > maxValue) {
                    System.out.println(errorMessage);
                    continue;
                }
                isRightInput = true;
            } else {
                System.out.println(errorMessage);
                scanner.nextLine();
            }
        }

        return inputNumber;
    }

    private static boolean isYearLeap(int year) {
        return year % 4 == 0 || (year % 100 == 0 && year % 400 == 0);
    }

    private static boolean isNextDayInNewMonth(int day, int month) {
        return day == LEAP_YEAR_DAY_OF_MONTH_QUANTITY[month - 1] || day == NON_LEAP_YEAR_DAY_OF_MONTH_QUANTITY[month - 1];
    }

    private static boolean isNextDayInNewYear(int day, int month) {
        return month == 12 && isNextDayInNewMonth(day, month);
    }

    private static boolean isInputDateRight(int day, int month, int year) {
        if (isYearLeap(year) && day <= LEAP_YEAR_DAY_OF_MONTH_QUANTITY[month - 1]) {
            return true;
        } else {
            return !isYearLeap(year) && day <= NON_LEAP_YEAR_DAY_OF_MONTH_QUANTITY[month - 1];
        }
    }

    private static void printDatesInfo(int day, int month, int year) {
        if (!isInputDateRight(day, month, year)) {
            System.out.println("Введенная дата не соответствует календарю. Вывод следующей даты невозможен.");
        } else {
            System.out.printf("Вы ввели дату - %d %d %d%n", day, month, year);

            int[] nextDate = nextDate(day, month, year);
            System.out.printf("Следующая дата - %d %d %d%n", nextDate[0], nextDate[1], nextDate[2]);

            LocalDate date = LocalDate.now();
            System.out.printf("Текущая системная дата %d %d %d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        }
    }

    private static int[] nextDate(int day, int month, int year) {
        if (isNextDayInNewYear(day, month)) {
            return new int[]{1, 1, year + 1};
        } else if (isNextDayInNewMonth(day, month)) {
            return new int[]{1, month + 1, year};
        } else {
            return new int[]{day + 1, month, year};
        }
    }
}