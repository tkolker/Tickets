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

    public Show showLocationAndDateExist(List<Show> shows, Show showToSerch) {
        for(Show s: shows){
            if(s.getShowDate() == showToSerch.getShowDate() && s.getLocation() == showToSerch.getLocation() &&
                    (s.getShowName().contains(showToSerch.getShowName()) || showToSerch.getShowName().contains(s.getShowName()))){
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

    public Show showIDExist(List<Show> shows, int showID) {
        for(Show s: shows){
            if(s.getShowID() == showID){
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
        TypedQuery<Show> query = entityManager.createQuery("SELECT s FROM Show s WHERE s.m_ShowID=:"+showID, Show.class);
        return query.getSingleResult();
    }

    public List<Show> getAllShowsByDates(EntityManager em) {
        TypedQuery<Show> query = em.createQuery("SELECT s FROM Show s ORDER BY s.m_Date asc", Show.class);
        return query.getResultList();
    }
}
