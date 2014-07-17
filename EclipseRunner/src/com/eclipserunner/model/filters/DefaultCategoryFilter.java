package com.eclipserunner.model.filters;

import org.eclipse.jface.preference.IPreferenceStore;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IExpandable;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.model.common.AbstractFilter;

public class DefaultCategoryFilter extends AbstractFilter {

	private final IRunnerModel runnerModel;

	public DefaultCategoryFilter(String propery, IRunnerModel runnerModel, IPreferenceStore preferenceStore) {
		super(propery, preferenceStore);
		this.runnerModel = runnerModel;
	}

	@Override
	public boolean filterWhenActive(ILaunchNode launchNode) {
		return false;
	}

	@Override
	public boolean filterWhenActive(ICategoryNode categoryNode) {
		IExpandable defaultCategoryNode = runnerModel.getDefaultCategoryNode();
		if (defaultCategoryNode.equals(categoryNode)) {
			return true;
		}
		return false;
	}

}
