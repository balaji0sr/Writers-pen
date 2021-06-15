//$Id$
package com.object;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Genre {
	
	@Id
	private int genreid;
	private String genrename;
	
	public int getGenreid() {
		return genreid;
	}
	public void setGenreid(int genreid) {
		this.genreid = genreid;
	}
	public String getGenrename() {
		return genrename;
	}
	public void setGenrename(String genrename) {
		this.genrename = genrename;
	}
	
}
