CREATE DATABASE IF NOT EXISTS garaman_db
  CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE garaman_db;

-- Schema follows the approved GaraMan ERD. Monetary columns use DECIMAL
-- to avoid floating-point rounding in stored amounts.
CREATE TABLE IF NOT EXISTS User_0 (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(60) NOT NULL,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(120) NOT NULL,
  phone VARCHAR(30),
  address VARCHAR(255),
  role VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uq_user_username (username),
  KEY idx_user_role (role)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Customer_0 (
  id INT NOT NULL,
  license_plate VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_customer_user FOREIGN KEY (id) REFERENCES User_0(id)
    ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Supplier_0 (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  address VARCHAR(255),
  phone VARCHAR(30),
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Service_0 (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  description TEXT,
  PRIMARY KEY (id),
  KEY idx_service_name (name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Part_0 (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  price DECIMAL(12,2) NOT NULL,
  stock_quantity INT NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  KEY idx_part_name (name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Appointment_0 (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  status VARCHAR(30) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_appointment_customer_date (customer_id, `date`),
  CONSTRAINT fk_appointment_customer FOREIGN KEY (customer_id) REFERENCES Customer_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Invoice_0 (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  salesStaff_id INT NOT NULL,
  total_amount DECIMAL(14,2) NOT NULL,
  payment_date DATE NOT NULL,
  PRIMARY KEY (id),
  KEY idx_invoice_payment_date (payment_date),
  KEY idx_invoice_customer_date (customer_id, payment_date),
  CONSTRAINT fk_invoice_customer FOREIGN KEY (customer_id) REFERENCES Customer_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_invoice_sales_staff FOREIGN KEY (salesStaff_id) REFERENCES User_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS InvoiceServiceDetail_0 (
  id INT NOT NULL AUTO_INCREMENT,
  invoice_id INT NOT NULL,
  service_id INT NOT NULL,
  quantity INT NOT NULL,
  current_price DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_service_detail_invoice (invoice_id),
  KEY idx_service_detail_service (service_id),
  CONSTRAINT fk_service_detail_invoice FOREIGN KEY (invoice_id) REFERENCES Invoice_0(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_service_detail_service FOREIGN KEY (service_id) REFERENCES Service_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS InvoicePartDetail_0 (
  id INT NOT NULL AUTO_INCREMENT,
  invoice_id INT NOT NULL,
  part_id INT NOT NULL,
  quantity INT NOT NULL,
  current_price DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_part_detail_invoice (invoice_id),
  KEY idx_part_detail_part (part_id),
  CONSTRAINT fk_part_detail_invoice FOREIGN KEY (invoice_id) REFERENCES Invoice_0(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_part_detail_part FOREIGN KEY (part_id) REFERENCES Part_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ImportInvoice_0 (
  id INT NOT NULL AUTO_INCREMENT,
  supplier_id INT NOT NULL,
  warehouseStaff_id INT NOT NULL,
  import_date DATE NOT NULL,
  total_amount DECIMAL(14,2) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_import_supplier FOREIGN KEY (supplier_id) REFERENCES Supplier_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_import_warehouse_staff FOREIGN KEY (warehouseStaff_id) REFERENCES User_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ImportDetail_0 (
  id INT NOT NULL AUTO_INCREMENT,
  importInvoice_id INT NOT NULL,
  part_id INT NOT NULL,
  quantity INT NOT NULL,
  import_price DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_import_detail_invoice (importInvoice_id),
  KEY idx_import_detail_part (part_id),
  CONSTRAINT fk_import_detail_invoice FOREIGN KEY (importInvoice_id) REFERENCES ImportInvoice_0(id)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT fk_import_detail_part FOREIGN KEY (part_id) REFERENCES Part_0(id)
    ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB;

-- Demo data. The manager, sales staff, warehouse staff and customers are
-- represented by User_0 roles; only customer-specific data has a subtype row.
INSERT IGNORE INTO User_0 (id, username, password, name, phone, address, role) VALUES
  (1, 'manager1', '12345', 'Quản lý GaraMan', '0900000001', 'Hà Nội', 'Manager0'),
  (2, 'sales.demo', 'demo-not-used', 'Nhân viên bán hàng mẫu', '0900000002', 'Hà Nội', 'SalesStaff0'),
  (3, 'warehouse.demo', 'demo-not-used', 'Nhân viên kho mẫu', '0900000003', 'Hà Nội', 'WarehouseStaff0'),
  (10, 'customer1', '12345', 'Nguyễn Văn An', '0903123456', 'Hà Nội', 'Customer0'),
  (11, 'customer.binh', 'demo-not-used', 'Trần Thị Bình', '0912456789', 'Hà Nội', 'Customer0'),
  (12, 'customer.khang', 'demo-not-used', 'Lê Minh Khang', '0988222333', 'TP. Hồ Chí Minh', 'Customer0');

-- Keep the two classroom demo logins in sync when this script is rerun.
UPDATE User_0 SET username = 'manager1', password = '12345' WHERE id = 1 AND role = 'Manager0';
UPDATE User_0 SET username = 'customer1', password = '12345' WHERE id = 10 AND role = 'Customer0';

INSERT IGNORE INTO Customer_0 (id, license_plate) VALUES
  (10, '29A-12345'), (11, '30F-67890'), (12, '51H-24680');

INSERT IGNORE INTO Supplier_0 (id, name, address, phone) VALUES
  (1, 'Nhà cung cấp phụ tùng mẫu', 'Hà Nội', '02430000000');

INSERT IGNORE INTO Service_0 (id, name, price, description) VALUES
  (1, 'Thay dầu động cơ', 500000, 'Thay dầu định kỳ và kiểm tra rò rỉ cơ bản.'),
  (2, 'Cân bằng động lốp', 200000, 'Cân bằng bánh xe để giảm rung lắc.'),
  (3, 'Bảo dưỡng hệ thống phanh', 650000, 'Kiểm tra má phanh, đĩa phanh và hệ thống phanh.');

INSERT IGNORE INTO Part_0 (id, name, price, stock_quantity) VALUES
  (1, 'Lốp xe Michelin Primacy 4', 3500000, 12),
  (2, 'Cần gạt nước Bosch', 420000, 20),
  (3, 'Nhớt máy tổng hợp 5W-30', 280000, 30);

INSERT IGNORE INTO Appointment_0 (id, customer_id, `date`, `time`, status) VALUES
  (1, 10, '2026-11-20', '09:00:00', 'Confirmed'),
  (2, 11, '2026-11-21', '10:30:00', 'Pending');

INSERT IGNORE INTO Invoice_0 (id, customer_id, salesStaff_id, total_amount, payment_date) VALUES
  (1, 10, 2, 2000000, '2026-09-15'),
  (2, 10, 2, 3000000, '2026-09-28'),
  (3, 11, 2, 3200000, '2026-10-04'),
  (4, 12, 2, 800000, '2026-10-14');

INSERT IGNORE INTO InvoiceServiceDetail_0 (id, invoice_id, service_id, quantity, current_price) VALUES
  (1, 1, 1, 2, 500000), (2, 1, 2, 4, 200000),
  (3, 2, 2, 1, 200000),
  (4, 3, 3, 1, 650000),
  (5, 4, 1, 1, 500000);

INSERT IGNORE INTO InvoicePartDetail_0 (id, invoice_id, part_id, quantity, current_price) VALUES
  (1, 1, 3, 1, 200000),
  (2, 2, 1, 1, 2500000), (3, 2, 2, 1, 300000),
  (4, 3, 1, 1, 2550000),
  (5, 4, 3, 2, 150000);

INSERT IGNORE INTO ImportInvoice_0 (id, supplier_id, warehouseStaff_id, import_date, total_amount) VALUES
  (1, 1, 3, '2026-09-01', 7500000);

INSERT IGNORE INTO ImportDetail_0 (id, importInvoice_id, part_id, quantity, import_price) VALUES
  (1, 1, 1, 2, 3000000), (2, 1, 3, 5, 300000);
