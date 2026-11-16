package vn.garaman.model;

import java.math.BigDecimal;

public class CustomerRevenue0 {
    private final Customer0 customer;
    private final long invoiceCount;
    private final BigDecimal revenue;

    public CustomerRevenue0(Customer0 customer, long invoiceCount, BigDecimal revenue) {
        this.customer = customer;
        this.invoiceCount = invoiceCount;
        this.revenue = revenue;
    }

    public Customer0 getCustomer() { return customer; }
    public long getInvoiceCount() { return invoiceCount; }
    public BigDecimal getRevenue() { return revenue; }
}
