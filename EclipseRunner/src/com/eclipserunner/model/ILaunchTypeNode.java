package com.eclipserunner.model;

import java.util.Collection;

import org.eclipse.debug.core.ILaunchConfigurationType;

public interface ILaunchTypeNode extends IBookmarkable, IDroppable, IExpandable {

	ILaunchConfigurationType getLaunchConfigurationType();
	void setLaunchConfigurationType(ILaunchConfigurationType launchConfigurationType);

	ICategoryNode getCategoryNode();
	void setCategoryNode(ICategoryNode categoryNode);

	Collection<ILaunchNode> getLaunchNodes();

}
