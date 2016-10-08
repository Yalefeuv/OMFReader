package yalefeuv.omfreader.records;

final class FieldReader {
	static boolean isOneByteIndex(byte first){
		return (first & 0x80) == 0;
	}
	static int index(byte first, byte second){
		int res = (first & 0x7F) * 0x100 + (second & 0xFF);
		return res;
	}
	static short index(byte b){
		short res = (short) (b & 0xFF);
		return res;
	}
}
