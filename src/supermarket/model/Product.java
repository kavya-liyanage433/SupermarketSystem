package supermarket.model;

public class Product {
    private int productId;
    private String name;
    private double price;
    private int stockQty;
    private int categoryId;
    private int supplierId;

    public Product() {}

    public Product(int productId, String name, double price,
                   int stockQty, int categoryId, int supplierId) {
        this.productId  = productId;
        this.name       = name;
        this.price      = price;
        this.stockQty   = stockQty;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
    }

    public int    getProductId()  { return productId; }
    public void   setProductId(int productId) { this.productId = productId; }
    public String getName()       { return name; }
    public void   setName(String name) { this.name = name; }
    public double getPrice()      { return price; }
    public void   setPrice(double price) { this.price = price; }
    public int    getStockQty()   { return stockQty; }
    public void   setStockQty(int stockQty) { this.stockQty = stockQty; }
    public int    getCategoryId() { return categoryId; }
    public void   setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public int    getSupplierId() { return supplierId; }
    public void   setSupplierId(int supplierId) { this.supplierId = supplierId; }
}
