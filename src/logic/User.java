package logic;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    //ctor
    public User() {}

    // Persistent Fields:
    @Id
    String m_Email;
    private String m_fName;
    private String m_lName;
    private String m_Password;

    public User(String id, String fname, String lname, String pass) {
        this.m_Email = id;
        this.m_fName = fname;
        this.m_lName = lname;
        this.m_Password = pass;
    }

    public User(String id, String pass) {
        this.m_Email = id;
        this.m_Password = pass;
    }

    // String Representation:
    @Override
    public String toString() {
        return m_fName + " " + m_lName;
    }

    public String getEmail() {
        return m_Email;
    }

    public String getFirstName() {
        return m_fName;
    }

    public String getLastName() {
        return m_lName;
    }

    public String getPassword() {
        return m_Password;
    }

    public boolean emailValidation(String email)
    {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public void copyUser(String email, String firstName, String lastName, String password) {
        this.m_Email = email;
        this.m_fName = firstName;
        this.m_lName = lastName;
        this.m_Password = password;
    }
}
