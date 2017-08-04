package logic;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class UserShows {
    @Id
    int m_Key;
    String m_UserEmail;
    private int m_ShowID;
    private int m_Type;

    public UserShows(int key, String id, int showId, int type)
    {
        m_Key = key;
        m_UserEmail = id;
        m_ShowID = showId;
        m_Type = type;
    }

    public UserShows() {
    }

    public String getUserID() { return m_UserEmail; }

    public int getShowId() {return  m_ShowID;}

}
