//$Id$
package com.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Likes {	
	
	@Id
	@Column(name="likeid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long likeid;
	
	private long storyid;
	private long userid;
	private int status;
	
	public long getLikeid() {
		return likeid;
	}
	public void setLikeid(long likeid) {
		this.likeid = likeid;
	}
	public long getStoryid() {
		return storyid;
	}
	public void setStoryid(long storyid) {
		this.storyid = storyid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
