package generator.config;

import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 默认的代码生成的配置
 *
 * @author fengshuonan
 * @date 2017-10-28-下午8:27
 */
public class GunsGeneratorConfig extends AbstractGeneratorConfig {

    @Override
    protected void globalConfig() {
        globalConfig.setOutputDir("/Users/stylefeng/work/ideaSpace/guns/guns-admin/src/main/java");//写自己项目的绝对路径,注意具体到java目录
        globalConfig.setFileOverride(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setOpen(false);
        globalConfig.setAuthor("stylefeng");
    }

    @Override
    protected void dataSourceConfig() {
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root");
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/guns?characterEncoding=utf8");
    }

    @Override
    protected void strategyConfig() {
        //strategy.setTablePrefix(new String[]{"_"});// 此处可以修改为您的表前缀
        strategyConfig.setInclude(new String[]{"user"});//这里限制需要生成的表,不写则是生成所有表
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
    }

    @Override
    protected void packageConfig() {
        packageConfig.setParent(null);
        packageConfig.setEntity("com.stylefeng.guns.common.persistence.model");
        packageConfig.setMapper("com.stylefeng.guns.common.persistence.dao");
        packageConfig.setXml("com.stylefeng.guns.common.persistence.dao.mapping");
    }

    @Override
    protected void contextConfig() {
        contextConfig.setBizChName("用户管理");
        contextConfig.setBizEnName("user");
        contextConfig.setModuleName("system");
        contextConfig.setProjectPath("/Users/stylefeng/work/ideaSpace/guns/guns-admin");//写自己项目的绝对路径
        contextConfig.setEntityName("User");
        sqlConfig.setParentMenuName("系统管理");//这里写已有菜单的名称,当做父节点

        /**
         * mybatis-plus 生成器开关
         */
        contextConfig.setEntitySwitch(true);
        contextConfig.setDaoSwitch(true);
        contextConfig.setServiceSwitch(true);

        /**
         * guns 生成器开关
         */
        contextConfig.setControllerSwitch(true);
        contextConfig.setIndexPageSwitch(true);
        contextConfig.setAddPageSwitch(true);
        contextConfig.setEditPageSwitch(true);
        contextConfig.setJsSwitch(true);
        contextConfig.setInfoJsSwitch(true);
        contextConfig.setSqlSwitch(true);
    }
}
