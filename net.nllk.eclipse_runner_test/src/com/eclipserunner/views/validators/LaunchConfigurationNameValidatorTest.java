package com.eclipserunner.views.validators;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.Messages;

public class LaunchConfigurationNameValidatorTest {

	private static final String INITIAL_LAUNCH_CONFIGURATION_NAME = "config";
	private static final String EXISTING_LAUNCH_CONFIGURATION_NAME = "exists";
	private static final String NOT_EXISTING_LAUNCH_CONFIGURATION_NAME = "not_exists";

	static {
		Messages.Message_errorLaunchConfigurationEmptyName = "aaaa";
		Messages.Message_errorLaunchConfigurationAlreadyExists = "bbbb";
	}

	@Mock
	private ILaunchManager luanchManagerMock;

	@Before
	public void initMocks() throws CoreException {
		MockitoAnnotations.initMocks(this);

		when(luanchManagerMock.isExistingLaunchConfigurationName(EXISTING_LAUNCH_CONFIGURATION_NAME)).thenReturn(true);
		when(luanchManagerMock.isExistingLaunchConfigurationName(NOT_EXISTING_LAUNCH_CONFIGURATION_NAME)).thenReturn(false);
	}

	@Test
	public void testIsValidEmptyString() throws Exception {
		LaunchConfigurationNameValidator validator = new LaunchConfigurationNameValidator(INITIAL_LAUNCH_CONFIGURATION_NAME, luanchManagerMock);

		assertTrue(Messages.Message_errorLaunchConfigurationEmptyName.equals(validator.isValid("")));
	}

	@Test
	public void testIsValidWhitespaceString() throws Exception {
		LaunchConfigurationNameValidator validator = new LaunchConfigurationNameValidator(INITIAL_LAUNCH_CONFIGURATION_NAME, luanchManagerMock);

		assertTrue(Messages.Message_errorLaunchConfigurationEmptyName.equals(validator.isValid("    ")));
	}

	@Test
	public void testIsValidLaunchConfigurationNameExists() throws Exception {
		LaunchConfigurationNameValidator validator = new LaunchConfigurationNameValidator(INITIAL_LAUNCH_CONFIGURATION_NAME, luanchManagerMock);

		assertTrue(Messages.Message_errorLaunchConfigurationAlreadyExists.equals(validator.isValid(EXISTING_LAUNCH_CONFIGURATION_NAME)));
		verify(luanchManagerMock).isExistingLaunchConfigurationName(anyString());
	}

	@Test
	public void testIsValidLaunchConfigurationNameNotExists() throws Exception {
		LaunchConfigurationNameValidator validator = new LaunchConfigurationNameValidator(INITIAL_LAUNCH_CONFIGURATION_NAME, luanchManagerMock);

		assertNull(validator.isValid(NOT_EXISTING_LAUNCH_CONFIGURATION_NAME));
		verify(luanchManagerMock).isExistingLaunchConfigurationName(anyString());
	}

	@Test
	public void testIsValidInitialValue() throws Exception {
		LaunchConfigurationNameValidator validator = new LaunchConfigurationNameValidator(INITIAL_LAUNCH_CONFIGURATION_NAME, luanchManagerMock);

		assertNull(validator.isValid(INITIAL_LAUNCH_CONFIGURATION_NAME));
	}

}
