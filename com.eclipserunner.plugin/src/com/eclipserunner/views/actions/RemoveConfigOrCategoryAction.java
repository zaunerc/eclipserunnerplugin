package com.eclipserunner.views.actions;

import static com.eclipserunner.Messages.Message_removeConfirmPrompt;
import static com.eclipserunner.Messages.Message_removeConfirmTitle;

import java.util.Collection;
import java.util.List;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchTypeNode;
import com.eclipserunner.model.INodeSelection;
import com.eclipserunner.model.IRunnerModel;

/**
 * @author vachacz, bary
 */
public class RemoveConfigOrCategoryAction extends BaseRunnerAction {

	private INodeSelection nodeSelection;
	private IRunnerModel runnerModel;

	public RemoveConfigOrCategoryAction(INodeSelection launchConfigurationSelection, IRunnerModel runnerModel) {
		this.nodeSelection = launchConfigurationSelection;
		this.runnerModel = runnerModel;
	}

	@Override
	public void run() {
		if (nodeSelection.allNodesHaveSameType()) {
			if (nodeSelection.firstNodeHasType(ILaunchNode.class)) {
				removeLaunchNodes(nodeSelection.getSelectedNodesByType(ILaunchNode.class));
			}
			else if (nodeSelection.firstNodeHasType(ILaunchTypeNode.class)) {
				removeLaunchTypeNodes(nodeSelection.getSelectedNodesByType(ILaunchTypeNode.class));
			}
			else if (nodeSelection.firstNodeHasType(ICategoryNode.class)) {
				removeCategoryNodes(nodeSelection.getSelectedNodesByType(ICategoryNode.class));
			}
		}
	}

	private void removeLaunchNodes(List<ILaunchNode> launchNodes) {
		if (userConfirmedDeletion()) {
			removeLaunchNodesWithoutPrompt(launchNodes);
		}
	}

	private void removeLaunchTypeNodes(List<ILaunchTypeNode> launchTypeNodes) {
		if (userConfirmedDeletion()) {
			for (ILaunchTypeNode launchTypeNode : launchTypeNodes) {
				removeLaunchNodesWithoutPrompt(launchTypeNode.getLaunchNodes());
			}
		}
	}

	private void removeCategoryNodes(List<ICategoryNode> categoryNodes) {
		if (userConfirmedDeletion()) {
			for (ICategoryNode categoryNode : categoryNodes) {
				runnerModel.removeCategoryNode(categoryNode);
			}
		}
	}
	
	private void removeLaunchNodesWithoutPrompt(Collection<ILaunchNode> launchNodes) {
		for (ILaunchNode launchNode : launchNodes) {
			runnerModel.removeLaunchNode(launchNode);
		}
	}

	private boolean userConfirmedDeletion() {
		return openConfirmDialog(Message_removeConfirmTitle, Message_removeConfirmPrompt);
	}

}
