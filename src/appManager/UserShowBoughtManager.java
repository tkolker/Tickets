package appManager;

import logic.User;
import logic.UserShowBought;
import logic.UserShowsInterface;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class UserShowBoughtManager implements UserShowsManagerInterface {
    ArrayList<UserShowBought> m_UserShowsBoughtList =  new ArrayList<>();

    public ArrayList<UserShowBought> getUsers(){
        return m_UserShowsBoughtList;
    }

    public UserShowBought getUserShow(String userEmail, int showID)
    {
        for(UserShowBought u: m_UserShowsBoughtList){
            if(u.getUserID().equals(userEmail)){
                if(u.getShowId() == showID){
                    return u;
                }
            }
        }
        return null;
    }

    public UserShowBought userIDExist(User user) {
        for(UserShowBought u: m_UserShowsBoughtList){
            if(u.getUserID().equals(user.getEmail())){
                return u;
            }
        }
        return null;
    }

    public void addUserShow(UserShowBought userShowsToAdd)
    {
        m_UserShowsBoughtList.add(userShowsToAdd);
    }

    public List<UserShowBought> getAllShows(EntityManager entityManager)
    {
        TypedQuery<UserShowBought> query = entityManager.createQuery("SELECT s FROM UserShows s ORDER BY s.m_ShowID", UserShowBought.class);
        return query.getResultList();
    }

    public static boolean showIDExistInUser(EntityManager entityManager, String userId, int showId)
    {
        TypedQuery<UserShowBought> query = entityManager.createQuery("SELECT s FROM UserShows s WHERE s.m_ShowID =:showId and s.m_UserEmail =:userId", UserShowBought.class);
        query = query.setParameter("userId", userId).setParameter("showId", showId);
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
        for(UserShowBought u: m_UserShowsBoughtList){
            if (u.getUserID().equals(userId) && u.getShowId() == showID){
                return true;
            }
        }
        return false;
    }

    public int countAll(EntityManager em) {
        TypedQuery<UserShowBought> query = em.createQuery("SELECT s.m_Key FROM UserShows s ORDER BY s.m_Key desc", UserShowBought.class);
        return query.getFirstResult();
    }

    public List<UserShowsInterface> getShowByUserID(EntityManager em, String userId) {
        TypedQuery<UserShowsInterface> query = em.createQuery("SELECT s FROM UserShows s WHERE s.m_UserEmail=:mail ORDER BY s.m_ShowID asc", UserShowsInterface.class);
        query.setParameter("mail", userId);
        return query.getResultList();
    }

    public List<UserShowBought> getShowByUserIDDesc(EntityManager em, String userId) {
        TypedQuery<UserShowBought> query = em.createQuery("SELECT s FROM UserShows s WHERE s.m_UserEmail=:mail ORDER BY s.m_ShowID desc", UserShowBought.class);

        return query.setParameter("mail", userId).getResultList();
    }
}
