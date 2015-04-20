package com.misnotas.persistence;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

public class NotesDaoImplJdo implements NotesDao {

	@Override
	public void createNote(Note note) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
            /**/
			transaction.begin();
			Note makePersistent = pm.makePersistent(note);
			System.out.println(makePersistent);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			pm.close();
		}
	}

	@Override
	public Note selectNote(String id) {
		Note myNote = null;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		//final Query query = pm.newQuery(Note.class);
		//query.setFilter("id==" + id);
		try {
			myNote = pm.getObjectById(Note.class, id);
			//			List<Note> list = (List<Note>)query.execute();
			//				if (list!=null && list.size()==1) {
			//					myNote = list.get(0);
			//			}
		} catch (Exception e) {

		}
		return myNote;
	}

	@Override
	public void updateNote(Note note) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction transaction = pm.currentTransaction();
		try {
			transaction.begin();
			Note noteToUpdate = pm.getObjectById(Note.class, note.getId());
			noteToUpdate.setTextBlob(note.getTextBlob());
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			pm.close();
		}		
	}

}
