package com.milktea.service.impl;

import com.milktea.dao.ProductDAO;
import com.milktea.dao.impl.ProductDAOImpl;
import com.milktea.entity.Product;
import com.milktea.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Product findById(Integer productId) throws SQLException {
        return productDAO.findById(productId);
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) throws SQLException {
        return productDAO.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return productDAO.findAll();
    }

    @Override
    public List<Product> findActiveProducts() throws SQLException {
        return productDAO.findActiveProducts();
    }

    @Override
    public boolean addProduct(Product product) throws SQLException {
        return productDAO.add(product) > 0;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        return productDAO.update(product) > 0;
    }

    @Override
    public boolean deleteProduct(Integer productId) throws SQLException {
        return productDAO.delete(productId) > 0;
    }

    @Override
    public boolean activateProduct(Integer productId) throws SQLException {
        Product product = productDAO.findById(productId);
        if (product != null) {
            product.setStatus(1); // 1表示在售
            return productDAO.update(product) > 0;
        }
        return false;
    }

    @Override
    public boolean deactivateProduct(Integer productId) throws SQLException {
        Product product = productDAO.findById(productId);
        if (product != null) {
            product.setStatus(0); // 0表示下架
            return productDAO.update(product) > 0;
        }
        return false;
    }
}