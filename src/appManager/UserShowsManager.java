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
}

