package cn.cnm.security;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import cn.cnm.component.RabbitMqFastJsonConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.*;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lele
 * @version 1.0
 * @Description form表单提交默认安全配置
 * @Email 414955507@qq.com
 * @date 2019/11/23 14:21
 */
@SuppressWarnings("all")
// @EnableWebSecurity
public class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {
    // 日志对象实例
    private static final Logger logger = LogManager.getLogger(RabbitMqFastJsonConverter.class);
    // 成功
    private static final String SUCCESS = "{\"result_code\": \"00000\", \"result_msg\": \"处理成功\"}";
    // 失败
    private static final String FAILED = "{\"result_code\": \"99999\", \"result_msg\": \"处理失败\"}";
    // 登录过期
    private static final String LOGIN_EXPIRE = "{\"result_code\": \"10001\", \"result_msg\": \"登录过期\"}";
    // 权限限制
    private static final String ROLE_LIMIT = "{\"result_code\": \"10002\", \"result_msg\": \"权限不足\"}";
    // 登录 URL
    private static final String LOGIN_URL = "/authc/login";
    // 登出 URL
    private static final String LOGOUT_URL = "/authc/logout";
    // 授权 URL
    private static final String AUTH_URL_REG = "/authc/**";
    // 登录用户名参数名
    private static final String LOGIN_NAME = "username";
    // 登录密码参数名
    private static final String LOGIN_PWD = "password";
    // 记住登录参数名
    private static final String REMEMBER_ME = "rememberMe";
    // token有效时间10天, 此处使用redis实现
    private static final Long TOKEN_VALID_DAYS = 10L;
    private final UserDetailsService webUserDetailsService;
    private final WebUserDaoImpl webUserDao;

    public FormWebSecurityConfig(@Qualifier(value = "webUserDetailsService") UserDetailsService webUserDetailsService, @Qualifier("webUserDao") WebUserDaoImpl webUserDao, RedisTemplate redisTemplate) {
        this.webUserDetailsService = webUserDetailsService;
        this.webUserDao = webUserDao;
        this.redisTemplate = redisTemplate;
    }

    private final RedisTemplate redisTemplate;

    // cors跨域
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.addExposedHeader("access-control-allow-methods");
        corsConfiguration.addExposedHeader("access-control-allow-headers");
        corsConfiguration.addExposedHeader("access-control-allow-origin");
        corsConfiguration.addExposedHeader("access-control-max-age");
        corsConfiguration.addExposedHeader("X-Frame-Options");

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration(AUTH_URL_REG, corsConfiguration);
        return configurationSource;
    }

    // http安全配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable();

        http
                .exceptionHandling()
                .accessDeniedHandler(new DefinedAccessDeniedHandler())
                .authenticationEntryPoint(new DefinedAuthenticationEntryPoint());

        http
                .authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .withObjectPostProcessor(new DefindeObjectPostProcessor());

        http
                .authorizeRequests()
                .antMatchers(AUTH_URL_REG).authenticated()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().permitAll();

        http
                .formLogin()
                .usernameParameter(LOGIN_NAME)
                .passwordParameter(LOGIN_PWD)
                .loginProcessingUrl(LOGIN_URL)
                .successHandler(new DefinedAuthenticationSuccessHandler())
                .failureHandler(new DefindeAuthenticationFailureHandler());

        http
                .logout()
                .logoutUrl(LOGOUT_URL)
                .invalidateHttpSession(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new DefinedLogoutSuccessHandler());

        http
                .rememberMe()
                .rememberMeParameter(REMEMBER_ME)
                .tokenRepository(new RedisTokenRepositoryImpl());

    }

    // 配置登录验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(webUserDetailsService);
        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String loginUsername = authentication.getName();
                String loginPassword = (String) authentication.getCredentials();
                logger.info("用户登录，用户名 [{}]，密码 [{}]", loginUsername, loginPassword);

                WebUserDetail webUserDetail = (WebUserDetail) webUserDetailsService.loadUserByUsername(loginUsername);
                // 此处自定义密码加密处理规则
                if (!loginPassword.equals(webUserDetail.getPassword())) {
                    throw new DisabledException("用户登录，密码错误");
                }

                return new UsernamePasswordAuthenticationToken(webUserDetail, webUserDetail.getPassword(), webUserDetail.getAuthorities());
            }

            // 支持使用此方法验证， 没有特殊处理，返回true，否则不会用这个配置进行验证
            @Override
            public boolean supports(Class<?> aClass) {
                return true;
            }
        });
    }

    // 决策管理
    private AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new UrlRoleVoter());
        return new UnanimousBased(decisionVoters);
    }

    class DefindeObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
        @Override
        public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            object.setSecurityMetadataSource(new DefinedFilterInvocationSecurityMetadataSource());
            return object;
        }
    }

    static class UrlRoleVoter implements AccessDecisionVoter<Object> {

        @Override
        public boolean supports(ConfigAttribute attribute) {
            return null != attribute.getAttribute();
        }

        @Override
        public boolean supports(Class<?> clazz) {
            return true;
        }

        @Override
        public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
            if (null == authentication) {
                return ACCESS_DENIED;
            }
            int result = ACCESS_ABSTAIN;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (ConfigAttribute attribute : attributes) {
                if (this.supports(attribute)) {
                    result = ACCESS_DENIED;
                    for (GrantedAuthority authority : authorities) {
                        if (attribute.getAttribute().equals(authority.getAuthority())) {
                            return ACCESS_GRANTED;
                        }
                    }
                }
            }
            return result;
        }
    }

    // 权限验证数据源， 此处实现是从数据库中获取URL对应的role信息
    class DefinedFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
        @Override
        public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
            String requestUrl = ((FilterInvocation) o).getRequestUrl();
            List<String> roleIds = webUserDao.listRoleByUrl(requestUrl);
            return SecurityConfig.createList(roleIds.toArray(new String[0]));
        }

        @Override
        public Collection<ConfigAttribute> getAllConfigAttributes() {
            return null;
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return FilterInvocation.class.isAssignableFrom(aClass);
        }
    }

    // 权限拒绝handler
    static class DefinedAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
            if (logger.isDebugEnabled()) {
                logger.debug("权限不足 [{}]", accessDeniedException.getMessage());
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(ROLE_LIMIT);
        }
    }

    // 授权入口， 登录过期
    static class DefinedAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            if (logger.isDebugEnabled()) {
                logger.debug("登录过期 [{}]", authException.getMessage());
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(LOGIN_EXPIRE);
        }
    }

    // 授权成功handler
    static class DefinedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            logger.info("用户登录成功 [{}]", authentication.getName());
            // 获取登录成功信息
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(SUCCESS);
        }
    }

    // 授权失败handler
    static class DefindeAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
            logger.info("用户登录失败 [{}]", exception.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(FAILED);
        }
    }

    // 注销成功hanlder
    static class DefinedLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            logger.info("注销成功 [{}]", null != authentication ? authentication.getName() : null);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(SUCCESS);
        }
    }

    /**
     * redis保存用户token
     * <p>
     * remember me
     * <p>
     * {@link org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl}
     * {@link org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices}
     * {@link org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken}
     * PersistentRememberMeToken 没有实现Serializable，无法进行序列化，自定义存储数据结构
     */
    class RedisTokenRepositoryImpl implements PersistentTokenRepository {
        @Override
        public void createNewToken(PersistentRememberMeToken token) {
            if (logger.isDebugEnabled()) {
                logger.debug("token create seriesId: [{}]", token.getSeries());
            }
            String key = generateKey(token.getSeries());
            HashMap<String, String> map = new HashMap<>();
            map.put("username", token.getUsername());
            map.put("tokenValue", token.getTokenValue());
            map.put("date", String.valueOf(token.getDate().getTime()));
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
        }

        @Override
        public void updateToken(String series, String tokenValue, Date lastUsed) {
            String key = generateKey(series);
            HashMap<String, String> map = new HashMap<>();
            map.put("tokenValue", tokenValue);
            map.put("date", String.valueOf(lastUsed.getTime()));
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
        }

        @Override
        public PersistentRememberMeToken getTokenForSeries(String seriesId) {
            String key = generateKey(seriesId);
            List<String> hashKeys = new ArrayList<>();
            hashKeys.add("username");
            hashKeys.add("tokenValue");
            hashKeys.add("date");
            List<String> hashValues = redisTemplate.opsForHash().multiGet(key, hashKeys);
            String username = hashValues.get(0);
            String tokenValue = hashValues.get(1);
            String date = hashValues.get(2);
            if (null == username || null == tokenValue || null == date) {
                return null;
            }
            long timestamp = Long.parseLong(date);
            Date time = new Date(timestamp);
            return new PersistentRememberMeToken(username, seriesId, tokenValue, time);
        }

        @Override
        public void removeUserTokens(String username) {
            if (logger.isDebugEnabled()) {
                logger.debug("token remove username: [{}]", username);
            }
            byte[] hashKey = redisTemplate.getHashKeySerializer().serialize("username");
            RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
            try (Cursor<byte[]> cursor = redisConnection.scan(ScanOptions.scanOptions().match(generateKey("*")).count(1024).build())) {
                while (cursor.hasNext()) {
                    byte[] key = cursor.next();
                    byte[] hashValue = redisConnection.hGet(key, hashKey);
                    String storeName = (String) redisTemplate.getHashValueSerializer().deserialize(hashValue);
                    if (username.equals(storeName)) {
                        redisConnection.expire(key, 0L);
                        return;
                    }
                }
            } catch (IOException ex) {
                logger.warn("token remove exception", ex);
            }
        }

        // 生成key
        private String generateKey(String series) {
            return "spring:security:rememberMe:token:" + series;
        }
    }
}
