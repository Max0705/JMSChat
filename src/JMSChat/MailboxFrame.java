package JMSChat;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * @author Markus Schulmeister
 * @date 2013-04-12
 *
 */
public class MailboxFrame extends JFrame {
    private MailboxPanel mp1;
	/**
     * Der Konstruktor erzeugt das Fenster und legt bestimmte Eigenschaften dafür fest.
     */
	
    public MailboxFrame(String name,String pw, String url) {
        mp1 = new MailboxPanel(name,pw, url, this);
    	Container c = getContentPane();
        c.add (mp1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize (400, 400); // Größe des Fensters
        setVisible (true);  // setzt ob man das Fenster sieht
        setTitle ("MailBox");    // der Titel für das Fenster wird gesetzt
    }
    
    public void close(){
    	mp1.abmelden();
    }
}
        