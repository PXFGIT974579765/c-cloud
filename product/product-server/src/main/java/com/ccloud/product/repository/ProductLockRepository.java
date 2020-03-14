package com.ccloud.product.repository;

import com.ccloud.product.dataobject.ProductLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/12 23:04
 * @description：商品持久层
 * @modified By：
 */
public interface ProductLockRepository extends JpaRepository<ProductLock, Integer> {

    /**
     * 功能描述:
     * // 让数据立刻写到数据库，防止多实例高并发时会出现问题
     *
     * @Author: 腾云先生
     * @Date: 2020/03/14 15:36
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "insert ignore into product_lock(product_id,phone) values(:#{#lock.productId},:#{#lock.phone})", nativeQuery = true)
    int savetIngore(ProductLock lock);

    @Modifying
    @Query(value = "delete from product_lock  WHERE product_id = ?1 and phone = ?2", nativeQuery = true)
    int unLockProduct(String productId, String phone);
}