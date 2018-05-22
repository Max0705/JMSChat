package JMSChat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

public class GUIPanel extends JPanel {
	public static JTextArea chat;
	
	private JButton senden, anmelden, abmelden, mailbox, mailsenden;
	private JLabel fname, fpw, fchatroom, fip;
	private JTextField name, pw, chatroom, ip, nachricht;
	private JPanel p, p1, p2;
	private JScrollPane sp1;
	private JMSChatSender s1;
	private JMSChatReceiver r1;
	private MailboxFrame mf1;
	private MailSendenFrame msf1;
	private Boolean hilfsv;
	private StartEmpfaenger t1;

	public GUIPanel() {
		senden = new JButton("Senden");
		anmelden = new JButton("Anmelden");
		abmelden = new JButton("Abmelden");
		mailbox = new JButton("MAILBOX");
		mailsenden = new JButton("Neue Mail");

		fname = new JLabel(" Name");
		fchatroom = new JLabel(" Chatroom");
		fip = new JLabel(" IP des Brookers");
		fpw = new JLabel(" Passwort");

		name = new JTextField("Name");
		chatroom = new JTextField("Chatroom");
		ip = new JTextField("localhost");
		pw = new JPasswordField("Passwort");
		nachricht = new JTextField("Nachricht hier eingeben");

		chat = new JTextArea(" Chatverlauf: \n ");
		sp1 = new JScrollPane(chat);
		sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.setLayout(new BorderLayout());
		nachricht.setEditable(false);
		abmelden.setEnabled(false);
		senden.setEnabled(false);
		chat.setEditable(false);
		mailbox.setEnabled(false);
		mailsenden.setEnabled(false);

		p = new JPanel();
		p.setLayout(new BorderLayout());

		p1 = new JPanel();
		p1.setLayout(new GridLayout(5, 2));
		p1.add(fname);
		p1.add(name);
		p1.add(fpw);
		p1.add(pw);
		p1.add(fchatroom);
		p1.add(chatroom);
		p1.add(fip);
		p1.add(ip);
		p1.add(anmelden);
		p1.add(abmelden);

		p.add(p1, BorderLayout.NORTH);
		p.add(nachricht, BorderLayout.CENTER);
		p.add(senden, BorderLayout.SOUTH);
		
		p2 = new JPanel();
		p2.setLayout(new GridLayout(2,1));
		p2.add(mailbox);
		p2.add(mailsenden);

		this.add(p, BorderLayout.NORTH);
		this.add(sp1, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);

		MyActionListener al = new MyActionListener();
		senden.addActionListener(al);
		anmelden.addActionListener(al);
		abmelden.addActionListener(al);
		mailbox.addActionListener(al);
		mailsenden.addActionListener(al);
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == anmelden) {
				hilfsv = true;
				try {
					s1 = new JMSChatSender(name.getText(), pw.getText(),
							ip.getText(), InetAddress.getLocalHost()
							.getHostAddress(), chatroom.getText());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				r1 = new JMSChatReceiver(name.getText(), pw.getText(),
						ip.getText(), chatroom.getText());
				abmelden.setEnabled(true);
				anmelden.setEnabled(false);
				name.setEditable(false);
				pw.setEditable(false);
				chatroom.setEditable(false);
				ip.setEditable(false);
				nachricht.setEditable(true);
				senden.setEnabled(true);
				mailbox.setEnabled(true);
				mailsenden.setEnabled(true);
				s1.anmelden();
				r1.anmelden();
				t1 = new StartEmpfaenger();
				t1.start();
			}
			if (e.getSource() == abmelden) {
				hilfsv = false;
				s1.abmelden();
				r1.abmelden();
				abmelden.setEnabled(false);
				anmelden.setEnabled(true);
				name.setEditable(true);
				pw.setEditable(true);
				chatroom.setEditable(true);
				ip.setEditable(true);
				nachricht.setEditable(false);
				senden.setEnabled(false);
				mailbox.setEnabled(false);
				mailsenden.setEnabled(false);
				if(mf1 != null){
					mf1.close();
				}
			}
			if (e.getSource() == senden) {
				s1.senden(nachricht.getText());
				nachricht.setText("");

			}
			if (e.getSource() == mailbox){
				mf1 = new MailboxFrame(name.getText(),pw.getText(), ip.getText());
			}
			if (e.getSource() == mailsenden){
				msf1 = new MailSendenFrame(name.getText(),pw.getText(), ip.getText());
			}
		}

	}

	private class StartEmpfaenger extends Thread {
		public void run() {
			while(true){
				if(hilfsv==true){
					r1.empfangen();
				}
			}
		}
	}
}
