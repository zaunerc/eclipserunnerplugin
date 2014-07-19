package com.eclipserunner.views.actions;

import org.eclipse.jface.action.Action;

import com.eclipserunner.views.IRunnerView;

/**
 * @author vachacz
 */
public class CollapseAllAction extends Action {

	private IRunnerView view;

	public CollapseAllAction(IRunnerView view) {
		this.view = view;
	}

	@Override
	public void run() {
		view.collapseAll();
	}
}
