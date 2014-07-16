package com.eclipserunner.views.actions;

import static com.eclipserunner.RunnerPlugin.getRunnerShell;

import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchConfigurationManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;

import com.eclipserunner.RunnerPlugin;

/**
 * Base action class wraps all static calls used by actions.
 * 
 * @author lwachowi
 */
@SuppressWarnings("restriction")
public class BaseRunnerAction extends Action {

	public BaseRunnerAction() {
		super();
	}

	public BaseRunnerAction(String text, int style) {
		super(text, style);
	}

	protected IPreferenceStore getPreferenceStore() {
		return RunnerPlugin.getDefault().getPreferenceStore();
	}
	
	protected LaunchConfigurationManager getLaunchConfigurationManager() {
		return DebugUIPlugin.getDefault().getLaunchConfigurationManager();
	}

	protected void openErrorDialog(String title, String message) {
		MessageDialog.openError(getRunnerShell(), title, message);
	}

	protected InputDialog openInputDialog(String title, String message, String initialValue, IInputValidator validator) {
		InputDialog dialog = new InputDialog(getRunnerShell(), title, message, initialValue, validator);
		dialog.open();
		return dialog;
	}

	protected boolean openConfirmDialog(String title, String message) {
		return MessageDialog.openConfirm(RunnerPlugin.getRunnerShell(), title, message);
	}
	
}
