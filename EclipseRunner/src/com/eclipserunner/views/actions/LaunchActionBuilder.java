package com.eclipserunner.views.actions;

import static com.eclipserunner.Messages.Message_addNewCategory;
import static com.eclipserunner.Messages.Message_addNewCategoryTooltip;
import static com.eclipserunner.Messages.Message_bookmark;
import static com.eclipserunner.Messages.Message_bookmarkTooltip;
import static com.eclipserunner.Messages.Message_unbookmark;
import static com.eclipserunner.Messages.Message_unbookmarkTooltip;
import static com.eclipserunner.Messages.Message_collapseAll;
import static com.eclipserunner.Messages.Message_collapseAllTooltip;
import static com.eclipserunner.Messages.Message_openItem;
import static com.eclipserunner.Messages.Message_openItemTooltip;
import static com.eclipserunner.Messages.Message_createToggleDefaultCategory;
import static com.eclipserunner.Messages.Message_createToggleDefaultCategoryTooltip;
import static com.eclipserunner.Messages.Message_debugConfiguration;
import static com.eclipserunner.Messages.Message_debugConfigurationTooltip;
import static com.eclipserunner.Messages.Message_expandAll;
import static com.eclipserunner.Messages.Message_expandAllTooltip;
import static com.eclipserunner.Messages.Message_openDebugConfigurationsDialog;
import static com.eclipserunner.Messages.Message_openDebugConfigurationsDialogTooltip;
import static com.eclipserunner.Messages.Message_openRunConfigurationsDialog;
import static com.eclipserunner.Messages.Message_openRunConfigurationsDialogTooltip;
import static com.eclipserunner.Messages.Message_remove;
import static com.eclipserunner.Messages.Message_removeTooltip;
import static com.eclipserunner.Messages.Message_rename;
import static com.eclipserunner.Messages.Message_renameTooltip;
import static com.eclipserunner.Messages.Message_runConfiguration;
import static com.eclipserunner.Messages.Message_runConfigurationTooltip;
import static com.eclipserunner.Messages.Message_toggleBookmarkMode;
import static com.eclipserunner.Messages.Message_toggleBookmarkModeTooltip;
import static com.eclipserunner.Messages.Message_treeModeFlat;
import static com.eclipserunner.Messages.Message_treeModeFlatTooltip;
import static com.eclipserunner.Messages.Message_treeModeWithTypes;
import static com.eclipserunner.Messages.Message_treeModeWithTypesTooltip;
import static com.eclipserunner.Messages.Message_createToggleDefaultRunMode;
import static com.eclipserunner.Messages.Message_createToggleDefaultRunModeTooltip;
import static org.eclipse.debug.ui.IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP;
import static org.eclipse.debug.ui.IDebugUIConstants.ID_RUN_LAUNCH_GROUP;

import java.util.List;

import org.eclipse.debug.internal.ui.launchConfigurations.LaunchGroupExtension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.eclipserunner.PreferenceConstants;
import com.eclipserunner.RunnerPlugin;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeSelection;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.views.IRunnerView;
import com.eclipserunner.views.TreeMode;

/**
 * Builder creates Actions related to test launching
 *
 * @author vachacz
 */
@SuppressWarnings("restriction")
public final class LaunchActionBuilder {

	private INodeSelection nodeSelection;
	private IRunnerModel runnerModel;
	private IRunnerView runnerView;

	private LaunchActionBuilder() {
		// use factory method instead
	}

	public static final LaunchActionBuilder newInstance() {
		return new LaunchActionBuilder();
	}

	public LaunchActionBuilder withLaunchConfigurationSelection(INodeSelection nodeSelection) {
		this.nodeSelection = nodeSelection;
		return this;
	}

	public LaunchActionBuilder withRunnerModel(IRunnerModel model) {
		this.runnerModel = model;
		return this;
	}

	public LaunchActionBuilder withRunnerView(IRunnerView runnerView) {
		this.runnerView = runnerView;
		return this;
	}

	public Action createShowRunConfigurationDialogAction() {
		return decorate(new ShowLaunchConfigurationsDialogAction(nodeSelection, ID_RUN_LAUNCH_GROUP))
			.withTitle(Message_openRunConfigurationsDialog)
			.withTooltip(Message_openRunConfigurationsDialogTooltip)
			.withImage(Image.RUN_CONFIGURATIONS)
			.andReleaseAction();
	}

	public Action createShowDebugConfigurationDialogAction() {
		return decorate(new ShowLaunchConfigurationsDialogAction(nodeSelection, ID_DEBUG_LAUNCH_GROUP))
			.withTitle(Message_openDebugConfigurationsDialog)
			.withTooltip(Message_openDebugConfigurationsDialogTooltip)
			.withImage(Image.DEBUG_CONFIGURATIONS)
			.andReleaseAction();
	}

	public Action createShowLaunchOtherConfigurationDialogAction(LaunchGroupExtension launchGroupExtension, String title, String tooltip, ImageDescriptor image) {
		return decorate(new ShowLaunchOtherConfigurationsDialogAction(nodeSelection, launchGroupExtension))
			.withTitle(title)
			.withTooltip(tooltip)
			.withImageDescriptor(image)
			.andReleaseAction();
	}

	public Action createRunConfigurationAction() {
		return decorate(new LaunchConfigurationAction(nodeSelection, ID_RUN_LAUNCH_GROUP))
			.withTitle(Message_runConfiguration)
			.withTooltip(Message_runConfigurationTooltip)
			.withImage(Image.RUN)
			.andReleaseAction();
	}

	public Action createDefaultConfigurationAction() {
		return decorate(new DefaultLaunchConfigurationAction(nodeSelection, ID_RUN_LAUNCH_GROUP))
			.withTitle(Message_runConfiguration)
			.withTooltip(Message_runConfigurationTooltip)
			.withImage(Image.RUN)
			.andReleaseAction();
	}

	public Action createDebugConfigurationAction() {
		return decorate(new LaunchConfigurationAction(nodeSelection, ID_DEBUG_LAUNCH_GROUP))
			.withTitle(Message_debugConfiguration)
			.withTooltip(Message_debugConfigurationTooltip)
			.withImage(Image.DEBUG)
			.andReleaseAction();
	}

	public Action createLaunchOtherConfigurationAction(String mode, String title, String tooltip, ImageDescriptor image) {
		return decorate(new LaunchOtherConfigurationAction(nodeSelection, mode))
			.withTitle(title)
			.withTooltip(tooltip)
			.withImageDescriptor(image)
			.andReleaseAction();
	}

	public Action createOpenItemAction() {
		return decorate(new OpenItemAction(nodeSelection, ID_DEBUG_LAUNCH_GROUP))
			.withTitle(Message_openItem)
			.withTooltip(Message_openItemTooltip)
			.andReleaseAction();
	}

	public Action createAddNewCategoryAction() {
		return decorate(new AddNewCategoryAction(runnerModel))
			.withTitle(Message_addNewCategory)
			.withTooltip(Message_addNewCategoryTooltip)
			.withImage(Image.NEW_CATEGORY)
			.andReleaseAction();
	}

	public Action createCollapseAllAction(TreeViewer viewer) {
		return decorate(new CollapseAllAction(viewer))
			.withTitle(Message_collapseAll)
			.withTooltip(Message_collapseAllTooltip)
			.withSharedImage(ISharedImages.IMG_ELCL_COLLAPSEALL)
			.andReleaseAction();
	}

	public Action createExpandAllAction(TreeViewer viewer) {
		return decorate(new ExpandAllAction(viewer))
			.withTitle(Message_expandAll)
			.withTooltip(Message_expandAllTooltip)
			.withImage(Image.EXPAND_ALL)
			.andReleaseAction();
	}

	public Action createRenameAction() {
		return decorate(new RenameConfigOrCategoryAction(nodeSelection, runnerModel))
			.withTitle(Message_rename)
			.withTooltip(Message_renameTooltip)
			.andReleaseAction();
	}

	public Action createRemoveAction() {
		return decorate(new RemoveConfigOrCategoryAction(nodeSelection, runnerModel))
			.withTitle(Message_remove)
			.withTooltip(Message_removeTooltip)
			.withSharedImage(ISharedImages.IMG_ETOOL_DELETE)
			.andReleaseAction();
	}

	public Action createBookmarkAction() {
		return decorate(new BookmarkAction(nodeSelection, true))
			.withTitle(Message_bookmark)
			.withTooltip(Message_bookmarkTooltip)
			.withImage(Image.BOOKMARK)
			.andReleaseAction();
	}

	public Action createUnbookmarkAction() {
		return decorate(new BookmarkAction(nodeSelection, false))
			.withTitle(Message_unbookmark)
			.withTooltip(Message_unbookmarkTooltip)
			.withImage(Image.UNBOOKMARK)
			.andReleaseAction();
	}

	public Action createToggleFlatModeAction() {
		return decorate(new ToggleTreeModeAction(runnerView, TreeMode.FLAT_MODE))
			.withTitle(Message_treeModeFlat)
			.withTooltip(Message_treeModeFlatTooltip)
			.withImage(Image.FLAT_TREE)
			.andReleaseAction();
	}

	public Action createToggleTypeModeAction() {
		return decorate(new ToggleTreeModeAction(runnerView, TreeMode.HIERARCHICAL_MODE))
			.withTitle(Message_treeModeWithTypes)
			.withTooltip(Message_treeModeWithTypesTooltip)
			.withImage(Image.TYPE_TREE)
			.andReleaseAction();
	}

	public Action createToggleRunModeAction() {
		return decorate(new ToggleRunModeAction(PreferenceConstants.RUN_MODE))
			.withTitle(Message_createToggleDefaultRunMode)
			.withTooltip(Message_createToggleDefaultRunModeTooltip)
			.withImage(Image.DEBUG)
			.andReleaseAction();
	}

	public Action createToggleDefaultCategoryAction() {
		return decorate(new ToggleFilterAction(PreferenceConstants.FILTER_DEFAULT_CATEGORY, runnerView))
			.withTitle(Message_createToggleDefaultCategory)
			.withTooltip(Message_createToggleDefaultCategoryTooltip)
			.withImage(Image.DEFAULT_CATEGORY)
			.andReleaseAction();
	}

	public Action createToggleBookmarkModeAction() {
		return decorate(new ToggleFilterAction(PreferenceConstants.FILTER_BOOKMARKED, runnerView))
			.withTitle(Message_toggleBookmarkMode)
			.withTooltip(Message_toggleBookmarkModeTooltip)
			.withImage(Image.BOOKMARK)
			.andReleaseAction();
	}

	public Action createToggleClosedProjectFilterAction() {
		return decorate(new ToggleFilterAction(PreferenceConstants.FILTER_CLOSED_PROJECT, runnerView))
			.withTitle("Filter closed projects")
			.withTooltip("Filter closed projects")
			.andReleaseAction();
	}

	public Action createDelectedProjectFilterAction() {
		return decorate(new ToggleFilterAction(PreferenceConstants.FILTER_DELETED_PROJECT, runnerView))
			.withTitle("Filter deleted projects")
			.withTooltip("Filter deleted projects")
			.andReleaseAction();
	}

	public Action createActiveWorkingSetFilterAction() {
		return decorate(new ToggleFilterAction(PreferenceConstants.FILTER_ACTIVE_WORKING_SET, runnerView))
			.withTitle("Filter currect working set")
			.withTooltip("Filter currect working set")
			.andReleaseAction();
	}

	public Action createActiveProjektFilterAction() {
		return decorate(new ToggleFilterAction(PreferenceConstants.FILTER_ACTIVE_PROJECT, runnerView))
			.withTitle("Filter current project")
			.withTooltip("Filter current project")
			.andReleaseAction();
	}

	public IAction createMoveToCategoryAction(List<ILaunchNode> selectedLaunchNodes, ICategoryNode node) {
		return decorate(new MoveToCategoryAction(selectedLaunchNodes, node))
			.withTitle(node.getName())
			.withTooltip("Move selected run configuration to " + node.getName())
			.andReleaseAction();
	}

	private ActionDecorator decorate(Action action) {
		return new ActionDecorator(action);
	}

	/**
	 * Simple decorator class, that simplifies the way we customize an action.
	 *
	 * @author lwachowi
	 */
	private class ActionDecorator {
		Action action;
		public ActionDecorator(Action action) {
			this.action = action;
		}
		public ActionDecorator withTitle(String title) {
			action.setText(title);
			return this;
		}
		public ActionDecorator withTooltip(String tooltip) {
			action.setToolTipText(tooltip);
			return this;
		}
		public ActionDecorator withImage(Image image) {
			return withImageDescriptor(RunnerPlugin.getDefault().getImageDescriptor(image.getPath()));
		}
		public ActionDecorator withSharedImage(String sharedImage) {
			return withImageDescriptor(getSharedImage(sharedImage));
		}
		public Action andReleaseAction() {
			return action;
		}
		public ActionDecorator withImageDescriptor(ImageDescriptor imageDescriptor) {
			action.setImageDescriptor(imageDescriptor);
			return this;
		}
		private ImageDescriptor getSharedImage(String image) {
			return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(image);
		}
	}

}
