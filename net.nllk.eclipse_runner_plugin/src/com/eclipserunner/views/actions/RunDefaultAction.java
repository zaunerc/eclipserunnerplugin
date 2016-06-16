package com.eclipserunner.views.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

/**
 * @author scharf
 */
public class RunDefaultAction extends BaseRunnerAction implements IMenuCreator  {

	private Menu subMenu;
	private MenuManager menuManager;
	private IAction defaultAction;
	private IAction originalState;
	public RunDefaultAction() {
		super("subMenu", AS_DROP_DOWN_MENU);
		menuManager = new MenuManager();
		setMenuCreator(this);
	}

	@Override
	public void run() {
		if(defaultAction!=null) {
			defaultAction.run();
		}
	}

	public void dispose() {
		if(menuManager!=null) {
			menuManager.dispose();
			menuManager = null;
		}
	}
	public IMenuManager getMenuManager() {
		return menuManager;
	}
	public Menu getMenu(Control parent) {
		if(subMenu==null) {
			subMenu = menuManager.createContextMenu(parent);
		}
		return subMenu;
	}

	public Menu getMenu(Menu parent) {
		// should never be called...
		return null;
	}

	public void update() {
		// remember the 'original' state of this action
		if (originalState == null) {
			// to make life simple, we create an action with our state....
			originalState = new Action() {
			};
			// and copy our state
			copyState(this, originalState);
		}
		// we search the action that is checked for the current selection
		defaultAction = getCurrentlyCheckedAction();
		setEnabled(defaultAction != null);
		if (defaultAction != null) {
			// copy the state of the default action
			copyState(defaultAction, this);
		} else {
			// restore the original state as the action was created
			copyState(originalState, this);

		}
	}

	private IAction getCurrentlyCheckedAction() {
		for (IContributionItem item : menuManager.getItems()) {
			if(item instanceof ActionContributionItem) {
				IAction action= ((ActionContributionItem) item).getAction();
				if(action.isChecked()) {
					return action;
				}
			}
		}
		return null;
	}

	private void copyState(IAction from, IAction to) {
		to.setImageDescriptor(from.getImageDescriptor());
		to.setText(from.getText());
		to.setToolTipText(from.getToolTipText());
	}

}
