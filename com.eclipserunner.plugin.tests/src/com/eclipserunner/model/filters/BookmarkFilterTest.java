package com.eclipserunner.model.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;

/**
 * @author vachacz
 */
public class BookmarkFilterTest {

	private static final String PROPERTY_NAME = "test";

	@Mock IPreferenceStore enabledPreferenceStoreMock;
	@Mock IPreferenceStore disabledPreferenceStoreMock;

	@Mock ILaunchNode bookmarkedLaunchNodeMock;
	@Mock ILaunchNode notBookmarkedLaunchNodeMock;

	@Mock ICategoryNode bookmarkedCategoryNodeMock;
	@Mock ICategoryNode notBookmarkedCategoryNodeMock;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

		when(enabledPreferenceStoreMock.getBoolean(anyString())).thenReturn(true);
		when(disabledPreferenceStoreMock.getBoolean(anyString())).thenReturn(false);

		when(bookmarkedLaunchNodeMock.isBookmarked()).thenReturn(true);
		when(notBookmarkedLaunchNodeMock.isBookmarked()).thenReturn(false);

		when(bookmarkedCategoryNodeMock.getLaunchNodes()).thenReturn(Arrays.asList(bookmarkedLaunchNodeMock));
		when(notBookmarkedCategoryNodeMock.getLaunchNodes()).thenReturn(Arrays.asList(notBookmarkedLaunchNodeMock));
	}

	@Test
	public void testFilterBookmarkedLaunchNodeWhenEnabled() throws Exception {
		BookmarkFilter filter = new BookmarkFilter(PROPERTY_NAME, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(bookmarkedLaunchNodeMock);

		assertFalse(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
		verify(bookmarkedLaunchNodeMock).isBookmarked();
	}

	@Test
	public void testFilterBookmarkedCategoryNodeWhenEnabled() throws Exception {
		BookmarkFilter filter = new BookmarkFilter(PROPERTY_NAME, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(bookmarkedCategoryNodeMock);

		assertFalse(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
		verify(bookmarkedCategoryNodeMock).getLaunchNodes();
		verify(bookmarkedLaunchNodeMock).isBookmarked();
	}

	@Test
	public void testFilterNotBookmarkedCategoryNodeWhenEnabled() throws Exception {
		BookmarkFilter filter = new BookmarkFilter(PROPERTY_NAME, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(notBookmarkedLaunchNodeMock);

		assertTrue(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
		verify(notBookmarkedLaunchNodeMock).isBookmarked();
	}

	@Test
	public void testFilterNotBookmarkedLaunchNodeWhenEnabled() throws Exception {
		BookmarkFilter filter = new BookmarkFilter(PROPERTY_NAME, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(notBookmarkedCategoryNodeMock);

		assertTrue(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
		verify(notBookmarkedCategoryNodeMock).getLaunchNodes();
		verify(notBookmarkedLaunchNodeMock).isBookmarked();
	}

	@Test
	public void testFilterCategoryNodeWhenDisabled() throws Exception {
		BookmarkFilter filter = new BookmarkFilter(PROPERTY_NAME, disabledPreferenceStoreMock);
		boolean filtered = filter.filter(bookmarkedCategoryNodeMock);

		assertFalse(filtered);

		verify(disabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
	}

	@Test
	public void testFilterLaunchNodeWhenDisabled() throws Exception {
		BookmarkFilter filter = new BookmarkFilter(PROPERTY_NAME, disabledPreferenceStoreMock);

		boolean filtered = filter.filter(bookmarkedLaunchNodeMock);

		assertFalse(filtered);

		verify(disabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
	}

}
