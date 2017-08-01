package logic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Show implements Serializable {
    @Id @GeneratedValue
    private int m_ShowID;
    private String m_ShowName;
    private String m_Location;
    private String m_PictureUrl;
    private int m_Price;
    private Date m_Date;
    private String m_About;
    //private ArrayList<Ticket> m_Tickets;
    private int m_NumOfTickets;

    public Show(){
        m_ShowID = ShowNumber.showNumber++;
    }

    public Show(String name, String location, String url, int numOfTickets, int price, Date date, String about /*ArrayList<Ticket> tickets*/){
        m_ShowID = ShowNumber.showNumber++;
        m_ShowName = name;
        m_Location = location;
        m_Price = price;
        m_Date = date;
        m_PictureUrl = url;
        m_NumOfTickets = numOfTickets;
        m_About = about;
        //m_Tickets = tickets;
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
        if(showToUpdate.getShowDate().toString().isEmpty())
            showToUpdate.setShowDate(showToDelete.getShowDate());
        if(Integer.toString(showToUpdate.getShowPrice()).isEmpty())
            showToUpdate.setShowPrice(showToDelete.getShowPrice());
        if(showToUpdate.getAbout().isEmpty())
            showToUpdate.setAbout(showToDelete.getAbout());

        showToUpdate.setShowId(showToDelete.getShowID());
    }
}
