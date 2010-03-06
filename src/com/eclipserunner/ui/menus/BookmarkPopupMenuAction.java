package com.eclipserunner.ui.menus;

import static com.eclipserunner.utils.SelectionUtils.selectionAsList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.actions.LaunchShortcutAction;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchShortcutExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

/**
 * TODO LWA: under development
 * 
 * @author vachacz
 */
@SuppressWarnings("restriction")
public class BookmarkPopupMenuAction implements IObjectActionDelegate, IMenuCreator {

	private IStructuredSelection selection;
	private boolean rebuildMenu = true;
	
	private IAction delegateAction;

	public Menu getMenu(Menu parent) {
		Menu menu = new Menu(parent);
		
		menu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuShown(MenuEvent event) {
				if (rebuildMenu) {
					Menu menu = (Menu) event.widget;
					disposeMenuItems(menu);
					if (selection != null) {
						fillMenu(menu);
					}
					rebuildMenu = false;
				}
			}
		});
		return menu;
	}

	public void selectionChanged(IAction action, ISelection iSelection) {
		if (iSelection instanceof IStructuredSelection) {
			selection = (IStructuredSelection) iSelection;
			rebuildMenu = true;
			if (delegateAction != action) {
				delegateAction = action;
				delegateAction.setMenuCreator(this);
			}
			action.setEnabled(true);
		} else {
			action.setEnabled(false);
		}
	}

	@SuppressWarnings("unchecked")
	protected void fillMenu(Menu menu) {
		List<LaunchShortcutExtension> applicableShortcuts = getApplicableShortcuts();
		
		Iterator<LaunchShortcutExtension> iterator = applicableShortcuts.iterator();
		while (iterator.hasNext()) {
			LaunchShortcutExtension extension = iterator.next();
			
			Iterator modeIter = extension.getModes().iterator();
			while (modeIter.hasNext()) {
				String mode = (String) modeIter.next();
				if (ILaunchManager.RUN_MODE.equals(mode)) {
					populateMenuItem(mode, extension, menu);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<LaunchShortcutExtension> getApplicableShortcuts() {
		IEvaluationContext context = createContext();
		
		List<LaunchShortcutExtension> applicableShortcuts = new ArrayList<LaunchShortcutExtension>();
		Iterator iterator = getLaunchShortcuts().iterator();
		while (iterator.hasNext()) {
			LaunchShortcutExtension launchShortcut = (LaunchShortcutExtension) iterator.next();
			if (isApplicable(launchShortcut, context)) {
				applicableShortcuts.add(launchShortcut);
			}
		}
		return applicableShortcuts;
	}

	private IEvaluationContext createContext() {
		IEvaluationContext context = new EvaluationContext(null, selectionAsList(selection));
		context.setAllowPluginActivation(true);
		context.addVariable("selection", selection);
		return context;
	}
	
	private boolean isApplicable(LaunchShortcutExtension launchShortcut, IEvaluationContext context) {
		try {
			Expression expr = launchShortcut.getContextualLaunchEnablementExpression();
			return !WorkbenchActivityHelper.filterItem(launchShortcut) 
				&& launchShortcut.evalEnablementExpression(context, expr);
		} catch (CoreException e) {
			return false;
		}
	}

	private void populateMenuItem(String mode, LaunchShortcutExtension launchShortcut, Menu menu) {
		Action action = new LaunchShortcutAction(mode, launchShortcut);

		action.setActionDefinitionId(launchShortcut.getId() + "." + mode);
		
		// replace default action label with context label if specified.
		String contextLabel = launchShortcut.getContextLabel(mode);
		action.setText(contextLabel != null ? contextLabel : action.getText());
		
		new ActionContributionItem(action).fill(menu, -1);
	}
	
	public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
		// not needed
	}
	
	public void run(IAction action) {
		// action provides sub menu and does not handle any action
	}
	
	public Menu getMenu(Control control) {
		// never called
		return null;
	}

	public void dispose() {
		// nothing to do
	}
	
	@SuppressWarnings("unchecked")
	private List getLaunchShortcuts() {
		return DebugUIPlugin.getDefault().getLaunchConfigurationManager().getLaunchShortcuts();
	}
	
	private void disposeMenuItems(Menu menu) {
		MenuItem[] menuItems = menu.getItems();
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i].dispose();
		}
	}
}