package utils;

import appManager.ShowsManager;
import appManager.UserShowsManager;
import appManager.UsersManager;

import javax.servlet.ServletContext;

public class ServletUtils {

    private static final String USERS_MANAGER_ATTRIBUTE_NAME = "usersManager";
    private static final String SHOWS_MANAGER_ATTRIBUTE_NAME = "showsManager";
    private static final String USER_SHOWS_MANAGER_ATTRIBUTE_NAME = "userShowsManager";

    public static UsersManager getUsersManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USERS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USERS_MANAGER_ATTRIBUTE_NAME, new UsersManager());
        }
        return (UsersManager) servletContext.getAttribute(USERS_MANAGER_ATTRIBUTE_NAME);
    }

    public static ShowsManager getShowsManager(ServletContext servletContext) {
        if (servletContext.getAttribute(SHOWS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(SHOWS_MANAGER_ATTRIBUTE_NAME, new ShowsManager());
        }
        return (ShowsManager) servletContext.getAttribute(SHOWS_MANAGER_ATTRIBUTE_NAME);
    }

    public static UserShowsManager getUserShowsManager(ServletContext servletContext) {
        if (servletContext.getAttribute(USER_SHOWS_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(USER_SHOWS_MANAGER_ATTRIBUTE_NAME, new UserShowsManager());
        }
        return (UserShowsManager) servletContext.getAttribute(USER_SHOWS_MANAGER_ATTRIBUTE_NAME);
    }
}
