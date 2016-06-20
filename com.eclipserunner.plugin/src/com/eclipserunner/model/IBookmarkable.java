package com.eclipserunner.model;

/**
 * Interface for tree elements supporting bookmarking.
 * 
 * @author lwachowi
 */
public interface IBookmarkable {

	boolean isBookmarked();
	void setBookmarked(boolean state);
	
}
