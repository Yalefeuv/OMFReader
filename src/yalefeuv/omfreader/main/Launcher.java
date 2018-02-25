package yalefeuv.omfreader.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import yalefeuv.omfreader.OMFReader;

public class Launcher {
	public static void main(String[] args) {
		byte[] input;
		if (!isArgsValid(args)) {
			printUsage();
		} else {
			try {
				input = Files.readAllBytes(Paths.get(args[0]));
			} catch (IOException | InvalidPathException e) {
				System.err.println("Unable to read " + args[0]);
				return;
			}
			OMFReader recManager = new OMFReader();
			int nextIndex = recManager.parseContent(input);
			if (nextIndex != input.length)
				System.err.println("Format seems incorrect.");
			input = null;
			recManager.printRecordsList();
			if (args.length > 1) {
				recManager.exportData(Paths.get(args[0]));
			}
		}
	}

	private static boolean isArgsValid(String[] args) {
		return !(args.length < 1 || args.length > 3 || (args.length == 2 && !args[1].equals("-x")));
	}

	private static void printUsage() {
		System.out.println("Usage: objFile [-x]\nOptions:\n-x\tExport data (EXPERIMENTAL)");
	}
}
