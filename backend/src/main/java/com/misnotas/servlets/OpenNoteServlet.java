package com.misnotas.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.misnotas.persistence.Note;
import com.misnotas.persistence.NotesDao;
import com.misnotas.persistence.NotesDaoImplJdo;

public class OpenNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		String myId = request.getRequestURI();
		myId = myId.substring(1);
		NotesDao notesDao = new NotesDaoImplJdo();
		Note note = notesDao.selectNote(myId);
		if (note==null) {
			response.sendRedirect("/index.html" );
		} else {
			//note.getText().replaceAll("\r\n", "\\\\n")
			String str = StringEscapeUtils.escapeEcmaScript(note.getText()); 
			request.setAttribute("NOTE_TEXT", str);
			request.setAttribute("NOTE_FOUND", note);
			rd = request.getRequestDispatcher("jsps/editor.jsp");		
			rd.forward(request, response);
		}
	}
}
