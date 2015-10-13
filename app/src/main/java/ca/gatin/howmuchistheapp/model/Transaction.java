package ca.gatin.howmuchistheapp.model;

/**
 * @author RGatin
 * @since 12-Oct-2015
 */
public class Transaction {
    private String type;
    private String result;
    private double amount;
    private long transaction_id;
    private String cvv2;
    private String auth_code;

    public Transaction() {}

    public Transaction(String type, String result, String card_type, double amount, long transaction_id, String cvv2, String auth_code) {
        this.type = type;
        this.result = result;
        this.amount = amount;
        this.transaction_id = transaction_id;
        this.cvv2 = cvv2;
        this.auth_code = auth_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

}
