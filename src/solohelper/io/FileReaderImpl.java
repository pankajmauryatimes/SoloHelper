package solohelper.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReaderImpl implements FileReader {

	@Override
	public List<String> readLines(String fileName) {
		List<String> lines = new ArrayList<String>();

		try {
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis);
			BufferedReader br = new BufferedReader(isr);
			
			do {
				String line = br.readLine();
				if (line == null) {
					return lines;
				}
				lines.add(line);
			} while (true);
		} catch (IOException e) {
			System.out.println("Failed to read file " + fileName);
		}
		return lines;
	}
}
