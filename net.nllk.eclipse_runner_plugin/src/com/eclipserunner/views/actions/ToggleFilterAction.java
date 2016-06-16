package com.eclipserunner.views.actions;

import com.eclipserunner.views.IRunnerView;

/**
 * @author vachacz
 */
public class ToggleFilterAction extends BaseRunnerAction {

	private final String preferenceProperty;
	private final IRunnerView runnerView;

	public ToggleFilterAction(String preferenceProperty, IRunnerView runnerView) {
		this.preferenceProperty = preferenceProperty;
		this.runnerView = runnerView;

		boolean active = getPreferenceStore().getBoolean(preferenceProperty);
		setChecked(active);
	}

	@Override
	public void run() {
		getPreferenceStore().setValue(preferenceProperty, isChecked());

		runnerView.refresh();
	}

}
