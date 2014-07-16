package com.eclipserunner.model.adapters;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import com.eclipserunner.model.IFilteredRunnerModel;
import com.eclipserunner.model.INodeFilter;
import com.eclipserunner.model.filters.ProjectFilter;
import com.eclipserunner.views.impl.RunnerView;

/**
 * Adapter listening the currently selected project.
 * 
 * @author bary, scharf
 */
public class RunnerProjectSelectionListenerAdapter implements ISelectionListener {

	private RunnerView view;
	private ProjectFilter projectFilter;

	public RunnerProjectSelectionListenerAdapter(IFilteredRunnerModel model, RunnerView view) {
		this.view = view;
		// Find ProjectFilter filter ;)
		for (INodeFilter filter : model.getFilters()) {
			if (filter instanceof ProjectFilter) {
				projectFilter = (ProjectFilter) filter;
				break;
			}
		}
	}
	private boolean equalsNull(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		String projectName = projectFilter.getFilterProjectName();
		IResource resource = extractResource(selection);
		if(resource!=null && resource.getProject()!=null) {
			projectFilter.setProjectName(resource.getProject().getName());
		}
		if(!equalsNull(projectName, projectFilter.getFilterProjectName())) {
			// Refresh View 
			view.refresh();
		}
	}

	private IResource extractResource(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object element = ss.getFirstElement();
		if (element instanceof IResource)
			return (IResource) element;
		if (!(element instanceof IAdaptable))
			return null;
		IAdaptable adaptable = (IAdaptable) element;
		Object adapter = adaptable.getAdapter(IResource.class);
		return (IResource) adapter;

	}	
}
