package yalefeuv.omfreader;
import java.util.LinkedList;
import java.util.List;
public class OMFReader {
	private List<Record> records;
	public OMFReader() {
		records = new LinkedList<Record>();
	}
	
	public List<Record> getRecords() {
		return records;
	}
	public int parseContent(byte[] data, int offset, int beginRecordindexLimit){
		if(offset < 0) return 0;
		int index = offset;
		RecordDecoder dec = new RecordDecoder(data);
		while (dec.isHeaderCorrect(index) && index < beginRecordindexLimit){
			Record rec = dec.decode(index);
			records.add(rec);
			index += 3 + rec.getLength();
		}
		return index ;
	}
	public int parseContent(byte[] input) {
		return parseContent(input, 0, input.length);
	}
	
	public void printRecordsList() {
		System.out.println("Number of records: "+records.size());
		for(Record r: records){
			if(r.isIncorrectDecoding())
				System.err.println(r);
			else
				System.out.println(r);
		}
	}
}
