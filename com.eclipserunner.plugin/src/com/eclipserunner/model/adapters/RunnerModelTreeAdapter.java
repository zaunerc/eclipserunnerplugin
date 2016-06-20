package com.eclipserunner.model.adapters;

import java.util.Collection;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewSite;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.IRunnerModel;

/**
 * Adapter class adapts IRunnerModel to ITreeContentProvider interface.
 *
 * @author vachacz
 */
public class RunnerModelTreeAdapter implements ITreeContentProvider {

	private IRunnerModel runnerModel;

	public RunnerModelTreeAdapter(IRunnerModel runnerModel) {
		this.runnerModel = runnerModel;
	}

	public Object[] getChildren(Object object) {
		if (object instanceof ICategoryNode) {
			ICategoryNode categoryNode = (ICategoryNode) object;
			return categoryNode.getLaunchNodes().toArray();
		}
		return null;
	}

	public Object getParent(Object object) {
		if (object instanceof ILaunchNode) {
			return ((ILaunchNode) object).getCategoryNode();
		}
		return null;
	}

	public boolean hasChildren(Object parent) {
		if (parent instanceof ICategoryNode) {
			ICategoryNode categoryNode = (ICategoryNode) parent;
			return !categoryNode.getLaunchNodes().isEmpty();
		}
		return false;
	}

	public Object[] getElements(Object parent) {
		if (parent instanceof IViewSite) {
			Collection<ICategoryNode> categoryNodes = runnerModel.getCategoryNodes();
			return categoryNodes.toArray();
		}
		return getChildren(parent);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

}
