package com.sjtu.ist.charge.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.MessageDao;
import com.sjtu.ist.charge.Model.Message;


@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private MessageDao messageDao;

    public MessageDao getMessageDao() {
        return messageDao;
    }

    @Resource(name = "messageDao")
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }


    public boolean addMessage(Message message) {
        return messageDao.add(message);
    }

	public List<Message> showUnsolvedMsg(int user_id, int type) {
		return messageDao.unsolvedMsg(user_id, type);
	}

	public Message load(int id) {
		return messageDao.load(id);
	}

	public boolean updateMsg(Message message) {
		return messageDao.update(message);
	}


}
