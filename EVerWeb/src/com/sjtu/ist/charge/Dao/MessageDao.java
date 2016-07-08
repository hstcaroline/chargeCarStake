package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Message;



public interface MessageDao extends BaseDao<Message> {
   /**
    * 查看某用户某种类型未处理的消息
    * @param user_id
    * @param type
    * @return
    */
    public List<Message> unsolvedMsg(int user_id,int type);
    
}
