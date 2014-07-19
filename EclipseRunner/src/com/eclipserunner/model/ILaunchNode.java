package com.eclipserunner.model;

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

	/**
	 * @param mode the default launch mode
	 */
	void setDefaultMode(String mode);
	String getDefaultMode();
	boolean supportsMode(String mode, String category);
	
	void launch(String mode);
}
