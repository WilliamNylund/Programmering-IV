import java.util.ArrayList;
import java.util.List;

public class GreatClipsAlbum extends SearchBasedAlbum {
	
	
	private List<SoundClip> greatClips = new ArrayList<SoundClip>();
	private List<SoundClip> clips = new ArrayList<SoundClip>(); //lista med albumets ljud

	public GreatClipsAlbum(String albumName) {
		super(albumName);
	}

	@Override
	public Boolean matchCriteria(SoundClip clip) {
		if(clip.graded) {
			if(Integer.parseInt(clip.getGrade()) >=4) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void update() {
		greatClips.clear();
		for(SoundClip clip: clips) {
			if(matchCriteria(clip)) {
				greatClips.add(clip);
			} 
		}
	}
	
	@Override
	
	public void addSoundClip(SoundClip clip) {
		clips.add(clip);
	}

	@Override
	public List<SoundClip> getSpecialClips() {
		return greatClips;
	}

}
