package replicatorg.app.ui.extras.mqttprefs;

import java.awt.Color;
import java.util.prefs.Preferences;

import javax.swing.JLabel;

import org.eclipse.paho.client.mqttv3.*;

import replicatorg.app.Base;
import replicatorg.drivers.Driver;
import replicatorg.machine.Machine;
import replicatorg.machine.MachineInterface;
import replicatorg.machine.MachineState;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class MqttCommunications implements MqttCallback {
	
	private static final String MQTT_NODE = "/com/replicatorg/extra/mqtt";
	private static JLabel lblConnectionTest = null;
	private static MqttClient client = null;
	private static Preferences prefs = Preferences.userRoot().node(MQTT_NODE);
	
	// Implement MachineInterface? Maybe later
	private static String lastKnownState = null;
	
	/**
	 * Constructs an instance of the mqtt client communications
	 * @return 
	 * @throws MqttException
	 */
    public MqttCommunications() {

    	try {
    		
    		String testWire = prefs.get("serveraddress", "");
    		
    		if(("").equals(testWire)) {
    			
    			client = null;
    			
    		} else {
    			// Construct the MqttClient instance
        		client = new MqttClient(prefs.get("serveraddress", ""), prefs.get("publishprintername", ""));
        		
    			// Set this wrapper as the callback handler
    	    	client.setCallback(this);
    		}
    		
		} catch (MqttException e) {
			System.err.println("Could not wire mqtt");
		}
    }
	
	/**
	 * Publishes a message to the mqtt broker
	 * @param data String The message you want to send
	 * @param type String The type of message it is
	 * @return 
	 * @throws MqttException
	 */
	public void publish(String data, String sub) {
		
		if ((client != null) && client.isConnected()) { 
			// Don't do anything
		} else {
			
			try {
				
				String testWire = prefs.get("serveraddress", "");
	    		
	    		if(("").equals(testWire)) {
	    			
	    			client = null;
	    			
	    		} else {

					// Connect to the broker
					client.connect();
	    		}

			} catch (MqttException ex) {
				
				System.err.println("Could not connect");
				
			}
			
		}

		if ((client != null) && client.isConnected()) {
			MqttTopic topic = client.getTopic(prefs.get("servertopic", "").concat(sub));
			
			String sendtobroker = new Payload(data).toJson();
						
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
				
			}
		}
	}
	
	/**
	 * Subscribes to a topic on the mqtt broker
	 * @return 
	 * @throws MqttException
	 */
	public void subscribe() {
		
		if ((client != null) && client.isConnected()) { 
			// Don't do anything
		} else {
			
			try {
				String testWire = prefs.get("serveraddress", "");
	    		
	    		if(("").equals(testWire)) {
	    			
	    			client = null;
	    			
	    		} else {

					// Connect to the broker
					client.connect();
	    		}

			} catch (MqttException ex) {
				
				System.err.println("Could not connect");
				
			}
			
		}

    	// We're looking for something specific
    	String topicSetter = prefs.get("servertopic", "").concat("/get");
    	
    	if ((client != null) && client.isConnected()) {
	    	// Subscribe to the topic
	    	try {
				client.subscribe(topicSetter, 2);
				System.out.println("Subscribe complete");
				
			} catch (MqttSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	
	/**
	 * Tests the server configuration on the mqtt prefs window
	 * @param label JLabel The label from the mqtt prefs window
	 * @return 
	 * @throws MqttException
	 */
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
			
			try {
				client.disconnect();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lblConnectionTest.setText("Connection Succesful!");
		}
	}

	public void connectionLost(Throwable cause) {

		System.err.println("MQTT connection lost");
		
		if ((client != null) && client.isConnected()) { 
			// Don't do anything
		} else {
			
			try {
			
				client.connect();
			
			} catch (MqttSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
			
		}
		
		
	}

	public void deliveryComplete(MqttDeliveryToken token) {
		
		System.out.println("MQTT message delivery complete");
	}
	

	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		
		if (new String(message.getPayload()).equals("info")) {
			this.publish(new BotInfo().toJson(), "/info");
		}
		
		if (new String(message.getPayload()).equals("state")) {
			
			if (lastKnownState == null) {
				lastKnownState = "Can not determine machine state; possibly not running?";
			} 	
			
			this.publish(lastKnownState, "/state");
		}
		
	}
	
	public void setState(String text) {
		lastKnownState = text;
	}
}


class Payload {
	
	private static final String MQTT_NODE = "/com/replicatorg/extra/mqtt";
	
	@SerializedName("bot")
	String bot;
	 	 
	@SerializedName("message")
	String message;

	
	public Payload(String message) {
		Preferences prefs = Preferences.userRoot().node(MQTT_NODE);
		
		this.bot = prefs.get("publishprintername", "");
		this.message = message;
	}
	
	public String toJson() {
	    
	    Gson myGson = new Gson();
	    
	    return myGson.toJson(this);
	}
	 
}

class BotInfo {
	
	private static final String MQTT_NODE = "/com/replicatorg/extra/mqtt";
		 
	@SerializedName("org")
	String org;
	 
	@SerializedName("loc")
	String loc;
	 
	@SerializedName("machine")
	String machine;
	
	@SerializedName("driver")
	String driver;
	
	@SerializedName("firmware")
	String firmware;
	
	public BotInfo() {
		Preferences prefs = Preferences.userRoot().node(MQTT_NODE);
		Driver driver = Base.getMachineLoader().getDriver();
		
		this.org = prefs.get("publishorganization", "");
		this.loc = prefs.get("publishlocation", "");
		this.machine =  Base.getMachineLoader().getMachineInterface().getMachineName();
		this.driver = Base.getMachineLoader().getDriver().getDriverName();
		this.firmware = driver.getFirmwareInfo();
	}
	
	public String toJson() {
	    
	    Gson myGson = new Gson();
	    
	    return myGson.toJson(this);
	}
	 
}