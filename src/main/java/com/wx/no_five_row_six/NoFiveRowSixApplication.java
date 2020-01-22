package com.wx.no_five_row_six;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.wx.common.util.TimeUtil;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.wx.common.spring")
@ComponentScan("com.wx.common.baidu")
@ComponentScan("com.wx.no_five_row_six")
@MapperScan("com.wx.no_five_row_six.mapper")
@EnableConfigurationProperties({SystemConfig.class,EmailConfig.class,WinxinConfig.class})
@EnableScheduling
public class NoFiveRowSixApplication {

    @Autowired
    private WinxinConfig winxinConfig;

    public static void main(String[] args) {
        SpringApplication.run(NoFiveRowSixApplication.class, args);
    }
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean
    public WxMpService wxMpService() {
        WxMpService service = new WxMpServiceImpl();
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(this.winxinConfig.getAppId());
        configStorage.setSecret(this.winxinConfig.getSecret());
        service.setWxMpConfigStorage(configStorage);
        return service;
    }
    /**
     * 模版引擎
     */
    @Bean(name = "jfinalViewResolver")
    public JFinalViewResolver getJFinalViewResolver() {
        JFinalViewResolver jfr = new JFinalViewResolver();
        // setDevMode 配置放在最前面
        jfr.setDevMode(true);

        // 使用 ClassPathSourceFactory 从 class path 与 jar 包中加载模板文件
        jfr.setSourceFactory(new ClassPathSourceFactory());


        // 在使用 ClassPathSourceFactory 时要使用 setBaseTemplatePath
        // 代替 jfr.setPrefix("/view/")
        JFinalViewResolver.engine.setBaseTemplatePath("/templates/");
        jfr.setSuffix(".html");
        jfr.setContentType("text/html;charset=UTF-8");
        jfr.setOrder(0);
        jfr.addSharedFunction("/_layout.html");
        //用于前端时间转换
        jfr.addSharedMethod(new TimeUtil());
//        jfr.addSharedMethod(new UserUtil());
        return jfr;
    }
}
