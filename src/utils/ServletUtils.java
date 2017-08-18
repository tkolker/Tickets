package utils;

import appManager.*;
import logic.UserShowBought;

import javax.servlet.ServletContext;

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
}
