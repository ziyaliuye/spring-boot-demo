package cn.cnm.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author lele
 * @version 1.0
 * @Description 用户信息获取service
 * @Email 414955507@qq.com
 * @date 2019/11/23 13:05
 */
// @Service("webUserDetailsService")
public class WebUserDetailsServiceImpl implements UserDetailsService {
    private final WebUserDaoImpl webUserDao;

    public WebUserDetailsServiceImpl(@Qualifier("webUserDao") WebUserDaoImpl webUserDao) {
        this.webUserDao = webUserDao;
    }

    // 根据用户名登录
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebUserDO webUserDO = webUserDao.getUserByUsername(username);
        if (null == webUserDO) {
            throw new UsernameNotFoundException("用户登录，用户信息查询失败");
        }
        Set<String> roleSet = webUserDao.listRoleByUserId(webUserDO.getId());

        //  封装为框架使用的 userDetail
        WebUserDetail webUserDetail = new WebUserDetail(webUserDO);
        webUserDetail.setRoleSet(roleSet);
        return webUserDetail;
    }
}
