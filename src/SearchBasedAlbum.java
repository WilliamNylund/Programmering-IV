import java.util.ArrayList;
import java.util.List;

public abstract class SearchBasedAlbum extends Album {

	private String albumName;
	
	private List<SoundClip> clips = new ArrayList<SoundClip>(); //lista med albumets ljud
	
	
	public SearchBasedAlbum(String albumName) {
		this.albumName = albumName;
	}
	abstract Boolean matchCriteria(SoundClip clip);
	abstract void update();
	abstract List<SoundClip> getSpecialClips();
	abstract void addSoundClip(SoundClip clip);

	@Override
	public List<SoundClip> getClips() {
		return this.clips;
	}
	
	@Override
	public void setClips(List<SoundClip> clips) {
		this.clips = clips;
	}
	
	@Override
	public String getAlbumName() {
		return this.albumName;
	}
	@Override
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	
	@Override
	public RegAlbum getParent() {
		return this.parent;
	}
	
	@Override
	public void setParent(RegAlbum parent) {
		this.parent = parent;
	}
	public String toString() {
		return albumName;
	}
	

	@Override
	public List<SoundClip> getRecentClips() {
		return null;
	}
	@Override
	void removeSoundClip(SoundClip clip) {
		// TODO Auto-generated method stub
	}

}
