package vn.garaman.model;

import java.math.BigDecimal;

public class InvoiceLine0 {
    private final String type;
    private final String name;
    private final int quantity;
    private final BigDecimal unitPrice;

    public InvoiceLine0(String type, String name, int quantity, BigDecimal unitPrice) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getType() { return type; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getLineTotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
