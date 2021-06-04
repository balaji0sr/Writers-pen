//$Id$
package com.object;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Genre {
	
	@Id
	private int genreid;
	private int genrename;
	
	public int getGenreid() {
		return genreid;
	}
	public void setGenreid(int genreid) {
		this.genreid = genreid;
	}
	public int getGenrename() {
		return genrename;
	}
	public void setGenrename(int genrename) {
		this.genrename = genrename;
	}
}
