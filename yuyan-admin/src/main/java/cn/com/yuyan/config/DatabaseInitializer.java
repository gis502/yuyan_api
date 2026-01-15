package cn.com.yuyan.config;

import cn.com.yuyan.entity.Team;
import cn.com.yuyan.mapper.TeamMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库初始化配置
 * 在项目启动时检查team表是否存在，如果不存在则创建表并插入默认数据
 */
@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private TeamMapper teamMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始检查数据库表结构...");
        
        // 检查team表是否存在
        int tableExists = teamMapper.tableExists();
        
        if (tableExists == 0) {
            log.info("team表不存在，正在创建...");
            teamMapper.createTable();
            log.info("team表创建完成");
            
            // 插入默认数据
            insertDefaultData();
            log.info("默认数据插入完成");
        } else {
            log.info("team表已存在，跳过创建");
        }
        
        log.info("数据库初始化完成");
    }

    /**
     * 插入默认团队数据
     */
    private void insertDefaultData() {
        log.info("开始插入默认团队数据...");
        
        List<Team> allTeams = new ArrayList<>();
        
        // 北京
        allTeams.add(createTeam("北京飞马航遥科技有限公司", "北京"));
        allTeams.add(createTeam("北京神州数码有限公司", "北京"));
        allTeams.add(createTeam("北京未来智能科技有限公司", "北京"));
        allTeams.add(createTeam("北京中科浩电科技有限公司", "北京"));
        allTeams.add(createTeam("北京蓝天飞扬科技有限公司", "北京"));
        allTeams.add(createTeam("北京东进航空科技股份有限公司", "北京"));
        allTeams.add(createTeam("华盈信通技术有限公司", "北京"));
        allTeams.add(createTeam("航天宏图信息技术股份有限公司", "北京"));
        allTeams.add(createTeam("北京根越科技有限公司", "北京"));
        allTeams.add(createTeam("北京凌空远航科技有限公司", "北京"));
        allTeams.add(createTeam("北京云圣智能科技有限公司", "北京"));
        allTeams.add(createTeam("北京京东乾石科技有限公司", "北京"));
        allTeams.add(createTeam("北京帝信科技有限公司", "北京"));
        allTeams.add(createTeam("北京捷翔天地信息技术有限公司", "北京"));
        allTeams.add(createTeam("中测新图（北京）遥感技术有限责任公司", "北京"));
        allTeams.add(createTeam("中建材信息技术股份有限公司", "北京"));

        // 天津
        allTeams.add(createTeam("天津津准工程勘测有限公司", "天津"));
        allTeams.add(createTeam("天津众恒地信科技有限公司", "天津"));
        allTeams.add(createTeam("中水北方勘测设计研究有限责任公司", "天津"));

        // 河北
        allTeams.add(createTeam("河北福诺航空科技股份有限公司", "河北"));
        allTeams.add(createTeam("河北零佰科技有限公司", "河北"));
        allTeams.add(createTeam("河北惠得低空科技发展有限公司", "河北"));
        allTeams.add(createTeam("河北风航无人机科技有限公司", "河北"));
        allTeams.add(createTeam("石家庄铁路职业技术学院", "河北"));
        allTeams.add(createTeam("中国地质调查局廊坊自然资源综合调查中心", "河北"));

        // 山西
        allTeams.add(createTeam("山西火鸟航空科技有限公司", "山西"));
        allTeams.add(createTeam("山西翼锦未来科技有限责任公司", "山西"));
        allTeams.add(createTeam("山西元图测绘有限公司", "山西"));
        allTeams.add(createTeam("山西昊天视界航空科技有限公司", "山西"));
        allTeams.add(createTeam("山西如虹盛达科技股份有限公司", "山西"));
        allTeams.add(createTeam("山西文达通信息技术有限公司", "山西"));

        // 内蒙古
        allTeams.add(createTeam("呼和浩特市富迈商贸有限责任公司", "内蒙古"));
        allTeams.add(createTeam("呼伦贝尔市楠木科技发展有限公司", "内蒙古"));
        allTeams.add(createTeam("内蒙古云航智能设备有限公司", "内蒙古"));
        allTeams.add(createTeam("内蒙古国讯富通科技有限公司", "内蒙古"));

        // 辽宁
        allTeams.add(createTeam("辽宁省化工地质勘查院有限责任公司", "辽宁"));
        allTeams.add(createTeam("辽宁众云图测绘科技有限公司", "辽宁"));
        allTeams.add(createTeam("辽宁川云智能科技集团有限公司", "辽宁"));
        allTeams.add(createTeam("鞍钢工程技术勘测设计研究院（鞍山）有限公司", "辽宁"));
        allTeams.add(createTeam("沈阳九州飞翔科技有限公司", "辽宁"));

        // 吉林
        allTeams.add(createTeam("吉林市测绘院", "吉林"));
        allTeams.add(createTeam("吉林省元吉土地勘测规划设计有限公司", "吉林"));
        allTeams.add(createTeam("吉林云信空间科技有限公司", "吉林"));
        allTeams.add(createTeam("敦化市城乡规划设计院", "吉林"));
        allTeams.add(createTeam("前郭县远大测绘有限公司", "吉林"));

        // 黑龙江
        allTeams.add(createTeam("黑龙江精臻科技发展有限公司", "黑龙江"));
        allTeams.add(createTeam("黑龙江几何测绘地理信息有限公司", "黑龙江"));
        allTeams.add(createTeam("黑龙江省第六地质勘查院", "黑龙江"));
        allTeams.add(createTeam("黑龙江齐伦瀚科技有限公司", "黑龙江"));
        allTeams.add(createTeam("哈尔滨市国土资源勘测规划院", "黑龙江"));
        allTeams.add(createTeam("东北工程技术学校", "黑龙江"));
        allTeams.add(createTeam("黑龙江省地质测绘地理信息院", "黑龙江"));
        allTeams.add(createTeam("黑龙江省第五地质勘查院", "黑龙江"));
        allTeams.add(createTeam("黑龙江省第九地质勘查院", "黑龙江"));
        allTeams.add(createTeam("黑龙江省物探测量勘查院", "黑龙江"));
        allTeams.add(createTeam("黑龙江省自然资源调查院", "黑龙江"));

        // 上海
        allTeams.add(createTeam("上海畅突智能科技有限公司", "上海"));
        allTeams.add(createTeam("必胜航空科技集团有限公司", "上海"));
        allTeams.add(createTeam("上海锋则航空科技有限公司", "上海"));
        allTeams.add(createTeam("上海翰萨智能科技有限公司", "上海"));
        allTeams.add(createTeam("上海双瀛航空科技有限公司", "上海"));
        allTeams.add(createTeam("上海华测导航技术股份有限公司", "上海"));

        // 江苏
        allTeams.add(createTeam("江苏丰东信息科技有限公司", "江苏"));
        allTeams.add(createTeam("江苏嗨森网络科技有限公司", "江苏"));
        allTeams.add(createTeam("南京渊湛信息科技有限公司", "江苏"));
        allTeams.add(createTeam("泰州鸿鹄信息科技有限公司", "江苏"));
        allTeams.add(createTeam("苏州游鹰无人机技术有限公司", "江苏"));
        allTeams.add(createTeam("南通亿思特机器人科技有限公司", "江苏"));
        allTeams.add(createTeam("华软科技股份有限公司", "江苏"));
        allTeams.add(createTeam("宿迁智在疆宇航空科技有限公司", "江苏"));
        allTeams.add(createTeam("南京青年蓝天救援队", "江苏"));
        allTeams.add(createTeam("淮安云翼无人机科技有限公司", "江苏"));
        allTeams.add(createTeam("江苏佰易信息科技有限公司", "江苏"));
        allTeams.add(createTeam("常州市赛思网络科技有限公司", "江苏"));
        allTeams.add(createTeam("江苏飞睿得科技有限公司", "江苏"));
        allTeams.add(createTeam("江苏三益航空发展有限公司", "江苏"));
        allTeams.add(createTeam("江苏嗨森无人机智能科技有限公司", "江苏"));
        allTeams.add(createTeam("南京大翼航空科技有限公司", "江苏"));
        allTeams.add(createTeam("南京模幻天空航空科技有限公司", "江苏"));
        allTeams.add(createTeam("苏州嗨森无人机科技有限公司", "江苏"));
        allTeams.add(createTeam("小天无人机科技（盐城）有限公司", "江苏"));

        // 浙江
        allTeams.add(createTeam("浙江空时吾智能科技有限公司", "浙江"));
        allTeams.add(createTeam("浙江威航智能科技有限公司", "浙江"));
        allTeams.add(createTeam("浙江天拓航空科技有限公司", "浙江"));
        allTeams.add(createTeam("浙江守正数智空间技术有限公司", "浙江"));
        allTeams.add(createTeam("杭州明智时空科技有限公司", "浙江"));
        allTeams.add(createTeam("安邦通航智能科技（衢州）有限公司", "浙江"));
        allTeams.add(createTeam("嘉兴天旭航空技术有限公司", "浙江"));
        allTeams.add(createTeam("杭州时尚科技有限公司", "浙江"));
        allTeams.add(createTeam("浙江点创信息科技有限公司", "浙江"));
        allTeams.add(createTeam("浙江科比特创新科技有限公司", "浙江"));

        // 安徽
        allTeams.add(createTeam("安徽北斗智航科技有限公司", "安徽"));
        allTeams.add(createTeam("安徽福海电子技术有限公司", "安徽"));
        allTeams.add(createTeam("安徽中测空间信息技术有限公司", "安徽"));

        // 福建
        allTeams.add(createTeam("福建拓佳天地信息科技有限公司", "福建"));
        allTeams.add(createTeam("福建省121地质大队", "福建"));
        allTeams.add(createTeam("福建省测绘院", "福建"));
        allTeams.add(createTeam("福建福莱航空科技有限公司", "福建"));
        allTeams.add(createTeam("硕威工程科技股份有限公司", "福建"));
        allTeams.add(createTeam("伟志股份公司", "福建"));

        // 江西
        allTeams.add(createTeam("江西省天安泰科技有限公司", "江西"));
        allTeams.add(createTeam("江西空中未来科技创新集团有限公司", "江西"));

        // 山东
        allTeams.add(createTeam("山东鹰视角智能科技有限公司", "山东"));
        allTeams.add(createTeam("东营竹蜻蜓智能科技有限公司", "山东"));
        allTeams.add(createTeam("东营市博时智能科技有限责任公司", "山东"));
        allTeams.add(createTeam("济南泓特商贸有限公司", "山东"));
        allTeams.add(createTeam("济南智航时空地理信息科技有限公司", "山东"));
        allTeams.add(createTeam("烟台云都海鹰无人机应用技术有限公司", "山东"));
        allTeams.add(createTeam("山东科享云信息科技有限公司", "山东"));
        allTeams.add(createTeam("山东省鲁南地质工程勘察院（山东省地勘局第二地质大队）", "山东"));
        allTeams.add(createTeam("山东省地质测绘院", "山东"));

        // 河南
        allTeams.add(createTeam("河南顺优通实业有限公司", "河南"));
        allTeams.add(createTeam("河南省拓普北斗科技有限公司", "河南"));
        allTeams.add(createTeam("郑州飞米电子科技有限公司", "河南"));
        allTeams.add(createTeam("河南省地质局矿产资源勘查中心", "河南"));

        // 湖北
        allTeams.add(createTeam("武汉拓普新科无人机科技有限公司", "湖北"));
        allTeams.add(createTeam("武汉恒梵科技有限公司", "湖北"));
        allTeams.add(createTeam("武汉纵横天地空间信息技术有限公司", "湖北"));
        allTeams.add(createTeam("电鹰科技集团有限公司", "湖北"));
        allTeams.add(createTeam("武汉九州飞翔科技有限公司", "湖北"));
        allTeams.add(createTeam("武汉乐星图科技有限公司", "湖北"));
        allTeams.add(createTeam("普宙科技有限公司", "湖北"));
        allTeams.add(createTeam("中冶武勘工程技术有限公司", "湖北"));

        // 湖南
        allTeams.add(createTeam("湖南省自由飞电子科技有限公司", "湖南"));
        allTeams.add(createTeam("湖南中电金骏科技集团有限公司", "湖南"));
        allTeams.add(createTeam("岳阳云端通讯有限公司", "湖南"));
        allTeams.add(createTeam("长沙市岳麓区蓝天应急救援队", "湖南"));
        allTeams.add(createTeam("长沙群力测绘科技有限公司", "湖南"));
        allTeams.add(createTeam("湖南林科达信息科技有限公司", "湖南"));
        allTeams.add(createTeam("湖南省皓宇电子科技有限公司", "湖南"));

        // 广东
        allTeams.add(createTeam("广东诚进科技股份有限公司", "广东"));
        allTeams.add(createTeam("广东睿旗地理信息技术有限公司", "广东"));
        allTeams.add(createTeam("汕头市新飞龙航空科技有限公司", "广东"));
        allTeams.add(createTeam("珠海金华威科技数码有限公司", "广东"));
        allTeams.add(createTeam("广州市天河区成至无人机应急救援中心", "广东"));
        allTeams.add(createTeam("深圳市天空领域实业发展有限公司", "广东"));
        allTeams.add(createTeam("深圳市鹏锦科技有限公司", "广东"));
        allTeams.add(createTeam("深圳市翼志博科技有限公司", "广东"));
        allTeams.add(createTeam("深圳头名科技有限公司", "广东"));
        allTeams.add(createTeam("深圳高度创新技术有限公司", "广东"));
        allTeams.add(createTeam("深圳市恒唯信科技有限公司", "广东"));
        allTeams.add(createTeam("梅州市晟邦科技有限公司", "广东"));
        allTeams.add(createTeam("广东省国土资源测绘院", "广东"));
        allTeams.add(createTeam("蓝疆创新（深圳）科技有限公司", "广东"));

        // 广西
        allTeams.add(createTeam("广西视像通安全技术服务有限公司", "广西"));
        allTeams.add(createTeam("广西能飞无人机科技有限公司", "广西"));
        allTeams.add(createTeam("广西大雄鹰科技有限公司", "广西"));

        // 海南
        allTeams.add(createTeam("海南展飞信息科技有限公司", "海南"));
        allTeams.add(createTeam("海南华诚测绘科技有限公司", "海南"));
        allTeams.add(createTeam("海南中农航服科技有限公司", "海南"));
        allTeams.add(createTeam("海南马斯克科技有限公司", "海南"));
        allTeams.add(createTeam("海南星飞帆科技有限公司", "海南"));

        // 重庆
        allTeams.add(createTeam("重庆同汇勘测规划有限公司", "重庆"));
        allTeams.add(createTeam("睿宇时空科技（重庆）股份有限公司", "重庆"));
        allTeams.add(createTeam("重庆万航星空信息技术有限公司", "重庆"));

        // 四川
        allTeams.add(createTeam("四川测绘地理信息局测绘技术服务中心", "四川"));
        allTeams.add(createTeam("四川省第二地质大队", "四川"));
        allTeams.add(createTeam("四川携恩数创科技有限责任公司", "四川"));
        allTeams.add(createTeam("四川星辰测绘仪器有限公司", "四川"));
        allTeams.add(createTeam("四川立巢航空科技有限公司", "四川"));
        allTeams.add(createTeam("成都威尔奇空间信息技术有限公司", "四川"));
        allTeams.add(createTeam("成都辰宇智航科技有限公司", "四川"));
        allTeams.add(createTeam("成都徕拓测绘工程有限公司", "四川"));
        allTeams.add(createTeam("自贡市水利电力勘测设计院有限公司", "四川"));
        allTeams.add(createTeam("成都特旺科技有限责任公司", "四川"));
        allTeams.add(createTeam("成都纵横自动化技术股份有限公司", "四川"));
        allTeams.add(createTeam("四川众智鸿图信息科技有限公司", "四川"));
        allTeams.add(createTeam("柒零叁信息科技有限公司", "四川"));

        // 贵州
        allTeams.add(createTeam("贵州天地通科技有限公司", "贵州"));
        allTeams.add(createTeam("贵州省有色金属和核工业地质勘查局五总队", "贵州"));
        allTeams.add(createTeam("贵州地矿一一三地质工程有限公司", "贵州"));
        allTeams.add(createTeam("贵州遥感科技有限公司", "贵州"));
        allTeams.add(createTeam("贵州星测科技有限公司", "贵州"));
        allTeams.add(createTeam("贵州点云测绘服务有限公司", "贵州"));
        allTeams.add(createTeam("贵州鑫疆基业科技有限责任公司", "贵州"));
        allTeams.add(createTeam("贵州云图瞰景地理信息技术有限公司", "贵州"));
        allTeams.add(createTeam("黔东南富源测绘有限公司", "贵州"));
        allTeams.add(createTeam("都匀市南方地理信息科技有限公司", "贵州"));
        allTeams.add(createTeam("黔西南州兴源水利电力勘察设计有限公司", "贵州"));
        allTeams.add(createTeam("兴义市点云测绘仪器有限公司", "贵州"));
        allTeams.add(createTeam("贵州省水利水电勘测设计研究院有限公司", "贵州"));

        // 云南
        allTeams.add(createTeam("寸度智慧空间科技（云南）有限公司", "云南"));
        allTeams.add(createTeam("云南振蓝信息科技有限公司", "云南"));
        allTeams.add(createTeam("云南近达信息技术有限公司", "云南"));
        allTeams.add(createTeam("云南省基础测绘技术中心", "云南"));
        allTeams.add(createTeam("云南新坐标智能技术有限公司", "云南"));
        allTeams.add(createTeam("临沧市水利水电勘测设计研究院有限公司", "云南"));
        allTeams.add(createTeam("昆明佳尼潮科技有限公司", "云南"));

        // 西藏
        allTeams.add(createTeam("西藏大学", "西藏"));
        allTeams.add(createTeam("西藏德众地理信息有限公司", "西藏"));
        allTeams.add(createTeam("西藏攀索实业有限公司", "西藏"));

        // 陕西
        allTeams.add(createTeam("蛐蛐（西安）科技有限公司", "陕西"));
        allTeams.add(createTeam("陕西凌越航空科技有限公司", "陕西"));
        allTeams.add(createTeam("陕西皇朝航空科技有限公司", "陕西"));
        allTeams.add(createTeam("陕西星辰时代科技发展有限公司", "陕西"));
        allTeams.add(createTeam("西安大地测绘股份有限公司", "陕西"));

        // 甘肃
        allTeams.add(createTeam("甘肃省地质矿产勘查开发局测绘勘查院", "甘肃"));
        allTeams.add(createTeam("甘肃煤田地质局庆阳资源勘查院", "甘肃"));
        allTeams.add(createTeam("甘肃汇智岚图信息科技有限责任公司", "甘肃"));
        allTeams.add(createTeam("甘肃省水利水电勘测设计研究院有限责任公司", "甘肃"));
        allTeams.add(createTeam("甘肃苍穹无人机应急救援服务中心", "甘肃"));
        allTeams.add(createTeam("甘肃启远智能科技有限责任公司", "甘肃"));
        allTeams.add(createTeam("武威市基础地理勘测技术服务中心", "甘肃"));
        allTeams.add(createTeam("张掖指南针测绘有限责任公司", "甘肃"));

        // 青海
        allTeams.add(createTeam("青海九零六工程勘察设计院有限责任公司", "青海"));
        allTeams.add(createTeam("青海智勘科技有限公司", "青海"));
        allTeams.add(createTeam("青海众飞电子科技有限公司", "青海"));
        allTeams.add(createTeam("青海省自然资源遥感中心", "青海"));
        allTeams.add(createTeam("青海省柴达木综合地质矿产勘查院（青海省盐湖地质调查院）", "青海"));
        allTeams.add(createTeam("青海省交通规划设计研究院有限公司", "青海"));
        allTeams.add(createTeam("青海有色测绘勘察院有限责任公司", "青海"));
        allTeams.add(createTeam("中国水利水电第四工程局有限公司", "青海"));
        allTeams.add(createTeam("青海华疆电子科技有限公司", "青海"));
        allTeams.add(createTeam("青海华谨信息技术有限公司", "青海"));

        // 宁夏
        allTeams.add(createTeam("银川天之源测绘仪器有限公司", "宁夏"));
        allTeams.add(createTeam("宁夏隆合科技有限公司", "宁夏"));
        allTeams.add(createTeam("宁夏天司创新科技有限公司", "宁夏"));

        // 新疆
        allTeams.add(createTeam("新疆疆海测绘科技有限公司", "新疆"));
        allTeams.add(createTeam("新疆国源测绘规划设计院有限公司", "新疆"));
        allTeams.add(createTeam("中国科学院新疆生态与地理研究所", "新疆"));
        allTeams.add(createTeam("阿克苏市时代规划设计院有限责任公司", "新疆"));
        allTeams.add(createTeam("克拉玛依天地图有限公司", "新疆"));

        // 批量插入数据
        for (Team team : allTeams) {
            teamMapper.insert(team);
        }
        
        log.info("共插入 {} 条默认团队数据", allTeams.size());
    }

    /**
     * 创建团队对象
     */
    private Team createTeam(String name, String region) {
        Team team = new Team();
        team.setName(name);
        team.setRegion(region);
        team.setDescription(null);  // 默认为null
        return team;
    }
}