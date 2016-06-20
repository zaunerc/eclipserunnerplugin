package com.eclipserunner.model.impl;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.eclipserunner.model.IActionEnablement;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ICategoryNodeChangeListener;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchNodeChangeListener;
import com.eclipserunner.ui.dnd.RunnerViewDropListener;

/**
 * Container of launch configurations presented in RunnerView tree.
 *
 * @author vachacz
 */
public class CategoryNode implements ICategoryNode, ILaunchNodeChangeListener, IActionEnablement {

	private static final int PRIME_MULTIPLYER = 11;
	private static final int PRIME_BASE       = 17;

	class LaunchNodeComparator implements Comparator<ILaunchNode> {
		public int compare(ILaunchNode o1, ILaunchNode o2) {
			return o1.getLaunchConfiguration().getName().compareTo(o2.getLaunchConfiguration().getName());
		}
	}

	private String name;
	private Set<ILaunchNode> launchNodes = new TreeSet<ILaunchNode>(new LaunchNodeComparator());
	private Set<ICategoryNodeChangeListener> categoryNodeChangeListeners = new HashSet<ICategoryNodeChangeListener>();
	private Set<String> expandedChildren = new TreeSet<String>();

	private boolean removable  = true;
	private boolean renameable = true;
	private boolean expanded = true;

	public CategoryNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		fireCategoryNodeChangedEvent();
	}

	public final Set<ILaunchNode> getLaunchNodes() {
		return launchNodes;
	}

	public void add(ILaunchNode launchNode) {
		launchNode.setCategoryNode(this);
		launchNode.addLaunchNodeChangeListener(this);

		launchNodes.add(launchNode);
		fireCategoryNodeChangedEvent();
	}

	public void remove(ILaunchNode launchNode) {
		if (launchNodes.contains(launchNode)) {
			launchNodes.remove(launchNode);

			// TODO BARY investigate why this causes NullPointerException on RunnerModelTreeWithTypesAdapter line 80
			//launchNode.setCategoryNode(null);
			launchNode.removeLaunchNodeChangeListener(this);
		}
		fireCategoryNodeChangedEvent();
	}

	public void setBookmarked(boolean state) {
		for (ILaunchNode launchNode : launchNodes) {
			launchNode.setBookmarked(state);
		}
		fireCategoryNodeChangedEvent();
	}

	public void addCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener) {
		categoryNodeChangeListeners.add(categoryNodeChangeListener);
	}

	public void removeCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener) {
		categoryNodeChangeListeners.remove(categoryNodeChangeListener);
	}

	private void fireCategoryNodeChangedEvent() {
		for (ICategoryNodeChangeListener categoryNodeChangeListener : categoryNodeChangeListeners) {
			categoryNodeChangeListener.categoryNodeChanged();
		}
	}

	public void launchNodeChanged() {
		fireCategoryNodeChangedEvent();
	}

	public boolean isRemovable() {
		return removable;
	}

	public boolean isRenamable() {
		return renameable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public void setRenameable(boolean renameable) {
		this.renameable = renameable;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ICategoryNode) {
			ICategoryNode categoryNode = (ICategoryNode) obj;
			return getName().equals(categoryNode.getName());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode () {
		int code = PRIME_BASE;
		code = PRIME_MULTIPLYER * code + getName().hashCode();
		return code;
	}

	public boolean isBookmarked() {
		throw new UnsupportedOperationException("Category cannot be bookmarked.");
	}

	public boolean supportsDrop(int currentLocation) {
		return currentLocation == RunnerViewDropListener.LOCATION_ON;
	}

	public boolean drop(List<ILaunchNode> launchNodesToMove) {
		for (ILaunchNode launchNode : launchNodesToMove) {
			ICategoryNode sourceCategoryNode = launchNode.getCategoryNode();
			sourceCategoryNode.remove(launchNode);
			this.add(launchNode);
		}
		return true;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;		
	}

	public boolean isExpandChild(String identifier) {
		return expandedChildren.contains(identifier);
	}

	public void setExpandChild(boolean expanded, String identifier) {
		if(expanded) {
			expandedChildren.add(identifier);
		} else {
			expandedChildren.remove(identifier);
			
		}
	}
}
