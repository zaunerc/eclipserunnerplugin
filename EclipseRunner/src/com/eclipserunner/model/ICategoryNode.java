package com.eclipserunner.model;

import java.util.Collection;

/**
 * Launch configuration category interface.
 *
 * @author vachacz
 */
public interface ICategoryNode extends IActionEnablement, IBookmarkable, IDroppable {

	String getName();
	void setName(String name);
	
	boolean isExpanded();
	void setExpanded(boolean expanded);
	
	Collection<ILaunchNode> getLaunchNodes();

	void add(ILaunchNode launchNode);
	void remove(ILaunchNode launchNode);

	void addCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener);
	void removeCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener);

}
