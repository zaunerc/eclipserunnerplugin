package com.eclipserunner.model;

import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * @author vachacz
 */
public interface ILaunchNode extends IBookmarkable, IDroppable {

	/**
	 * It is always safe to call {@link ILaunchConfiguration#getName()}, but for some other
	 * methods make sure the launch configuration {@link #isExisting()}
	 * @return the launch configuration associated with this node
	 */
	ILaunchConfiguration getLaunchConfiguration();
	/**
	 * @return true if it is safe to use the object returned by #getLaunchConfiguration()
	 */
	boolean isExisting();
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
