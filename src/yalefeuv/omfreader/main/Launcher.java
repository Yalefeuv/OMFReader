package yalefeuv.omfreader.main;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import yalefeuv.omfreader.OMFReader;
public class Launcher {
	public static void main(String[] args){
		byte[] input;
		if(args.length != 1){
			System.out.println("Usage: objFile");
		}else{
			try {
				input = Files.readAllBytes(Paths.get(args[0]));
			} catch (IOException | InvalidPathException e) {
				System.err.println("Unable to read "+ args[0]);
				return;
			}
			OMFReader recManager = new OMFReader();
			int nextIndex = recManager.parseContent(input);
			if(nextIndex != input.length)
				System.err.println("Format seems incorrect.");
			input =null;
			recManager.printRecordsList();
		}
	}
}
