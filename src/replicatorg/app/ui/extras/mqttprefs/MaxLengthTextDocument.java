package replicatorg.app.ui.extras.mqttprefs;

import javax.print.attribute.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxLengthTextDocument extends PlainDocument {

	private static final long serialVersionUID = 1L;
	//Store maximum characters permitted
	private int maxChars;

	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if(str != null && (getLength() + str.length() < maxChars)){
			super.insertString(offs, str, (javax.swing.text.AttributeSet) a);
		}
	}

	public void setMaxChars(int i) {
		maxChars = i;
	}
	
	public int getMaxChars() {
		return maxChars;
	}
}
