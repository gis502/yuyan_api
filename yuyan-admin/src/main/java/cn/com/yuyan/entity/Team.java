package cn.com.yuyan.entity;

import lombok.Data;

/**
 * 团队实体类
 */
@Data
public class Team {
    private Long id;
    private String name;
    private String region;
    private String description;  // 描述字段，默认为null
}