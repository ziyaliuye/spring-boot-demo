package cn.cnm.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author lele
 * @version 1.0
 * @Description 优雅停服请求权限控制
 * @Email 414955507@qq.com
 * @date 2019/11/24 0:48
 */
@EnableWebSecurity
public class DowntimeSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/").permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .anyRequest().authenticated().and()
                //开启basic认证，若不添加此项，将不能通过curl的basic方式传递用户信息
                .httpBasic();
    }
}
