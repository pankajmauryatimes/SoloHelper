package solohelper.io;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import solohelper.clip.MusicClip;

public class MusicClipsWriterImpl implements MusicClipsWriter {
	
	@Override
	public void writeClips(String configFileName, Map<String, MusicClip> clipMap) {
		try {
			// We overwrite the file as all clips are in memory.
			FileOutputStream fos = new FileOutputStream(configFileName, false /*append*/);
	        BufferedOutputStream bos = new BufferedOutputStream(fos);
	        OutputStreamWriter osw = new OutputStreamWriter(bos);
	        BufferedWriter writer = new BufferedWriter(osw);
	        for (MusicClip clip : clipMap.values()) {
	        	writer.write(clip.getCsvString());
	        }
	        writer.flush();
	        writer.close();	
		} catch (IOException e) {
			System.out.println("Failed to write file " + configFileName);
		}
	}
}
