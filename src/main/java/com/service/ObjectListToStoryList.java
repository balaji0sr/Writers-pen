//$Id$
package com.service;

import java.util.ArrayList;
import java.util.List;

import com.object.Story;

public class ObjectListToStoryList {
	
	public static ArrayList<Story> change(List<Object[]> list) {
		
		ArrayList<Story> slist = new ArrayList<Story>();
		
		for(Object[] o : list) {
			Story s = new Story();
			s.setStoryid((long) o[0]);
			s.setUserid((long) o[1]);
			s.setUsername((String) o[2]);
			
			s.setTitle((String) o[3]);
			s.setContent((String) o[4]);
			s.setType((int) o[5]);
			
			s.setParentstoryid((long) o[6]);
			s.setUpdatetime((String) o[7]);
			s.setModifiedtime((String) o[8]);
			
			s.setGenreid((long) o[9]);
			s.setLikecount((long) o[10]);
			s.setViewcount((long) o[11]);
			s.setContenttext((String) o[12]);
			
			slist.add(s);		
		}
		return slist;
	}
}
