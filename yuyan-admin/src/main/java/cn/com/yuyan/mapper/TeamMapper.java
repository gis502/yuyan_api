package cn.com.yuyan.mapper;

import cn.com.yuyan.entity.Team;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * Team数据访问层
 */
@Mapper
public interface TeamMapper {

    /**
     * 查询所有团队
     */
    List<Team> findAll();

    /**
     * 根据名称模糊查询团队
     */
    List<Team> findByKeyword(@Param("keyword") String keyword);

    /**
     * 根据ID查询团队
     */
    Team findById(@Param("id") Long id);

    /**
     * 添加团队
     */
    int insert(Team team);

    /**
     * 更新团队
     */
    int update(Team team);

    /**
     * 删除团队
     */
    int deleteById(@Param("id") Long id);

    /**
     * 检查表是否存在
     */
    int tableExists();

    /**
     * 创建team表
     */
    void createTable();
}