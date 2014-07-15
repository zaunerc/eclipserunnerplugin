package com.eclipserunner.views.actions;

import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeSelection;

/**
 * Action responsible for launching selected configuration.
 * 
 * @author bary
 */
@SuppressWarnings("restriction")
public class LaunchConfigurationAction extends AbstractLaunchAction {

	final protected INodeSelection selection;

	public LaunchConfigurationAction(INodeSelection selection, String launchGroupId) {
		super(launchGroupId);
		this.selection = selection;
	}

	@Override
	public void run() {
		if (selection.hasExactlyOneNode() && selection.firstNodeHasType(ILaunchNode.class)) {
			selection.getFirstNodeAs(ILaunchNode.class).launch(getLaunchMode());
		}
	}

	protected String getLaunchMode() {
		return getLaunchConfigurationManager().getLaunchGroup(getLaunchGroupId()).getMode();
	}

}
