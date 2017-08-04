package appManager;

import logic.Show;
import logic.User;
import logic.UserShows;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserShowsManager {

    ArrayList<UserShows> m_UserShowsList =  new ArrayList<>();

    public ArrayList<UserShows> getUsers(){
        return m_UserShowsList;
    }

    public UserShows userIDExist(User user) {
        for(UserShows u: m_UserShowsList){
            if(u.getUserID().equals(user.getEmail())){
                return u;
            }
        }
        return null;
    }

    public void addUserShow(UserShows userShowsToAdd)
    {
        m_UserShowsList.add(userShowsToAdd);
    }

    public List<UserShows> getAllShows(EntityManager entityManager)
    {
        TypedQuery<UserShows> query = entityManager.createQuery("SELECT s FROM UserShows s ORDER BY s.m_ShowID", UserShows.class);
        return query.getResultList();
    }

    public static boolean showIDExistInUser(EntityManager entityManager, String userId, int showId)
    {
        TypedQuery<UserShows> query = entityManager.createQuery("SELECT s FROM UserShows s WHERE s.m_ShowID =: showid and s.m_UserEmail =: userId", UserShows.class);
        //return query.getResultList();
        if (query.getSingleResult() != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isShowAlreadyExist(String userId, int showID)
    {
        for(UserShows u: m_UserShowsList){
            if (u.getUserID().equals(userId) && u.getShowId() == showID){
                return true;
            }
        }
        return false;
    }
}

