import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MusicOrganizerController {

	private MusicOrganizerWindow view;
	private SoundClipBlockingQueue queue;
	private RegAlbum root;
	private Command command;
	private Invoker invoker;
	private SearchBasedAlbum gc, fc;
	private List<SearchBasedAlbum> observers = new ArrayList<SearchBasedAlbum>();
	
	public MusicOrganizerController() {
		
		// TODO: Create the root album for all sound clips
		root = new RegAlbum("All Sound Clips");
		
		
		invoker = new Invoker();
		
		// Create the View in Model-View-Controller
		view = new MusicOrganizerWindow(this);
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();
		
		gc = new GreatClipsAlbum("Great Sound Clips");
		root.addSubAlbum(gc);
		observers.add(gc);
	
		fc = new FlaggedClipsAlbum("Flagged Sound Clips");
		root.addSubAlbum(fc);
		observers.add(fc);
		
		view.onAlbumAdded(gc);
		view.onAlbumAdded(fc);
		
		// Create a separate thread for the sound clip player and start it
		(new Thread(new SoundClipPlayer(queue))).start();
	}

	/**
	 * Load the sound clips found in all subfolders of a path on disk. If path is not
	 * an actual folder on disk, has no effect.
	 */
	public Set<SoundClip> loadSoundClips(String path) {
		Set<SoundClip> clips = SoundClipLoader.loadSoundClips(path);
		// TODO: Add the loaded sound clips to the root album
		for(SoundClip clip: clips) {
			root.addSoundClip(clip);
			fc.addSoundClip(clip);
			gc.addSoundClip(clip);
		}
		return clips;
	}
	
	/**
	 * Returns the root album
	 */
	public RegAlbum getRootAlbum(){
		return root;
	}
	
	/**
	 * Adds an album to the Music Organizer
	 */
	public void addNewAlbum(RegAlbum selected) { //TODO Update parameters if needed - e.g. you might want to give the currently selected album as parameter
		String name = view.promptForAlbumName();
		if (name == null) {
			return;
		} else if (name.equals("")) {
			view.showMessage("Enter a valid name");
			addNewAlbum(selected);
			return;
		}
		view.getButtonPanel().getUndoButton().setEnabled(true);
		view.getButtonPanel().getRedoButton().setEnabled(false);
		invoker.execute(new CreateAlbumCommand(new RegAlbum(name), selected, view));
		
	}
	
	/**
	 * Removes an album from the Music Organizer
	 */
	public void deleteAlbum(RegAlbum selected){ //TODO Update parameters if needed
		if (selected.equals(root)) {
			view.showMessage("Cannot remove All Sound Clips album");
			return;
		}
		invoker.execute(new DeleteAlbumCommand(selected, selected.getParent(), view));

		
	}
	
	/**
	 * Adds sound clips to an album
	 */
	public void addSoundClips(List<SoundClip> clips, RegAlbum selected){ //TODO Update parameters if needed
		// TODO: Add your code here
		invoker.execute(new CreateClipCommand(clips, selected, view));
	}
	
	/**
	 * Removes sound clips from an album
	 */
	public void removeSoundClips(List<SoundClip> clips, RegAlbum selected){ //TODO Update parameters if needed
		// TODO: Add your code here
		invoker.execute(new DeleteClipCommand(clips, selected, view));
	}
	public void undo(){
		invoker.undo();
	}
	
	public void redo() {
		invoker.redo();
	}
	public void flag(List<SoundClip> clips) {
		for(SoundClip clip : clips) {
			if (clip.flagged) {
				clip.setFlag(false);
			} else clip.setFlag(true);
		}
		notifyAllObservers();
		view.onClipsUpdated();
	}
	public void grade(List<SoundClip> clips) {
		if (clips.size() == 0) {
			view.showMessage("Choose soundclips to grade");
			return;
		}
		String grade = view.promtForGrade();
		if (grade == null) return;
		if (grade.equals("0") || grade.equals("1") || grade.equals("2") || grade.equals("3") || grade.equals("4") || grade.equals("5")) {
			for(SoundClip clip : clips) {
				clip.setGrade(grade);
			}
			notifyAllObservers();
			view.onClipsUpdated();
		} else {
		view.showMessage("Enter a grade between 0-5");
		return;
		}
	}
	
	public void notifyAllObservers(){
	      for (SearchBasedAlbum observer : observers) {
	         observer.update();
	      }
	   }
	/**
	 * Puts the selected sound clips on the queue and lets
	 * the sound clip player thread play them. Essentially, when
	 * this method is called, the selected sound clips in the 
	 * SoundClipTable are played.
	 */
	public void playSoundClips(){
		List<SoundClip> l = view.getSelectedSoundClips();
		for(int i=0;i<l.size();i++)
			queue.enqueue(l.get(i));
	}
	public int getCommandSize() {
		return invoker.getCommandList();
	}
	public int getRedoSize() {
		return invoker.getRedoList();
	}

}
