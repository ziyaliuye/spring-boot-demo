package cn.cnm.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lele
 * @version 1.0
 * @Description 扩展默认的UserDetails, 支持更多自定义的字段
 * @Email 414955507@qq.com
 * @date 2019/11/23 13:08
 */
@EqualsAndHashCode(callSuper = true)
@Data // get/set
@AllArgsConstructor // 全参构造
@NoArgsConstructor // 无参构造
@Accessors(chain = true) // 链式风格访问
public class WebUserDetail extends WebUserDO implements UserDetails {
    private static final long serialVersionUID = 1722355548410670790L;
    //  角色
    private Set<String> roleSet;

    // 获取权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        将角色信息封装为框架要求格式
         */
        if (roleSet == null) {
            return null;
        }
        return roleSet.stream().map(
                SimpleGrantedAuthority::new
        ).collect(Collectors.toSet());
    }

    WebUserDetail(WebUserDO webUserDO) {
        super.id = webUserDO.getId();
        super.username = webUserDO.getUsername();
        super.password = webUserDO.getPassword();
        super.salt = webUserDO.getSalt();
        super.mobile = webUserDO.getMobile();
        super.realName = webUserDO.getRealName();
        super.locked = webUserDO.getLocked();
        super.gmtCreate = webUserDO.getGmtCreate();
        super.gmtModified = webUserDO.getGmtModified();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
