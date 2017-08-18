package appManager;

import logic.User;
import logic.UserShows;
import logic.UserShowsInterface;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserShowsManager implements UserShowsManagerInterface{

    ArrayList<UserShows> m_UserShowsList =  new ArrayList<>();

    public ArrayList<UserShows> getUsers(){
        return m_UserShowsList;
    }

    public UserShows getUserShow(String userEmail, int showID)
    {
        for(UserShows u: m_UserShowsList){
            if(u.getUserID().equals(userEmail)){
                if(u.getShowId() == showID){
                    return u;
                }
            }
        }
        return null;
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
        TypedQuery<UserShows> query = entityManager.createQuery("SELECT s FROM UserShows s WHERE s.m_ShowID =:showId and s.m_UserEmail =:userId", UserShows.class);
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
        for(UserShows u: m_UserShowsList){
            if (u.getUserID().equals(userId) && u.getShowId() == showID){
                return true;
            }
        }
        return false;
    }

    public int countAll(EntityManager em) {
        TypedQuery<UserShows> query = em.createQuery("SELECT s.m_Key FROM UserShows s ORDER BY s.m_Key desc", UserShows.class);
        return query.getFirstResult();
    }

    public List<UserShowsInterface> getShowByUserID(EntityManager em, String userId) {
        TypedQuery<UserShowsInterface> query = em.createQuery("SELECT s FROM UserShows s WHERE s.m_UserEmail=:mail ORDER BY s.m_ShowID asc", UserShowsInterface.class);

        return query.setParameter("mail", userId).getResultList();
    }

    public String getUserByShowId(EntityManager em, int ShowId) {
        TypedQuery<UserShows> query = em.createQuery("SELECT s FROM UserShows s WHERE s.m_ShowID=:showID", UserShows.class);
        query.setParameter("showID", ShowId);
        return query.getSingleResult().getUserID();
    }

    public List<UserShows> getShowByUserIDDesc(EntityManager em, String userId) {
        TypedQuery<UserShows> query = em.createQuery("SELECT s FROM UserShows s WHERE s.m_UserEmail=:mail ORDER BY s.m_ShowID desc", UserShows.class);

        return query.setParameter("mail", userId).getResultList();
    }
}

