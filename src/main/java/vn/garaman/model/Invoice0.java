package vn.garaman.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice0 {
    private final int id;
    private final int customerId;
    private final String customerName;
    private final String licensePlate;
    private final BigDecimal totalAmount;
    private final LocalDate paymentDate;

    public Invoice0(int id, int customerId, String customerName, String licensePlate,
                    BigDecimal totalAmount, LocalDate paymentDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.licensePlate = licensePlate;
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getLicensePlate() { return licensePlate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public LocalDate getPaymentDate() { return paymentDate; }
}
