package com.misnotas.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Blob;
import com.misnotas.persistence.Note;
import com.misnotas.persistence.NotesDao;
import com.misnotas.persistence.NotesDaoImplJdo;


public class SaveNoteServlet extends HttpServlet{

	/**
	 * 
	 */
	public static final long MAX_SIZE = 100000;
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String myId = request.getParameter("myId");
		String myText = request.getParameter("myText");
		if (myText.length()<MAX_SIZE) {
			Note note = new Note();
			note.setId(myId);
			note.setTextBlob(new Blob(myText.getBytes(StandardCharsets.UTF_8)));
			NotesDao notesDao = new NotesDaoImplJdo();
			notesDao.updateNote(note);
			request.setAttribute("NOTE_FOUND", note);		
		}
		response.sendRedirect("/" + myId);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
