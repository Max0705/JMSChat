package JMSChat;
import javax.swing.*;
import java.awt.*;

/**
 * Diese Klasse erzeugt das Fenster und fuehrt dies in der main-Methode aus.
 * 
 * @author Markus Schulmeister
 * @date 2013-04-12
 */
public class MailSendenFrame extends JFrame {
    private MailSendenPanel mp1;
	/**
     * Der Konstruktor erzeugt das Fenster und legt bestimmte Eigenschaften dafür fest.
     */
	
    public MailSendenFrame(String name, String pw, String url) {
        mp1 = new MailSendenPanel(name, pw, url, this);
    	Container c = getContentPane();
        c.add (mp1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize (400, 400); // Größe des Fensters
        setVisible (true);  // setzt ob man das Fenster sieht
        setTitle ("Neue Mail");    // der Titel für das Fenster wird gesetzt
    }
}
        