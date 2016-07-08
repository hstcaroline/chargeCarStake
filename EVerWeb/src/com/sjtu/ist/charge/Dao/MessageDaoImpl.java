package com.sjtu.ist.charge.Dao;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sjtu.ist.charge.Model.Message;


@Repository(value = "messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao {

	public List<Message> unsolvedMsg(int user_id, int type) {
		 // type=0为车桩审核消息,type=1为投诉消息
        String hql = "from Message msg where msg.done=0 and msg.userByToId.id= :uid and msg.type= :type ";
        Session session = getSession();
        Query query = session.createQuery(hql);
        List<Message> messages = query.setParameter("uid", user_id).setParameter("type", type).list();
        releaseSession(session);
        if (!messages.isEmpty()) {
            return messages;
        }
        return null;
	}

}
