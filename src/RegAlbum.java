
import java.util.ArrayList;
import java.util.List;

public class RegAlbum extends Album {
	
	private String albumName;
	private RegAlbum parent; 
	private List <Album> children = new ArrayList<Album>(); //lista med albumets subalbum
	private List<SoundClip> clips = new ArrayList<SoundClip>(); //lista med albumets ljud
	private List<SoundClip> recentClips = new ArrayList<SoundClip>();
	
	
	public RegAlbum(String albumName) { //skapar rotalbumet
		assert nameInvariant(albumName);
		this.albumName = albumName;
		assert nameInvariant(albumName);
	}

	Album addSubAlbum(Album album){ //skapar subalbum		
		album.setParent(this);
		this.children.add(album);		
		return album;
	}
	
	RegAlbum selfDestruct() { //tar bort pointers till och från föräldern
		assert albumInvariant(this);
		
		for (int i = 0; i < this.parent.children.size(); i++) {
			if (this.albumName.equals(this.parent.children.get(i).getAlbumName())){
				this.parent.children.remove(i);
			}
		}
		this.parent = null;
		assert albumInvariant(this);
		return this;
	}
	
	void addSoundClip(SoundClip clip) { //addar clip till albumet samt dess föräldrar
		assert clipInvariant(clip);

		if (this.getClips().contains(clip)) {
			return;
		}
		this.clips.add(clip);
		if (this.parent == null) {
			return;
		}
		this.parent.addSoundClip(clip);
		assert clipInvariant(clip);
	}
	
	void removeSoundClip(SoundClip clip) { //removar clip från albumet samt dess barn
		assert clipInvariant(clip);
		this.clips.remove(clip);
		
		for(Album child : this.children) {
			if (child.getClips().contains(clip)) {
				child.getRecentClips().add(clip);
			}
			child.removeSoundClip(clip);
		}
		
		
		assert clipInvariant(clip);
	}
	public String toString() {
		return albumName;
	}
	
	// getters och setters
	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public RegAlbum getParent() {
		return parent;
	}

	public void setParent(RegAlbum parent) {
		this.parent = parent;
	}

	public List<Album> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Album> children) {
		this.children = children;
	}

	public List<SoundClip> getClips() {
		return clips;
	}

	public void setClips(List<SoundClip> clips) {
		this.clips = clips;
	}
	
	
	public List<SoundClip> getRecentClips() {
		return recentClips;
	}
	public void setRecentClips(ArrayList<SoundClip> Recentclips) {
		this.recentClips = recentClips;
	}
		
	//Skap en invariant som kollar allt
	private boolean albumInvariant(RegAlbum album) {
		return album.getClass().getName().equals("model.Album");
	}
	private boolean nameInvariant(String name) {
		return name.getClass().getName().equals("java.lang.String")
		|| !name.equals(null) || !name.equals("");
	}
	private boolean clipInvariant(SoundClip clip) {
		return clip.getClass().getName().equals("model.SoundClip");
	}
	
}
