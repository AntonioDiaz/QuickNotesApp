package com.misnotas.persistence;

public interface NotesDao {

	public void createNote(Note note);
	
	public void updateNote(Note note);
	
	public Note selectNote(String id);
	
}
