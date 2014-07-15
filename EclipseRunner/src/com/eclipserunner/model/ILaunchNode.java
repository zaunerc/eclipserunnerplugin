package com.eclipserunner.model;

import java.util.Set;

import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * @author vachacz
 */
public interface ILaunchNode extends IBookmarkable, IDroppable {

	ILaunchConfiguration getLaunchConfiguration();
	void setLaunchConfiguration(ILaunchConfiguration launchConfiguration);

	ICategoryNode getCategoryNode();
	void setCategoryNode(ICategoryNode categoryNode);

	void addLaunchNodeChangeListener(ILaunchNodeChangeListener launchNodeChangeListener);
	void removeLaunchNodeChangeListener(ILaunchNodeChangeListener launchNodeChangeListener);

	void setLaunchMode(String mode);
	String getLaunchMode();
	boolean supportsMode(String mode);
	void launch(String mode);
}
