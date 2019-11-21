package cn.cnm.service;

import cn.cnm.pojo.Flower;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/21 11:52
 */
public interface FlowerService {
    Flower selectById(Integer id);

    void updateById(Integer id);

    void deleteById(Integer id);
}
