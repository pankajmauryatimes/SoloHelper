package solohelper.clip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import solohelper.io.FileWriter;

public class MusicClipsWriterImpl implements MusicClipsWriter {
	
	private final FileWriter fileWriter;

	@Inject
	public MusicClipsWriterImpl(FileWriter fileWriter) {
		this.fileWriter = fileWriter;
	}
	
	@Override
	public void writeClips(String configFileName, Map<String, MusicClip> clipMap) {
		List<String> lines = new ArrayList<String>();
        for (MusicClip clip : clipMap.values()) {
        	lines.add(clip.getCsvString());
        }
		this.fileWriter.writeLines(configFileName, lines, false);
	}
}
