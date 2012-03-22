package solohelper.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileWriterImpl implements FileWriter {

	@Override
	public void writeLines(String fileName, List<String> lines, boolean append) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName, append);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        OutputStreamWriter osw = new OutputStreamWriter(bos);
	        BufferedWriter writer = new BufferedWriter(osw);
	        for (String line : lines) {
	        	writer.write(line);
	        }
	        writer.flush();
	        writer.close();	
		} catch (IOException e) {
			System.out.println("Failed to write file " + fileName);
		}
	}
}
