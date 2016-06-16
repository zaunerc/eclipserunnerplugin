package com.eclipserunner.model.filters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.common.AbstractFilter;


public class ProjectFilter extends AbstractFilter {

	private String projectName;

	public ProjectFilter(String propery, IPreferenceStore preferenceStore) {
		super(propery, preferenceStore);
	}
	
	@Override
	public boolean filterWhenActive(ILaunchNode launchNode) {		
		if (getFilterProjectName() == null) {
			return false;
		}
		String launchConfigurationProjectName = getLaunchConfigurationProjectName(launchNode.getLaunchConfiguration());
		return filter(launchConfigurationProjectName);
	}
	boolean filter(String launchConfigurationProjectName) {
		if(launchConfigurationProjectName == null || getFilterProjectName() == null)
			return false;
		return !(getFilterProjectName().equals(launchConfigurationProjectName));
	}
	@Override
	public boolean filterWhenActive(ICategoryNode categoryNode) {
		if (getFilterProjectName() == null) {
			return false;
		}
		for (ILaunchNode launchNode : categoryNode.getLaunchNodes()) {
			String launchConfigurationProjectName = getLaunchConfigurationProjectName(launchNode.getLaunchConfiguration());
			if (!filter(launchConfigurationProjectName)) {
				return false;
			}
		}
		return true;
	}	
	
	private String getLaunchConfigurationProjectName(ILaunchConfiguration launchConfiguration) {
		try {
			IResource[] resources = launchConfiguration.getMappedResources();
			if (resources != null) {
				for (IResource resource : resources) {
					IResource parentResource = resource.getParent();
					while (parentResource != null) {
						if (IResource.PROJECT == parentResource.getType()) {
							IProject project = (IProject) parentResource;
							return project.getName();
						}
						parentResource = parentResource.getParent();
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public String getFilterProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
