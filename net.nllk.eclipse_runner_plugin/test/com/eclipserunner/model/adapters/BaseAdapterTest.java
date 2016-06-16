package com.eclipserunner.model.adapters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.junit.Assert;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;

public class BaseAdapterTest {

	// mock initialization

	protected void setupLaunchConfigurationMock(ILaunchConfiguration config, String name) throws CoreException {
		when(config.getName()).thenReturn(name);
	}

	protected void setupLaunchConfigurationMock(ILaunchConfiguration config, ILaunchConfigurationType type, String name) throws CoreException {
		when(config.getType()).thenReturn(type);
		setupLaunchConfigurationMock(config, name);
	}

	protected void setupCategoryNodeMock(ICategoryNode category, ILaunchConfiguration ...launches) {
		Collection<ILaunchNode> launchNodes = setupLaunchNodeMocks(category, launches);
		when(category.getLaunchNodes()).thenReturn(launchNodes);
	}

	protected Collection<ILaunchNode> setupLaunchNodeMocks(ICategoryNode category, ILaunchConfiguration ...configurations) {
		List<ILaunchNode> nodes = new ArrayList<ILaunchNode>(configurations.length);
		for (ILaunchConfiguration configuration : configurations) {
			nodes.add(setupLaunchNodeMock(configuration, category));
		}
		return nodes;
	}

	protected ILaunchNode setupLaunchNodeMock(ILaunchConfiguration configuration, ICategoryNode category) {
		ILaunchNode node = mock(ILaunchNode.class);
		when(node.getLaunchConfiguration()).thenReturn(configuration);
		when(node.getCategoryNode()).thenReturn(category);
		return node;
	}

	// custom asserts

	protected void assertEqualsLaunchConfigurationNodeArray(Object[] elements, ILaunchConfiguration ...configs) {
		if (elements.length != configs.length) {
			Assert.fail();
		}
		for (int i = 0; i < elements.length; i++) {
			ILaunchNode node = (ILaunchNode) elements[i];
			if (!node.getLaunchConfiguration().equals(configs[i])) {
				Assert.fail();
			}
		}
	}

}
