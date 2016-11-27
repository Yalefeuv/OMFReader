package yalefeuv.omfreader;
import java.util.Arrays;

import yalefeuv.omfreader.records.DummyRecord;
import yalefeuv.omfreader.records.GenericRecord;
import yalefeuv.omfreader.records.ListOfNamesRecord;
import yalefeuv.omfreader.records.LogicalEnumeratedDataRecord16;
import yalefeuv.omfreader.records.TranslatorHeaderRecord;

class RecordDecoder {
	
	private byte[] data;
	RecordDecoder(byte[] data) {
		this.data = data;
	}
	boolean isHeaderCorrect(int index){
		if(data.length < index + 3)
			return false;
		return data.length >= index + 3 + readLengthAhead(index);
	}
	private int readLengthAhead(int index) {
		int length = (data[index+2] & 0xFF)*0x100 + (data[1+index] & 0xFF); 
		return length;
	}
	private boolean isDummyAhead(int index){
		return readLengthAhead(index) < 2;
	}
	private byte calculateChecksum(int index, int length){
		int res=data[index];
		int i=1;
		while(i<length+2)
			res +=data[index+i++];
		return (byte) (-res);
	}
	Record decode(int index) {
		Record res;
		int length = readLengthAhead(index);
		byte type = data[index];
		if(!isDummyAhead(index)){
			byte[] content =  Arrays.copyOfRange(data, index+3,index+length+2);
			byte checksum =data[index+length+2];
			res = buildRecord(type, content);
			res.setChecksum(checksum);
			res.setCalculatedChecksum(calculateChecksum(index, length));
		}else {
			res =new DummyRecord();
		}
		res.setType(type);
		res.setLength(length);
		return res;
	}
	private Record buildRecord(byte b, byte[] content) {
		Record res ;
		switch(b){
		case (byte) 0x80:
			res = new TranslatorHeaderRecord(content);
		break;
		case (byte) 0x96:
			res = new ListOfNamesRecord(content);
		break;
		case (byte) 0xA0:
			res = new LogicalEnumeratedDataRecord16(content);
		break;
		default:
			res =new GenericRecord(content);
		}
		res.setType(b);
		return res;
	}
}
