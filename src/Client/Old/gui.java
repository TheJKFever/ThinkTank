//package Client;
//
//import java.awt.BorderLayout;
//import java.awt.Graphics;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.Image;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.ExecutorService;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//public class gui extends JFrame  {
//
//	ImageIcon tankii = new ImageIcon("tank.png");
//	Image tanki = tankii.getImage();
//	Tank tank;
//	myPanel battleField;
//	JPanel rightPanel, functionBtnPanel;
//	JButton l,r,f,b,s;
//	
//	Enemy enemy;
//	
//	public gui() {
//		super();
//		setSize(800,600);
//		setLocation(200,100);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		configureBtnPanel();
//		configureDirBtnAction();
//		
//		battleField = new myPanel();
//		add(battleField);
//		
//		
//		tank = new Tank(this);
//		enemy = new Enemy(this);	
//
//		
//		setVisible(true);
//	}
//	
//	public void configureBtnPanel() {
//		rightPanel = new JPanel(new BorderLayout());
//		add(rightPanel, BorderLayout.EAST);
//		l = new JButton("<");
//		r = new JButton(">");
//		f = new JButton("^");
//		b = new JButton("v");
//		s = new JButton("S");
//		
//		functionBtnPanel = new JPanel(new GridBagLayout());
//		
//		addFunctionBtn(f,1,0);
//		addFunctionBtn(l,0,1);
//		addFunctionBtn(r,2,1);
//		addFunctionBtn(b,1,2);
//		addFunctionBtn(s,1,1);
//		
//		rightPanel.add(functionBtnPanel, BorderLayout.SOUTH);
//	}
//	
//	// add listener to Buttons to control the tank
//	public void configureDirBtnAction() {
//		
//		f.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.forward = true;
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.forward = false;
//				battleField.grabFocus();
//			}
//		});
//		
//		b.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.backward = true;
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.backward = false;
//				battleField.grabFocus();
//			}
//		});
//		
//		l.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.leftward = true;
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.leftward = false;
//				battleField.grabFocus();
//			}
//		});
//		
//		r.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.rightward = true;
//			}
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				tank.rightward = false;
//				battleField.grabFocus();
//			}
//		});	
//
//		s.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				tank.shoot();
//				battleField.grabFocus();
//			}
//		});
//	}
//	
//	// add tank-control buttons to the GUI
//	public void addFunctionBtn(JButton btn,int gridx, int gridy) {
//		GridBagConstraints c = new GridBagConstraints();
//		c.gridx = gridx;
//		c.gridy = gridy;
//		functionBtnPanel.add(btn, c);
//	}
//	
//	public static void main(String[] args) {
//		new gui();
//	}
//	
//	class myPanel extends JPanel implements Runnable {
//
//		// this method is to add keylistener so that user can use keyboard to control tank
//		public void addKeyListenerToBattleField() {
//			this.setFocusable(true);
//			this.requestFocusInWindow();
//			this.addKeyListener(new KeyListener() {
//
//				@Override
//				public void keyTyped(KeyEvent e) {
//					if(e.getKeyChar()=='s' || e.getKeyChar()=='S')
//						tank.shoot();
//				}
//
//				@Override
//				public void keyPressed(KeyEvent e) {
//					int input = e.getKeyCode();
//					if(input == KeyEvent.VK_RIGHT)
//						tank.rightward = true;
//					else if(input== KeyEvent.VK_LEFT)
//						tank.leftward = true;
//					else if(input == KeyEvent.VK_UP)
//						tank.forward=  true;
//					else if(input==KeyEvent.VK_DOWN)
//						tank.backward = true;
//					else;
//				}
//
//				@Override
//				public void keyReleased(KeyEvent e) {
//					int input = e.getKeyCode();
//					if(input == KeyEvent.VK_RIGHT)
//						tank.rightward = false;
//					else if(input== KeyEvent.VK_LEFT)
//						tank.leftward = false;
//					else if(input == KeyEvent.VK_UP)
//						tank.forward=  false;
//					else if(input==KeyEvent.VK_DOWN)
//						tank.backward = false;
//					
//				}
//			});
//		}
//		
//		public myPanel() {
//			setLayout(new BorderLayout());
//			
//			Thread t = new Thread(this);
//			t.start();
//			
//			addKeyListenerToBattleField();
//		
//		}
//		
//		
//		public void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			
//			// we can paint the background map here if necessary
//			
//			
//			// first paint tank of myself
//			g.drawImage(tanki, tank.x, tank.y, 50, 50, null);
//			
//			// then paint enemy tank if they are still alive
//			if(enemy.visible == true)
//				g.fillRect(enemy.x, enemy.y, 30, 30);
//			
//			//  paint all ammos shooted by me
//			for(int i=0;i<tank.ammoVt.size();i++) {
//				Ammo ammo = tank.ammoVt.get(i);
//				if(ammo.visible == true) {
//					g.fillOval(ammo.x, ammo.y, 10, 10);
//				}
//			}
//			
//			
//			
//			// should also paint all ammos shooted by the enemy tank here
//			
//			
//			
//		}
//		
//		
//		// constantly repaint the battle field panel
//		public void run() {
//			while(true) {
//				repaint();
//			}
//		}
//	}
//}
