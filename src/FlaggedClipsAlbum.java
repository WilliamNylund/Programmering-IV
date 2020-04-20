import java.util.ArrayList;
import java.util.List;

public class FlaggedClipsAlbum extends SearchBasedAlbum {

	private List<SoundClip> clips = new ArrayList<SoundClip>(); 
	private List<SoundClip> flaggedClips = new ArrayList<SoundClip>();
	
	public FlaggedClipsAlbum(String albumName) {
		super(albumName);
	}

	@Override
	public Boolean matchCriteria(SoundClip clip) {
		return clip.flagged;
	}


	@Override
	public void update() {
		flaggedClips.clear();
		for(SoundClip clip : clips) {
			if (matchCriteria(clip)) {
				flaggedClips.add(clip);
			}
		}
	}
	
	@Override
	public void addSoundClip(SoundClip clip) {
		clips.add(clip);
	}

	@Override
	public List<SoundClip> getSpecialClips() {
		return flaggedClips;
	}

}
