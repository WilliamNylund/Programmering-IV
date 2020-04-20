
public class CreateAlbumCommand implements Command{

	RegAlbum album;
	RegAlbum parent;
	MusicOrganizerWindow view;
	
	public CreateAlbumCommand(RegAlbum album, RegAlbum parent, MusicOrganizerWindow view) {
		this.album = album;
		this.parent = parent;
		this.view = view;
	}
	public CreateAlbumCommand() {
	}
	
	@Override
	public void execute() {
		view.getSelectedAlbum().addSubAlbum(album);
		view.onAlbumAdded(album);
	}

	@Override
	public void undo() {
		album.selfDestruct();
		view.onAlbumRemoved(album);
	}
	@Override
	public void redo() {
		this.parent.addSubAlbum(album);
		view.onAlbumAdded(album);
	}
	
}
