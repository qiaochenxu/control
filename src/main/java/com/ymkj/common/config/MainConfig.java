package com.ymkj.common.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.ymkj.common.interceptor.CrossDomainInterceptor;
import com.ymkj.common.routes.AdminRoutes;

import javax.swing.*;

/**
 * @author 乔晨旭
 */
public class MainConfig extends JFinalConfig {
    private WallFilter wallFilter;

   private static Prop p;
    static void loadConfig(){
        if (p == null){
            p = PropKit.use("config.properties").appendIfExists("config-pro.properties");
        }
    }

    @Override
    public void configConstant(Constants me) {
        // 获取配置文件
        loadConfig();
        //设置当前是否为开发模式
        me.setDevMode(p.getBoolean("devMode"));
        // 下载路径
        me.setBaseDownloadPath("");
        //上传路径
        me.setBaseUploadPath("");
        // 配置视图类型
        me.setViewType(ViewType.JFINAL_TEMPLATE);
        me.setEncoding("UTF8");
        // 配置依赖注入
        me.setInjectDependency(true);
    }

    @Override
    public void configRoute(Routes me) {
        // 配置后台管理系统路由
        me.add(new AdminRoutes());

    }

    @Override
    public void configEngine(Engine me) {
        // 配置模板支持热加载
        me.setDevMode(p.getBoolean("engineDevMode",true));

    }
    public static DruidPlugin getDruidPlugin(){
        // 配置数据源插件
        loadConfig();
        return new DruidPlugin(p.get("jdbcUrl"),p.get("user"),p.get("password"));
    }

    @Override
    public void configPlugin(Plugins me) {
        loadConfig();
        DruidPlugin dbPlugin = getDruidPlugin();
        //加强数据库安全;
        wallFilter = new WallFilter();
        wallFilter.setDbType("mysql");
        dbPlugin.addFilter(wallFilter);
        //添加StatFiler才会有统计
        dbPlugin.addFilter(new StatFilter());

        //数据映射
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dbPlugin);
        arp.setShowSql(p.getBoolean("devMode"));
        arp.setDialect(new MysqlDialect());
        dbPlugin.setDriverClass("com.mysql.jdbc.Driver");
        /********在此添加数据库 表-Model 映射*********/
        //如果使用了JFinal Model 生成器 生成了BaseModel 把下面注释解开即可
        //_MappingKit.mapping(arp);

        arp.getEngine().setToClassPathSourceFactory();
        //添加数据库模板
        arp.getSqlKit();
        //开发模式是否在控制台打印显示
        arp.setShowSql(false);

        //添加到插件列表
        me.add(dbPlugin);
        me.add(arp);

    }

    @Override
    public void configInterceptor(Interceptors me) {
        //me.add(new CrossDomainInterceptor());
        me.addGlobalActionInterceptor(new SessionInViewInterceptor());

    }

    @Override
    public void onStart() {
        wallFilter.getConfig().setSelectUnionCheck(false);
    }

    @Override
    public void configHandler(Handlers me) {

    }
}
