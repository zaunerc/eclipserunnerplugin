package com.eclipserunner.views.actions;

import static org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog.LAUNCH_CONFIGURATION_DIALOG_OPEN_ON_LAST_LAUNCHED;
import static org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog.LAUNCH_CONFIGURATION_DIALOG_OPEN_ON_SELECTION;

import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationsDialog;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchGroupExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import com.eclipserunner.RunnerPlugin;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeSelection;

/**
 * Action responsible for showing LaunchConfigurationsDialog.
 *
 * @author bary
 */
@SuppressWarnings("restriction")
public class ShowLaunchOtherConfigurationsDialogAction extends BaseRunnerAction {

	private INodeSelection selection;
	private LaunchGroupExtension launchGroupExtension;

	public ShowLaunchOtherConfigurationsDialogAction(INodeSelection selection, LaunchGroupExtension launchGroupExtension) {
		this.selection = selection;
		this.launchGroupExtension = launchGroupExtension;
	}

	@Override
	public void run() {
		LaunchConfigurationsDialog dialog = new LaunchConfigurationsDialog(
				RunnerPlugin.getRunnerShell(),
				launchGroupExtension
				);

		if (selection.hasExactlyOneNode() && selection.firstNodeHasType(ILaunchNode.class)) {
			dialog.setOpenMode(LAUNCH_CONFIGURATION_DIALOG_OPEN_ON_SELECTION);
			dialog.setInitialSelection(
					asStructuredSelection(selection.getFirstNodeAs(ILaunchNode.class).getLaunchConfiguration())
					);
		}
		else {
			dialog.setOpenMode(LAUNCH_CONFIGURATION_DIALOG_OPEN_ON_LAST_LAUNCHED);
		}

		dialog.open();
	}

	public static IStructuredSelection asStructuredSelection(Object object) {
		return new StructuredSelection(object);
	}
	public String getMode() {
		return launchGroupExtension.getMode();
	}

}
