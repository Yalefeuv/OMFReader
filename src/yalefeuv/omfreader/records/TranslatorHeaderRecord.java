package yalefeuv.omfreader.records;
import java.io.UnsupportedEncodingException;
import yalefeuv.omfreader.Record;
public class TranslatorHeaderRecord extends Record {
	private short length;
	private String name;
	public TranslatorHeaderRecord(byte[] content) {
		initLength(content[0]);
		try {
			name = new String(content,1,length, "US-ASCII");
		} catch (IndexOutOfBoundsException | UnsupportedEncodingException e) {
			incorrectDecoding = true;
		}
	}
	private void initLength(byte unsignedLength){
		length = (short) (unsignedLength & 0xFF);
	}
	public String toString(){
		String res = "Translator Header Record\t";
		res+= "String Length: " + length;
		String content;
		if(incorrectDecoding)
			content = "Incorrect length !";
		else
			content = "Name: "+name;  
		return super.toString()+res+"\t"+content;
	}
	public String getName() {
		return name;
	}
}
