package cn.com.yuyan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/teams")
    public List<Map<String, String>> teams() {
        List<Map<String, String>> teamList = new ArrayList<>();
        
        Map<String, String> team1 = new HashMap<>();
        team1.put("id", "1");
        team1.put("name", "北京飞马航遥科技有限公司");
        team1.put("region", "北京");
        team1.put("description", "北京飞马航遥科技有限公司成立于2017年5月23日，法定代表人为张世杰，总部位于北京市海淀区建枫路（南延）6号院，注册资本1000万元人民币，系深圳飞马机器人股份有限公司全资子公司。公司主要从事技术开发、测绘服务、智能无人飞行器制造及数据处理业务，下设3家分支机构，2024年员工27人，税务信用等级连续四年获评A级。");
        teamList.add(team1);
        
        Map<String, String> team2 = new HashMap<>();
        team2.put("id", "2");
        team2.put("name", "北京神州数码有限公司");
        team2.put("region", "北京");
        team2.put("description", "");
        teamList.add(team2);
        
        Map<String, String> team3 = new HashMap<>();
        team3.put("id", "3");
        team3.put("name", "北京未来智能科技有限公司");
        team3.put("region", "北京");
        team3.put("description", "");
        teamList.add(team3);
        
        return teamList;
    }
}
