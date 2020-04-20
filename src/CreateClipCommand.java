import java.util.List;

public class CreateClipCommand implements Command {

	RegAlbum selected;
	MusicOrganizerWindow view;
	List<SoundClip> clips;
	
	public CreateClipCommand(List<SoundClip> clips, RegAlbum selected, MusicOrganizerWindow view) {
		this.clips = clips;
		this.selected = selected;
		this.view = view;
	}

	@Override
	public void execute() {
		for (SoundClip clip: clips) {
			selected.addSoundClip(clip);
		}
		view.onClipsUpdated();

	}

	@Override
	public void undo() {
		for (SoundClip clip: clips) {
			selected.removeSoundClip(clip);
		}
		view.onClipsUpdated(selected);
	}

	@Override
	public void redo() {
		for (SoundClip clip: clips) {
			selected.addSoundClip(clip);
		}
		view.onClipsUpdated(selected);
	}

}
