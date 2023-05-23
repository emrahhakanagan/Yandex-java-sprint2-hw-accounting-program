import java.util.HashMap;
import java.util.Objects;

public class Checker {
    Chars chars = new Chars();
    public YearlyReport yearlyReport;
    public MonthlyReport monthlyReport;
    boolean check = true;

    public Checker(YearlyReport yearlyReport, MonthlyReport monthlyReport) {
        this.yearlyReport = yearlyReport;
        this.monthlyReport = monthlyReport;
    }

    public boolean filesReadingCheck() {
        boolean checkReading = true;
        boolean checkReportMonthly;
        boolean checkReportYearly;
        if (monthlyReport.monthly.isEmpty()) {
            System.out.println(chars.warningMark + " Месячные отчеты еще не считаны! Чтобы считать введите команду --> 1");
            checkReportMonthly = false;
        } else {
            checkReportMonthly = true;
        }
        if (yearlyReport.yearly.isEmpty()) {
            System.out.println(chars.warningMark + " Годовой отчет еще не считан! Чтобы считать введите команду --> 2");
            checkReportYearly = false;
        } else {
            checkReportYearly = true;
        }
        if (checkReportMonthly && checkReportYearly) {
            checkReading = true;
        } else {
            checkReading = false;
        }
        return checkReading;
    }



    public boolean check() {
        //boolean check = true;

        HashMap<Integer, Integer> inComingYearly = new HashMap<>();
        HashMap<Integer, Integer> expenseYearly = new HashMap<>();

        for (Year valueYearly : yearlyReport.yearly) {
            if(!valueYearly.isExpense) {
                    inComingYearly.put(valueYearly.month, valueYearly.amount);
            } else {
                expenseYearly.put(valueYearly.month, valueYearly.amount);
            }
        }

        HashMap<Integer, Integer> inComingPerMonth = new HashMap<>();
        HashMap<Integer, Integer> expensePerMonth = new HashMap<>();

        for (Month valueMonthly : monthlyReport.monthly) {
            if (!valueMonthly.isExpense) {
                    int sumInComingMonthly = valueMonthly.sumOfOne*valueMonthly.quantity;
                    inComingPerMonth.put(valueMonthly.numberOfMonth, inComingPerMonth.getOrDefault(valueMonthly.numberOfMonth, 0) + sumInComingMonthly);
            } else {
                int sumExpenseMonthly = valueMonthly.sumOfOne*valueMonthly.quantity;
                expensePerMonth.put(valueMonthly.numberOfMonth, expensePerMonth.getOrDefault(valueMonthly.numberOfMonth, 0) + sumExpenseMonthly);
            }
        }

        System.out.print("\n");
        System.out.println(chars.dataOutput + " Месячный Отчет;");
        for (Integer value : inComingPerMonth.keySet()) {

            System.out.println(chars.dataOutput + " В месяце <" + Month.convertMonth(value) + ">  >>>>  " + " : Доход(+) -> " +
                    inComingPerMonth.get(value) + chars.ruble +" // Расход(-) -> " + expensePerMonth.get(value) + chars.ruble);
        }

        System.out.print("\n");
        for (Integer monthNumber : inComingYearly.keySet()) {
            if(!Objects.equals(inComingYearly.get(monthNumber), inComingPerMonth.get(monthNumber))) {
                System.out.println(chars.warningMark + " В годовом отчете, месяца -" + Month.convertMonth(monthNumber) + "- Доход: " +
                        inComingYearly.get(monthNumber) + " а в месячном отчете Доход: " + inComingPerMonth.get(monthNumber));
                check = false;
            }
        }

        for (Integer monthNumber : expensePerMonth.keySet()) {
            if(!Objects.equals(expenseYearly.get(monthNumber), expensePerMonth.get(monthNumber))) {
                System.out.println(chars.warningMark + " В годовом отчете, месяца -" + Month.convertMonth(monthNumber) + "- Расход: " + expenseYearly.get(monthNumber) +
                        chars.ruble + " а в месячном отчете Расход: " + expensePerMonth.get(monthNumber) + chars.ruble);
                check = false;
            }
        }
        if (check) {
            System.out.println(chars.dataOk + " Все отчеты << по Доходу >> и << по Расходу >> соответствуют друг другу");
        }
        //System.out.println("\n");
        return check;
    }

}
