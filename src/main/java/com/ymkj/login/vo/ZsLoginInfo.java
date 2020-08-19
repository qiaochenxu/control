package com.ymkj.login.vo;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Record;
import org.eclipse.jetty.util.StringUtil;

import java.io.Serializable;

/**
 * @author 乔晨旭
 */
public class ZsLoginInfo implements Serializable {

    private static final long serialVersionUID = -6512180289361798823L;
    private Record rd = new Record();

    private ZsLoginInfo init(Record rd) {
        if (rd != null) {
            this.rd = rd;
        }
        return this;
    }

    public ZsLoginInfo() {
        this.init(rd);
    }

    public ZsLoginInfo(Record rd) {
        this.init(rd);
    }

    public boolean isEmpty() {
        return StrUtil.isBlank(this.getId());
    }

   public String getId(){
        return rd.get("userid","");
   }
   public ZsLoginInfo setId(String id){
        this.rd.set("userid",id);
        return this;
   }

   public String getName(){
        return rd.get("username","");
   }
   public ZsLoginInfo setName(String name){
        this.rd.set("username",name);
        return this;
   }


}
