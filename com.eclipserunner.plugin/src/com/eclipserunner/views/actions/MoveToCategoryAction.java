package com.eclipserunner.views.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;

/**
 * Drops specified launch configurations to target node.
 * 
 * @author lwachowi
 */
public class MoveToCategoryAction extends Action {

	private final List<ILaunchNode> selectedLaunchNode;
	private final ICategoryNode targetNode;

	public MoveToCategoryAction(List<ILaunchNode> selectedLaunchNodes, ICategoryNode node) {
		this.selectedLaunchNode = selectedLaunchNodes;
		this.targetNode = node;
	}
	
	@Override
	public void run() {
		targetNode.drop(selectedLaunchNode);
	}

}
