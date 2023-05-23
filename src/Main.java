import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Создаем объекты из классов
        YearlyReport yearlyReport = new YearlyReport();
        MonthlyReport monthlyReport = new MonthlyReport();
        Checker checker = new Checker(yearlyReport, monthlyReport);
        Chars chars = new Chars();

        Scanner scanner = new Scanner(System.in);
        int userInput = 1111111111; // На консуле, чтобы показать правильный текст показа меню, присваиваем по умолчанию команду
        printMenu(userInput, chars.dataFalse); // в начале программы сразу показываем Главное Меню

        // int userInput = scanner.nextInt(); - здесь "логика меню == показа меню" зависимо от ввода пользователя
        userInput = scanner.nextInt();

        while (userInput != 0) {
           if (userInput==1) {
               monthlyReport.loadFile("resources/");
           }

           if (userInput==2) {
               yearlyReport.loadFile("resources/y." + yearlyReport.yearNo() + ".csv");
           }

           if (userInput==3) {
               if(checker.filesReadingCheck()) {
                   checker.check();
               }
           }

           if (userInput==4) {
               if(checker.filesReadingCheck()) {
                   monthlyReport.getReportInComingForEveryMonthMonthly();
                   monthlyReport.getReportExpenseForEveryMonthMonthly();
               }
            }

           if (userInput==5) {
               if(checker.filesReadingCheck()) {
                   yearlyReport.getProfitForEveryMonth();
               }
           }

            printMenu(userInput, chars.dataFalse); // показываем Главное Меню ещё раз перед завершением предыдущего действия
            userInput = scanner.nextInt(); // повторное считывание данных от пользователя
        }
        System.out.println("Программа завершена");
    }

    public static void printMenu(int userInput, char inputDataFalse) {
        if (((userInput>=0) && (userInput<=5)) || userInput==1111111111) {
            System.out.println("===============================================");
            System.out.println("Пожалуйста выберите интересующиеся Вас выбор:");
        } else {
            System.out.println(inputDataFalse + " Извините, такой команды нет! Пожалуйста введите соответствующую команду:");
        }
        System.out.println("Пожалуйста введите номер желаемого меню");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Выход");
    }
}

