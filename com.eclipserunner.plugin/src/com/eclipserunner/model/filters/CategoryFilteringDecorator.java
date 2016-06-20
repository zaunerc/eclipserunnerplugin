package com.eclipserunner.model.filters;

import java.util.ArrayList;
import java.util.Collection;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeFilter;
import com.eclipserunner.model.common.CategoryDelegatingDecorator;

public class CategoryFilteringDecorator extends CategoryDelegatingDecorator {

	private INodeFilter nodeFilter;

	public CategoryFilteringDecorator(ICategoryNode category) {
		super(category);
	}

	public void setNodeFilter(INodeFilter nodeFilter) {
		this.nodeFilter = nodeFilter;
	}

	@Override
	public Collection<ILaunchNode> getLaunchNodes() {
		Collection <ILaunchNode> filteredLaunchNodes = new ArrayList<ILaunchNode>();
		for (ILaunchNode launchNode : category.getLaunchNodes()) {
			if (nodeFilter.filter(launchNode)) {
				continue;
			}
			filteredLaunchNodes.add(launchNode);
		}
		return filteredLaunchNodes;
	}

}
