package com.eclipserunner.views.validators;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.CoreException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.eclipserunner.Messages;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.IRunnerModel;

public class CategoryNameValidatorTest {

	private static final String INITIAL_CATEGORY_NAME = "category";
	private static final String EXISTING_CATEGORY_NAME1 = "exists1";
	private static final String EXISTING_CATEGORY_NAME2 = "exists2";
	private static final String NOT_EXISTING_CATEGORY_NAME = "not_exists";

	static {
		Messages.Message_errorCatogoryEmptyName = "aaaa";
		Messages.Message_errorCategoryAlreadyExists = "bbbb";
	}

	@Mock
	private IRunnerModel runnerModelMock;

	@Mock
	private ICategoryNode categoryNode1Mock;

	@Mock
	private ICategoryNode categoryNode2Mock;

	@Before
	public void initMocks() throws CoreException {
		MockitoAnnotations.initMocks(this);

		when(categoryNode1Mock.getName()).thenReturn(EXISTING_CATEGORY_NAME1);
		when(categoryNode2Mock.getName()).thenReturn(EXISTING_CATEGORY_NAME2);

		Collection<ICategoryNode> categoryMockNodes = new HashSet<ICategoryNode>();
		categoryMockNodes.add(categoryNode1Mock);
		categoryMockNodes.add(categoryNode2Mock);

		when(runnerModelMock.getCategoryNodes()).thenReturn(categoryMockNodes);
	}


	@Test
	public void testIsValidEmptyString() throws Exception {
		CategoryNameValidator validator = new CategoryNameValidator(INITIAL_CATEGORY_NAME, runnerModelMock);

		assertTrue(Messages.Message_errorCatogoryEmptyName.equals(validator.isValid("")));
	}

	@Test
	public void testIsValidWhitespaceString() throws Exception {
		CategoryNameValidator validator = new CategoryNameValidator(INITIAL_CATEGORY_NAME, runnerModelMock);

		assertTrue(Messages.Message_errorCatogoryEmptyName.equals(validator.isValid("    ")));
	}

	@Test
	public void testIsValidCategoryNameExists() throws Exception {
		CategoryNameValidator validator = new CategoryNameValidator(INITIAL_CATEGORY_NAME, runnerModelMock);

		assertTrue(Messages.Message_errorCategoryAlreadyExists.equals(validator.isValid(EXISTING_CATEGORY_NAME1)));
		verify(runnerModelMock).getCategoryNodes();
	}

	@Test
	public void testIsValidCategoryNameNotExists() throws Exception {
		CategoryNameValidator validator = new CategoryNameValidator(INITIAL_CATEGORY_NAME, runnerModelMock);

		assertNull(validator.isValid(NOT_EXISTING_CATEGORY_NAME));
		verify(runnerModelMock).getCategoryNodes();
	}

	@Test
	public void testIsValidInitialValue() throws Exception {
		CategoryNameValidator validator = new CategoryNameValidator(INITIAL_CATEGORY_NAME, runnerModelMock);

		assertNull(validator.isValid(INITIAL_CATEGORY_NAME));
	}

}
