package cn.com.yuyan.service;

import cn.com.yuyan.entity.Team;
import java.util.List;

/**
 * Team业务逻辑接口
 */
public interface TeamService {
    /**
     * 查询所有团队
     */
    List<Team> findAll();

    /**
     * 根据关键词模糊查询团队
     */
    List<Team> findByKeyword(String keyword);

    /**
     * 根据ID查询团队
     */
    Team findById(Long id);

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
    int deleteById(Long id);
}