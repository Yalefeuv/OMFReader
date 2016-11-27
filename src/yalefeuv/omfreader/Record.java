package yalefeuv.omfreader;

public abstract class Record {
	private byte checksum;
	private byte calculatedChecksum;
	private int length;
	private byte type;
	protected boolean incorrectDecoding;

	public int getLength() {
		return length;
	}
	void setLength(int length) {
		this.length = length;
	}
	void setChecksum(byte checksum) {
		this.checksum = checksum;
	}
	void setCalculatedChecksum(byte calculatedChecksum) {
		this.calculatedChecksum = calculatedChecksum;
	}
	public String toString(){
		String t = String.format("Type: %02X\t", type);
		String c= String.format("Checksum: %02X\t", checksum);
		String cc = String.format("Calculated: %02X\t", calculatedChecksum);
		return t+c+cc;
	}
	void setType(byte b) {
		type = b; 
	}
	
	public boolean isIncorrectDecoding(){
		return incorrectDecoding;
	}
	public byte getChecksum() {
		return checksum;
	}
	public byte getCalculatedChecksum() {
		return calculatedChecksum;
	}
	
	public byte getType() {
		return type;
	}
}

