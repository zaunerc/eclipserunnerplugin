package com.eclipserunner.model.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.IRunnerModel;

/**
 * @author vachacz
 */
public class DefaultCategoryFilterTest {

	private static final String PROPERTY_NAME = "test";

	@Mock IRunnerModel runnerModel;

	@Mock IPreferenceStore enabledPreferenceStoreMock;

	@Mock ILaunchNode launchNodeMock;

	@Mock ICategoryNode defaultCategoryNodeMock;
	@Mock ICategoryNode otherCategoryNodeMock;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

		when(enabledPreferenceStoreMock.getBoolean(anyString())).thenReturn(true);

		when(runnerModel.getDefaultCategoryNode()).thenReturn(defaultCategoryNodeMock);
	}

	@Test
	public void testFilterLaunchNode() throws Exception {
		DefaultCategoryFilter filter = new DefaultCategoryFilter(PROPERTY_NAME, runnerModel, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(launchNodeMock);

		assertFalse(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
	}

	@Test
	public void testFilterDefaultCategoryNode() throws Exception {
		DefaultCategoryFilter filter = new DefaultCategoryFilter(PROPERTY_NAME, runnerModel, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(defaultCategoryNodeMock);

		assertTrue(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
	}

	@Test
	public void testFilterCustomCategoryNode() throws Exception {
		DefaultCategoryFilter filter = new DefaultCategoryFilter(PROPERTY_NAME, runnerModel, enabledPreferenceStoreMock);

		boolean filtered = filter.filter(otherCategoryNodeMock);

		assertFalse(filtered);

		verify(enabledPreferenceStoreMock).getBoolean(PROPERTY_NAME);
	}
}
