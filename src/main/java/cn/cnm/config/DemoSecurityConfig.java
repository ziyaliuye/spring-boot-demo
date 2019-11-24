package cn.cnm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @author lele
 * @version 1.0
 * @Description Security配置类Demo
 * @Email 414955507@qq.com
 * @date 2019/11/23 11:26
 */
/* 开启SecurityWeb的权限控制, 这个注解上面已经注解了@Configuration */
// @EnableWebSecurity
@SuppressWarnings("all")
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {
    // 配置权限规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 设置权限认证：请求/路径都默认通过
        // 先默认给所有请求放行， 有需要在去掉第26行
        http.authorizeRequests().antMatchers("/").permitAll()
                // 设置权限认证：请求前缀/security/leve1/的路径都需要VIP1角色
                .antMatchers("/security/level1/**").hasRole("VIP1")
                .antMatchers("/security/level2/**").hasRole("VIP2")
                .antMatchers("/security/level3/**").hasRole("VIP3");
        // 开启登录功能, 如果没有权限就会强制跳转登录页面（注意这里登录页面是Security自带的登录页面）
        // 如果登录失败则默认重定向到/login?error请求
        http.formLogin();
        // 开启自动注销功能, 注销后清空Session， 并默认跳转到Security的登录页面
        // logoutSuccessUrl注销成功后跳转的页面
        http.logout().logoutSuccessUrl("/");
        // 开启记住我功能, 登录页面会多出一个记住我勾选按钮， 默认有有效期14天
        http.rememberMe();
    }

    // 配置认证规则， 就是验证账户密码， 默认从内存中获取
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /* inXxx就是从内存中获取， jdbcXxx则是通过查询数据库方式从表中获取登录信息 */
        // 设置匹配的账户密码， roles该账户所属角色， 可以有多个角色
        auth.inMemoryAuthentication().withUser("user1").password("123456")
                .roles("VIP1", "VIP2", "VIP3")
                .and()
                .withUser("user2").password("123456")
                .roles("VIP1", "VIP2")
                .and()
                .withUser("user3").password("123456")
                .roles("VIP1");
    }

    /*
     *  SpringBoot2.X抛弃了NoOpPasswordEncoder
     *      要求用户保存的密码必须要使用加密算法后存储
     *      在登录验证的时候Security会将获得的密码在进行编码后再和数据库中加密后的密码进行对比
     */
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}

