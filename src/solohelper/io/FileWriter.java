package solohelper.io;

import java.util.List;

public interface FileWriter {

	public abstract void writeLines(String fileName, List<String> lines,
			boolean append);

}