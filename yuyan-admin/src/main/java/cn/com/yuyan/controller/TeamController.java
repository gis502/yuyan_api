package cn.com.yuyan.controller;

import cn.com.yuyan.entity.Team;
import cn.com.yuyan.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Team控制器，提供团队相关的REST API接口
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * 查询所有团队
     */
    @GetMapping("/all")
    public List<Team> getAllTeams() {
        return teamService.findAll();
    }

    /**
     * 根据关键词模糊查询团队
     */
    @GetMapping("/search")
    public List<Team> getTeamsByKeyword(@RequestParam String keyword) {
        return teamService.findByKeyword(keyword);
    }

    /**
     * 根据ID查询团队
     */
    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return teamService.findById(id);
    }

    /**
     * 添加团队
     */
    @PostMapping
    public int addTeam(@RequestBody Team team) {
        return teamService.insert(team);
    }

    /**
     * 更新团队
     */
    @PutMapping
    public int updateTeam(@RequestBody Team team) {
        return teamService.update(team);
    }

    /**
     * 删除团队
     */
    @DeleteMapping("/{id}")
    public int deleteTeam(@PathVariable Long id) {
        return teamService.deleteById(id);
    }
}