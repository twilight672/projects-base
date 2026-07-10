package com.milktea.dao.impl;

import com.milktea.dao.ProductDAO;
import com.milktea.entity.Product;
import com.milktea.util.DBUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    private QueryRunner queryRunner = new QueryRunner(DBUtil.getDataSource());

    @Override
    public Product findById(Integer productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        return queryRunner.query(sql, new BeanHandler<>(Product.class), productId);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) throws SQLException {
        String sql = "SELECT * FROM product WHERE category_id = ? ORDER BY product_id";
        return queryRunner.query(sql, new BeanListHandler<>(Product.class), categoryId);
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM product ORDER BY product_id";
        return queryRunner.query(sql, new BeanListHandler<>(Product.class));
    }

    @Override
    public List<Product> findActiveProducts() throws SQLException {
        String sql = "SELECT * FROM product WHERE status = 1 ORDER BY product_id";
        return queryRunner.query(sql, new BeanListHandler<>(Product.class));
    }

    @Override
    public int add(Product product) throws SQLException {
        String sql = "INSERT INTO product (product_name, category_id, price, description, image_url, status) VALUES (?, ?, ?, ?, ?, ?)";
        return queryRunner.update(sql, product.getProductName(), product.getCategoryId(), product.getPrice(),
                product.getDescription(), product.getImageUrl(), product.getStatus());
    }

    @Override
    public int update(Product product) throws SQLException {
        String sql = "UPDATE product SET product_name = ?, category_id = ?, price = ?, description = ?, image_url = ?, status = ? WHERE product_id = ?";
        return queryRunner.update(sql, product.getProductName(), product.getCategoryId(), product.getPrice(),
                product.getDescription(), product.getImageUrl(), product.getStatus(), product.getProductId());
    }

    @Override
    public int delete(Integer productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id = ?";
        return queryRunner.update(sql, productId);
    }
}