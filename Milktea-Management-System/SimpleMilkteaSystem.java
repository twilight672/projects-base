import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class SimpleMilkteaSystem extends JFrame {
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
    private int selectedProductIndex = -1;
    private int selectedOrderIndex = -1;
    private int productIdCounter = 1;
    private int orderIdCounter = 1;
    private List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SimpleMilkteaSystem() {
        super("奶茶点单管理系统");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 初始化产品数据
        initSampleProducts();
        
        // 初始化UI
        initUI();
        
        // 加载数据
        loadProducts();
    }

    private void initSampleProducts() {
        products.add(new Product(productIdCounter++, "珍珠奶茶", 15.0, 100, 1));
        products.add(new Product(productIdCounter++, "波霸奶茶", 16.0, 100, 1));
        products.add(new Product(productIdCounter++, "草莓奶盖茶", 18.0, 100, 1));
        products.add(new Product(productIdCounter++, "芒果冰沙", 17.0, 100, 1));
        products.add(new Product(productIdCounter++, "柠檬绿茶", 14.0, 100, 1));
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
                selectedProductIndex = productTable.getSelectedRow();
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
                selectedOrderIndex = orderTable.getSelectedRow();
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
        for (Product product : products) {
            String status = product.getStatus() == 1 ? "在售" : "下架";
            productTableModel.addRow(new Object[]{
                product.getProductId(),
                product.getProductName(),
                product.getPrice(),
                product.getStock(),
                status
            });
        }
    }

    private void loadOrders() {
        orderTableModel.setRowCount(0);
        for (Order order : orders) {
            String status = order.getOrderStatus() == 1 ? "待完成" : "已完成";
            orderTableModel.addRow(new Object[]{
                order.getOrderId(),
                order.getCustomerName(),
                order.getCustomerPhone(),
                order.getTotalAmount(),
                order.getOrderTime(),
                status
            });
        }
    }

    private void loadProductComboBox() {
        productComboBox.removeAllItems();
        for (Product product : products) {
            if (product.getStatus() == 1) {
                productComboBox.addItem(product.getProductId() + ": " + product.getProductName());
            }
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
                
                Product product = new Product(productIdCounter++, name, price, stock, status);
                products.add(product);
                
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
        if (selectedProductIndex == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要编辑的产品!");
            return;
        }
        
        Product product = products.get(selectedProductIndex);
        
        JDialog dialog = new JDialog(this, "编辑产品", true);
        dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        dialog.add(panel);
        
        JTextField nameField = new JTextField(product.getProductName());
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
        JTextField stockField = new JTextField(String.valueOf(product.getStock()));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"在售", "下架"});
        statusComboBox.setSelectedIndex(product.getStatus());
        
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
                product.setProductName(nameField.getText());
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setStock(Integer.parseInt(stockField.getText()));
                product.setStatus(statusComboBox.getSelectedIndex());
                
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

    private void deleteProduct() {
        if (selectedProductIndex == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要删除的产品!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除该产品吗?", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            products.remove(selectedProductIndex);
            selectedProductIndex = -1;
            loadProducts();
            loadProductComboBox();
            JOptionPane.showMessageDialog(this, "产品删除成功!");
        }
    }

    private void viewOrderItems() {
        if (selectedOrderIndex == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要查看的订单!");
            return;
        }
        
        Order order = orders.get(selectedOrderIndex);
        orderItemTableModel.setRowCount(0);
        
        for (OrderItem item : order.getOrderItems()) {
            orderItemTableModel.addRow(new Object[]{
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
            });
        }
    }

    private void completeOrder() {
        if (selectedOrderIndex == -1) {
            JOptionPane.showMessageDialog(this, "请先选择要完成的订单!");
            return;
        }
        
        Order order = orders.get(selectedOrderIndex);
        order.setOrderStatus(0);
        loadOrders();
        JOptionPane.showMessageDialog(this, "订单已完成!");
    }

    private void addOrderItem() {
        try {
            String selectedProduct = (String) productComboBox.getSelectedItem();
            int productId = Integer.parseInt(selectedProduct.split(":")[0]);
            int quantity = Integer.parseInt(quantityField.getText());
            
            // 查找产品
            Product product = null;
            for (Product p : products) {
                if (p.getProductId() == productId) {
                    product = p;
                    break;
                }
            }
            
            if (product == null) {
                JOptionPane.showMessageDialog(this, "产品不存在!");
                return;
            }
            
            if (quantity > product.getStock()) {
                JOptionPane.showMessageDialog(this, "库存不足!");
                return;
            }
            
            double subtotal = product.getPrice() * quantity;
            
            // 添加到当前订单
            OrderItem item = new OrderItem(product.getProductId(), product.getProductName(), quantity, product.getPrice(), subtotal);
            currentOrderItems.add(item);
            
            // 更新总计
            totalAmount += subtotal;
            totalLabel.setText("总计: ￥" + String.format("%.2f", totalAmount));
            
            // 更新库存
            product.setStock(product.getStock() - quantity);
            
            loadProducts(); // 刷新产品列表以更新库存
            JOptionPane.showMessageDialog(this, "产品已添加到订单!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "添加产品失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearOrder() {
        // 恢复库存
        for (OrderItem item : currentOrderItems) {
            for (Product product : products) {
                if (product.getProductId() == item.getProductId()) {
                    product.setStock(product.getStock() + item.getQuantity());
                    break;
                }
            }
        }
        
        currentOrderItems.clear();
        totalAmount = 0;
        totalLabel.setText("总计: ￥0.00");
        loadProducts();
    }

    private void submitOrder() {
        if (currentOrderItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "订单不能为空!");
            return;
        }
        
        String customerName = customerNameField.getText();
        String customerPhone = customerPhoneField.getText();
        String orderTime = dateFormat.format(new Date());
        
        // 创建订单
        Order order = new Order(orderIdCounter++, customerName, customerPhone, totalAmount, orderTime, 1, new ArrayList<>(currentOrderItems));
        orders.add(order);
        
        // 重置订单
        resetOrderForm();
        
        // 刷新订单列表
        loadOrders();
        
        JOptionPane.showMessageDialog(this, "订单提交成功!");
    }

    private void resetOrderForm() {
        customerNameField.setText("");
        customerPhoneField.setText("");
        clearOrder();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleMilkteaSystem system = new SimpleMilkteaSystem();
            system.setVisible(true);
        });
    }

    // 内部类：产品
    private class Product {
        private int productId;
        private String productName;
        private double price;
        private int stock;
        private int status;
        
        public Product(int productId, String productName, double price, int stock, int status) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.stock = stock;
            this.status = status;
        }
        
        public int getProductId() { return productId; }
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public int getStock() { return stock; }
        public int getStatus() { return status; }
        
        public void setProductName(String productName) { this.productName = productName; }
        public void setPrice(double price) { this.price = price; }
        public void setStock(int stock) { this.stock = stock; }
        public void setStatus(int status) { this.status = status; }
    }

    // 内部类：订单
    private class Order {
        private int orderId;
        private String customerName;
        private String customerPhone;
        private double totalAmount;
        private String orderTime;
        private int orderStatus;
        private List<OrderItem> orderItems;
        
        public Order(int orderId, String customerName, String customerPhone, double totalAmount, String orderTime, int orderStatus, List<OrderItem> orderItems) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.customerPhone = customerPhone;
            this.totalAmount = totalAmount;
            this.orderTime = orderTime;
            this.orderStatus = orderStatus;
            this.orderItems = orderItems;
        }
        
        public int getOrderId() { return orderId; }
        public String getCustomerName() { return customerName; }
        public String getCustomerPhone() { return customerPhone; }
        public double getTotalAmount() { return totalAmount; }
        public String getOrderTime() { return orderTime; }
        public int getOrderStatus() { return orderStatus; }
        public List<OrderItem> getOrderItems() { return orderItems; }
        
        public void setOrderStatus(int orderStatus) { this.orderStatus = orderStatus; }
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