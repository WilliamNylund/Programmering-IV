import java.io.File;

/**
 * SoundClip is a class representing a digital
 * sound clip file on disk.
 */
public class SoundClip {

	Boolean flagged = false;
	String clipName;
	private final File file;
	String grade;
	boolean graded = false;
	
	/**
	 * Make a SoundClip from a file.
	 * Requires file != null.
	 */
	public SoundClip(File file) {
		assert file != null;
		this.file = file;
		this.clipName = file.getName();
	}

	/**
	 * @return the file containing this sound clip.
	 */
	public File getFile() {
		return file;
	}
	
	public String toString(){
		return clipName;
	}
	
	@Override
	public boolean equals(Object obj) {
		return 
			obj instanceof SoundClip
			&& ((SoundClip)obj).file.equals(file);
	}
	
	@Override
	public int hashCode() {
		return file.hashCode();
	}
	public String getName() {
		return clipName;
	}
	public void setName(String name) {
		this.clipName = name;
	}
	public Boolean getFlagged(){
		return flagged;
	}
	public void setFlag(Boolean bool) {
		this.flagged = bool;
		if (bool == true) {
			if (this.graded) {	//Kanske måst ändra -10 ============================>
				String tempName = this.getName().substring(0, this.getName().length()-9);
				this.setName(tempName + " -F" + " Grade: "+ this.getGrade());
				
			} else {
			this.setName(this.getName() + " -F");
			}
		} else {
			String name = this.getName();
			String newName = name.replace(" -F", "");
			this.setName(newName);
		}
	}
	public String getGrade() {
		return this.grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
		if (this.graded) {
			String name = this.getName();
			String[] nameParts = name.split("Grade:");
			nameParts[nameParts.length-1] = " Grade: "+ grade;
			this.setName("");
			
			for(String part : nameParts) {
				this.setName(this.getName() + part);
			}
			
		} else {
			this.setName(this.getName() + " Grade: " + grade);
		}
		this.graded = true;
		
	}
}
