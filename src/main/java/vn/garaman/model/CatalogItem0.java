package vn.garaman.model;

import java.math.BigDecimal;

public class CatalogItem0 {
    private final int id;
    private final String type;
    private final String name;
    private final BigDecimal price;
    private final String description;
    private final Integer stockQuantity;

    public CatalogItem0(int id, String type, String name, BigDecimal price, String description, Integer stockQuantity) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public Integer getStockQuantity() { return stockQuantity; }
}
