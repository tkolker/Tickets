package logic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class Message {
    @Id
    @GeneratedValue
    private int m_MsgId;
    private String m_Msg;
    private Date m_Date;
    private String m_SenderId;
    private String m_SenderName;
    private String m_ReceiverId;
    private String m_ReceiverName;
    private int m_ShowId;
    private String m_ShowName;

    public Message(int msgId, String msg, String senderId, String senderName, String receiverId, String receiverName, int showId, String showName){
        m_MsgId = msgId;
        m_Msg = msg;
        m_Date = new Date();
        m_SenderId = senderId;
        m_SenderName = senderName;
        m_ReceiverId = receiverId;
        m_ReceiverName = receiverName;
        m_ShowId = showId;
        m_ShowName = showName;
    }

    public String getM_Msg() {
        return m_Msg;
    }

    public void setM_Msg(String m_Msg) {
        this.m_Msg = m_Msg;
    }

    public int getM_MsgId() {
        return m_MsgId;
    }

    public void setM_MsgId(int m_MsgId) {
        this.m_MsgId = m_MsgId;
    }

    public Date getM_Date() {
        return m_Date;
    }

    public void setM_Date(Date m_Date) {
        this.m_Date = m_Date;
    }

    public String getM_SenderId() {
        return m_SenderId;
    }

    public void setM_SenderId(String m_SenderId) {
        this.m_SenderId = m_SenderId;
    }

    public String getM_SenderName() {
        return m_SenderName;
    }

    public void setM_SenderName(String m_SenderName) {
        this.m_SenderName = m_SenderName;
    }

    public String getM_ReceiverId() {
        return m_ReceiverId;
    }

    public void setM_ReceiverId(String m_ReceiverId) {
        this.m_ReceiverId = m_ReceiverId;
    }

    public String getM_ReceiverName() {
        return m_ReceiverName;
    }

    public void setM_ReceiverName(String m_ReceiverName) {
        this.m_ReceiverName = m_ReceiverName;
    }
}


