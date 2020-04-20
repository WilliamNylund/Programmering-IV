import java.util.ArrayList;
import java.util.List;

public abstract class Album {
	
	private String albumName;
	private List<SoundClip> clips = new ArrayList<SoundClip>(); //lista med albumets ljud
	private List<SoundClip> recentClips = new ArrayList<SoundClip>();
	public RegAlbum parent;

	public Album() {
		
	}
	
	abstract void addSoundClip(SoundClip clip);
	abstract void removeSoundClip(SoundClip clip);
	public abstract List<SoundClip> getClips();
	public abstract void setClips(List<SoundClip> clips);
	public abstract String getAlbumName();
	public abstract void setAlbumName(String albumName);
	public abstract RegAlbum getParent();
	public abstract void setParent(RegAlbum parent);
	public abstract List<SoundClip> getRecentClips();
}


