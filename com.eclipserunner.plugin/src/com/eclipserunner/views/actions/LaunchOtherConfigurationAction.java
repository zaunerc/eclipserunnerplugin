package com.eclipserunner.views.actions;

import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeSelection;

/**
 * Action responsible for launching selected configuration.
 *
 * @author bary
 */
public class LaunchOtherConfigurationAction extends BaseRunnerAction {

	private INodeSelection selection;
	private String mode;
	private String category;

	public LaunchOtherConfigurationAction(INodeSelection selection, String mode, String category) {
		this.selection = selection;
		this.mode = mode;
		this.category = category;
	}
	public String getMode() {
		return mode;
	}
	public String getCategory() {
		return category;
	}
	@Override
	public void run() {
		if (selection.hasExactlyOneNode() && selection.firstNodeHasType(ILaunchNode.class)) {
			selection.getFirstNodeAs(ILaunchNode.class).launch(mode);
		}
	}

}
