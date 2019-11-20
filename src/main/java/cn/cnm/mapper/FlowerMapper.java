package cn.cnm.mapper;

import cn.cnm.pojo.Flower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author lele
 * @version 1.0
 * @Description
 * @Email 414955507@qq.com
 * @date 2019/11/20 22:49
 */
// @Mapper指定这是一个操作数据库的Mapper
@Mapper
public interface FlowerMapper {
    @Select("select * from flower where id = #{id} ")
    Flower selectById(Integer id);
}
