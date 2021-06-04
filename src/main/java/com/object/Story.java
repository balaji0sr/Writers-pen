//$Id$
package com.object;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Story {

	@Id
	private long storyid;

	private long userid;
	private String username;
	private String title;
	private String content;
	private int type;
	private long parentstoryid;
	private String updatetime;
	private String modifiedtime;
	private long genreid;
	private long likecount;
	private long viewcount;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getParentstoryid() {
		return parentstoryid;
	}

	public void setParentstoryid(long parentstoryid) {
		this.parentstoryid = parentstoryid;
	}




	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getModifiedtime() {
		return modifiedtime;
	}

	public void setModifiedtime(String modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	public long getGenreid() {
		return genreid;
	}

	public void setGenreid(long genreid) {
		this.genreid = genreid;
	}

	public long getLikecount() {
		return likecount;
	}

	public void setLikecount(long likecount) {
		this.likecount = likecount;
	}

	public long getViewcount() {
		return viewcount;
	}

	public void setViewcount(long viewcount) {
		this.viewcount = viewcount;
	}
}
