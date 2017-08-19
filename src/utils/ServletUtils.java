package utils;

import appManager.*;
import logic.UserShowBought;
import appManager.ShowsManager;
import appManager.UserShowsManager;
import appManager.UsersManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import servlets.Constants;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ServletUtils {

    private static final String USERS_MANAGER_ATTRIBUTE_NAME = "usersManager";
    private static final String SHOWS_MANAGER_ATTRIBUTE_NAME = "showsManager";
    private static final String USER_SHOWS_MANAGER_ATTRIBUTE_NAME = "userShowsManager";
    private static final String USER_SHOW_BOUGHT_MANAGER_ATTRIBUTE_NAME = "userShowBoughtManager";
    private static final String SHOWS_ARCHIVE_MANAGER_ATTRIBUTE_NAME = "userShowsArchiveManager";

    public static UsersManager getUsersManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USERS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USERS_MANAGER_ATTRIBUTE_NAME, new UsersManager());
        }
        return (UsersManager) servletContext.getAttribute(USERS_MANAGER_ATTRIBUTE_NAME);
    }

    public static UserShowBoughtManager getUserShowBoughtManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USER_SHOW_BOUGHT_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USER_SHOW_BOUGHT_MANAGER_ATTRIBUTE_NAME, new UserShowBoughtManager());
        }
        return (UserShowBoughtManager) servletContext.getAttribute(USER_SHOW_BOUGHT_MANAGER_ATTRIBUTE_NAME);
    }

    public static ShowsManager getShowsManager(ServletContext servletContext) {
        if (servletContext.getAttribute(SHOWS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(SHOWS_MANAGER_ATTRIBUTE_NAME, new ShowsManager());
        }
        return (ShowsManager) servletContext.getAttribute(SHOWS_MANAGER_ATTRIBUTE_NAME);
    }

    public static ShowsArchiveManager getShowsArchiveManager(ServletContext servletContext) {
        if (servletContext.getAttribute(SHOWS_ARCHIVE_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(SHOWS_ARCHIVE_MANAGER_ATTRIBUTE_NAME, new ShowsArchiveManager());
        }
        return (ShowsArchiveManager) servletContext.getAttribute(SHOWS_ARCHIVE_MANAGER_ATTRIBUTE_NAME);
    }

    public static UserShowsManager getUserShowsManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USER_SHOWS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USER_SHOWS_MANAGER_ATTRIBUTE_NAME, new UserShowsManager());
        }
        return (UserShowsManager) servletContext.getAttribute(USER_SHOWS_MANAGER_ATTRIBUTE_NAME);
    }

    public static String uploadImageToCloud(String img, int type) throws IOException {
        String publicId;
        Cloudinary cloudinary = new Cloudinary();
        Map uploadResult = null;

        try {
            if (type == Constants.IMG)
                uploadResult = cloudinary.uploader().upload(new File(img), ObjectUtils.emptyMap());
            else
                uploadResult = cloudinary.uploader().upload(img, ObjectUtils.emptyMap());
        } catch (Exception e){
            int i = 0;
        }
        publicId = (String) uploadResult.get("public_id");
        return publicId;
    }
}
