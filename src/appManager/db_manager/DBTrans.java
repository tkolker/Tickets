package appManager.db_manager;

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
            //em.close();
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
            //em.close();
        }
    }
}