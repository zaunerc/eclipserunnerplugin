package com.eclipserunner.model.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;

import com.eclipserunner.model.IActionEnablement;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchNodeChangeListener;
import com.eclipserunner.ui.dnd.RunnerViewDropListener;

/**
 * @author vachacz
 */
public class LaunchNode implements ILaunchNode, IActionEnablement {

	private static final int PRIME_MULTIPLYER = 11;
	private static final int PRIME_BASE       = 17;

	private ILaunchConfiguration launchConfiguration;
	private ICategoryNode categoryNode;
	private boolean bookmarked;

	private Set<ILaunchNodeChangeListener> launchNodeChangeListeners = new HashSet<ILaunchNodeChangeListener>();
	private String mode;

	public LaunchNode() {
	}

	public void setLaunchConfiguration(ILaunchConfiguration launchConfiguration) {
		this.launchConfiguration = launchConfiguration;
	}

	public ILaunchConfiguration getLaunchConfiguration() {
		return launchConfiguration;
	}

	public void setCategoryNode(ICategoryNode categoryNode) {
		this.categoryNode = categoryNode;
	}

	public ICategoryNode getCategoryNode() {
		return categoryNode;
	}

	public void setBookmarked(boolean state) {
		this.bookmarked = state;
		fireLaunchNodeChangedEvent();
	}

	public boolean isBookmarked() {
		return bookmarked;
	}

	public void addLaunchNodeChangeListener(ILaunchNodeChangeListener launchNodeChangeListener) {
		launchNodeChangeListeners.add(launchNodeChangeListener);
	}

	public void removeLaunchNodeChangeListener(ILaunchNodeChangeListener launchNodeChangeListener) {
		launchNodeChangeListeners.remove(launchNodeChangeListener);
	}

	private void fireLaunchNodeChangedEvent() {
		for (ILaunchNodeChangeListener launchNodeChangeListener : launchNodeChangeListeners) {
			launchNodeChangeListener.launchNodeChanged();
		}
	}

	public boolean isRemovable() {
		return true;
	}

	public boolean isRenamable() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LaunchNode) {
			LaunchNode launchNode = (LaunchNode) obj;
			return launchConfiguration.equals(launchNode.getLaunchConfiguration());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode () {
		int code = PRIME_BASE;
		code = PRIME_MULTIPLYER * code + launchConfiguration.hashCode();
		return code;
	}

	public boolean supportsDrop(int currentLocation) {
		return categoryNode.supportsDrop(RunnerViewDropListener.LOCATION_ON);
	}

	public boolean drop(List<ILaunchNode> launchNodesToMove) {
		return categoryNode.drop(launchNodesToMove);
	}

	public void setLaunchMode(String mode) {
		this.mode=mode;
		fireLaunchNodeChangedEvent();
	}

	public String getLaunchMode() {
		return mode;
	}

	public void launch(String mode) {
		setLaunchMode(mode);
		DebugUITools.launch(getLaunchConfiguration(),mode);
	}

}
