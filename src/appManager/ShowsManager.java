package appManager;

import logic.Show;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ShowsManager {
    ArrayList<Show> shows =  new ArrayList<>();

    public ArrayList<Show> getShows(){
        return shows;
    }

    public Show showExist(List<Show> shows, Show showToSerch) {
        for(Show s: shows){
            if(s.getShowID() == showToSerch.getShowID()){
                return s;
            }
        }
        return null;
    }

    public Show showIDExist(List<Show> shows, Show show) {
        for(Show s: shows){
            if(s.getShowID() == show.getShowID()){
                return s;
            }
        }
        return null;
    }

    public void deleteShow(Show showTodelete)
    {
        shows.remove(showTodelete);
    }

    public void addShow(Show showToAdd)
    {
        shows.add(showToAdd);
    }

    public List<Show> getAllShows(EntityManager entityManager)
    {
        TypedQuery<Show> query = entityManager.createQuery("SELECT s FROM Show s ORDER BY s.m_ShowID", Show.class);
        return query.getResultList();
    }

    public Show getShowByID(EntityManager entityManager, int showID) {
        TypedQuery<Show> query = entityManager.createQuery("SELECT s FROM Show s WHERE s.m_ShowID=:showID", Show.class);
        return query.getSingleResult();
    }
}
