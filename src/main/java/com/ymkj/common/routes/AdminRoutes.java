package com.ymkj.common.routes;

import com.jfinal.aop.Before;
import com.jfinal.config.Routes;
import com.ymkj.common.interceptor.CrossDomainInterceptor;
import com.ymkj.index.IndexController;
@Before(CrossDomainInterceptor.class)
public class AdminRoutes extends Routes {
    @Override
    public void config() {
        this.add("/", IndexController.class);
    }
}
