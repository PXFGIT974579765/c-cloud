package com.ccloud.user.vo;

import com.ccloud.user.dataobject.UserInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author ：腾云先生
 * @date ：Created in 2020/03/13 00:34
 * @description：返回的user信息，隐藏了私密数据
 * @modified By：
 */
@Data
public class UserVo {

    private String id;

    private String username;

    private String phone;

    private String token;

    public static UserVo adapt(UserInfo userInfo){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userInfo,userVo);
        return userVo;
    }
}
