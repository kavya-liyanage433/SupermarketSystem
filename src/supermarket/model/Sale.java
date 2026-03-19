package supermarket.model;

public class Sale {
    private int saleId;
    private int customerId;
    private double totalAmount;
    private String cashierName;
    private java.util.List<supermarket.model.SaleItem> items;

    public Sale() {}

    public Sale(int customerId, double totalAmount, String cashierName) {
        this.customerId  = customerId;
        this.totalAmount = totalAmount;
        this.cashierName = cashierName;
    }

    public int    getSaleId()      {
        return saleId;
    }
    public void   setSaleId(int saleId) {
        this.saleId = saleId; 
    }
    public int    getCustomerId()  { 
        return customerId; 
    }
    public void   setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }
    public double getTotalAmount() { 
        return totalAmount; 
    }
    public void   setTotalAmount(double totalAmount) { 
        this.totalAmount = totalAmount;
    }
    public String getCashierName() { 
        return cashierName; 
    }
    public void   setCashierName(String cashierName) {
        this.cashierName = cashierName; 
    }
    public java.util.List<supermarket.model.SaleItem> getItems() { 
    return items; 
}
    public void setItems(java.util.List<supermarket.model.SaleItem> items) { 
    this.items = items; 
}
}