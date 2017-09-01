package servlets;

import appManager.ShowsManager;
import appManager.db_manager.DBTrans;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import logic.Show;
import org.cloudinary.json.JSONArray;
import org.json.simple.parser.JSONParser;
import utils.ServletUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


@MultipartConfig
@WebServlet(name = "CrawlersServlet", urlPatterns = {"/Crawlers"})
public class CrawlersServlet extends HttpServlet {

    EntityManagerFactory emf;
    EntityManager em;
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "tickets",
            "api_key", "363777688854323",
            "api_secret", "Ug-k08JZjiPTcwcXEShfkLO1Eeo"));
    private JspContext servletContext;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();

        request.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        Show show;
        ShowsManager showsManager = ServletUtils.getShowsManager(getServletContext());

        switch (actionType) {
            case Constants.CRAWLER_ZAPPA_UPDATE:
                try {
                    writeCrawlResults(request, response, showsManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case Constants.CRAWLER_BRAVO_UPDATE:
                try {
                    writeBravoCrawlResults(request, response, showsManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void writeBravoCrawlResults(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws Exception, FileNotFoundException, ParseException {

        ArrayList<Show> shows = new ArrayList<>();
        String ResultsStr = request.getParameter(Constants.CRAWLER_SHOWS);

        String[] parsedShows = ServletUtils.parseRequestParams(ResultsStr);

        for (int i = 0; i < parsedShows.length; i++) {
            shows.add(Show.parseShowFromBravo(em, parsedShows[i], showsManager, i + 1));
        }

        addShowFromCrawler(shows, showsManager);
    }

    private void writeCrawlResults(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws Exception {
        ArrayList<Show> shows = new ArrayList<>();
        String sStr = request.getParameter(Constants.CRAWLER_SHOWS);

        String[] parsedShows = ServletUtils.parseRequestParams(sStr);

        for (int i = 0; i < parsedShows.length; i++) {
            shows.add(Show.parseShow(em, parsedShows[i], showsManager, i + 1));
        }

        addShowFromCrawler(shows, showsManager);
    }


    private void addShowFromCrawler(ArrayList<Show> shows, ShowsManager showsManager) {
        for (Show s : shows) {
            if (showsManager.showLocationAndDateExist(showsManager.getAllShows(em), s) == null) {
                String picture = ServletUtils.uploadImageToCloud(s.getPictureUrl());
                s.setPictureUrl(picture);
                DBTrans.persist(em, s);
            }
        }
        em.close();
    }
}