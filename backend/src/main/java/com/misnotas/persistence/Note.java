/**
 * toma toma
 */
package com.misnotas.persistence;

import java.io.UnsupportedEncodingException;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.builder.ToStringBuilder;


import com.google.appengine.api.datastore.Blob;



@PersistenceCapable(detachable = "true")
public class Note {

	
	
	@Persistent
	@PrimaryKey
	String id;

	
	@Persistent
	Blob textBlob;
	
	public String getId() { 
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Blob getTextBlob() {
		return textBlob;
	}
	
	
	public void setTextBlob(Blob text) {
		this.textBlob = text;
	}

	public String getText(){
		String str = "";
		try {
			if (textBlob!=null) {
                byte[] decodedBytes = Base64.decodeBase64(textBlob.getBytes());
				str = new String(textBlob.getBytes(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			str = "error" + e.getMessage();
		}
		return str;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}



}
