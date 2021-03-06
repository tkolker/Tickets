package utils;

import appManager.*;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import servlets.Constants;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Map;

public class ServletUtils {

    private static final String USERS_MANAGER_ATTRIBUTE_NAME = "usersManager";
    private static final String SHOWS_MANAGER_ATTRIBUTE_NAME = "showsManager";
    private static final String USER_SHOWS_MANAGER_ATTRIBUTE_NAME = "userShowsManager";
    private static final String USER_SHOW_BOUGHT_MANAGER_ATTRIBUTE_NAME = "userShowBoughtManager";
    private static final String SHOWS_ARCHIVE_MANAGER_ATTRIBUTE_NAME = "userShowsArchiveManager";
    private static final String MESSAGE_MANAGER_ATTRIBUTE_NAME = "messageManager";

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

    public static MessageManager getMessageManager(ServletContext servletContext) {
        if (servletContext.getAttribute(MESSAGE_MANAGER_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(MESSAGE_MANAGER_ATTRIBUTE_NAME, new MessageManager());
        }
        return (MessageManager) servletContext.getAttribute(MESSAGE_MANAGER_ATTRIBUTE_NAME);
    }

    public static String uploadImageToCloud(HttpServletRequest request, int type) throws IOException {
        String publicId;
        Map uploadResult = null;

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tickets",
                "api_key", "363777688854323",
                "api_secret", "Ug-k08JZjiPTcwcXEShfkLO1Eeo"));


        try {
            if (type == Constants.IMG)

                uploadResult = cloudinary.uploader().upload(new File(saveImageTemporary(request.getPart(Constants.PICTURE_URL))), ObjectUtils.emptyMap());
            else {
                uploadResult = cloudinary.uploader().upload(request.getParameter(Constants.PICTURE_URL), ObjectUtils.emptyMap());
            }
        } catch (Exception e) {
            int i = 0;
        }

        publicId = uploadResult.get("url").toString();
        return publicId;
    }


    private static String saveImageTemporary(Part filePart)
            throws ServletException, IOException {

        String fileName = getFileName(filePart);
        if (fileName == null)
            return null;

        String filePath = null;
        OutputStream out = null;
        InputStream fileContent = null;
        String tempDirPath = System.getProperty("java.io.tmpdir");

        try {
            filePath = tempDirPath + File.separator + fileName;
            out = new FileOutputStream(new File(filePath));
            fileContent = filePart.getInputStream();

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            //Problems during file upload
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }

        return filePath;
    }

    private static String getFileName(final Part part) {
        String partHeader = part.getHeader("content-disposition");

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }


    public static String[] parseRequestParams(String sStr) {
        String[] showsStr = sStr.split("(?<=})");
        String[] parsedShows = new String[showsStr.length - 1];

        for(int i = 0; i < showsStr.length - 1; i++){
            if(i == showsStr.length - 2){
                parsedShows[i] = showsStr[i].substring(1, showsStr[i].length());
            }
            else {
                parsedShows[i] = showsStr[i].substring(1);
            }
        }

        return parsedShows;
    }

    public static String uploadImageToCloud(String pictureUrl, String type) {
        String publicId;
        Map uploadResult = null;

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tickets",
                "api_key", "363777688854323",
                "api_secret", "Ug-k08JZjiPTcwcXEShfkLO1Eeo"));


        try {
            if(type.equals(Constants.ZAPPA)) {
                uploadResult = cloudinary.uploader().upload(pictureUrl.substring(1, pictureUrl.length() - 1), ObjectUtils.emptyMap());
            }
            else{
                uploadResult = cloudinary.uploader().upload(pictureUrl, ObjectUtils.emptyMap());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        publicId = uploadResult.get("url").toString();
        return publicId;
    }

}

