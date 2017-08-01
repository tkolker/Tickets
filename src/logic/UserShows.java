package logic;

import java.util.ArrayList;


public class UserShows {

    private String m_UserEmail;
    private ArrayList<Integer> m_ShowToSellID;
    private ArrayList<Integer> m_PurchaseShowsID;

    public UserShows(String id)
    {
        m_UserEmail = id;
        m_PurchaseShowsID = new ArrayList<Integer>();
        m_ShowToSellID = new ArrayList<Integer>();
    }

    public String getUserID() { return m_UserEmail; }

    public void addShowToSell(int showId)
    {
        m_ShowToSellID.add(showId);
    }

    public void addPurchaseShow(int showId)
    {
        m_PurchaseShowsID.add(showId);
    }

    public ArrayList<Integer> getShowToSellID()
    {
        return m_ShowToSellID;
    }

    public ArrayList<Integer> getPurchaseShow()
    {
        return m_PurchaseShowsID;
    }
}
