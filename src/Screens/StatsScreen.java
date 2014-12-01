package Screens;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Client.ThinkTankGUI;
import Game.Event;
import Server.StatsObject;

public class StatsScreen extends JPanel {
	
	private ThinkTankGUI gui;
	private JScrollPane jsp;
	private JTable table;
	private DefaultTableModel tableModel;
	private String [] columnNames={"", ""};
	private Object [][]data={{""}};
	private Dimension dimension=new Dimension(200,600);

	public StatsScreen(ThinkTankGUI gui) {
		this.gui = gui;
		this.setLayout(null);
		this.add(new JLabel("Waiting for Stats"));
	}
	
	public void statsResponse(Event response) {
		if (response.result) {
			this.setLayout(new BorderLayout());
			StatsObject stats = (StatsObject) response.data;
			tableModel=new DefaultTableModel(data, columnNames);
			tableModel.addRow(new Object[]{"User Name",stats.username});
			tableModel.addRow(new Object[]{"Total Games",stats.total_games});
			tableModel.addRow(new Object[]{"Total Wins", stats.wins});
			tableModel.addRow(new Object[]{"Total Shots", stats.numShots});
			tableModel.addRow(new Object[]{"Total Hits", stats.numHits});
			tableModel.addRow(new Object[]{"Accuracy", ((stats.numHits)/((double)stats.numShots + 0.0000001)*100) + "%"});
			tableModel.addRow(new Object[]{"Total Kills", stats.numKills});
			tableModel.addRow(new Object[]{"Total Deaths", stats.numDeaths});
			tableModel.addRow(new Object[]{"Total Brains Destroyed", stats.brainsDestroyed});
			table=new JTable(tableModel);
			jsp=new JScrollPane(table);
			jsp.setPreferredSize(dimension);
			this.add(jsp, BorderLayout.CENTER);
		} else {
			JOptionPane.showMessageDialog(null, response.data, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void getStatsFor(String username) {
		gui.centralConnection.sendEvent(new Event("get stats", username));
	}
	
}
