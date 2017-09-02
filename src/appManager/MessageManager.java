package appManager;

import logic.Message;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MessageManager {

    public List<Message> getMessagesByReceiver(EntityManager entityManager, String receiverId)
    {
        TypedQuery<Message> query = entityManager.createQuery("SELECT s FROM Message s WHERE s.m_ReceiverId=:id", Message.class);
        query = query.setParameter("id", receiverId);
        return query.getResultList();
    }

    public List<Message> getAllMessages(EntityManager em) {
        TypedQuery<Message> query = em.createQuery("SELECT s FROM Message s ORDER BY s.m_MsgId asc", Message.class);
        return query.getResultList();
    }
}
