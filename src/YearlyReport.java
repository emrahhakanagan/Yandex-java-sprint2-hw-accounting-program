import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {

    public ArrayList<Year> yearly = new ArrayList<>();
    Chars chars = new Chars();
    FileReader fileReader = new FileReader();
    public String path;
    boolean flag = false;
    String pathDirectory = "resources/";

    public int yearNo() { // получаем название файла Годового Отчета из директории
        int noYear = 0;

        File fileYearlyReport = new File(pathDirectory); // создаем объект класса File
        String[] listFilesYearlyReport = fileYearlyReport.list(); // список файлов сохраняем в массив-переменную
        for (String nameFile : listFilesYearlyReport) { // циклом for берем файлы на проверку
            String checkNameFileYearlyReport = new String(nameFile.toCharArray(), 0, 1); // определяем первую букву
            if (!checkNameFileYearlyReport.equals("y")) { // проверяем что название начинается ли с "y", если нет цикл повторяет проверку
                continue;
            }
            noYear = Integer.parseInt(new String(nameFile.toCharArray(), 2, 4)); // сохраняем название именно где цифры года
        }
        return noYear; // возвращаем название/номер года
    }
    public void loadFile(String path) {

        String content = fileReader.readFileContentsOrNull(path);
        String[] lines = content.split("\r?\n");
        for (int i = 1; i < lines.length; i++) { // month,amount,is_expense
            String line = lines[i];
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0]);
            int amount = Integer.parseInt(parts[1]);
            Boolean isExpense = Boolean.valueOf(parts[2]);

            Year year = new Year(month, isExpense, amount);
            flag = this.yearly.add(year);
        }
        if(flag) {
            System.out.println(chars.dataOk + " Успешно считан -> годовой отчет !");
        } else {
            System.out.println(chars.dataFalse + " Проблема при считывании годового отчета !");
        }
    }

    public void getProfitForEveryMonth() {

        HashMap<Integer, Integer> inComingForEveryMonthInYearly = new HashMap<>(); // month -> false - amount
        HashMap<Integer, Integer> expenseForEveryMonthInYearly = new HashMap<>(); // month -> true - amount

        for (Year year : yearly) {
            if (!year.isExpense) { // inComing Amount
                inComingForEveryMonthInYearly.put(year.month, year.amount);
            } else { // expense Amount
                expenseForEveryMonthInYearly.put(year.month, year.amount);
            }
        }

        int sumInComing = 0;
        int sumExpense = 0;

        System.out.println(chars.dataOutput + " Рассматриваемый год: << " + yearNo() + " >>");
        System.out.println(chars.dataOutput + " Прибыль по каждому месяцу;");
        for (Integer month : inComingForEveryMonthInYearly.keySet()) {
            int inComing = inComingForEveryMonthInYearly.get(month);
            int expense = expenseForEveryMonthInYearly.get(month);
            int profit = inComing - expense;

            sumInComing += inComingForEveryMonthInYearly.get(month);
            sumExpense += expenseForEveryMonthInYearly.get(month);

            System.out.println(chars.dataOutput + " В месяце <" + Month.convertMonth(month) + ">  >>>>  " +"Прибыль - " + profit + chars.ruble);
        }
        System.out.print("\n");
        System.out.println(chars.dataOutput + " Средний доход за все месяцы в году - " + sumInComing/12 + chars.ruble);
        System.out.println(chars.dataOutput + " Средний расход за все месяцы в году - " + sumExpense/12 + chars.ruble);
    }


}
