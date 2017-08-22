package logic;

import servlets.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class Show implements Serializable, ShowInterface {
    @Id @GeneratedValue
    private int m_ShowID;
    private String m_ShowName;
    private String m_Location;
    private String m_PictureUrl;
    private int m_Price;
    private Date m_Date;
    private String m_About;
    private int m_NumOfTickets;
    private int m_Seller = Constants.INDIVIDUAL_SELLER;

    public Show(){
        m_ShowID = ShowNumber.showNumber++;
    }

    public Show(String name, String location, String url, int numOfTickets, int price, Date date, String about){
        m_ShowID = ShowNumber.showNumber++;
        m_ShowName = name;
        m_Location = location;
        m_Price = price;
        m_Date = date;
        m_PictureUrl = url;
        m_NumOfTickets = numOfTickets;
        m_About = about;
    }

    public Show(int id, String name, String location, String url, int numOfTickets, int price, Date date, String about){
        m_ShowID = id;
        m_ShowName = name;
        m_Location = location;
        m_Price = price;
        m_Date = date;
        m_PictureUrl = url;
        m_NumOfTickets = numOfTickets;
        m_About = about;
    }

    public void setSeller(int i_seller)
    {
        m_Seller = i_seller;
    }

    public int getShowPrice() { return  m_Price; }

    public void setShowPrice(int price) { m_Price = price; }

    public Date getShowDate() { return m_Date; }

    public String getShowName()
    {
        return m_ShowName;
    }

    public String getLocation()
    {
        return m_Location;
    }

    public String getPictureUrl()
    {
        return m_PictureUrl;
    }

    public int getShowID() { return  m_ShowID; }

    public String getAbout(){
        return m_About;
    }

    public void setShowName(String showName) {
        this.m_ShowName = showName;
    }

    public void setShowLocation(String showLocation) {
        this.m_Location = showLocation;
    }

    public void setPictureUrl(String pictureUrl) {
        this.m_PictureUrl = pictureUrl;
    }

    public void setShowDate(Date showDate) {
        this.m_Date = showDate;
    }

    public void setShowId(int showId) {
        this.m_ShowID = showId;
    }

    public void setAbout(String about){
        m_About = about;
    }

    public static void updateShowDetails(Show showToUpdate, Show showToDelete) {

        if(showToUpdate.getShowName().isEmpty())
            showToUpdate.setShowName(showToDelete.getShowName());
        if(showToUpdate.getLocation().isEmpty())
            showToUpdate.setShowLocation(showToDelete.getLocation());
        if(showToUpdate.getPictureUrl().isEmpty())
            showToUpdate.setPictureUrl(showToDelete.getPictureUrl());
        if(showToUpdate.getShowDate() == null)
            showToUpdate.setShowDate(showToDelete.getShowDate());
        if(showToUpdate.getShowPrice() < 0)
            showToUpdate.setShowPrice(showToDelete.getShowPrice());
        if(showToUpdate.getNumOfTickets() < 0)
            showToUpdate.setNumOfTickets(showToDelete.getNumOfTickets());
        if(showToUpdate.getAbout().isEmpty())
            showToUpdate.setAbout(showToDelete.getAbout());

        showToUpdate.setShowId(showToDelete.getShowID());
    }

    public static Show createShow(String name, String loc, String url, int numOfTickets, int price, LocalDateTime date, String about) {
        Date showDate = Date.from(ZonedDateTime.of(date, ZoneId.systemDefault()).toInstant());
        return new Show(name, loc, url, numOfTickets, price, showDate, about);
    }

    public static Show createShowToUpdate(String id, String name, String loc, String pic, String numOfTickets, String price, String date, String about) {
        int tid = Integer.parseInt(id);
        int tnum;
        int tprice;
        Date tdate;

        if(numOfTickets.equals(""))
            tnum = -1;
        else
            tnum = Integer.parseInt(numOfTickets);

        if(price.equals(""))
            tprice = -1;
        else
            tprice = Integer.parseInt(price);

        if(date.equals(""))
            tdate = null;
        else
            tdate = Date.from(ZonedDateTime.of(LocalDateTime.parse(date), ZoneId.systemDefault()).toInstant());

        return new Show(tid, name, loc, pic, tnum, tprice, tdate, about);
    }

    public int getNumOfTickets() {
        return m_NumOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.m_NumOfTickets = numOfTickets;
    }
}

