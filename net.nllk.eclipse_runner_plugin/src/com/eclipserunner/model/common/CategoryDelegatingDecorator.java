package com.eclipserunner.model.common;

import java.util.Collection;
import java.util.List;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ICategoryNodeChangeListener;
import com.eclipserunner.model.ILaunchNode;

/**
 * @author vachacz
 */
public class CategoryDelegatingDecorator implements ICategoryNode {

	protected final ICategoryNode category;
	
	public boolean isExpandChild(String identifier) {
		return category.isExpandChild(identifier);
	}

	public void setExpandChild(boolean expanded, String identifier) {
		category.setExpandChild(expanded, identifier);
	}

	public CategoryDelegatingDecorator(ICategoryNode category) {
		this.category = category;
	}

	public void add(ILaunchNode launchNode) {
		category.add(launchNode);
	}

	public void addCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener) {
		category.addCategoryNodeChangeListener(categoryNodeChangeListener);
	}

	public Collection<ILaunchNode> getLaunchNodes() {
		return category.getLaunchNodes();
	}

	public String getName() {
		return category.getName();
	}

	public void remove(ILaunchNode launchNode) {
		category.remove(launchNode);
	}

	public void removeCategoryNodeChangeListener(ICategoryNodeChangeListener categoryNodeChangeListener) {
		category.removeCategoryNodeChangeListener(categoryNodeChangeListener);
	}

	public void setBookmarked(boolean state) {
		category.setBookmarked(state);
	}

	public void setName(String name) {
		category.setName(name);
	}

	public boolean isRemovable() {
		return category.isRemovable();
	}

	public boolean isRenamable() {
		return category.isRenamable();
	}

	@Override
	public boolean equals(Object obj) {
		return category.equals(obj);
	}

	@Override
	public int hashCode () {
		return category.hashCode();
	}

	public boolean isBookmarked() {
		throw new UnsupportedOperationException("Category cannot be bookmarked.");
	}

	public boolean supportsDrop(int currentLocation) {
		return category.supportsDrop(currentLocation);
	}

	public boolean drop(List<ILaunchNode> launchNodesToMove) {
		return category.drop(launchNodesToMove);
	}

	public boolean isExpanded() {
		return category.isExpanded();
	}

	public void setExpanded(boolean expanded) {
		category.setExpanded(expanded);
	}

}
