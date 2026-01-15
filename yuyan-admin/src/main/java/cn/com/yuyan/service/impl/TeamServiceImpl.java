package cn.com.yuyan.service.impl;

import cn.com.yuyan.entity.Team;
import cn.com.yuyan.mapper.TeamMapper;
import cn.com.yuyan.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Team业务逻辑实现类
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public List<Team> findAll() {
        return teamMapper.findAll();
    }

    @Override
    public List<Team> findByKeyword(String keyword) {
        return teamMapper.findByKeyword(keyword);
    }

    @Override
    public Team findById(Long id) {
        return teamMapper.findById(id);
    }

    @Override
    public int insert(Team team) {
        return teamMapper.insert(team);
    }

    @Override
    public int update(Team team) {
        return teamMapper.update(team);
    }

    @Override
    public int deleteById(Long id) {
        return teamMapper.deleteById(id);
    }
}