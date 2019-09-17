package model;

public class ItemDetails {
    private String useway;
    private String time;
    private String str_expenses;
    private String str_balance;
    private String date;
    private double expenses;
    private double balance;

    public ItemDetails(String useway,String date,String time, double expenses, double balance) {
        this.useway = useway;
        this.time = time;
        this.str_expenses = str_expenses;
        this.str_balance = str_balance;
        this.date = date;
        this.expenses = expenses;
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStr_expenses() {
        return str_expenses;
    }

    public void setStr_expenses(String str_expenses) {
        this.str_expenses = str_expenses;
    }

    public String getStr_balance() {
        return str_balance;
    }

    public void setStr_balance(String str_balance) {
        this.str_balance = str_balance;
    }

    public String getUseway() {
        return useway;
    }

    public void setUseway(String useway) {
        this.useway = useway;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
