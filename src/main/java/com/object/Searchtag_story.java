//$Id$
package com.object;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Searchtag_story {
	
	@Id
	private int Searchtag_storyid;
	private int storyid;
	private int tagid;
	
	public int getSearchtag_storyid() {
		return Searchtag_storyid;
	}
	public void setSearchtag_storyid(int searchtag_storyid) {
		Searchtag_storyid = searchtag_storyid;
	}
	public int getStoryid() {
		return storyid;
	}
	public void setStoryid(int storyid) {
		this.storyid = storyid;
	}
	public int getTagid() {
		return tagid;
	}
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
}
