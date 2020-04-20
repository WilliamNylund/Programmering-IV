import java.util.ArrayList;
import java.util.List;

public class DeleteClipCommand implements Command {

	List<SoundClip> clips;
	RegAlbum selected;
	MusicOrganizerWindow view;
	
	public DeleteClipCommand(List<SoundClip> clips, RegAlbum selected, MusicOrganizerWindow view) {
		this.clips = clips;
		this.selected = selected;
		this.view = view;
	}

	@Override
	public void execute() {
		for (SoundClip clip: clips) {
			selected.removeSoundClip(clip);
		}
		view.onClipsUpdated();

	}

	@Override
	public void undo() {
		for (SoundClip clip: clips) {
			selected.addSoundClip(clip);
		}
		for (Album child : selected.getChildren()) {
			for (SoundClip clip: clips) {	
				if (child.getRecentClips().contains(clip)){
					child.addSoundClip(clip);
				}
			}
		}
		view.onClipsUpdated(selected);

	}

	@Override
	public void redo() {
		for (SoundClip clip: clips) {
			selected.removeSoundClip(clip);
		}
		view.onClipsUpdated(selected);

	}

}
