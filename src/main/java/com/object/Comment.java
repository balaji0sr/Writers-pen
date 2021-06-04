//$Id$
package com.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {
	
	@Id
	@Column(name="commentid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long commentid;
	
	private Long storyid;
	private Long userid;
	private String name;
	private String comment;
	private String updatetime;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCommentid() {
		return commentid;
	}
	
	public Long getStoryid() {
		return storyid;
	}
	public void setStoryid(Long storyid) {
		this.storyid = storyid;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
}
