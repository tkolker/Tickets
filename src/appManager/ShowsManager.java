package appManager;

import logic.Show;
import logic.ShowInterface;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ShowsManager implements ShowsManagerInterface{
    ArrayList<Show> shows =  new ArrayList<>();

    public ArrayList<Show> getShows(){
        return shows;
    }

    public Show showLocationAndDateExist(List<ShowInterface> shows, Show showToSerch) {
        for(ShowInterface s: shows){
            if(s.getShowDate().equals(showToSerch.getShowDate()) && s.getLocation().equals(showToSerch.getLocation()) &&
                    (s.getShowName().contains(showToSerch.getShowName()) || showToSerch.getShowName().contains(s.getShowName()))){
                return (Show) s;
            }
        }
        return null;
    }

    public Show showIDExist(List<ShowInterface> shows, Show show) {
        for(ShowInterface s: shows){
            if(s.getShowID() == show.getShowID()){
                return ((Show) s);
            }
        }
        return null;
    }

    public Show showIDExist(List<ShowInterface> shows, int showID) {
        for(ShowInterface s: shows){
            if(s.getShowID() == showID){
                return (Show) s;
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

    public List<Show> getAllShowsWithName(EntityManager entityManager, String showName)
    {
        TypedQuery<Show> query = entityManager.createQuery("SELECT s FROM Show s WHERE s.m_ShowName LIKE '%"+showName+"%' ORDER BY s.m_ShowID", Show.class);
        query = query.setParameter("showName", showName);
        return query.getResultList();
    }

    public List<ShowInterface> getAllShows(EntityManager entityManager)
    {
        TypedQuery<ShowInterface> query = entityManager.createQuery("SELECT s FROM Show s ORDER BY s.m_ShowID", ShowInterface.class);
        return query.getResultList();
    }

    public Show getShowByID(EntityManager entityManager, int showID) {
        TypedQuery<Show> query = entityManager.createQuery("SELECT s FROM Show s WHERE s.m_ShowID=:id", Show.class);
        return query.setParameter("id", showID).getSingleResult();
    }

    public List<Show> getAllShowsByDates(EntityManager em) {
        TypedQuery<Show> query = em.createQuery("SELECT s FROM Show s ORDER BY s.m_Date asc", Show.class);
        return query.getResultList();
    }

    public List<Show> getAllShowsDesc(EntityManager em) {
        TypedQuery<Show> query = em.createQuery("SELECT s FROM Show s ORDER BY s.m_ShowID desc", Show.class);
        return query.getResultList();
    }
}
