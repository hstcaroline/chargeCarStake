package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.Message;


public interface MessageService {
    /**
     * 显示某用户所有未处理的某类型消息
     * @param user_id
     * @param type
     * @return
     */
    public List<Message> showUnsolvedMsg(int user_id,int type);
    //发布消息
    public boolean addMessage(Message message);
    
    /**
     * 根据id查询message
     * @param id
     * @return
     */
    public Message load(int id);
    
    /**
     * 修改消息
     * @param message
     * @return
     */
    public boolean updateMsg(Message message);
}
