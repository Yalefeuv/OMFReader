package yalefeuv.omfreader.records;
import java.util.Arrays;
import yalefeuv.omfreader.Record;
public class LogicalEnumeratedDataRecord16 extends Record {
	private byte[] data;
	private int segmentIndex;
	private int offset;
	public LogicalEnumeratedDataRecord16(byte[] content) {
		int i;
		try{
			if (FieldReader.isOneByteIndex(content[0])){
				i=1;
				segmentIndex = FieldReader.index(content[0]);
			}else{
				i=2;
				segmentIndex = FieldReader.index(content[0], content[1]);
			}
			offset = (content[i+1] & 0xFF) * 0x100 + (content[i] & 0xFF);
			this.data= Arrays.copyOfRange(content, i+2, content.length);
		}catch(IndexOutOfBoundsException e){
			incorrectDecoding = true;
		}
	}
	public String toString(){
		String res = "Logical Enumerated Data Record (16-bit version)";
		String content;
		if(incorrectDecoding)
			content = "Incorrect decoding !";
		else{
			content = "Segment index: "+ segmentIndex +"\tOffset: "+ offset + "\tData: ";
			int i = 0;
			while (i<data.length)
				content += String.format("%02X ", data[i++]);
		}
		return super.toString()+ res + "\t" +content;
	}
	public byte[] getData() {
		return data;
	}
	public int getSegmentIndex() {
		return segmentIndex;
	}
	public int getOffset() {
		return offset;
	}
}
