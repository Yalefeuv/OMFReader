package yalefeuv.omfreader.records;

import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import yalefeuv.omfreader.Record;

public class ListOfNamesRecord extends Record {
	private List<String> stringList;
	private short badLength;

	public ListOfNamesRecord(byte[] content) {
		stringList = new LinkedList<String>();
		int currentOffset = 0;
		String s = null;
		short length;
		while (currentOffset < content.length) {
			length = (short) (content[currentOffset] & 0xFF);
			try {
				s = new String(content, 1 + currentOffset, length, "US-ASCII");
			} catch (IndexOutOfBoundsException | UnsupportedEncodingException e) {
				incorrectDecoding = true;
				badLength = length;
			}
			if (!incorrectDecoding)
				stringList.add(s);
			currentOffset += 1 + length;
		}
	}

	public String toString() {
		String res = "";
		res += "List of Names Record";
		if (incorrectDecoding)
			res += "\tIncorrect last length ! (" + badLength + ")";

		for (String s : stringList)
			res += "\n\tLength: " + s.length() + "\tName: " + s;
		return super.toString() + res;
	}

	public List<String> getStringList() {
		return stringList;
	}

	public short getBadLength() {
		return badLength;
	}

}
