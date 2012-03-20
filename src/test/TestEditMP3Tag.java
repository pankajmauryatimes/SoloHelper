package test;

import tag.EditMP3Tag;

public class TestEditMP3Tag {
	public static void main(String args[]){
		String songDir = "songs\\metallica";
		String fileName = null;
		if(args.length > 0)
			fileName = args[0];
		String fileSeperator = System.getProperty("file.separator");
		String userDir = System.getProperty("user.dir");
		StringBuffer path = new StringBuffer();
		
		path.append(userDir).append(fileSeperator).append(songDir);
		if(fileName!=null)
			path.append(fileSeperator).append(fileName);
		String userHome = System.getProperty("user.home");
		System.setProperty("user.home", userDir);					
		EditMP3Tag e = new EditMP3Tag(path.toString());
		System.setProperty("user.home", userHome);
	}
	
}
