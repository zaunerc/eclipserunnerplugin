package com.eclipserunner.model;

import java.util.Collection;

public interface IRunnerModel {

	void addLaunchNode(ILaunchNode launchNode);
	void removeLaunchNode(ILaunchNode launchNode);

	void addCategoryNode(ICategoryNode categoryNode);
	void removeCategoryNode(ICategoryNode categoryNode);

	Collection<ICategoryNode> getCategoryNodes();

	void addModelChangeListener(IModelChangeListener modelChangeListener);
	
	// FIXME: do we need this remove? garbage collector will do it anyway ;)
	void removeModelChangeListener(IModelChangeListener modelChangeListener);

	ICategoryNode getDefaultCategoryNode();

}
