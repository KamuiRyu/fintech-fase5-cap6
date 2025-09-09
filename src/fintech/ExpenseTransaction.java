package fintech;

import java.util.Date;

public class ExpenseTransaction extends Transaction{
    private String category;

    public ExpenseTransaction(int id, String description, double amount, Date occuredAt, String category){
        super(id, description, amount, occuredAt);
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    @Override
    public void processTransaction(){
        System.out.println("Gasto com " + getCategory() + " no valor de: R$ " + getAmount());
    }
}
