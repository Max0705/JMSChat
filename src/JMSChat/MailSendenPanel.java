package JMSChat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.jms.JMSException;
import javax.swing.*;

/**
 * 
 * @author Markus Schulmeister
 * @date 2013-04-12
 *
 */
public class MailSendenPanel extends JPanel {
	public static JTextArea text;
	
	private JButton schliessen, senden;
	private JLabel fname, empfaenger, leer;
	private JTextField destIp;
	private JPanel p, p1;
	private JScrollPane sp1;
	private JMSQueueSender s2;
	private MailSendenFrame msf1;
	
	private String user;
	private String pw;
	private String url;
	private String eigIP;
	
	public MailSendenPanel(String name, String pw, String url, MailSendenFrame msf1) {
		schliessen = new JButton("Schliessen");
		senden = new JButton("Senden");
		
		this.msf1 = msf1;

		try {
			this.user = name;
			this.pw = pw;
			this.url = url;
			this.eigIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fname = new JLabel(" Sender: " + name + "; IP: " + eigIP);
		empfaenger = new JLabel(" IP des Empfaengers ");
		leer = new JLabel("");
		
		destIp = new JTextField("127.0.0.1");

		text = new JTextArea(" Nachricht eingeben: \n ");
		sp1 = new JScrollPane(text);
		sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.setLayout(new BorderLayout());

		p = new JPanel();
		p1 = new JPanel();
		p.setLayout(new GridLayout(2,1));
		p1.setLayout(new GridLayout(2,2));
		
		p1.add(fname);
		p1.add(leer);
		p1.add(empfaenger);
		p1.add(destIp);

		p.add(senden);
		p.add(schliessen);
		
		this.add(p1, BorderLayout.NORTH);
		this.add(sp1, BorderLayout.CENTER);
		this.add(p, BorderLayout.SOUTH);

		MyActionListener al = new MyActionListener();
		schliessen.addActionListener(al);
		senden.addActionListener(al);
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==schliessen){
				if(s2 != null){
					s2.abmelden();
					msf1.dispose();
				}else{
					msf1.dispose();
				}
			}
			if(e.getSource() == senden){
				s2 = new JMSQueueSender(user, pw, url, destIp.getText());
				System.out.println(user + pw + url + destIp.getText() );
				try {
					s2.anmelden();
					s2.senden("Von: " + user + "\n\tGesendet am: " + Calendar.getInstance().getTime() + "\n\tNachricht:\n\t\t" + text.getText());
					s2.abmelden();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					msf1.dispose();
				}
			}
		}

	}
	
}
