/*
package servlets;

import appManager.ShowsArchiveManager;
import appManager.ShowsManager;
import appManager.UserShowsManagerInterface;
import appManager.UsersManager;
import appManager.db_manager.DBTrans;
import com.google.gson.Gson;
import logic.ShowInterface;
import logic.User;
import logic.UserShowsInterface;
import utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@WebServlet(name = "ProfileServlet", urlPatterns = {"profile"})
public class ProfileServlet extends HttpServlet {

    EntityManagerFactory emf;
    EntityManager em;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        ShowsManager showsManager = ServletUtils.getShowsManager(getServletContext());

        switch(actionType){
            case Constants.GET_FAVORITES:
                getRecommendedShows(request, response, showsManager);
                break;
            case Constants.GET_SELL_SHOWS:
                getMySellShows(request, response, showsManager);
                break;
            case Constants.GET_BOUGHT_TICKETS:
                //TODO: debug
                getBoughtTickets(request, response, showsManager);
                break;
        }
    }

    private void getRecommendedShows(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");
        ArrayList<ShowInterface> favorites = new ArrayList<>();
        HashSet<ShowInterface> checkSet = new HashSet<>();
        String favLocStr = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getFavLocations();
        String favShowStr = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getFavShows();
        List<ShowInterface> shows = showsManager.getAllShows(em);

        String[] favLoc = favLocStr.split(",");
        String[] favShow = favShowStr.split(",");

        for(ShowInterface s: shows){
            for(int i = 0; i < favLoc.length; i++){
                if(s.getLocation().contains(favLoc[i])){
                    favorites.add(s);
                    checkSet.add(s);
                }
            }
        }

        for(ShowInterface s: shows){
            for(int i = 0; i < favShow.length; i++){
                if(s.getShowName().contains(favShow[i]) && !(checkSet.contains(s))){
                    favorites.add(s);
                }
            }
        }

        Gson gson = new Gson();
        String res = gson.toJson(favorites);
        String size = gson.toJson(favorites.size());
        response.getWriter().write("[" + res + "," + size + "]");
        response.getWriter().flush();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        UsersManager usersManager = ServletUtils.getUsersManager(getServletContext());

        switch(actionType){
            case Constants.ADD_FAV_LOCATION:
                addFavoriteLocation(request, response, usersManager);
                break;
            case Constants.ADD_FAV_SHOW:
                addFavoriteShows(request, response, usersManager);
                break;
        }
    }

    private void addFavoriteShows(HttpServletRequest request, HttpServletResponse response, UsersManager usersManager) throws IOException {
        response.setContentType("application/json");
        User user = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER));
        String show = request.getParameter(Constants.FAV_SHOW);
        if(user.getFavShows() == null){
            user.setFavShows(show.toLowerCase() + ",");
        }
        else{
            user.setFavShows(user.getFavShows() + show.toLowerCase() + ",");
        }

        updateUserOnDB(user.getEmail(), user, usersManager);

        Gson gson = new Gson();
        String shows = gson.toJson(user.getFavShows());
        response.getWriter().write(shows);
        response.getWriter().flush();
    }

    private void addFavoriteLocation(HttpServletRequest request, HttpServletResponse response, UsersManager usersManager) throws IOException {
        response.setContentType("application/json");
        User user = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER));
        String loc = request.getParameter(Constants.FAV_LOCATION);
        if(user.getFavLocations() == null){
            user.setFavLocations(loc.toLowerCase()+ ",");
        }
        else{
            user.setFavLocations(user.getFavLocations() + loc.toLowerCase() + ",");
        }
        updateUserOnDB(user.getEmail(), user, usersManager);

        Gson gson = new Gson();
        String locations = gson.toJson(user.getFavLocations());
        response.getWriter().write(locations);
        response.getWriter().flush();
    }

    private void updateUserOnDB(String id, User newUser, UsersManager usersManager) {
        User userToUpdate = usersManager.getUserByEmail(id, em);
        DBTrans.remove(em, userToUpdate);
        DBTrans.persist(em, newUser);
        em.close();
    }

    private void getMySellShows(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");
        ArrayList<ShowInterface> res = new ArrayList<>();
        String userId = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getEmail();
        UserShowsManagerInterface userShowsManager = ServletUtils.getUserShowsManager(getServletContext());
        List<ShowInterface> shows = showsManager.getAllShows(em);
        List<UserShowsInterface> userShows = userShowsManager.getShowByUserID(em, userId);

        createShowArrayResult(shows, userShows, res);

        Gson gson = new Gson();
        String showsStr = gson.toJson(res);
        String showNum = gson.toJson(res.size());
        response.getWriter().write("[" + showNum + "," + showsStr + "]");
        response.getWriter().flush();
    }


    private void createShowArrayResult(List<ShowInterface> shows, List<UserShowsInterface> userShowInList, ArrayList<ShowInterface> res)
    {
        int i = 0;
        for (ShowInterface s : shows) {
            if (s.getShowID() == userShowInList.get(i).getShowId()) {
                res.add(s);
                if (i < userShowInList.size() - 1)
                    i++;
                else
                    break;
            }
        }
    }


    //TODO : need to change to showArchive !!!! sivan's work :)
    private void getBoughtTickets(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");
        ArrayList<ShowInterface> res = new ArrayList<>();
        String userId = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getEmail();
        UserShowsManagerInterface userShowBoughtManager = ServletUtils.getUserShowBoughtManager(getServletContext());
        ShowsArchiveManager showsArchiveManager = ServletUtils.getShowsArchiveManager(getServletContext());
        List<ShowInterface> shows = showsArchiveManager.getAllShows(em);
        List<UserShowsInterface> userShowBought = userShowBoughtManager.getShowByUserID(em, userId);

        createShowArchiveArrayResult(shows, userShowBought, res);

        Gson gson = new Gson();
        if(!res.isEmpty()) {
            String showsStr = gson.toJson(res);
            String showNum = gson.toJson(res.size());
            response.getWriter().write("[" + showNum + "," + showsStr + "]");
        }
        else{
            String showResultString = gson.toJson(Constants.SHOW_NOT_EXIST);
            response.getWriter().write("[" + showResultString + "]");
        }
        response.getWriter().flush();
    }


    private void createShowArchiveArrayResult(List<ShowInterface> shows, List<UserShowsInterface> userShowBought, ArrayList<ShowInterface> res)
    {
        int i,j = 0;
        for (i = 0; i < shows.size(); i++) {
            if(shows.get(i).getShowID() == userShowBought.get(j).getShowId()){
                res.add(shows.get(i));
                j++;
            }
        }
    }
}
*/
