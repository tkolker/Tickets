package logic;

import servlets.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

import static logic.ShowArchiveNumber.showArchiveNumber;

@Entity
public class ShowArchive implements Serializable, ShowInterface{
    @Id
    @GeneratedValue
    private int m_ShowID;
    private String m_ShowName;
    private String m_Location;
    private String m_PictureUrl;
    private int m_Price;
    private Date m_Date;
    private String m_About;
    private int m_NumOfTickets;
    private String m_Email;

    public ShowArchive(){
        m_ShowID = showArchiveNumber++;
    }

    public ShowArchive(String name, String location, String url, int numOfTickets, int price, Date date, String about){
        m_ShowID = showArchiveNumber++;
        m_ShowName = name;
        m_Location = location;
        m_Price = price;
        m_Date = date;
        m_PictureUrl = url;
        m_NumOfTickets = numOfTickets;
        m_About = about;
    }

    public ShowArchive(int id, String name, String location, String url, int numOfTickets, int price, Date date, String about/*, String email*/){
        m_ShowID = id;
        m_ShowName = name;
        m_Location = location;
        m_Price = price;
        m_Date = date;
        m_PictureUrl = url;
        m_NumOfTickets = numOfTickets;
        m_About = about;
        //m_Email = email;
    }

    public int getShowPrice() { return  m_Price; }

    public void setShowPrice(int price) { m_Price = price; }

    public Date getShowDate() { return m_Date; }

    public String getShowName()
    {
        return m_ShowName;
    }

    @Override
    public int getSeller() {
        return Constants.INDIVIDUAL_SELLER;
    }

    @Override
    public String getUserId() {
        return m_Email;
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

    public int getShowTickets() {
        return m_NumOfTickets;
    }
}
