package cn.cnm.controller;

import cn.cnm.pojo.Flower;
import cn.cnm.service.FlowerService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lele
 * @version 1.0
 * @Description Mapper测试类， 跳过了Service直接使用
 * @Email 414955507@qq.com
 * @date 2019/11/20 22:53
 */
@RestController
public class FlowerController {
    private final FlowerService flowerService;

    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/flower/{id}")
    // @PathVariable将URL上的参数id绑定到右侧的参数中
    public Flower selectById(@PathVariable("id") Integer id) {
        System.out.println("7777");
        return flowerService.selectById(id);
    }

    @GetMapping("/updateflower/{id}")
    // @PathVariable将URL上的参数id绑定到右侧的参数中
    public String updateById(@PathVariable("id") Integer id) {
        flowerService.updateById(id);
        return "id：" + id + "更新成功";
    }

    @GetMapping("/deleteflower/{id}")
    // @PathVariable将URL上的参数id绑定到右侧的参数中
    /*
     * 清空指定缓存（目前发现在service层注解无效， 需要挪到Controller层， 后续使用需先测）
     *   allEntries = true 表示删除所有缓存数据
     *   beforeInvocation = false 缓存的清除是否在方法之前执行（默认）， 如果程序出错导致退出， 缓存也会被清除
     */
    @CacheEvict(value = "flower", key = "#id", condition = "#id<5")
    public String deleteById(@PathVariable("id") Integer id) {
        flowerService.selectById(id);
        return "id：" + id + "删除成功";
    }
}
