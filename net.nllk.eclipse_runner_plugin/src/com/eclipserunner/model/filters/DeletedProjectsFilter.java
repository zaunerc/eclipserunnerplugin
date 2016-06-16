package com.eclipserunner.model.filters;

import org.eclipse.debug.internal.ui.launchConfigurations.DeletedProjectFilter;
import org.eclipse.jface.preference.IPreferenceStore;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.common.AbstractFilter;

@SuppressWarnings("restriction")
public class DeletedProjectsFilter extends AbstractFilter {

	public DeletedProjectsFilter(String propery, IPreferenceStore preferenceStore) {
		super(propery, preferenceStore);
	}

	@Override
	public boolean filterWhenActive(ILaunchNode launchNode) {
		return ! new DeletedProjectFilter()
			.select(null, null, launchNode.getLaunchConfiguration());
	}

	@Override
	public boolean filterWhenActive(ICategoryNode categoryNode) {
		return false;
	}


}
