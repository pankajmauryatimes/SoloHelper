package solohelper.clip;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import solohelper.io.FileReader;

public class MusicClipsReaderImpl implements MusicClipsReader {

	private final FileReader fileReader;

	@Inject
	public MusicClipsReaderImpl(FileReader fileReader) {
		this.fileReader = fileReader;
	}
	
	@Override
	public Map<String, MusicClip> readClips(String configFileName) {
		Map<String, MusicClip> clipMap = new TreeMap<String, MusicClip>();

		List<String> lines = this.fileReader.readLines(configFileName);
		for (String line : lines) {
			MusicClipImpl clip = new MusicClipImpl(line);
			clipMap.put(clip.getLabel(), clip);
		}
		return clipMap;
	}
}
