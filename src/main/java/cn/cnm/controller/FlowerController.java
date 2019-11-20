package cn.cnm.controller;

import cn.cnm.mapper.FlowerMapper;
import cn.cnm.pojo.Flower;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lele
 * @version 1.0
 * @Description Mapper测试类， 跳过了Service直接使用
 * @Email 414955507@qq.com
 * @date 2019/11/20 22:53
 */
@RestController
public class FlowerController {
    @Resource
    private FlowerMapper flowerMapper;

    @GetMapping("/flower/{id}")
    // @PathVariable将URL上的参数id绑定到右侧的参数中
    public Flower selectById(@PathVariable("id") Integer id) {
        return flowerMapper.selectById(id);
    }
}
