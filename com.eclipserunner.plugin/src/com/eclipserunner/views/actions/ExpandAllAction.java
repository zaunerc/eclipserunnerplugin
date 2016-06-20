package com.eclipserunner.views.actions;

import org.eclipse.jface.action.Action;

import com.eclipserunner.views.IRunnerView;

/**
 * @author vachacz
 */
public class ExpandAllAction extends Action {

	private IRunnerView view;

	public ExpandAllAction(IRunnerView view) {
		this.view = view;
	}

	@Override
	public void run() {
		view.expandAll();
	}
}
