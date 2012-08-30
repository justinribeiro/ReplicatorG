/*
 Part of the ReplicatorG project - http://www.replicat.org
 Copyright (c) 2008 Zach Smith

 Forked from Arduino: http://www.arduino.cc

 Based on Processing http://www.processing.org
 Copyright (c) 2004-05 Ben Fry and Casey Reas
 Copyright (c) 2001-04 Massachusetts Institute of Technology

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
 $Id: MainWindow.java 370 2008-01-19 16:37:19Z mellis $
 */

package replicatorg.app.ui.extras.mqttprefs;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.print.attribute.AttributeSet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import net.miginfocom.swing.MigLayout;
import replicatorg.app.Base;
import java.util.prefs.*;

public class MqttPrefsWindow extends JFrame implements ChangeListener, WindowListener {
	
	private JTextField fldAddress;
	private JTextField fldUsername;
	private JPasswordField fldPassword;
	private JTextField fldPrinterName;
	private JTextField fldOrganization;
	private JTextField fldUrl;
	private JTextField fldTopic;
	private JTextField fldLocation;
	private JLabel lblConnectionTest;
	
	private static final String MQTT_NODE = "/com/replicatorg/extra/mqtt";
	
	// Autogenerated by serialver
	static final long serialVersionUID = -3494348039028986935L;

	protected JPanel mainPanel;

	protected JTabbedPane toolsPane;

	private static MqttPrefsWindow instance = null;

	public static synchronized MqttPrefsWindow getPrefs() {
		if (instance == null) {
			instance = new MqttPrefsWindow();
		} else {
			instance.dispose();
		}
		return instance;
	}
	
	private MqttPrefsWindow() {
		super("MQTT Broker Preferences");
		
		// We'll use this for getting our MQTT preferences
		// Note, it's different node that the standard ReplicatorG prefs
		Preferences prefs = Preferences.userRoot().node(MQTT_NODE);
		
		Image icon = Base.getImage("images/mqtt.gif", this);
		setIconImage(icon);
				
		// default behavior
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setPreferredSize(new Dimension(480, 525));

		// no menu bar.
		setJMenuBar(createMenuBar());
		
		// create all our GUI interfaces
		mainPanel = new JPanel(new MigLayout("", "[][425.00,grow][]", "[][][][][][][][][]"));
		
		JLabel lblNewLabel = new JLabel("<html><p>MQTT is a machine-to-machine (M2M)/\"Internet of Things\" connectivity protocol. It was designed as an extremely lightweight publish/subscribe messaging transport. It is useful for connections with remote locations where a small code footprint is required and/or network bandwidth is at a premium.</p><br /><p>For more information, see <a href=\"http://mqtt.org/\">http://mqtt.org/</a>.</p><br /></html>");
		mainPanel.add(lblNewLabel, "flowx,cell 1 2");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Server Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(panel, "cell 1 3,growx");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lblAddress = new JLabel("Address");
		panel.add(lblAddress, "cell 0 0,alignx right");
		
		fldAddress = new JTextField();
		fldAddress.setText(prefs.get("serveraddress", ""));
		panel.add(fldAddress, "cell 1 0,growx");
		fldAddress.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		panel.add(lblUsername, "cell 0 1,alignx right");
		
		fldUsername = new JTextField();
		fldUsername.setText(prefs.get("serverusername", ""));
		panel.add(fldUsername, "cell 1 1,growx");
		fldUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword, "cell 0 2,alignx right");
		
		fldPassword = new JPasswordField();
		fldPassword.setText(prefs.get("serverpassword", ""));
		panel.add(fldPassword, "cell 1 2,growx");
		
		JLabel lblTopic = new JLabel("Topic");
		panel.add(lblTopic, "cell 0 3,alignx right");
		
		fldTopic = new JTextField();
		fldTopic.setText(prefs.get("servertopic", ""));
		panel.add(fldTopic, "cell 1 3,growx");
		fldTopic.setColumns(10);
		
		JButton btnTestConnection = new JButton("Test Connection");
		panel.add(btnTestConnection, "flowx,cell 1 4");
		
		lblConnectionTest = new JLabel("Not connected");
		panel.add(lblConnectionTest, "cell 1 4");

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Publish Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mainPanel.add(panel_1, "cell 1 5,grow");
		panel_1.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lbldPrinterName = new JLabel("Printer Name");
		panel_1.add(lbldPrinterName, "cell 0 0,alignx right");
		
		// we're going to use 
		MaxLengthTextDocument maxLength = new MaxLengthTextDocument();
		maxLength.setMaxChars(23);
		
		fldPrinterName = new JTextField();
		fldPrinterName.setDocument(maxLength);
		fldPrinterName.setText(prefs.get("publishprintername", ""));
		panel_1.add(fldPrinterName, "cell 1 0,growx");
		fldPrinterName.setColumns(10);
		
		JLabel lblOrganization = new JLabel("Organization");
		panel_1.add(lblOrganization, "cell 0 1,alignx right");
		
		fldOrganization = new JTextField();
		fldOrganization.setText(prefs.get("publishorganization", ""));
		panel_1.add(fldOrganization, "cell 1 1,growx");
		fldOrganization.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL");
		panel_1.add(lblUrl, "cell 0 2,alignx right");
		
		fldUrl = new JTextField();
		fldUrl.setText(prefs.get("publishorgurl", ""));
		panel_1.add(fldUrl, "cell 1 2,growx");
		fldUrl.setColumns(10);
		
		JLabel lblLocation = new JLabel("Location");
		panel_1.add(lblLocation, "cell 0 3,alignx right");
		
		fldLocation = new JTextField();
		fldLocation.setText(prefs.get("publishlocation", ""));
		panel_1.add(fldLocation, "cell 1 3,growx");
		fldLocation.setColumns(10);
		
		JCheckBox chckbxStatus = new JCheckBox("Status");
		panel_1.add(chckbxStatus, "cell 0 4");
		
		JCheckBox chckbxProgress = new JCheckBox("Progress");
		panel_1.add(chckbxProgress, "flowx,cell 1 4");
		
		JCheckBox chckbxModelName = new JCheckBox("Model Name");
		panel_1.add(chckbxModelName, "cell 1 4");
		
		JCheckBox chckbxMachineType = new JCheckBox("Machine Type");
		panel_1.add(chckbxMachineType, "cell 1 4");
		
		JButton btnSave = new JButton("Save");
		mainPanel.add(btnSave, "cell 1 7,alignx center");

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMQTTprefs();
			}
		
		});
		
		btnTestConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				testConnection();
			}		
		});

		add(mainPanel);

		// add our listener hooks.
		addWindowListener(this);
	}

	protected void testConnection() {
		
		//Preferences prefs = Preferences.userRoot().node(MQTT_NODE);
		//MqttClient client = null;
		
		MqttCommunications clientTest = new MqttCommunications();
		clientTest.testConnection(lblConnectionTest);
		
//		try {
//			client = new MqttClient(fldAddress.getText(), fldPrinterName.getText());
//			client.connect();
//			
//			String what = prefs.get("publishprintername", "");
//			String who = prefs.get("publishorganization", "");
//			String where = prefs.get("publishlocation", "");
//			String topic = prefs.get("servertopic", "");
//			
//			String sendtobroker = what.concat("-").concat(who).concat("-").concat(where);
//			
//			MqttMessage message = new MqttMessage(sendtobroker.getBytes());
//			message.setQos(0);
//			client.getTopic(topic).publish(message);
//			
//			client.disconnect();
//			lblConnectionTest.setText("Connection Successful!");
//		} 
//		catch (MqttSecurityException e1) {
//			lblConnectionTest.setText("Connection failed: check your security credentials.");
//		}
//		catch (MqttException e2) {
//
//			lblConnectionTest.setText("Connection failed: check your server address.");
//		}
	}
	
	protected void saveMQTTprefs() {
		
		Preferences prefs = Preferences.userRoot().node(MQTT_NODE);
		
		prefs.put("serveraddress", fldAddress.getText());
		prefs.put("serverusername", fldUsername.getText());
		
		// getText is deprecated on jpasswordfield, so we just make a string
		// I don't even know why I'm using a password field, I hate them (generally, not just in Java)
		prefs.put("serverpassword", new String(fldPassword.getPassword()));
		
		prefs.put("servertopic", fldTopic.getText());
		prefs.put("publishprintername", fldPrinterName.getText());
		prefs.put("publishorganization", fldOrganization.getText());
		prefs.put("publishorgurl", fldUrl.getText());
		prefs.put("publishlocation", fldLocation.getText());
		
	}
	
	protected JMenuBar createMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		return bar;
	}
	
	public void windowClosing(WindowEvent e) {
		//updateThread.interrupt();
		//pollThread.interrupt();
	}

	public void windowClosed(WindowEvent e) {
		synchronized(getClass()) {
			// TODO: add this back in.
//			machine.removeMachineStateListener(this);
			if (instance == this) {
				instance = null;
			}
		}
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
