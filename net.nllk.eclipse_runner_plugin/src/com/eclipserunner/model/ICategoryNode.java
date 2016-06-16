package com.eclipserunner.model;

import java.util.Collection;

/**
 * Launch configuration category interface.
 *
 * @author vachacz
 */
public interface ICategoryNode extends IActionEnablement, IBookmarkable, IDroppable, IExpandable {

	String getName();
	void setName(String name);
	
	Collection<ILaunchNode> getLaunchNodes();

	void add(ILaunchNode launchNode);
	void remove(ILaunchNode launchNode);

	void addCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener);
	void removeCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener);

	boolean isExpandChild(String identifier);
	void setExpandChild(boolean expanded, String identifier);
}
