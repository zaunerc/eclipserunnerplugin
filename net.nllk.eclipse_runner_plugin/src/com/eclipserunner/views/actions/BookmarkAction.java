package com.eclipserunner.views.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.eclipserunner.model.IBookmarkable;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchTypeNode;
import com.eclipserunner.model.INodeSelection;

/**
 * Bookmark launch configuration.
 *
 * @author bary, vachacz
 */
public class BookmarkAction extends Action {

	private boolean state;
	private INodeSelection selection;

	public BookmarkAction(INodeSelection launchConfigurationSelection, boolean state) {
		this.selection = launchConfigurationSelection;
		this.state = state;
	}

	@Override
	public void run() {
		if (selection.allNodesHaveSameType()) {
			if (selection.firstNodeHasType(ILaunchNode.class)) {
				updateBookmark(selection.getSelectedNodesByType(ILaunchNode.class));
			}
			else if (selection.firstNodeHasType(ILaunchTypeNode.class)) {
				updateBookmark(selection.getSelectedNodesByType(ILaunchTypeNode.class));
			}
			else if (selection.firstNodeHasType(ICategoryNode.class)) {
				updateBookmark(selection.getSelectedNodesByType(ICategoryNode.class));
			}
		}
	}

	private <T extends IBookmarkable> void updateBookmark(List<T> bookmarkables) {
		for (IBookmarkable bookmarkable : bookmarkables) {
			bookmarkable.setBookmarked(state);
		}
	}

}
