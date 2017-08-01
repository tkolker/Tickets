package logic;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import static logic.TicketNumber.ticketNumber;

class TicketNumber { public static int ticketNumber = 1; }

public class Ticket {
    @Id @GeneratedValue
    private int m_TicketID;
    private int m_Price;
    private Date m_Date;

    public Ticket(int price, Date date)
    {
        m_TicketID = ticketNumber++;
        m_Price = price;
        m_Date = date;
    }

    public int getTicketID()
    {
        return m_TicketID;
    }

    public int getPrice()
    {
        return m_Price;
    }

    public Date getDate()
    {
        return m_Date;
    }
}
