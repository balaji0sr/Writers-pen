//$Id$
package com.object;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Searchtag {
	
	@Id
	private int tagid;
	private int tagname;
	public int getTagid() {
		return tagid;
	}
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
	public int getTagname() {
		return tagname;
	}
	public void setTagname(int tagname) {
		this.tagname = tagname;
	}
	
	

}
