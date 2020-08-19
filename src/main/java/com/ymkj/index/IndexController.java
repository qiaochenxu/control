package com.ymkj.index;

import com.jfinal.core.Controller;

/**
 * @author 乔晨旭
 */

public class IndexController extends Controller {

    public void index(){
        render("index.html");
    }
}
