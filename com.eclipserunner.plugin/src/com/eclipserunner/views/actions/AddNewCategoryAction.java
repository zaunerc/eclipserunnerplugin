package com.eclipserunner.views.actions;

import static com.eclipserunner.Messages.Message_categoryDialogMessage;
import static com.eclipserunner.Messages.Message_categoryDialogTitle;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.model.impl.CategoryNode;
import com.eclipserunner.views.validators.CategoryNameValidator;

/**
 * Action creates new empty category in plugin model.
 * 
 * @author vachacz
 */
public class AddNewCategoryAction extends BaseRunnerAction {

	private IRunnerModel runnerModel;
	
	private static final String INITIAL_CATEGORY_NAME = "";

	public AddNewCategoryAction(IRunnerModel runnerModel) {
		this.runnerModel = runnerModel;
	}

	@Override
	public void run() {
		InputDialog dialog = openInputDialog(Message_categoryDialogTitle, Message_categoryDialogMessage, INITIAL_CATEGORY_NAME, new CategoryNameValidator());
		if (dialog.getReturnCode() == Window.OK) {
			ICategoryNode categoryNode = new CategoryNode(dialog.getValue());
			runnerModel.addCategoryNode(categoryNode);
		}
	}

}
