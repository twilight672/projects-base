package com.milktea.service.impl;

import com.milktea.dao.CategoryDAO;
import com.milktea.dao.ProductDAO;
import com.milktea.dao.impl.CategoryDAOImpl;
import com.milktea.dao.impl.ProductDAOImpl;
import com.milktea.entity.Category;
import com.milktea.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Category findById(Integer categoryId) throws SQLException {
        return categoryDAO.findById(categoryId);
    }

    @Override
    public List<Category> findAll() throws SQLException {
        return categoryDAO.findAll();
    }

    @Override
    public boolean addCategory(Category category) throws SQLException {
        return categoryDAO.add(category) > 0;
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDAO.update(category) > 0;
    }

    @Override
    public boolean deleteCategory(Integer categoryId) throws SQLException {
        // 检查分类下是否有产品
        List<com.milktea.entity.Product> products = productDAO.findByCategoryId(categoryId);
        if (products != null && !products.isEmpty()) {
            return false; // 分类下有产品，不能删除
        }
        return categoryDAO.delete(categoryId) > 0;
    }
}