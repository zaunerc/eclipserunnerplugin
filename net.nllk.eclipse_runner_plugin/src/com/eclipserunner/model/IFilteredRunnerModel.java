package com.eclipserunner.model;

import java.util.List;

// TODO LWA remove me
public interface IFilteredRunnerModel extends IRunnerModel {

	void addFilter(INodeFilter filter);	
	List<INodeFilter> getFilters();
	
}
