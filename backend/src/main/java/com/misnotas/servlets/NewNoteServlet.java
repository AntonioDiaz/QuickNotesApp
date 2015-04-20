package com.misnotas.servlets;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.misnotas.persistence.Note;
import com.misnotas.persistence.NotesDao;
import com.misnotas.persistence.NotesDaoImplJdo;

public class NewNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Random randomGenerator = new Random();
		char myCharacter1 = (char) (65 + randomGenerator.nextInt(26));
		char myCharacter2 = (char) (65 + randomGenerator.nextInt(26));
		char myCharacter3 = (char) (65 + randomGenerator.nextInt(26));
		char myCharacter4 = (char) (65 + randomGenerator.nextInt(26));
		String idNewNote = "" + myCharacter1 + myCharacter2 + myCharacter3 + myCharacter4;

		req.setAttribute("idNote", idNewNote);

		NotesDao notesDao = new NotesDaoImplJdo();
		Note note = new Note();
		note.setId(idNewNote);
		note.setTextBlob(null);
		notesDao.createNote(note);

		resp.sendRedirect("/" + idNewNote);
		//RequestDispatcher rd = req.getRequestDispatcher(idNewNote);
		//rd.forward(req, resp);
	}

}
