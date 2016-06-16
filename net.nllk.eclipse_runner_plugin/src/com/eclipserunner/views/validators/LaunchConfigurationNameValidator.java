package com.eclipserunner.views.validators;

import static com.eclipserunner.Messages.Message_errorLaunchConfigurationAlreadyExists;
import static com.eclipserunner.Messages.Message_errorLaunchConfigurationEmptyName;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jface.dialogs.IInputValidator;

/**
 * @author vachacz
 */
public class LaunchConfigurationNameValidator implements IInputValidator {

	private ILaunchManager launchManager = null;
	private String initialValue = null;

	public LaunchConfigurationNameValidator() {
		this(null);
	}

	public LaunchConfigurationNameValidator(String initialName) {
		this(initialName, DebugPlugin.getDefault().getLaunchManager());
	}

	// FOR TESTS ONLY
	protected LaunchConfigurationNameValidator(String initialName, ILaunchManager launchManager) {
		this.initialValue  = initialName;
		this.launchManager = launchManager;
	}

	public String isValid(String input) {
		String name = input.trim();

		if (initialValue != null && initialValue.equals(input)) {
			return null;
		}
		else if (name.length() == 0) {
			return Message_errorLaunchConfigurationEmptyName;
		}
		else if (isExistingLaunchConfigurationName(name)) {
			return Message_errorLaunchConfigurationAlreadyExists;
		}
		return null;
	}

	private boolean isExistingLaunchConfigurationName(String name) {
		try {
			return launchManager.isExistingLaunchConfigurationName(name);
		} catch (CoreException e) {
			return false;
		}
	}

}