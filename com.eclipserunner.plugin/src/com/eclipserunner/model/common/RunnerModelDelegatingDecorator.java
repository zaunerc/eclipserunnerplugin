package com.eclipserunner.model.common;

import java.util.Collection;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.IModelChangeListener;
import com.eclipserunner.model.IRunnerModel;

/**
 * Base class for all IRunnerModel decorators. Implements all IRunnerModel methods as
 * delegating calls to decorated runner model instance.
 *
 * @author vachacz
 */
public class RunnerModelDelegatingDecorator implements IRunnerModel {

	protected final IRunnerModel runnerModel;

	public RunnerModelDelegatingDecorator(IRunnerModel runnerModel) {
		this.runnerModel = runnerModel;
	}

	public void addCategoryNode(ICategoryNode categoryNode) {
		runnerModel.addCategoryNode(categoryNode);
	}

	public void addLaunchNode(ILaunchNode launchNode) {
		runnerModel.addLaunchNode(launchNode);
	}

	public void addModelChangeListener(IModelChangeListener modelChangeListener) {
		runnerModel.addModelChangeListener(modelChangeListener);
	}

	public Collection<ICategoryNode> getCategoryNodes() {
		return runnerModel.getCategoryNodes();
	}

	public ICategoryNode getDefaultCategoryNode() {
		return runnerModel.getDefaultCategoryNode();
	}

	public void removeCategoryNode(ICategoryNode categoryNode) {
		runnerModel.removeCategoryNode(categoryNode);
	}

	public void removeLaunchNode(ILaunchNode launchNode) {
		runnerModel.removeLaunchNode(launchNode);
	}

	public void removeModelChangeListener(IModelChangeListener modelChangeListener) {
		runnerModel.removeModelChangeListener(modelChangeListener);
	}

}
