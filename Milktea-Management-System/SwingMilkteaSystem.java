import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SwingMilkteaSystem extends JFrame {
    private Connection conn;
    private JTabbedPane tabbedPane;
    private DefaultTableModel productTableModel;
    private DefaultTableModel orderTableModel;
    private DefaultTableModel orderItemTableModel;
    private JTable productTable;
    private JTable orderTable;
    private JTable orderItemTable;
    private JTextField productNameField;
    private JTextField productPriceField;
    private JTextField productStockField;
    private JTextField customerNameField;
    private JTextField customerPhoneField;
    private JComboBox<String> productComboBox;
    private JTextField quantityField;
    private List<OrderItem> currentOrderItems = new ArrayList<>();
    private double totalAmount = 0;
    private JLabel totalLabel;
    private int selectedProductId = -1;
    private int selectedOrderId = -1;

    public SwingMilkteaSystem() {
        super("奶茶点单管理系统");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 初始化数据库
        initDatabase();
        
        // 初始化UI
        initUI();
        
        // 加载数据
        loadProducts();
        loadOrders();
    }

    private void initDatabase() {
        try {
            // 连接SQLite数据库（自动创建）
            String url = "jdbc:sqlite:milktea_system.db";
            conn = DriverManager.getConnection(url);
            
            // 创建表
            Statement stmt = conn.createStatement();
            
            // 产品表
            String productTable = "CREATE TABLE IF NOT EXISTS product ("
                    + "product_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "product_name TEXT NOT NULL,"
                    + "price REAL NOT NULL,"
                    + "stock INTEGER DEFAULT 0,"
                    + "status INTEGER DEFAULT 1)";
            stmt.execute(productTable);
            
            // 订单表
            String orderTable = "CREATE TABLE IF NOT EXISTS orders ("
                    + "order_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "customer_name TEXT,"
                    + "customer_phone TEXT,"
                    + "total_amount REAL NOT NULL,"
                    + "order_time TEXT DEFAULT CURRENT_TIMESTAMP,"
                    + "order_status INTEGER DEFAULT 1)";
            stmt.execute(orderTable);
            
            // 订单项表
            String orderItemTable = "CREATE TABLE IF NOT EXISTS order_item ("
                    + "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "order_id INTEGER,"
                    + "product_id INTEGER,"
                    + "product_name TEXT,"
                    + "quantity INTEGER NOT NULL,"
                    + "unit_price REAL NOT NULL,"
                    + "subtotal REAL NOT NULL,"
                    + "FOREIGN KEY (order_id) REFERENCES orders(order_id),"
                    + "FOREIGN KEY (product_id) REFERENCES product(product_id))";
            stmt.execute(orderItemTable);
            
            // 插入初始产品数据
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM product");
            if (rs.next() && rs.getInt(1) == 0) {
                String[] products = {
                    "INSERT INTO product (product_name, price, stock) VALUES ('珍珠奶茶', 15.0, 100)",
                    "INSERT INTO product (product_name, price, stock) VALUES ('波霸奶茶', 16.0, 100)",
                    "INSERT INTO product (product_name, price, stock) VALUES ('草莓奶盖茶', 18.0, 100)",
                    "INSERT INTO product (product_name, price, stock) VALUES ('芒果冰沙', 17.0, 100)",
                    "INSERT INTO product (product_name, price, stock) VALUES ('柠檬绿茶', 14.0, 100)"
                };
                for (String sql : products) {
                    stmt.executeUpdate(sql);
                }
            }
            
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "数据库初始化失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initUI() {
        tabbedPane = new JTabbedPane();
        add(tabbedPane);
        
        // 产品管理标签页
        tabbedPane.addTab("产品管理", createProductPanel());
        
        // 订单管理标签页
        tabbedPane.addTab("订单管理", createOrderPanel());
        
        // 新增订单标签页
        tabbedPane.addTab("新增订单", createNewOrderPanel());
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 产品表格
        String[] columnNames = {"产品ID", "产品名称", "价格", "库存", "状态"};
        productTableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(productTableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // 产品操作面板
        JPanel controlPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        
        JButton addButton = new JButton("新增产品");
        addButton.addActionListener(e -> showAddProductDialog());
        controlPanel.add(addButton);
        
        JButton editButton = new JButton("编辑产品");
        editButton.addActionListener(e -> showEditProductDialog());
        controlPanel.add(editButton);
        
        JButton deleteButton = new JButton("删除产品");
        deleteButton.addActionListener(e -> deleteProduct());
        controlPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> loadProducts());
        controlPanel.add(refreshButton);
        
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        // 选择产品事件
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                selectedProductId = (int) productTableModel.getValueAt(productTable.getSelectedRow(), 0);
            }
        });
        
        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 订单表格
        String[] orderColumnNames = {"订单ID", "客户名称", "客户电话", "总金额", "订单时间", "状态"};
        orderTableModel = new DefaultTableModel(orderColumnNames, 0);
        orderTable = new JTable(orderTableModel);
        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        panel.add(orderScrollPane, BorderLayout.NORTH);
        
        // 订单项表格
        String[] orderItemColumnNames = {"产品名称", "数量", "单价", "小计"};
        orderItemTableModel = new DefaultTableModel(orderItemColumnNames, 0);
        orderItemTable = new JTable(orderItemTableModel);
        JScrollPane itemScrollPane = new JScrollPane(orderItemTable);
        panel.add(itemScrollPane, BorderLayout.CENTER);
        
        // 订单操作面板
        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        JButton viewButton = new JButton("查看订单项");
        viewButton.addActionListener(e -> viewOrderItems());
        controlPanel.add(viewButton);
        
        JButton completeButton = new JButton("完成订单");
        completeButton.addActionListener(e -> completeOrder());
        controlPanel.add(completeButton);
        
        JButton refreshOrderButton = new JButton("刷新");
        refreshOrderButton.addActionListener(e -> loadOrders());
        controlPanel.add(refreshOrderButton);
        
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        // 选择订单事件
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && orderTable.getSelectedRow() != -1) {
                selectedOrderId = (int) orderTableModel.getValueAt(orderTable.getSelectedRow(), 0);
            }
        });
        
        return panel;
    }

    private JPanel createNewOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 客户信息面板
        JPanel customerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        customerPanel.setBorder(BorderFactory.createTitledBorder("客户信息"));
        
        customerPanel.add(new JLabel("客户名称:"));
        customerNameField = new JTextField();
        customerPanel.add(customerNameField);
        
        customerPanel.add(new JLabel("客户电话:"));
        customerPhoneField = new JTextField();
        customerPanel.add(customerPhoneField);
        
        panel.add(customerPanel, BorderLayout.NORTH);
        
        // 产品选择面板
        JPanel productSelectPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        productSelectPanel.setBorder(BorderFactory.createTitledBorder("添加产品"));
        
        productSelectPanel.add(new JLabel("选择产品:"));
        productComboBox = new JComboBox<>();
        loadProductComboBox();
        productSelectPanel.add(productComboBox);
        
        productSelectPanel.add(new JLabel("数量:"));
        quantityField = new JTextField(5);
        quantityField.setText("1");
        productSelectPanel.add(quantityField);
        
        JButton addItemButton = new JButton("添加到订单");
        addItemButton.addActionListener(e -> addOrderItem());
        productSelectPanel.add(addItemButton);
        
        panel.add(productSelectPanel, BorderLayout.CENTER);
        
        // 订单汇总面板
        JPanel orderSummaryPanel = new JPanel(new BorderLayout());
        orderSummaryPanel.setBorder(BorderFactory.createTitledBorder("订单汇总"));
        
        // 订单商品列表
        JTextArea orderItemsArea = new JTextArea(10, 50);
        orderItemsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderItemsArea);
        orderSummaryPanel.add(scrollPane, BorderLayout.CENTER);
        
        // 总计和操作按钮
        JPanel totalPanel = new JPanel(new BorderLayout());
        
        JPanel totalInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalLabel = new JLabel("总计: ￥0.00");
        totalInfoPanel.add(totalLabel);
        totalPanel.add(totalInfoPanel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        
        JButton clearButton = new JButton("清空订单");
        clearButton.addActionListener(e -> clearOrder());
        buttonPanel.add(clearButton);
        
        JButton submitButton = new JButton("提交订单");
        submitButton.addActionListener(e -> submitOrder());
        buttonPanel.add(submitButton);
        
        JButton resetButton = new JButton("重置");
        resetButton.addActionListener(e -> resetOrderForm());
        buttonPanel.add(resetButton);
        
        totalPanel.add(buttonPanel, BorderLayout.SOUTH);
        orderSummaryPanel.add(totalPanel, BorderLayout.SOUTH);
        
        panel.add(orderSummaryPanel, BorderLayout.SOUTH);
        
        return panel;
    }

    private void loadProducts() {
        productTableModel.setRowCount(0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product");
            
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int status = rs.getInt("status");
                String statusText = status == 1 ? "在售" : "下架";
                
                productTableModel.addRow(new Object[]{id, name, price, stock, statusText});
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "加载产品失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadOrders() {
        orderTableModel.setRowCount(0);
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM orders ORDER BY order_time DESC");
            
            while (rs.next()) {
                int id = rs.getInt("order_id");
                String customerName = rs.getString("customer_name");
                String customerPhone = rs.getString("customer_phone");
                double total = rs.getDouble("total_amount");
                String time = rs.getString("order_time");
                int status = rs.getInt("order_status");
                String statusText = status == 1 ? "待完成" : "已完成";
                
                orderTableModel.addRow(new Object[]{id, customerName, customerPhone, total, time, statusText});
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "加载订单失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProductComboBox() {
        productComboBox.removeAllItems();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT product_id, product_name FROM product WHERE status = 1");
            
            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("product_name");
                productComboBox.addItem(id + ": " + name);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "加载产品列表失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog(this, "新增产品", true);
        dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        dialog.add(panel);
        
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"在售", "下架"});
        
        panel.add(new JLabel("产品名称:"));
        panel.add(nameField);
        panel.add(new JLabel("价格:"));
        panel.add(priceField);
        panel.add(new JLabel("库存:"));
        panel.add(stockField);
        panel.add(new JLabel("状态:"));
        panel.add(statusComboBox);
        
        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());
                int status = statusComboBox.getSelectedIndex();
                
                String sql = "INSERT INTO product (product_name, price, stock, status) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, stock);
                pstmt.setInt(4, status);
                pstmt.executeUpdate();
                
                pstmt.close();
                dialog.dispose();
                loadProducts();
                loadProductComboBox();
                JOptionPane.showMessageDialog(this, "产品添加成功!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "添加产品失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dialog.dispose());
        
        panel.add(okButton);
        panel.add(cancelButton);
        
        dialog.setVisible(true);
    }

    private void showEditProductDialog() {
        if (selectedProductId == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要编辑的产品!");
            return;
        }
        
        try {
            String sql = "SELECT * FROM product WHERE product_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectedProductId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int status = rs.getInt("status");
                
                JDialog dialog = new JDialog(this, "编辑产品", true);
                dialog.setSize(300, 250);
                dialog.setLocationRelativeTo(this);
                
                JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
                dialog.add(panel);
                
                JTextField nameField = new JTextField(name);
                JTextField priceField = new JTextField(String.valueOf(price));
                JTextField stockField = new JTextField(String.valueOf(stock));
                JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"在售", "下架"});
                statusComboBox.setSelectedIndex(status);
                
                panel.add(new JLabel("产品名称:"));
                panel.add(nameField);
                panel.add(new JLabel("价格:"));
                panel.add(priceField);
                panel.add(new JLabel("库存:"));
                panel.add(stockField);
                panel.add(new JLabel("状态:"));
                panel.add(statusComboBox);
                
                JButton okButton = new JButton("确定");
                okButton.addActionListener(e -> {
                    try {
                        String updateSql = "UPDATE product SET product_name = ?, price = ?, stock = ?, status = ? WHERE product_id = ?";
                        PreparedStatement updatePstmt = conn.prepareStatement(updateSql);
                        updatePstmt.setString(1, nameField.getText());
                        updatePstmt.setDouble(2, Double.parseDouble(priceField.getText()));
                        updatePstmt.setInt(3, Integer.parseInt(stockField.getText()));
                        updatePstmt.setInt(4, statusComboBox.getSelectedIndex());
                        updatePstmt.setInt(5, selectedProductId);
                        updatePstmt.executeUpdate();
                        
                        updatePstmt.close();
                        dialog.dispose();
                        loadProducts();
                        loadProductComboBox();
                        JOptionPane.showMessageDialog(this, "产品编辑成功!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "编辑产品失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    }
                });
                
                JButton cancelButton = new JButton("取消");
                cancelButton.addActionListener(e -> dialog.dispose());
                
                panel.add(okButton);
                panel.add(cancelButton);
                
                dialog.setVisible(true);
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "加载产品信息失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        if (selectedProductId == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的产品!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该产品吗?", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM product WHERE product_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, selectedProductId);
                pstmt.executeUpdate();
                
                pstmt.close();
                loadProducts();
                loadProductComboBox();
                JOptionPane.showMessageDialog(this, "产品删除成功!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "删除产品失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewOrderItems() {
        if (selectedOrderId == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要查看的订单!");
            return;
        }
        
        orderItemTableModel.setRowCount(0);
        try {
            String sql = "SELECT product_name, quantity, unit_price, subtotal FROM order_item WHERE order_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectedOrderId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double unitPrice = rs.getDouble("unit_price");
                double subtotal = rs.getDouble("subtotal");
                
                orderItemTableModel.addRow(new Object[]{productName, quantity, unitPrice, subtotal});
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "加载订单项失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void completeOrder() {
        if (selectedOrderId == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要完成的订单!");
            return;
        }
        
        try {
            String sql = "UPDATE orders SET order_status = 0 WHERE order_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, selectedOrderId);
            pstmt.executeUpdate();
            
            pstmt.close();
            loadOrders();
            JOptionPane.showMessageDialog(this, "订单已完成!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "完成订单失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addOrderItem() {
        try {
            String selectedProduct = (String) productComboBox.getSelectedItem();
            int productId = Integer.parseInt(selectedProduct.split(":")[0]);
            int quantity = Integer.parseInt(quantityField.getText());
            
            // 获取产品信息
            String sql = "SELECT product_name, price, stock FROM product WHERE product_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                
                if (quantity > stock) {
                    JOptionPane.showMessageDialog(this, "库存不足!");
                    return;
                }
                
                double subtotal = price * quantity;
                
                // 添加到当前订单
                OrderItem item = new OrderItem(productId, productName, quantity, price, subtotal);
                currentOrderItems.add(item);
                
                // 更新总计
                totalAmount += subtotal;
                totalLabel.setText("总计: ￥" + String.format("%.2f", totalAmount));
                
                // 更新库存
                String updateStockSql = "UPDATE product SET stock = stock - ? WHERE product_id = ?";
                PreparedStatement updatePstmt = conn.prepareStatement(updateStockSql);
                updatePstmt.setInt(1, quantity);
                updatePstmt.setInt(2, productId);
                updatePstmt.executeUpdate();
                
                updatePstmt.close();
                JOptionPane.showMessageDialog(this, "产品已添加到订单!");
            }
            
            rs.close();
            pstmt.close();
            loadProducts(); // 刷新产品列表以更新库存
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "添加产品失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearOrder() {
        currentOrderItems.clear();
        totalAmount = 0;
        totalLabel.setText("总计: ￥0.00");
    }

    private void submitOrder() {
        if (currentOrderItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "订单不能为空!");
            return;
        }
        
        String customerName = customerNameField.getText();
        String customerPhone = customerPhoneField.getText();
        
        try {
            conn.setAutoCommit(false);
            
            // 插入订单
            String orderSql = "INSERT INTO orders (customer_name, customer_phone, total_amount) VALUES (?, ?, ?)";
            PreparedStatement orderPstmt = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            orderPstmt.setString(1, customerName);
            orderPstmt.setString(2, customerPhone);
            orderPstmt.setDouble(3, totalAmount);
            orderPstmt.executeUpdate();
            
            // 获取订单ID
            ResultSet rs = orderPstmt.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            
            // 插入订单项
            String itemSql = "INSERT INTO order_item (order_id, product_id, product_name, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement itemPstmt = conn.prepareStatement(itemSql);
            
            for (OrderItem item : currentOrderItems) {
                itemPstmt.setInt(1, orderId);
                itemPstmt.setInt(2, item.getProductId());
                itemPstmt.setString(3, item.getProductName());
                itemPstmt.setInt(4, item.getQuantity());
                itemPstmt.setDouble(5, item.getUnitPrice());
                itemPstmt.setDouble(6, item.getSubtotal());
                itemPstmt.addBatch();
            }
            
            itemPstmt.executeBatch();
            
            conn.commit();
            
            orderPstmt.close();
            itemPstmt.close();
            
            // 重置订单
            resetOrderForm();
            
            // 刷新订单列表
            loadOrders();
            
            JOptionPane.showMessageDialog(this, "订单提交成功!");
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "提交订单失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void resetOrderForm() {
        customerNameField.setText("");
        customerPhoneField.setText("");
        clearOrder();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // 加载SQLite驱动
                Class.forName("org.sqlite.JDBC");
                
                SwingMilkteaSystem system = new SwingMilkteaSystem();
                system.setVisible(true);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "SQLite驱动加载失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }

    // 内部类：订单项
    private class OrderItem {
        private int productId;
        private String productName;
        private int quantity;
        private double unitPrice;
        private double subtotal;
        
        public OrderItem(int productId, String productName, int quantity, double unitPrice, double subtotal) {
            this.productId = productId;
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subtotal = subtotal;
        }
        
        public int getProductId() { return productId; }
        public String getProductName() { return productName; }
        public int getQuantity() { return quantity; }
        public double getUnitPrice() { return unitPrice; }
        public double getSubtotal() { return subtotal; }
    }
}