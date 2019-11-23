package cn.cnm.security;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/23 14:04
 */
@Repository("webUserDao")
public class WebUserDaoImpl {
    // 根据用户名查询
    public WebUserDO getUserByUsername(String username) {
        if ("user".equals(username)) {
            WebUserDO webUserDO = new WebUserDO();
            webUserDO.setId(1L);
            webUserDO.setUsername("user");
            webUserDO.setPassword("123456");
            webUserDO.setSalt("334D1C2486484CBB822C6FCF45C876C8");
            return webUserDO;
        }
        return null;
    }

    // 查询用户角色
    public Set<String> listRoleByUserId(Long id) {
        Set<String> roleSet = new HashSet<>();
        roleSet.add("watch");
        return roleSet;
    }

    //  查询url所属角色
    public List<String> listRoleByUrl(String url) {
        List<String> roleList = new ArrayList<>();
        if ("/authc/watch".equals(url)) {
            roleList.add("watch");
            return roleList;
        }
        if ("/authc/speak".equals(url)) {
            roleList.add("speak");
            return roleList;
        }
        if ("/authc/walk".equals(url)) {
            roleList.add("watch");
            roleList.add("speak");
            return roleList;
        }
        return roleList;
    }
}
