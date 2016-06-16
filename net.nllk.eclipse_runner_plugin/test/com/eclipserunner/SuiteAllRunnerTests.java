package com.eclipserunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.eclipserunner.model.adapters.RunnerModelTreeAdapterTest;
import com.eclipserunner.model.adapters.TreeWithTypesAdapterTest;
import com.eclipserunner.model.filters.BookmarkFilterTest;
import com.eclipserunner.model.filters.DefaultCategoryFilterTest;
import com.eclipserunner.model.impl.RunnerModelTest;
import com.eclipserunner.utils.SelectionUtilsTest;
import com.eclipserunner.views.actions.BookmarkActionTest;
import com.eclipserunner.views.impl.RunnerViewSelectionTest;
import com.eclipserunner.views.validators.CategoryNameValidatorTest;
import com.eclipserunner.views.validators.LaunchConfigurationNameValidatorTest;

@RunWith(Suite.class)
@SuiteClasses(value = {
	RunnerModelTest.class,
	SelectionUtilsTest.class,
	CategoryNameValidatorTest.class,
	LaunchConfigurationNameValidatorTest.class,
	TreeWithTypesAdapterTest.class,
	RunnerModelTreeAdapterTest.class,
	BookmarkFilterTest.class,
	DefaultCategoryFilterTest.class,
	RunnerViewSelectionTest.class,
	BookmarkActionTest.class
})
public class SuiteAllRunnerTests {

}
