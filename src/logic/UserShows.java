package logic;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class UserShows {
    @Id
    String m_UserEmail;
    private int m_ShowID;
    private int m_Type;

    public UserShows(String id, int showid, int type)
    {
        m_UserEmail = id;
        m_ShowID = showid;
        m_Type = type;
    }

    public UserShows() {
    }

    public String getUserID() { return m_UserEmail; }

    public int getShowId() {return  m_ShowID;}

}
