
public class DeleteAlbumCommand implements Command{

	RegAlbum album;
	RegAlbum parent;
	MusicOrganizerWindow view;
	
	public DeleteAlbumCommand(RegAlbum album, RegAlbum parent, MusicOrganizerWindow view) {
		this.album = album;
		this.parent = parent;
		this.view = view;
	}

	@Override
	public void execute() {
		album.selfDestruct();
		view.onAlbumRemoved(album);
	}

	@Override
	public void undo() {
		album.setParent(this.parent);
		album.getParent().addSubAlbum(album);
		view.onAlbumAdded(album);
	}

	@Override
	public void redo() {
		album.selfDestruct();
		view.onAlbumRemoved(album);
	}
}
