package cn.cnm.exception;

/**
 * @author lele
 * @version 1.0
 * @Description 自定义异常类, 仅供测试使用
 * @Email 414955507@qq.com
 * @date 2019/11/20 13:04
 */
public class UserNotExistException extends RuntimeException {
    public UserNotExistException() {
        super("用户不存在...");
    }
}
