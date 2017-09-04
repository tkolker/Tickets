package logic;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserShowBought implements UserShowsInterface {
    @Id
    int m_Key;
    String m_UserEmail;
    private int m_ShowID;

    public UserShowBought(int key, String id, int showId)
    {
        m_Key = key;
        m_UserEmail = id;
        m_ShowID = showId;
    }

    public UserShowBought(){}

    public String getUserID() { return m_UserEmail; }

    public int getShowId() {return  m_ShowID;}

    public int getKey() { return m_Key; }

}
