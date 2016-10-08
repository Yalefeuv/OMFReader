package yalefeuv.omfreader.records;

import yalefeuv.omfreader.Record;

public class GenericRecord extends Record {
	private byte[] content;
	public GenericRecord( byte[] content) {
		this.content = content;
	}
	public String toString(){
		String s ="Data: ";
		int i = 0;
		while (i<content.length)
			s += String.format("%02X ", content[i++]);
		return super.toString()+s;
	}
	public byte[] getContent() {
		return content;
	}
	
}
