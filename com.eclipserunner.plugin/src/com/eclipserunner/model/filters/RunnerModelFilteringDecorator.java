package com.eclipserunner.model.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IFilteredRunnerModel;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeFilter;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.model.common.RunnerModelDelegatingDecorator;

public class RunnerModelFilteringDecorator extends RunnerModelDelegatingDecorator 
		implements IFilteredRunnerModel, INodeFilter {

	private List<INodeFilter> nodeFilterList = new ArrayList<INodeFilter>();

	public RunnerModelFilteringDecorator(IRunnerModel runnerModel) {
		super(runnerModel);
	}

	@Override
	public Collection<ICategoryNode> getCategoryNodes() {
		Collection<ICategoryNode> decoratedCategories = new ArrayList<ICategoryNode>();
		for (ICategoryNode categoryNode : runnerModel.getCategoryNodes()) {
			if (filter(categoryNode)) {
				continue;
			}
			decoratedCategories.add(decorateCategory(categoryNode));
		}
		return decoratedCategories;
	}

	@Override
	public ICategoryNode getDefaultCategoryNode() {
		return decorateCategory(runnerModel.getDefaultCategoryNode());
	}

	private ICategoryNode decorateCategory(ICategoryNode category) {
		CategoryFilteringDecorator filteredCategory = new CategoryFilteringDecorator(category);
		filteredCategory.setNodeFilter(this);
		return filteredCategory;
	}

	public void addFilter(INodeFilter filter) {
		nodeFilterList.add(filter);
	}
	
	public List<INodeFilter> getFilters() {
		return nodeFilterList;
	}

	public boolean filter(ILaunchNode launchNode) {
		for (INodeFilter nodeFilter : nodeFilterList) {
			if (nodeFilter.filter(launchNode)) {
				return true;
			}
		}
		return false;
	}

	public boolean filter(ICategoryNode categoryNode) {
		for (INodeFilter nodeFilter : nodeFilterList) {
			if (nodeFilter.filter(categoryNode)) {
				return true;
			}
		}
		return false;
	}

	public void setFilterProperty(String key, String value) {
		throw new UnsupportedOperationException("Can not set filter property on RunnerModelFilteringDecorator!");		
	}

	public String getFilterProperty(String key) {
		throw new UnsupportedOperationException("Can not get filter property from RunnerModelFilteringDecorator!");
	}

}
