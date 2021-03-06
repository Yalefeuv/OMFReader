package yalefeuv.omfreader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import yalefeuv.omfreader.records.LogicalEnumeratedDataRecord16;

public class OMFReader {
	private List<Record> records;

	public OMFReader() {
		records = new LinkedList<Record>();
	}

	public List<Record> getRecords() {
		return records;
	}

	public int parseContent(byte[] data, int offset, int beginRecordindexLimit) {
		if (offset < 0)
			return 0;
		int index = offset;
		RecordDecoder dec = new RecordDecoder(data);
		while (dec.isHeaderCorrect(index) && index < beginRecordindexLimit) {
			Record rec = dec.decode(index);
			records.add(rec);
			index += 3 + rec.getLength();
		}
		return index;
	}

	public int parseContent(byte[] input) {
		return parseContent(input, 0, input.length);
	}

	public void printRecordsList() {
		System.out.println("Number of records: " + records.size());
		for (Record r : records) {
			if (r.isIncorrectDecoding())
				System.err.println(r);
			else
				System.out.println(r);
		}
	}

	public void exportData(Path path) {
		Path exportPath = path.resolveSibling(path.getFileName().toString() + "_data");
		File exportFile = exportPath.toFile();
		System.out.println("Exporting data to " + exportFile);
		boolean isCreated = false;
		try {
			isCreated = exportFile.createNewFile();
		} catch (IOException e) {
			System.err.println("Cannot create file " + exportFile + "!");
		}
		if (!isCreated) {
			System.out.println("File " + exportFile + " already exists !");
			return;
		}
		for (Record r : records) {
			if (r instanceof LogicalEnumeratedDataRecord16) {
				LogicalEnumeratedDataRecord16 led = (LogicalEnumeratedDataRecord16) r;
				if (led.getSegmentIndex() == 1) {
					try {
						Files.write(exportPath, led.getData(), StandardOpenOption.APPEND);
					} catch (IOException e) {
						System.err.println("Error during writing !");
					}
				}
			}
		}
		System.out.println("File written successfully");
	}
}
