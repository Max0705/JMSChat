package JMSChat;

import javax.swing.*;
import java.awt.*;

public class JMSChatFrame extends JFrame {
    
    public JMSChatFrame() {
        Container c = getContentPane();
        c.add (new GUIPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize (500, 700);
        setVisible (true);
        setTitle ("JMS Chat");
    }
    
    public static void main(String args[]) {
        new JMSChatFrame();
    }
}
        