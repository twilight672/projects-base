package com.milktea.service;

import com.milktea.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    /**
     * 根据ID查询产品
     *
     * @param productId 产品ID
     * @return 产品对象
     * @throws SQLException SQL异常
     */
    Product findById(Integer productId) throws SQLException;

    /**
     * 根据分类ID查询产品
     *
     * @param categoryId 分类ID
     * @return 产品列表
     * @throws SQLException SQL异常
     */
    List<Product> findByCategoryId(Integer categoryId) throws SQLException;

    /**
     * 查询所有产品
     *
     * @return 产品列表
     * @throws SQLException SQL异常
     */
    List<Product> findAll() throws SQLException;

    /**
     * 查询在售产品
     *
     * @return 产品列表
     * @throws SQLException SQL异常
     */
    List<Product> findActiveProducts() throws SQLException;

    /**
     * 添加产品
     *
     * @param product 产品对象
     * @return 添加成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean addProduct(Product product) throws SQLException;

    /**
     * 更新产品信息
     *
     * @param product 产品对象
     * @return 更新成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean updateProduct(Product product) throws SQLException;

    /**
     * 删除产品
     *
     * @param productId 产品ID
     * @return 删除成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean deleteProduct(Integer productId) throws SQLException;

    /**
     * 上架产品
     *
     * @param productId 产品ID
     * @return 操作成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean activateProduct(Integer productId) throws SQLException;

    /**
     * 下架产品
     *
     * @param productId 产品ID
     * @return 操作成功返回true，失败返回false
     * @throws SQLException SQL异常
     */
    boolean deactivateProduct(Integer productId) throws SQLException;
}