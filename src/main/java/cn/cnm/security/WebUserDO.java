package cn.cnm.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lele
 * @version 1.0
 * @Description 数据库用户信息
 * @Email 414955507@qq.com
 * @date 2019/11/23 13:06
 */
@Data // get/set
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
@Accessors(chain = true)
        // 链式风格访问
class WebUserDO implements Serializable {

    private static final long serialVersionUID = -5667555206849000578L;
    protected Long id;
    // 用户名
    protected String username;
    // 密码
    protected String password;
    // 盐值
    protected String salt;
    // 手机号
    protected String mobile;
    // 真实姓名
    protected String realName;
    // 是否锁定：0-锁定，1-正常
    protected Boolean locked;
    protected Date gmtCreate;
    protected Date gmtModified;

}
