package com.eclipserunner.model.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfigurationType;

import com.eclipserunner.model.IActionEnablement;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchTypeNode;
import com.eclipserunner.ui.dnd.RunnerViewDropListener;

/**
 * @author vachacz
 */
public class LaunchTypeNode implements ILaunchTypeNode, IActionEnablement {

	private static final int PRIME_MULTIPLYER = 23;
	private static final int PRIME_BASE       = 133;

	private ICategoryNode categoryNode;
	private ILaunchConfigurationType launchConfigurationType;

	public void setLaunchConfigurationType(ILaunchConfigurationType launchConfigurationType) {
		this.launchConfigurationType = launchConfigurationType;
	}

	public ILaunchConfigurationType getLaunchConfigurationType() {
		return launchConfigurationType;
	}

	public void setCategoryNode(ICategoryNode categoryNode) {
		this.categoryNode = categoryNode;
	}

	public ICategoryNode getCategoryNode() {
		return categoryNode;
	}

	public Collection<ILaunchNode> getLaunchNodes() {
		Set<ILaunchNode> launchNodes = new HashSet<ILaunchNode>();
		if (categoryNode !=  null) {
			for (ILaunchNode launchNode : categoryNode.getLaunchNodes()) {
				try {
					if (launchConfigurationType.equals(launchNode.getLaunchConfiguration().getType())) {
						launchNodes.add(launchNode);
					}
				} catch (CoreException e) {
				}
			}
		}
		return launchNodes;
	}

	public void setBookmarked(boolean state) {
		for (ILaunchNode launchNode : getLaunchNodes()) {
			launchNode.setBookmarked(state);
		}
	}

	public boolean isRemovable() {
		return true;
	}

	public boolean isRenamable() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LaunchTypeNode) {
			LaunchTypeNode typeNode = (LaunchTypeNode) obj;
			return launchConfigurationType.equals(typeNode.getLaunchConfigurationType()) && categoryNode.equals(typeNode.getCategoryNode());
		}
		return super.equals(obj);
	}

	@Override
	// TODO LWA danger of NullPointerException
	// integrate commons HashCodeBuilder
	public int hashCode () {
		int code = PRIME_BASE;
		code = PRIME_MULTIPLYER * code + launchConfigurationType.hashCode();
		code = PRIME_MULTIPLYER * code + categoryNode.hashCode();
		return code;
	}

	public boolean isBookmarked() {
		throw new UnsupportedOperationException("LaunchType cannot be bookmarked.");
	}

	public boolean supportsDrop(int currentLocation) {
		return categoryNode.supportsDrop(RunnerViewDropListener.LOCATION_ON);
	}

	public boolean drop(List<ILaunchNode> launchNodesToMove) {
		return categoryNode.drop(launchNodesToMove);
	}

	public boolean isExpanded() {
		return getCategoryNode().isExpandChild(launchConfigurationType.getIdentifier());
	}

	public void setExpanded(boolean expanded) {
		getCategoryNode().setExpandChild(expanded,launchConfigurationType.getIdentifier());
	}

}
