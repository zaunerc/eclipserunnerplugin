package com.eclipserunner.model.adapters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IRunnerModel;

public class RunnerModelTreeAdapterTest extends BaseAdapterTest {

	@Mock IViewPart viewPart;
	@Mock IViewSite viewSite;
	@Mock IRunnerModel model;

	@Mock ICategoryNode defaultCategory;
	@Mock ILaunchConfiguration defaultLaunch;

	@Mock ICategoryNode category1;
	@Mock ILaunchConfiguration cat1launch1;
	@Mock ILaunchConfiguration cat1launch2;
	@Mock ILaunchConfiguration cat1launch3;

	@Mock ICategoryNode category2;
	@Mock ILaunchConfiguration cat2launch1;

	@Mock ICategoryNode category3;

	@Before
	public void setupModel() throws CoreException {
		MockitoAnnotations.initMocks(this);

		List<ICategoryNode> categories =
			Arrays.asList(defaultCategory, category1, category2, category3);

		when(viewPart.getViewSite()).thenReturn(viewSite);

		when(model.getDefaultCategoryNode()).thenReturn(defaultCategory);
		when(model.getCategoryNodes()).thenReturn(categories);

		setupLaunchConfigurationMock(defaultLaunch, "c");
		setupLaunchConfigurationMock(cat1launch1, "a");
		setupLaunchConfigurationMock(cat1launch2, "g");
		setupLaunchConfigurationMock(cat1launch3, "w");
		setupLaunchConfigurationMock(cat2launch1, "e");

		setupCategoryNodeMock(defaultCategory, defaultLaunch);
		setupCategoryNodeMock(category1, cat1launch1, cat1launch2, cat1launch3);
		setupCategoryNodeMock(category2, cat2launch1);
		setupCategoryNodeMock(category3);
	}

	// getElements tests

	@Test
	public void testGetElementsRoot() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		Object[] elements = adapter.getElements(viewSite);

		assertArrayEquals(elements, new Object[] {defaultCategory, category1, category2, category3});
	}

	@Test
	public void testGetElementsCategory() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		Object[] elements = adapter.getElements(category1);

		assertEqualsLaunchConfigurationNodeArray(elements, cat1launch1, cat1launch2, cat1launch3);
	}

	@Test
	public void testGetElementsLuanchNode() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object[] elements = adapter.getElements(cat1launch2);

		Assert.assertNull(elements);
	}

	// hasChildren tests

	@Test
	public void testHasChildrenCategory() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		boolean hasChildren = adapter.hasChildren(category1);

		Assert.assertTrue(hasChildren);
	}

	@Test
	public void testHasChildrenCategoryWithoutChildren() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		boolean hasChildren = adapter.hasChildren(category3);

		Assert.assertFalse(hasChildren);
	}

	@Test
	public void testHasChildrenLaunchConfiguration() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		boolean hasChildren = adapter.hasChildren(cat1launch2);

		Assert.assertFalse(hasChildren);
	}

	// getParent tests

	@Test
	public void testGetParentLaunchConfiguration() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		Object parent = adapter.getParent(setupLaunchNodeMock(cat1launch2, category1));

		assertEquals(parent, category1);
	}

	@Test
	public void testGetParentCategory() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		Object parent = adapter.getParent(category1);

		Assert.assertNull(parent);
	}

	// getChildren tests

	@Test
	public void testGetChildrenLaunchConfiguration() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		Object children[] = adapter.getChildren(setupLaunchNodeMock(cat1launch2, category1));

		Assert.assertNull(children);
	}

	@Test
	public void testGetChildrenCategory() throws Exception {
		RunnerModelTreeAdapter adapter = new RunnerModelTreeAdapter(model);

		Object children[] = adapter.getChildren(category2);

		assertEqualsLaunchConfigurationNodeArray(children, cat2launch1);
	}
}
