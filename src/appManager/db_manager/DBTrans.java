package appManager.db_manager;

import logic.Show;
import logic.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;


@Stateless
public class DBTrans {

    public DBTrans(){};

    // Stores a new user
    public static void persist(EntityManager em, Object addData) {
        try{
            em.getTransaction().begin();
            em.persist(addData);
            em.getTransaction().commit();
        }
        finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
    }

    public static void updateShow(EntityManager em, int showId, int numberTickets)
    {
        try{
            Show showToUpdate = em.find(Show.class, showId);
            em.getTransaction().begin();
            showToUpdate.setNumOfTickets(numberTickets);
            em.getTransaction().commit();
        }
        finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }

    public static void updateUserFavShows(EntityManager em, String userId, String fav)
    {
        try{
            User userToUpdate = em.find(User.class, userId);
            em.getTransaction().begin();
            userToUpdate.setFavShows(fav);
            em.getTransaction().commit();
        }
        finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }

    public static void updateUserFavLocation(EntityManager em, String userId, String fav)
    {
        try{
            User userToUpdate = em.find(User.class, userId);
            em.getTransaction().begin();
            userToUpdate.setFavLocations(fav);
            em.getTransaction().commit();
        }
        finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            em.close();
        }
    }

    public static void remove(EntityManager em, Object addData) {
        try{
            em.getTransaction().begin();
            em.remove(addData);
            em.getTransaction().commit();
        }
        finally {
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
        }
    }
}