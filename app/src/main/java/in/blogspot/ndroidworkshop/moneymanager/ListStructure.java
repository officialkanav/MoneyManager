package in.blogspot.ndroidworkshop.moneymanager;

public class ListStructure {
    private String product,cash,username;
    ListStructure(){

    }

    public ListStructure(String product, String cash, String username) {
        this.product = product;
        this.cash = cash;
        this.username = username;
    }

    public String getProduct() {
        return product;
    }

    public String getCash() {
        return cash;
    }

    public String getUsername() {
        return username;
    }
}
