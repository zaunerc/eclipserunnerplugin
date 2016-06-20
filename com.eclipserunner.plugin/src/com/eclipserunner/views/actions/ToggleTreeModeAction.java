package com.eclipserunner.views.actions;

import org.eclipse.jface.action.Action;

import com.eclipserunner.model.RunnerModelProvider;
import com.eclipserunner.views.IRunnerView;
import com.eclipserunner.views.TreeMode;

/**
 * @author vachacz
 */
public class ToggleTreeModeAction extends Action {

	private final IRunnerView runnerView;
	private final TreeMode mode;

	public ToggleTreeModeAction(IRunnerView runnerView, TreeMode mode) {
		this.mode = mode;
		this.runnerView = runnerView;

		TreeMode currentTreeMode = RunnerModelProvider.getInstance().getCurrentTreeMode();
		if (mode.equals(currentTreeMode)) {
			setChecked(true);
		} else {
			setChecked(false);
		}
	}

	@Override
	public void run() {
		// TODO LWA why this is done twice?
		RunnerModelProvider.getInstance().setTreeMode(mode);
		runnerView.setTreeMode(mode);
	}

}
