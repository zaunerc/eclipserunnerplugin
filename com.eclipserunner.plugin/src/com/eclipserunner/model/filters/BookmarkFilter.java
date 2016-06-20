package com.eclipserunner.model.filters;

import org.eclipse.jface.preference.IPreferenceStore;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.common.AbstractFilter;

public class BookmarkFilter extends AbstractFilter {

	public BookmarkFilter(String propery, IPreferenceStore preferenceStore) {
		super(propery, preferenceStore);
	}

	@Override
	public boolean filterWhenActive(ILaunchNode launchNode) {
		return !launchNode.isBookmarked();
	}

	@Override
	public boolean filterWhenActive(ICategoryNode categoryNode) {
		for (ILaunchNode launchNode : categoryNode.getLaunchNodes()) {
			if (launchNode.isBookmarked()) {
				return false;
			}
		}
		return true;
	}

}
