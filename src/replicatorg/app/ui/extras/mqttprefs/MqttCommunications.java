package replicatorg.app.ui.extras.mqttprefs;

import java.util.prefs.Preferences;

import javax.swing.JLabel;

import org.eclipse.paho.client.mqttv3.*;


public class MqttCommunications implements MqttCallback {
	
	private static final String MQTT_NODE = "/com/replicatorg/extra/mqtt";
	private static JLabel lblConnectionTest = null;
	private static MqttClient client = null;
	
	public void run(String stat) {
		Preferences prefs = Preferences.userRoot().node(MQTT_NODE);

		if ((client != null) && client.isConnected()) { 
			// Don't do anything
		} else {
			
			try {
				// Create a client to communicate with a broker at the specified address
				client = new MqttClient(prefs.get("serveraddress", ""), prefs.get("publishprintername", ""));

				// Connect to the broker
				client.connect();

				// Setup a callback
				client.setCallback(this);

			} catch (MqttException ex) {
				System.err.println("Could not connect");
			}
			
		}

		if ((client != null) && client.isConnected()) {
			MqttTopic topic = client.getTopic(prefs.get("servertopic", ""));
			// Create message and set quality of service to deliver the message once
			
			String what = prefs.get("publishprintername", "");
			String who = prefs.get("publishorganization", "");
			String where = prefs.get("publishlocation", "");
			
			String sendtobroker = what.concat(" ").concat(who).concat(" ").concat(where).concat("|||").concat(stat);
			
			MqttMessage message = new MqttMessage(sendtobroker.getBytes());
			message.setQos(2);
	
			try {
				
				// Give the message to the client for publishing. For QoS 2, this
				// will involve multiple network calls, which will happen
				// asynchronously after this method has returned.
				topic.publish(message);
			
			} catch (MqttException ex) {
				
				// Client has not accepted the message due to a failure
				// Depending on the exception's reason code, we could always retry
				System.err.println("Failed to send message");
				
			}
		}
	}
	
	public void testConnection(JLabel label) {
		lblConnectionTest = label;
		Preferences prefs = Preferences.userRoot().node(MQTT_NODE);

		if ((client != null) && client.isConnected()) { 
			// Don't do anything
		} else {
			
			try {
				// Create a client to communicate with a broker at the specified address
				client = new MqttClient(prefs.get("serveraddress", ""), prefs.get("publishprintername", ""));

				// Connect to the broker
				client.connect();

				// Setup a callback
				client.setCallback(this);

			} catch (MqttException ex) {
				System.err.println("Could not connect");
				lblConnectionTest.setText("Connection failed: check your server address.");
			}
			
		}

		if ((client != null) && client.isConnected()) {
			MqttTopic topic = client.getTopic(prefs.get("servertopic", ""));
			// Create message and set quality of service to deliver the message once
			
			String what = prefs.get("publishprintername", "");
			String who = prefs.get("publishorganization", "");
			String where = prefs.get("publishlocation", "");
			
			String sendtobroker = what.concat(" ").concat(who).concat(" ").concat(where);
			
			MqttMessage message = new MqttMessage(sendtobroker.getBytes());
			message.setQos(0);
	
			try {
				// Give the message to the client for publishing. For QoS 2, this
				// will involve multiple network calls, which will happen
				// asynchronously after this method has returned.
				topic.publish(message);
			} catch (MqttException ex) {
				// Client has not accepted the message due to a failure
				// Depending on the exception's reason code, we could always retry
				System.err.println("Failed to send message");
				lblConnectionTest.setText("Connection failed: check your server address.");
			}
		}
	}

	public void connectionLost(Throwable cause) {
		// TODO: Implement reconnection logic
		System.err.println("Connection lost");
		lblConnectionTest.setText("Connection lost: check your server address.");
	}

	public void deliveryComplete(MqttDeliveryToken token) {
		lblConnectionTest.setText("Connection Successful, delivery complete!");
		System.out.println("Delivery complete");
	}

	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
	}
}
