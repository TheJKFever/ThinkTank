package Chat;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatClient extends JFrame implements Runnable {
	
	JPanel generalPanel;
	
	JPanel chatPanel,topPanel, centerPanel, bottomPanel, inputPanel, loginPanel;
	JPanel hostNamePanel, playerNamePanel;
	
	JTextField hostNameTf;
	JButton confirmBtn;
	JLabel hostNameLbl, playerNameLbl;
	JTextField playerNameTf;
	
	JScrollPane scroll;
	JTextField tf;
	JLabel chatLabel;
	JTextArea ta;
	JButton sendBtn;
	
	PrintWriter pw;
	BufferedReader br;
	
	String name;
	int index;

	
	public void configureGUI() {
	
		setSize(300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		generalPanel = new JPanel(new CardLayout());
		
		
//		/***** Login Panel *******/
//		loginPanel = new JPanel();
//		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
//		confirmBtn = new JButton("Confirm");
//		hostNameTf = new JTextField(10);
//		hostNameLbl = new JLabel("Host name");
//		
//		
//		hostNamePanel = new JPanel();
//		hostNameLbl = new JLabel("Host Name: ");
//		hostNameTf= new JTextField(10);
//		hostNamePanel.add(hostNameLbl);
//		hostNamePanel.add(hostNameTf);
//		
//		playerNamePanel = new JPanel();
//		playerNameLbl = new JLabel("Player name: ");
//		playerNamePanel.add(playerNameLbl);
//		playerNameTf = new JTextField(10);
//		playerNamePanel.add(playerNameTf);
//		
//		loginPanel.add(hostNamePanel);
//		loginPanel.add(playerNamePanel);
//
//		loginPanel.add(confirmBtn);
//		generalPanel.add(loginPanel, "login");
//		confirmBtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				((CardLayout)generalPanel.getLayout()).show(generalPanel, "chat");
//				name = playerNameTf.getText();
//				setTitle(name);
//				String ip = hostNameTf.getText();
//			}
//		});
		
		
		
		/****** Chat Panel*******/
		chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		
		// top part
		topPanel = new JPanel();
		chatLabel = new JLabel("Chat");
		topPanel.add(chatLabel);
		chatPanel.add(topPanel, BorderLayout.NORTH);
		
		// center part
		ta = new JTextArea();
		ta.setBorder(new EmptyBorder(20,20,20,20));
		ta.setWrapStyleWord(true);
		ta.setLineWrap(true);
		scroll = new JScrollPane(ta);
		
		centerPanel = new JPanel();
		centerPanel.add(scroll);
		chatPanel.add(centerPanel);
	
		chatPanel.add(scroll);
		
		// bottom part
		inputPanel = new JPanel(new BorderLayout());
		tf = new JTextField(20);
		inputPanel.add(tf);
		sendBtn = new JButton("Send");
		inputPanel.add(sendBtn, BorderLayout.EAST);
		chatPanel.add(inputPanel, BorderLayout.SOUTH);
		generalPanel.add(chatPanel,"chat");
		

		add(generalPanel);
		setVisible(true);
	}
	
	
	public ChatClient(String hostName, int port) {
		super();
		this.name = "Player ";
		this.setTitle(name);
		configureGUI();
		
		try {
			Socket s = new Socket(hostName, port);
			
			// wrap socket into pw and br
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			Thread t = new Thread(this);
			t.start();
			
			// This is where the client send sth out
			sendBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String newMessage = "["+ChatClient.this.name+"]"+"\t"+tf.getText();
					tf.setText("");
					String oldMessage = ta.getText();
					ta.setText(oldMessage+"\n"+newMessage);
					pw.println(newMessage);
					pw.flush();
				}
			});
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		while (true) {
			try {
				String message = br.readLine();
				if(message.substring(0,1).equalsIgnoreCase("i")) {
					String[] split = message.split(":");
					this.name +=split[1];
					this.setTitle(name);
					this.repaint();
				}
				else
					ta.setText(ta.getText()+"\n"+ message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("IP: ");
		String ip = scan.nextLine();
		new ChatClient(ip, 6789);
	}

}
