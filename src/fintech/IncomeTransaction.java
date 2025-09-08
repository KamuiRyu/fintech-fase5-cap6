import java.util.Date;

public class IncomeTransaction extends Transaction{
    private String source;

    public IncomeTransaction(int id, String description, double amount, Date occuredAt, String source){
        super(id, description,amount, occuredAt);
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public void processTransaction(){
        System.out.println("Receita: R$ +" + getAmount() + " recebido de " + getSource());
    }
}