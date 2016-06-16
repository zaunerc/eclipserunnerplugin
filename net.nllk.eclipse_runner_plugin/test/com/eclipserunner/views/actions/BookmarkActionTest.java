package com.eclipserunner.views.actions;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchTypeNode;
import com.eclipserunner.model.INodeSelection;

/**
 * BookmarkAction tests.
 * 
 * @author lwachowi
 */
public class BookmarkActionTest {

	@Mock INodeSelection selection;
	
	@Mock ILaunchNode     launchNode;
	@Mock ILaunchTypeNode launchTypeNode;
	@Mock ICategoryNode   categoryNode;
	
	private BookmarkAction action;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		action = new BookmarkAction(selection, false);
	}
	
	@Test
	public void shouldNotBookmarkWhenSelectionHasMultipleElements() throws Exception {
		when(selection.allNodesHaveSameType()).thenReturn(false);
		
		action.run();
	}
	
	@Test
	public void shouldBookmarkLaunchNode() throws Exception {
		when(selection.allNodesHaveSameType()).thenReturn(true);
		
		when(selection.firstNodeHasType(ILaunchNode.class)).thenReturn(true);
		when(selection.firstNodeHasType(ILaunchTypeNode.class)).thenReturn(false);
		when(selection.firstNodeHasType(ICategoryNode.class)).thenReturn(false);
		
		when(selection.getSelectedNodesByType(ILaunchNode.class)).thenReturn(Arrays.asList(launchNode));
		
		action.run();
		
		verify(launchNode).setBookmarked(false);
	}

	@Test
	public void shouldBookmarkLaunchTypeNode() throws Exception {
		when(selection.allNodesHaveSameType()).thenReturn(true);

		when(selection.firstNodeHasType(ILaunchNode.class)).thenReturn(false);
		when(selection.firstNodeHasType(ILaunchTypeNode.class)).thenReturn(true);
		when(selection.firstNodeHasType(ICategoryNode.class)).thenReturn(false);
		
		when(selection.getSelectedNodesByType(ILaunchTypeNode.class)).thenReturn(Arrays.asList(launchTypeNode));
		
		action.run();
		
		verify(launchTypeNode).setBookmarked(false);
	}

	@Test
	public void shouldBookmarkCategoryNode() throws Exception {
		when(selection.allNodesHaveSameType()).thenReturn(true);

		when(selection.firstNodeHasType(ILaunchNode.class)).thenReturn(false);
		when(selection.firstNodeHasType(ILaunchTypeNode.class)).thenReturn(false);
		when(selection.firstNodeHasType(ICategoryNode.class)).thenReturn(true);
		
		when(selection.getSelectedNodesByType(ICategoryNode.class)).thenReturn(Arrays.asList(categoryNode));
		
		action.run();
		
		verify(categoryNode).setBookmarked(false);
	}

}
