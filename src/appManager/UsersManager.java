package appManager;

import logic.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


public class UsersManager {
    ArrayList<User> users =  new ArrayList<>();

    public ArrayList<User> getUsers(){
        return users;
    }

    public User usernameExist(List<User> users, User user) {
        for(User u: users){
            if(u.getEmail().equals(user.getEmail())){
                return u;
            }
        }
        return null;
    }

    public List<User> getAllSignedUsers(EntityManager entityManager) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.m_Email", User.class);
        return query.getResultList();
    }

    public String getUserNameByEmail(EntityManager em, String mail)
    {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.m_Email=:mail", User.class);
        query.setParameter("mail", mail);
        return query.getSingleResult().getFirstName() + " " + query.getSingleResult().getLastName();
    }

    public User getUserByEmail(String id, EntityManager em) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u where u.m_Email=:id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
