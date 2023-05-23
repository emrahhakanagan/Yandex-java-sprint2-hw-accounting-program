import java.time.format.TextStyle;
import java.util.Locale;

public class Month {

    public int numberOfMonth;
    public String item_name;
    public boolean isExpense;
    public int quantity;
    public int sumOfOne;

    public Month(int numberOfMonth, String item_name, boolean isExpense, int quantity, int sumOfOne) {
        this.numberOfMonth = numberOfMonth;
        this.item_name = item_name;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.sumOfOne = sumOfOne;
    }

    static String convertMonth(int month) {
        Locale loc = Locale.forLanguageTag("ru");
        return java.time.Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, loc) .substring(0, 1).toUpperCase() +
               java.time.Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, loc) .substring(1);
    }

}
