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
}
