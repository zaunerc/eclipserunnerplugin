package com.eclipserunner.views.impl;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import com.eclipserunner.model.IActionEnablement;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeSelection;
import com.eclipserunner.utils.SelectionUtils;

// TODO [LW] remove selection utils
public class RunnerViewSelection implements INodeSelection {

	private final TreeViewer treeViewer;

	public RunnerViewSelection(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	@SuppressWarnings("unchecked")
	public <T> T getFirstNodeAs(Class<T> clazz) {
		return (T) getSelection().getFirstElement();
	}

	public boolean allNodesHaveSameType() {
		return SelectionUtils.isSameTypeNodeSelection(getSelection());
	}

	public boolean hasExactlyOneNode() {
		return getSelection().size() == 1;
	}

	public boolean firstNodeHasType(Class<?> clazz) {
		Object firstElement = getSelection().getFirstElement();
		if (firstElement != null) {
			return clazz.isAssignableFrom(firstElement.getClass());
		}
		return false;
	}
	
	public <T> List<T> getSelectedNodesByType(Class<T> clazz) {
		if (allNodesHaveSameType() && firstNodeHasType(clazz)) {
			return findSelectedNodesByType(clazz);
		}
		return Collections.emptyList();
	}

	public boolean canBeLaunched() {
		return hasExactlyOneNode() && firstNodeHasType(ILaunchNode.class);
	}

	public boolean canBeRenamed() {
		if (allNodesHaveSameType() && hasExactlyOneNode()) {
			Object selectedNode = getSelection().getFirstElement();
			if (selectedNode instanceof IActionEnablement) {
				return ((IActionEnablement) selectedNode).isRenamable();
			}
		}
		return false;
	}

	public boolean canBeRemoved() {
		if (! allNodesHaveSameType()) {
			return false;
		}
		for (Object selectedNode : getSelection().toList()) {
			if (selectedNode instanceof IActionEnablement) {
				if (!((IActionEnablement) selectedNode).isRemovable()) {
					return false;
				}
			}
			else {
				return false;
			}
		}
		return true;
	}

	public boolean canBeBookmarked() {
		return allNodesHaveSameType();
	}
	
	public boolean canBeOpened() {
		if (hasExactlyOneNode() && firstNodeHasType(ILaunchNode.class)) {
			ILaunchNode launchNode = getFirstNodeAs(ILaunchNode.class);
			ILaunchConfiguration configuration = launchNode.getLaunchConfiguration();
			try {
				for (IResource res : configuration.getMappedResources()) {
					if (res instanceof IFile) {
						return true;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	<T> List<T> findSelectedNodesByType(Class<T> type) {
		return SelectionUtils.getAllSelectedItemsByType(getSelection(), type);
	}
	
	private IStructuredSelection getSelection() {
		return (IStructuredSelection) treeViewer.getSelection();
	}

}
