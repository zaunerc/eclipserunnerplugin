package com.eclipserunner.views.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationListener;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.internal.ui.launchConfigurations.LaunchGroupExtension;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;

import com.eclipserunner.Messages;
import com.eclipserunner.PreferenceConstants;
import com.eclipserunner.RunnerPlugin;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IFilteredRunnerModel;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.IModelChangeListener;
import com.eclipserunner.model.RunnerModelProvider;
import com.eclipserunner.model.adapters.RunnerModelJdtSelectionListenerAdapter;
import com.eclipserunner.model.adapters.RunnerModelLaunchConfigurationListenerAdapter;
import com.eclipserunner.ui.dnd.RunnerViewDragListener;
import com.eclipserunner.ui.dnd.RunnerViewDropListener;
import com.eclipserunner.views.IRunnerView;
import com.eclipserunner.views.TreeMode;
import com.eclipserunner.views.actions.LaunchActionBuilder;

/**
 * Class provides plugin eclipse View UI component.
 *
 * @author vachacz, bary
 */
@SuppressWarnings("restriction")
public class RunnerView extends ViewPart
	implements IMenuListener, IDoubleClickListener, IModelChangeListener, IRunnerView {

	private IFilteredRunnerModel runnerModel;

	private TreeViewer viewer;

	private ILaunchConfigurationListener modelLaunchConfigurationListener;
	private ILaunchConfigurationListener viewLaunchConfigurationListener;
	private ISelectionListener selectionListener;

	private IPropertyChangeListener propertyChangeListener;

	private Action showRunConfigurationsDialogAction;
	private Action showDebugConfigurationsDialogAction;
	private List<Action> showLaunchOtherConfigurationActions = new ArrayList<Action>();

	private Action launchRunConfigurationAction;
	private Action launchDefuaultConfigurationAction;
	private Action launchDebugConfigurationAction;
	private List<Action> launchOtherConfigurationActions = new ArrayList<Action>();
	private Action openItemAction;

	private Action addNewCategoryAction;

	private Action collapseAllAction;
	private Action expandAllAction;

	private Action bookmarkAction;
	private Action unbookmarkAction;

	private Action renameAction;
	private Action removeAction;

	private Action toggleFlatModeAction;
	private Action toggleTypeModeAction;
	private Action toggleDefaultCategoryAction;
	private Action toggleDefaultRunModeAction;
	private Action toggleBookmarkModeAction;

	private Action toggleClosedProjectFilterAction;
	private Action toggleDelectedProjectFilterAction;
	private Action toggleActiveWorkingSetFilterAction;
	private Action toggleActiveProjektFilterAction;

	// we are listening only from this selection providers
	private final String[] selectonProviders = new String[] {
		"org.eclipse.jdt.ui.PackageExplorer",
		"org.eclipse.ui.navigator.ProjectExplorer"
	};

	private LaunchActionBuilder builder;
	private RunnerViewSelection selection;

	@Override
	public void createPartControl(Composite parent) {
		initializeModel();
		initializeViewer(parent);

		selection = new RunnerViewSelection(getViewer());

		initializeSelectionListeners();
		initializeLaunchConfigurationListeners();
		initializeResourceChangeListener();
		initializeDragAndDrop();

		setupActionBuilder();
		setupLaunchActions();
		setupContextMenu();
		setupActionBars();

	}

	private void initializeModel() {
		runnerModel = RunnerModelProvider.getInstance().getFilteredModel();
		runnerModel.addModelChangeListener(this);
	}

	private void initializeViewer(Composite parent) {
		PatternFilter patternFilter = new PatternFilter();
		patternFilter.setIncludeLeadingWildcard(true);

		FilteredTree tree = new FilteredTree(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL, patternFilter, true);

		viewer = tree.getViewer();
		setupTreeContentProvider();

		viewer.setLabelProvider(new LaunchTreeLabelProvider(runnerModel));
		viewer.addDoubleClickListener(this);
		viewer.setInput(getViewSite());

		// we're cooperative and also provide our selection
		getSite().setSelectionProvider(viewer);
	}

	private void initializeSelectionListeners() {
		selectionListener = new RunnerModelJdtSelectionListenerAdapter(runnerModel, this);
		for (String partId : selectonProviders) {
			getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(partId, selectionListener);
		}
	}

	private void initializeLaunchConfigurationListeners() {
		viewLaunchConfigurationListener = new ILaunchConfigurationListener() {
			public void launchConfigurationRemoved(ILaunchConfiguration configuration) {
				refresh();
			}
			public void launchConfigurationChanged(ILaunchConfiguration configuration) {
				refresh();
			}
			public void launchConfigurationAdded(ILaunchConfiguration configuration) {
				refresh();
			}
		};

		modelLaunchConfigurationListener = new RunnerModelLaunchConfigurationListenerAdapter(runnerModel);

		getLaunchManager().addLaunchConfigurationListener(viewLaunchConfigurationListener);
		getLaunchManager().addLaunchConfigurationListener(modelLaunchConfigurationListener);
	}

	private void initializeResourceChangeListener() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {
			public void resourceChanged(IResourceChangeEvent event) {
				refresh();
			}
		}, IResourceChangeEvent.POST_CHANGE);

	}

	private void initializeDragAndDrop() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] {
			LocalSelectionTransfer.getTransfer(), FileTransfer.getInstance()
		};

		getViewer().addDragSupport(operations, transferTypes, new RunnerViewDragListener(getViewer()));
		getViewer().addDropSupport(operations, transferTypes, new RunnerViewDropListener(getViewer()));
	}

	@Override
	public void dispose() {
		super.dispose();

		disposeModel();
		disposeSelectionListeners();
		disposeLaunchConfigurationListeners();
		disposePropertyChangeListeners();
	}

	private void disposeModel() {
		runnerModel.removeModelChangeListener(this);
	}

	private void disposeSelectionListeners() {
		for (String partId : selectonProviders) {
			getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(partId, selectionListener);
		}
	}

	private void disposeLaunchConfigurationListeners() {
		getLaunchManager().removeLaunchConfigurationListener(modelLaunchConfigurationListener);
		getLaunchManager().removeLaunchConfigurationListener(viewLaunchConfigurationListener);
	}

	private void disposePropertyChangeListeners() {
		if (propertyChangeListener != null) {
			JavaPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(propertyChangeListener);
			propertyChangeListener = null;
		}
	}

	private void setupLaunchActions() {
		showRunConfigurationsDialogAction   = builder.createShowRunConfigurationDialogAction();
		showDebugConfigurationsDialogAction = builder.createShowDebugConfigurationDialogAction();
		launchRunConfigurationAction        = builder.createRunConfigurationAction();
		launchDefuaultConfigurationAction   = builder.createDefaultConfigurationAction();
		launchDebugConfigurationAction      = builder.createDebugConfigurationAction();

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] launchModes = reg.getConfigurationElementsFor("org.eclipse.debug.core.launchModes");
		IConfigurationElement[] launchGroups = reg.getConfigurationElementsFor("org.eclipse.debug.ui.launchGroups");

		for( int i = 0; i < launchModes.length; i++ ) {
			// FIXME: Ignore default runners, they all seem to start with &
			if(!launchModes[i].getAttribute("label").startsWith("&")) {
				String mode = launchModes[i].getAttribute("mode");
				LaunchGroupExtension launchGroupExtension = null;
				ImageDescriptor image = null;

				for( IConfigurationElement launchGroup : launchGroups ) {
					if( launchGroup.getAttribute("mode").equals(mode) ) {
						launchGroupExtension = new LaunchGroupExtension(launchGroup);
						image = launchGroupExtension.getImageDescriptor();
						break;
					}
				}

				launchOtherConfigurationActions.add(builder.createLaunchOtherConfigurationAction(mode,
						launchModes[i].getAttribute("label"), launchModes[i].getAttribute("label"), image));

				if( launchGroupExtension != null ) {
					String label = String.format("Open %1$s configurations ...", launchGroupExtension.getMode());
					showLaunchOtherConfigurationActions.add(builder.createShowLaunchOtherConfigurationDialogAction(launchGroupExtension,
							label, label, image));
				}
			}
		}

		openItemAction                      = builder.createOpenItemAction();
		addNewCategoryAction                = builder.createAddNewCategoryAction();
		collapseAllAction                   = builder.createCollapseAllAction(viewer);
		expandAllAction                     = builder.createExpandAllAction(viewer);
		bookmarkAction                      = builder.createBookmarkAction();
		unbookmarkAction                    = builder.createUnbookmarkAction();
		renameAction                        = builder.createRenameAction();
		removeAction                        = builder.createRemoveAction();
		toggleFlatModeAction                = builder.createToggleFlatModeAction();
		toggleTypeModeAction                = builder.createToggleTypeModeAction();
		toggleDefaultCategoryAction         = builder.createToggleDefaultCategoryAction();
		toggleDefaultRunModeAction			= builder.createToggleRunModeAction();
		toggleBookmarkModeAction            = builder.createToggleBookmarkModeAction();
		toggleClosedProjectFilterAction     = builder.createToggleClosedProjectFilterAction();
		toggleDelectedProjectFilterAction   = builder.createDelectedProjectFilterAction();
		toggleActiveWorkingSetFilterAction  = builder.createActiveWorkingSetFilterAction();
		toggleActiveProjektFilterAction     = builder.createActiveProjektFilterAction();
	}

	private void setupActionBuilder() {
		builder = LaunchActionBuilder.newInstance()
			.withLaunchConfigurationSelection(selection)
			.withRunnerModel(runnerModel)
			.withRunnerView(this);
	}

	private void setupContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);

		Menu menu = menuMgr.createContextMenu(getViewerControl());
		getViewerControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void setupActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		setupLocalPullDown(bars.getMenuManager());
		setupLocalToolBar(bars.getToolBarManager());
	}

	private void setupLocalPullDown(IMenuManager manager) {
		manager.add(addNewCategoryAction);
		manager.add(new Separator());
		manager.add(toggleFlatModeAction);
		manager.add(toggleTypeModeAction);
		manager.add(new Separator());
		manager.add(toggleClosedProjectFilterAction);
		manager.add(toggleDelectedProjectFilterAction);
		manager.add(toggleActiveWorkingSetFilterAction);
		manager.add(toggleActiveProjektFilterAction);
		manager.add(new Separator());
		manager.add(showRunConfigurationsDialogAction);
		manager.add(showDebugConfigurationsDialogAction);
		for(Action showLaunchOtherConfigurationAction : showLaunchOtherConfigurationActions) {
			manager.add(showLaunchOtherConfigurationAction);
		}
	}

	private void setupLocalToolBar(IToolBarManager manager) {
		manager.add(toggleBookmarkModeAction);
		manager.add(toggleDefaultCategoryAction);
		manager.add(toggleDefaultRunModeAction);
		manager.add(new Separator());
		manager.add(addNewCategoryAction);
		manager.add(new Separator());
		manager.add(collapseAllAction);
		manager.add(expandAllAction);
	}

	public void menuAboutToShow(IMenuManager manager) {
		setupMenuItems(manager);
		setupActionEnablement();
	}

	private void setupMenuItems(IMenuManager manager) {
		if (selection.firstNodeHasType(ILaunchNode.class)) {
			manager.add(launchRunConfigurationAction);
			manager.add(launchDebugConfigurationAction);
			for(Action otherLaunchAction : launchOtherConfigurationActions) {
				manager.add(otherLaunchAction);
			}
			manager.add(openItemAction);
			manager.add(new Separator());
	        manager.add(moveToCategorySubMenu());
	        manager.add(new Separator());
		}
		manager.add(addNewCategoryAction);
		manager.add(new Separator());
		manager.add(renameAction);
		manager.add(removeAction);
		manager.add(new Separator());
		manager.add(bookmarkAction);
		manager.add(unbookmarkAction);
		manager.add(new Separator());
		manager.add(showRunConfigurationsDialogAction);
		manager.add(showDebugConfigurationsDialogAction);
		for(Action showLaunchOtherConfigurationAction : showLaunchOtherConfigurationActions) {
			manager.add(showLaunchOtherConfigurationAction);
		}
	}

	private MenuManager moveToCategorySubMenu() {
		MenuManager managet = new MenuManager(Messages.Message_moveToSubMenu, null);
		Collection<ICategoryNode> categories =
			RunnerModelProvider.getInstance().getDefaultModel().getCategoryNodes();
		for (ICategoryNode category : categories) {
			managet.add(moveToCategoryAction(category));
		}
		return managet;
	}

	private IAction moveToCategoryAction(ICategoryNode category) {
		return builder.createMoveToCategoryAction(selection.getSelectedNodesByType(ILaunchNode.class), category);
	}

	private void setupActionEnablement() {
		launchRunConfigurationAction.setEnabled(selection.canBeLaunched());
		launchDebugConfigurationAction.setEnabled(selection.canBeLaunched());
		for(Action otherLaunchAction : launchOtherConfigurationActions) {
			otherLaunchAction.setEnabled(selection.canBeLaunched());
		}
		renameAction.setEnabled(selection.canBeRenamed());
		removeAction.setEnabled(selection.canBeRemoved());
		bookmarkAction.setEnabled(selection.canBeBookmarked());
		unbookmarkAction.setEnabled(selection.canBeBookmarked());
		openItemAction.setEnabled(selection.canBeOpened());
	}

	public void doubleClick(DoubleClickEvent event) {
		launchDefuaultConfigurationAction.run();
	}

	public boolean shouldRunInDebugMode() {
		return RunnerPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.RUN_MODE);
	}

	public void modelChanged() {
		refresh();
	}

	@Override
	public void setFocus() {
		getViewerControl().setFocus();
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	public ITreeContentProvider getTreeContentProvider() {
		return (ITreeContentProvider) getViewer().getContentProvider();
	}

	private Control getViewerControl() {
		return getViewer().getControl();
	}

	private ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}

	public void setTreeMode(TreeMode mode) {
		setupTreeModeActions(mode);
		setupTreeContentProvider();
		refresh();
	}

	protected void setupTreeModeActions(TreeMode mode) {
		if (mode.isHierarchical()) {
			toggleFlatModeAction.setChecked(false);
			toggleTypeModeAction.setChecked(true);
		} else {
			toggleFlatModeAction.setChecked(true);
			toggleTypeModeAction.setChecked(false);
		}
	}

	protected void setupTreeContentProvider() {
		viewer.setContentProvider(
			RunnerModelProvider.getInstance().getTreeContentProvider()
		);
	}

	public void refresh() {
		RunnerPlugin.getDisplay().syncExec(new Runnable() {
			public void run() {
				getViewer().refresh();
			}
		});
	}

	void setTreeViewerForTesting(TreeViewer viewer) {
		this.viewer = viewer;
	}

}