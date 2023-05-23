import java.io.File;
import java.util.*;

public class MonthlyReport {

    ArrayList<Month> monthly = new ArrayList<>();
    Chars chars = new Chars();
    FileReader fileReader = new FileReader();
    int numberOfMonth = 0;
    boolean flag = false;
    String pathDirectory = "resources/";

    public void loadFile(String path) {

       // Получаем все файлы из директории
        File fileMonthlyReport = new File(path);
        String[] listFilesMonthlyReport = fileMonthlyReport.list();

        for (String nameFile : listFilesMonthlyReport) {
            String checkNameFileMonthlyReport = new String(nameFile.toCharArray(),0,1);
            if (!checkNameFileMonthlyReport.equals("m")) { // проверяем что название начинается ли с "m", если нет цикл повторяет проверку
                continue;
            }
            path = pathDirectory + nameFile; // если название файла начинается с "m", то присваиваем полный путь файла
            numberOfMonth ++;

          //System.out.println(numberOfMonth + " -- " + path);

            String content = fileReader.readFileContentsOrNull(path);
            String[] lines = content.split("\r?\n");
            for (int i = 1; i < lines.length; i++) { // item_name,is_expense,quantity,sum_of_one
                String line = lines[i];
                String[] parts = line.split(",");
                String itemName = parts[0];
                boolean isExpense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                int sumOfOne = Integer.parseInt(parts[3]);

                Month month = new Month(numberOfMonth, itemName, isExpense, quantity, sumOfOne);
                flag = monthly.add(month);
            }
        }
        if(flag) {
            System.out.println(chars.dataOk + " Успешно считаны -> все месячные отчёты !");
        } else {
            System.out.println(chars.dataFalse + " Проблема при считывании месячных отчётов !");
        }
    }

    public void getReportInComingForEveryMonthMonthly() {
        int sumOfSalesTitle;
        int monthNumberNew;
        HashMap<Integer, HashMap<String, Integer>> monthListInComingBySales = new HashMap<>();
        for (Month month : monthly) {
            if (month.isExpense) { // Если трата, цикл повторяется до того, как найдется доход (true-доход / false-расход)
                continue;
            }
            if (!monthListInComingBySales.containsKey(month.numberOfMonth)) {
                monthListInComingBySales.put(month.numberOfMonth, new HashMap<>());
            }
            HashMap<String, Integer> titleListInComingBySales = monthListInComingBySales.get(month.numberOfMonth);
            sumOfSalesTitle = month.sumOfOne * month.quantity;
            titleListInComingBySales.put(month.item_name, sumOfSalesTitle);
        }

        int countMonth = 0;
        int maxTitle = 0;
        String nameTitle = null;
        System.out.print("\n");
        System.out.println(chars.dataOutput + " Самый прибыльный товар месяца;");

        for (HashMap<String, Integer> perMonth : monthListInComingBySales.values()) {
            countMonth++;

            Set<Map.Entry<String,Integer>> titlesWithValuesInComing = perMonth.entrySet();
            for (Map.Entry<String, Integer> setValuesInComing: titlesWithValuesInComing) { // Получаем сумму самого прибыльного товара месяца

                if (maxTitle < setValuesInComing.getValue()) { // Ищем самую большую сумму
                    maxTitle = setValuesInComing.getValue();
                    nameTitle = setValuesInComing.getKey();
                }
            }

            System.out.println(chars.dataOutput + " В месяце <" + Month.convertMonth(countMonth) + ">  >>>>  " + nameTitle + " -> " + maxTitle + chars.ruble);
            maxTitle = 0; // Обнуляем значение, чтобы сохранить сумму следующего прибыльного товара другого месяца
        }
    }

    public void getReportExpenseForEveryMonthMonthly() {
        int sumOfExpenseTitle;

        HashMap<Integer, HashMap<String, Integer>> monthListExpenseBySales = new HashMap<>();
        for (Month month : monthly) {
            if (!month.isExpense) { // Если доход, цикл повторяется до того, как найдется трата  (true-доход / false-расход
                continue;
            }
            if (!monthListExpenseBySales.containsKey(month.numberOfMonth)) {
                monthListExpenseBySales.put(month.numberOfMonth, new HashMap<>());
            }
            HashMap<String, Integer> titleListExpenseBySales = monthListExpenseBySales.get(month.numberOfMonth);
            sumOfExpenseTitle = month.sumOfOne * month.quantity;
            titleListExpenseBySales.put(month.item_name, sumOfExpenseTitle);
        }

        int countMonth = 0;
        int maxExpense = 0;
        String nameTitle = null;
        System.out.print("\n");
        System.out.println(chars.dataOutput + " Самая большая трата месяца;");
        for (HashMap<String, Integer> perMonth : monthListExpenseBySales.values()) {
            countMonth++;

            Set<Map.Entry<String, Integer>> titlesWithValuesExpense = perMonth.entrySet();
            for (Map.Entry<String, Integer> setValuesExpense : titlesWithValuesExpense) { // Получаем сумму самой большой траты месяца
                if (maxExpense < setValuesExpense.getValue()) { // Ищем самую большую сумму
                    maxExpense = setValuesExpense.getValue();
                    nameTitle = setValuesExpense.getKey();
                }
            }

            System.out.println(chars.dataOutput + " В месяце <" + Month.convertMonth(countMonth) + ">  >>>>  " + nameTitle + " -> " + maxExpense + chars.ruble);
            maxExpense = 0; // Обнуляем значение, чтобы сохранить сумму следующей большой траты другого месяца
        }
    }
}


