//$Id$
package com.object;

public class Inform {
	
	private int limit ;
	private int offset ;
	private int count ;
	private int page ;
	private boolean moredata;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public boolean isMoredata() {
		return moredata;
	}
	public void setMoredata(boolean moredata) {
		this.moredata = moredata;
	}
	
	

}

