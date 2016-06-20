package com.eclipserunner.views.actions;

/**
 * Images binded to actions.
 * 
 * @author lwachowi
 */
public enum Image {

	RUN                  ("run.gif"),
	RUN_CONFIGURATIONS   ("run_configuration.gif"),
	DEBUG                ("debug.gif"),
	DEBUG_CONFIGURATIONS ("debug_configuration.gif"),
	NEW_CATEGORY         ("category_new.gif"),
	DEFAULT_CATEGORY     ("category-archive.gif"),
	EXPAND_ALL           ("expandall.gif"),
	BOOKMARK             ("bookmark_star.gif"),
	UNBOOKMARK           ("unbookmark.gif"),
	FLAT_TREE            ("flat.gif"),
	TYPE_TREE            ("hierarchical.gif");
	
	private final String path;

	private Image(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
	
}
