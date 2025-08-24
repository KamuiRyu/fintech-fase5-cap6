import java.util.Date;

public class Transaction {
    String type;
    double value;
    Date date;
    String description;


    public Transaction(){}

    public Transaction(String type, double value, Date date, String description){
        this.type = type;
        this.value = value;
        this.date = date;
        this.description = description;
    }

    public void addTransaction(){
        System.out.println("Adicionando receita/gasto");
    }

    public void infoTransaction(){
        System.out.println("Exibindo as informações do extrato da data");
    }

    public void updateTransaction(){
        System.out.println("Atualizando transação");
    }

    public void removeTransaction(){
        System.out.println("Removendo transação");
    }
}
