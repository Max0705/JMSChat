package JMSChat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.JMSException;
import javax.swing.*;

/**
 * 
 * @author Markus Schulmeister
 * @date 2013-04-12
 *
 */
public class MailboxPanel extends JPanel {
	public static JTextArea mails;
	
	private JButton schliessen, empfangen;
	private JLabel fname;
	private JPanel p;
	private JScrollPane sp1;
	private JMSQueueReceiver r2;
	private MailboxFrame mf1 = null;

	private String user;
	private String pw;
	private String url;
	private String eigIP;
	
	public MailboxPanel(String name, String pw, String url, MailboxFrame mf1) {
		schliessen = new JButton("Schliessen");
		empfangen = new JButton("Empfangen");
		
		this.mf1 = mf1;

		try {
			this.user = name;
			this.pw = pw;
			this.url = url;
			this.eigIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fname = new JLabel(" Mails von: " + name + "; IP: " + eigIP + ": ");

		mails = new JTextArea(" Mails: \n ");
		sp1 = new JScrollPane(mails);
		sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.setLayout(new BorderLayout());
		mails.setEditable(false);

		p = new JPanel();
		p.setLayout(new GridLayout(2,1));

		p.add(empfangen);
		p.add(schliessen);
		
		this.add(fname, BorderLayout.NORTH);
		this.add(sp1, BorderLayout.CENTER);
		this.add(p, BorderLayout.SOUTH);

		MyActionListener al = new MyActionListener();
		schliessen.addActionListener(al);
		empfangen.addActionListener(al);
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==schliessen){
				if(r2 != null){
					r2.abmelden();
					mf1.dispose();
				}else{
					mf1.dispose();
				}
			}
			if(e.getSource()==empfangen){
				r2 = new JMSQueueReceiver(user, pw, url, eigIP, mails);
				mails.setText("");
				try {
					r2.anmelden();
					System.out.println(eigIP);
					r2.loadMails(eigIP);
					System.out.println(url);
					r2.abmelden();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}

	}

	
	public void abmelden(){
		if(r2 != null){
			r2.abmelden();
			mf1.dispose();
		}else{
			mf1.dispose();
		}
	}
}
