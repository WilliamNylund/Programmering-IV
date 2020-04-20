import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class MusicOrganizerButtonPanel extends JPanel {

	private MusicOrganizerController controller;
	private MusicOrganizerWindow view;
	private Command command;
	
	private JButton newAlbumButton;
	private JButton deleteAlbumButton;
	private JButton addSoundClipsButton;
	private JButton removeSoundClipsButton;	
	private JButton playButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton flagButton;
	private JButton gradeButton;

	
	public MusicOrganizerButtonPanel(MusicOrganizerController contr, MusicOrganizerWindow view){
		super(new BorderLayout());

		controller = contr;
		
		this.view = view;
		
		JToolBar toolbar = new JToolBar();
		
		newAlbumButton = createNewAlbumButton();
		toolbar.add(newAlbumButton);

		deleteAlbumButton = createDeleteAlbumButton();
		toolbar.add(deleteAlbumButton);

		addSoundClipsButton = createAddSoundClipsButton();
		toolbar.add(addSoundClipsButton);

		removeSoundClipsButton = createRemoveSoundClipsButton();
		toolbar.add(removeSoundClipsButton);

		playButton = createPlayButton();
		toolbar.add(playButton);
		
		undoButton = createUndoButton();
		toolbar.add(undoButton);
		undoButton.setEnabled(false);
		
		redoButton = createRedoButton();
		toolbar.add(redoButton);
		redoButton.setEnabled(false);
		
		flagButton = createFlagButton();
		toolbar.add(flagButton);
		
		gradeButton = createGradeButton();
		toolbar.add(gradeButton);
		
		this.add(toolbar);

	}
	
	/**
	 * Note: You can replace the text strings in the instantiations of the JButtons
	 * below with ImageIcons if you prefer to have buttons with icons instead of
	 * buttons with text strings
	 * 
	 *  Example:
	 *  ImageIcon newAlbumIcon = new ImageIcon("icons/folder_add_32.png");
	 *  JButton newAlbumButton = new JButton(newAlbumIcon);
	 *  
	 *  will put the imageIcon on the button, instead of the text "New Album", as 
	 *  done below
	 * 
	 */
	
	private JButton createNewAlbumButton() {
		//ImageIcon newAlbumIcon = new ImageIcon("icons/folder_add_32.png");
		JButton newAlbumButton = new JButton("New Album");
		newAlbumButton.setToolTipText("New Album");
		newAlbumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(view.getSelectedAlbum() == null) {
					view.showMessage("Select an album");
					return;
				}
				controller.addNewAlbum(view.getSelectedAlbum());
				} catch(ClassCastException ex) {
					view.showMessage("You can not add subalbum to this album");
				}
			
			} 
		});
		return newAlbumButton;
	}
	
	private JButton createDeleteAlbumButton() {
		//ImageIcon deleteAlbumIcon = new ImageIcon("icons/folder_delete_32.png");
		JButton deleteAlbumButton = new JButton("Remove Album");
		deleteAlbumButton.setToolTipText("Delete Selected Album");
		deleteAlbumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(view.getSelectedAlbum() == null) {
					view.showMessage("Select an album");
					return;
				}
				controller.deleteAlbum(view.getSelectedAlbum());
				redoButton.setEnabled(false);
				undoButton.setEnabled(true);
				} catch(ClassCastException ex) {
					view.showMessage("You can not remove this album");
				}
			}
		});
		return deleteAlbumButton;
	}

	private JButton createAddSoundClipsButton() {
		//ImageIcon addSoundClipsIcon = new ImageIcon("icons/document_add_32.png");
		JButton addSoundClipButton = new JButton("Add Sound Clips");
		addSoundClipButton.setToolTipText("Add Selected Sound Clips To Selected Album");
		addSoundClipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(view.getSelectedAlbum() == null) {
					view.showMessage("Select an album");
					return;
				}else if (view.getSelectedSoundClips().isEmpty()) {
					view.showMessage("Select soundclips");
					return;
				}
				controller.addSoundClips(view.getSelectedSoundClips(), view.getSelectedAlbum());
				} catch(ClassCastException ex) {
					view.showMessage("You can not add a soundclip to this album");
				}
			}
		});
		return addSoundClipButton;
	}
	
	private JButton createRemoveSoundClipsButton() {
		//ImageIcon removeSoundClipsIcon = new ImageIcon("icons/document_delete_32.png");
		JButton removeSoundClipsButton = new JButton("Remove Sound Clips");
		removeSoundClipsButton.setToolTipText("Remove Selected Sound Clips From Selected Album");
		removeSoundClipsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if(view.getSelectedAlbum() == null) {
					view.showMessage("Select an album");
					return;
				}else if (view.getSelectedSoundClips().isEmpty()) {
					view.showMessage("Select soundclips");
					return;
				}
				controller.removeSoundClips(view.getSelectedSoundClips(), view.getSelectedAlbum());
				redoButton.setEnabled(false);
				undoButton.setEnabled(true);
				} catch(ClassCastException ex) {
					view.showMessage("You can not remove a soundclip from this album");
				}
			}
		});
		return removeSoundClipsButton;
	}
	
	private JButton createPlayButton() {
		//ImageIcon playIcon = new ImageIcon("icons/play_32.png");
		JButton playButton = new JButton("Play");
		playButton.setToolTipText("Play Selected Sound Clip");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.playSoundClips();
			}
		});
		return playButton;
	}
	
	private JButton createUndoButton() {
		JButton undoButton = new JButton("Undo");
		undoButton.setToolTipText("Undo last action");
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
				redoButton.setEnabled(true);
				if (controller.getCommandSize() == 0) {
					undoButton.setEnabled(false);
				}
			}
			
		});
		return undoButton;
	}
	
	private JButton createRedoButton() {
		JButton redoButton = new JButton("Redo");
		redoButton.setToolTipText("Redo last action");
		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
				undoButton.setEnabled(true);
				if (controller.getRedoSize() == 0) {
					redoButton.setEnabled(false);
				}
			}
		});
		return redoButton;
	}
	
	private JButton createFlagButton() {
		JButton flagButton = new JButton("Flag");
		flagButton.setToolTipText("Flag selected SoundClip");
		flagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(view.getSelectedSoundClips() == null) {
					view.showMessage("Choose soundclips to flag");
					return;
				} else {
				controller.flag(view.getSelectedSoundClips());
				}
			}
		});
		return flagButton;
	}
	private JButton createGradeButton() {
		JButton gradeButton = new JButton("Grade");
		gradeButton.setToolTipText("Grade selected SoundClip");
		gradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.grade(view.getSelectedSoundClips());
			}
		});
		return gradeButton;
	}
	
	public JButton getUndoButton() {
		return undoButton;
	}
	public JButton getRedoButton() {
		return redoButton;
	}
	public JButton getAddSoundClipsButton() {
		return addSoundClipsButton;
	}
	public JButton getRemoveSoundClipsButton() {
		return removeSoundClipsButton;
	}
	public JButton getNewAlbumButton() {
		return newAlbumButton;
	}
	public JButton getDeleteAlbumButton() {
		return deleteAlbumButton;
	}

}
