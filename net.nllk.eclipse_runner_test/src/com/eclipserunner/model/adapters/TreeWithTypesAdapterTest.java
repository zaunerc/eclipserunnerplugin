package com.eclipserunner.model.adapters;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IRunnerModel;
import com.eclipserunner.model.impl.LaunchTypeNode;

/**
 * @author vachacz
 */
public class TreeWithTypesAdapterTest extends BaseAdapterTest {

	@Mock IViewPart viewPart;
	@Mock IViewSite viewSite;
	@Mock IRunnerModel model;

	@Mock ILaunchConfigurationType type1;
	@Mock ILaunchConfigurationType type2;

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

		setupLaunchConfigurationMock(defaultLaunch, type1, "c");
		setupLaunchConfigurationMock(cat1launch1, type1, "a");
		setupLaunchConfigurationMock(cat1launch2, type2, "g");
		setupLaunchConfigurationMock(cat1launch3, type2, "w");
		setupLaunchConfigurationMock(cat2launch1, type1, "e");

		setupCategoryNodeMock(defaultCategory, defaultLaunch);
		setupCategoryNodeMock(category1, cat1launch1, cat1launch2, cat1launch3);
		setupCategoryNodeMock(category2, cat2launch1);
		setupCategoryNodeMock(category3);
	}

	// getElements tests

	@Test
	public void testGetElementsRoot() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object[] elements = adapter.getElements(viewSite);

		assertArrayEquals(elements, new Object[] {defaultCategory, category1, category2, category3});
	}

	@Test
	public void testGetElementsCategory() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object[] elements = adapter.getElements(category1);

		assertEqualsTypeNodeArrays(elements, asTypeNodeArray(category1, type1, type2));
	}

	@Test
	public void testGetElementsConfigurationTypeNode() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object[] elements = adapter.getElements(asLaunchConfigurationTypeNode(type2, category1));

		assertEqualsLaunchConfigurationNodeArray(elements, cat1launch2, cat1launch3);
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
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		boolean hasChildren = adapter.hasChildren(category1);

		Assert.assertTrue(hasChildren);
	}

	@Test
	public void testHasChildrenCategoryWithoutChildren() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		boolean hasChildren = adapter.hasChildren(category3);

		Assert.assertFalse(hasChildren);
	}

	@Test
	public void testHasChildrenLaunchConfiguration() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		boolean hasChildren = adapter.hasChildren(cat1launch2);

		Assert.assertFalse(hasChildren);
	}

	@Test
	public void testHasChildrenLaunchConfigurationTypeNode() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		boolean hasChildren = adapter.hasChildren(asLaunchConfigurationTypeNode(type2, category1));

		Assert.assertTrue(hasChildren);
	}

	// getParent tests

	@Test
	public void testGetParentLaunchConfiguration() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object parent = adapter.getParent(setupLaunchNodeMock(cat1launch2, category1));

		assertConfigurationTypeNode((LaunchTypeNode) parent, asLaunchConfigurationTypeNode(type2, category1));
	}

	@Test
	public void testGetParentTypeNode() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object parent = adapter.getParent(asLaunchConfigurationTypeNode(type2, category1));

		Assert.assertEquals(parent, category1);
	}

	@Test
	public void testGetParentCategory() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object parent = adapter.getParent(category1);

		Assert.assertNull(parent);
	}

	// getChildren tests

	@Test
	public void testGetChildrenLaunchConfiguration() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object children[] = adapter.getChildren(setupLaunchNodeMock(cat1launch2, category1));

		Assert.assertNull(children);
	}

	@Test
	public void testGetChildrenConfigurationTypeNode() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object children[] = adapter.getChildren(asLaunchConfigurationTypeNode(type2, category1));

		assertEqualsLaunchConfigurationNodeArray(children, cat1launch2, cat1launch3);
	}

	@Test
	public void testGetChildrenCategory() throws Exception {
		RunnerModelTreeWithTypesAdapter adapter = new RunnerModelTreeWithTypesAdapter(model);

		Object children[] = adapter.getChildren(category2);

		assertEqualsTypeNodeArrays(children, asTypeNodeArray(category1, type1));
	}

	// custom asserts

	protected void assertEqualsTypeNodeArrays(Object[] elements, Object[] typeNodes) {
		if (elements.length != typeNodes.length) {
			Assert.fail();
		}
		for (int i = 0; i < elements.length; i++) {
			LaunchTypeNode node1 = (LaunchTypeNode) elements[i];
			LaunchTypeNode node2 = (LaunchTypeNode) typeNodes[i];

			if (!node1.getLaunchConfigurationType().equals(node2.getLaunchConfigurationType())) {
				Assert.fail();
			}
		}
	}

	protected void assertConfigurationTypeNode(LaunchTypeNode parent, LaunchTypeNode typeNode) {
		Assert.assertEquals(parent.getLaunchConfigurationType(), typeNode.getLaunchConfigurationType());
		Assert.assertEquals(parent.getCategoryNode(), typeNode.getCategoryNode());
	}

	protected Object[] asTypeNodeArray(ICategoryNode category, ILaunchConfigurationType ...types) {
		List<LaunchTypeNode> typeNode = new ArrayList<LaunchTypeNode>();
		for (ILaunchConfigurationType type : types) {
			typeNode.add(asLaunchConfigurationTypeNode(type, category));
		}
		return typeNode.toArray();
	}

	protected LaunchTypeNode asLaunchConfigurationTypeNode(ILaunchConfigurationType type, ICategoryNode category) {
		LaunchTypeNode node = mock(LaunchTypeNode.class);
		when(node.getLaunchConfigurationType()).thenReturn(type);
		when(node.getCategoryNode()).thenReturn(category);
		return node;
	}

}
