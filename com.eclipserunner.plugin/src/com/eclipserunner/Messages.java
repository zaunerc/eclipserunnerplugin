package com.eclipserunner;

import org.eclipse.osgi.util.NLS;

/**
 * Eclipse runner property constants.
 *
 * @author vachacz
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "com.eclipserunner.messages";

	static {
		reloadMessages();
	}

	public static void reloadMessages() {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String Message_runConfiguration;
	public static String Message_runConfigurationTooltip;

	public static String Message_debugConfiguration;
	public static String Message_debugConfigurationTooltip;
	public static String Message_openItem;
	public static String Message_openItemTooltip;

	public static String Message_addNewCategory;
	public static String Message_addNewCategoryTooltip;

	public static String Message_uncategorized;

	public static String Message_categoryDialogTitle;
	public static String Message_categoryDialogMessage;

	public static String Message_collapseAll;
	public static String Message_collapseAllTooltip;

	public static String Message_expandAll;
	public static String Message_expandAllTooltip;

	public static String Message_rename;
	public static String Message_renameTooltip;
	public static String Message_renameLaunchConfiguration;
	public static String Message_renameCategory;

	public static String Message_remove;
	public static String Message_removeTooltip;
	public static String Message_removeConfirmTitle;
	public static String Message_removeConfirmPrompt;

	public static String Message_bookmark;
	public static String Message_bookmarkTooltip;
	public static String Message_unbookmark;
	public static String Message_unbookmarkTooltip;

	public static String Message_toggleBookmarkMode;
	public static String Message_toggleBookmarkModeTooltip;

	public static String Message_error;
	public static String Message_errorCatogoryEmptyName;
	public static String Message_errorCategoryAlreadyExists;
	public static String Message_errorLaunchConfigurationEmptyName;
	public static String Message_errorLaunchConfigurationAlreadyExists;

	public static String Message_treeModeFlat;
	public static String Message_treeModeFlatTooltip;
	public static String Message_treeModeWithTypes;
	public static String Message_treeModeWithTypesTooltip;

	public static String Message_createRunDefaultAction;
	public static String Message_createRunDefaultActionTooltip;

	public static String Message_createToggleDefaultCategory;
	public static String Message_createToggleDefaultCategoryTooltip;
	
	public static String Message_moveToSubMenu;

}
