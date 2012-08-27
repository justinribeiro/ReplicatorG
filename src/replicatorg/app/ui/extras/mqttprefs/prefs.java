package replicatorg.app.ui.extras.mqttprefs;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import net.miginfocom.swing.MigLayout;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class prefs extends JPanel {
	private JTextField fldAddress;
	private JTextField fldUsername;
	private JPasswordField fldPassword;
	private JTextField fldPrinterName;
	private JTextField fldOrganization;
	private JTextField fldUrl;
	private JTextField fldChannel;
	private JTextField fldLocation;

	/**
	 * Create the panel.
	 */
	public prefs() {
		setLayout(new MigLayout("", "[][400.00,grow][]", "[][][][][][100.00][][][]"));
		
		//JLabel lblMqttBrokerSettings = DefaultComponentFactory.getInstance().createTitle("MQTT Broker Settings");
		//lblMqttBrokerSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		//add(lblMqttBrokerSettings, "cell 1 1");
		
		JLabel lblNewLabel = new JLabel("<html><p>MQTT is a machine-to-machine (M2M)/\"Internet of Things\" connectivity protocol. It was designed as an extremely lightweight publish/subscribe messaging transport. It is useful for connections with remote locations where a small code footprint is required and/or network bandwidth is at a premium.</p><br /><p>For more information, see <a href=\"http://mqtt.org/\">http://mqtt.org/</a>.</p><br /></html>");
		add(lblNewLabel, "flowx,cell 1 2");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Server Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel, "cell 1 3,growx");
		panel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lblAddress = new JLabel("Address");
		panel.add(lblAddress, "cell 0 0,alignx right");
		
		fldAddress = new JTextField();
		panel.add(fldAddress, "cell 1 0,growx");
		fldAddress.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		panel.add(lblUsername, "cell 0 1,alignx right");
		
		fldUsername = new JTextField();
		panel.add(fldUsername, "cell 1 1,growx");
		fldUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		panel.add(lblPassword, "cell 0 2,alignx right");
		
		fldPassword = new JPasswordField();
		panel.add(fldPassword, "cell 1 2,growx");
		
		JLabel lblChannel = new JLabel("Channel");
		panel.add(lblChannel, "cell 0 3,alignx right");
		
		fldChannel = new JTextField();
		panel.add(fldChannel, "cell 1 3,growx");
		fldChannel.setColumns(10);
		
		JButton btnTestConnection = new JButton("Test Connection");
		panel.add(btnTestConnection, "flowx,cell 1 4");
		
		JLabel lblConnectionTest = new JLabel("Not connected");
		panel.add(lblConnectionTest, "cell 1 4");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Publish Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, "cell 1 5,grow");
		panel_1.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		
		JLabel lbldPrinterName = new JLabel("Printer Name");
		panel_1.add(lbldPrinterName, "cell 0 0,alignx right");
		
		fldPrinterName = new JTextField();
		panel_1.add(fldPrinterName, "cell 1 0,growx");
		fldPrinterName.setColumns(10);
		
		JLabel lblOrganization = new JLabel("Organization");
		panel_1.add(lblOrganization, "cell 0 1,alignx right");
		
		fldOrganization = new JTextField();
		panel_1.add(fldOrganization, "cell 1 1,growx");
		fldOrganization.setColumns(10);
		
		JLabel lblUrl = new JLabel("URL");
		panel_1.add(lblUrl, "cell 0 2,alignx right");
		
		fldUrl = new JTextField();
		panel_1.add(fldUrl, "cell 1 2,growx");
		fldUrl.setColumns(10);
		
		JLabel lblLocation = new JLabel("Location");
		panel_1.add(lblLocation, "cell 0 3,alignx right");
		
		fldLocation = new JTextField();
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
		add(btnSave, "cell 1 7,alignx center");

	}

}
