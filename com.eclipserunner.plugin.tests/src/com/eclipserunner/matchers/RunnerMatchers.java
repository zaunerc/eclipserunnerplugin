package com.eclipserunner.matchers;

import static org.mockito.Matchers.argThat;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.mockito.ArgumentMatcher;

import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;

public class RunnerMatchers {

	static class IsILaunchConfiguration extends ArgumentMatcher<ILaunchConfiguration> {
		@Override
		public boolean matches(Object object) {
			return (object instanceof ILaunchConfiguration);
		}
	}
	
	static class IsILaunchConfigurationNode extends ArgumentMatcher<ILaunchNode> {
		@Override
		public boolean matches(Object object) {
			return (object instanceof ILaunchNode);
		}
	}
	
	static class IsILaunchConfigurationCategory extends ArgumentMatcher<ICategoryNode> {
		@Override
		public boolean matches(Object object) {
			return (object instanceof ICategoryNode);
		}
	}
	
	public static ILaunchConfiguration anyLaunchConfiguration() {
		return argThat(new IsILaunchConfiguration());
	}
	
	public static ICategoryNode anyLaunchConfigurationCotegory() {
		return argThat(new IsILaunchConfigurationCategory());
	}
	
	public static ILaunchNode anyLaunchConfigurationNode() {
		return argThat(new IsILaunchConfigurationNode());
	}
}
