package solohelper.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import solohelper.launcher.MusicClip;
import solohelper.launcher.MusicClipImpl;

public class MusicClipsReaderImpl implements MusicClipsReader {

	@Override
	public Map<String, MusicClip> readClips(String configFileName) {
		Map<String, MusicClip> clipMap = new TreeMap<String, MusicClip>();

		try {
			FileInputStream fis = new FileInputStream(configFileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			InputStreamReader isr = new InputStreamReader(bis);
			BufferedReader br = new BufferedReader(isr);
			
			do {
				String line = br.readLine();
				if (line == null) {
					return clipMap;
				}
				MusicClipImpl clip = new MusicClipImpl(line);
				clipMap.put(clip.getLabel(), clip);
			} while (true);
		} catch (IOException e) {
			System.out.println("Failed to read file " + configFileName);
		}
		return clipMap;
	}
}
