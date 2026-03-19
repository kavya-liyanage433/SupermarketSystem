package supermarket.model;

public class SaleItem {
    private int saleId;
    private int productId;
    private String productName;
    private int qty;
    private double unitPrice;
    private double subtotal;

    public SaleItem(int productId, String productName,
                    int qty, double unitPrice) {
        this.productId   = productId;
        this.productName = productName;
        this.qty         = qty;
        this.unitPrice   = unitPrice;
        this.subtotal    = qty * unitPrice;
    }

    public int    getSaleId()      { return saleId; }
    public void   setSaleId(int saleId) { this.saleId = saleId; }
    public int    getProductId()   { return productId; }
    public String getProductName() { return productName; }
    public int    getQty()         { return qty; }
    public double getUnitPrice()   { return unitPrice; }
    public double getSubtotal()    { return subtotal; }
}