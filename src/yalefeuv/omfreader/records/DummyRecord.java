package yalefeuv.omfreader.records;
import yalefeuv.omfreader.Record;
public class DummyRecord extends Record {
	
	
	public String toString(){
		return super.toString() + "Dummy Record\tSize: "+getLength();
	}
}
