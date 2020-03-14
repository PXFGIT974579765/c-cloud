package com.ccloud.user.repository;

import com.ccloud.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:02
 * @description：用户持久层
 * @modified By：
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    UserInfo findByPhone(String phone);

    @Modifying
    @Query(value = "UPDATE user_info SET deposit = deposit - ?2 WHERE id = ?1", nativeQuery=true)
    int charge(String customerId, BigDecimal amount);
}
