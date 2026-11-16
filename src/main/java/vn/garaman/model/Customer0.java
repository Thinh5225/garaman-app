package vn.garaman.model;

public class Customer0 {
    private final int id;
    private final String name;
    private final String phone;
    private final String licensePlate;

    public Customer0(int id, String name, String phone, String licensePlate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.licensePlate = licensePlate;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getLicensePlate() { return licensePlate; }
}
