package com.eclipserunner.model;

import java.util.List;

/**
 * @author vachacz
 */
public interface INodeSelection {

	boolean allNodesHaveSameType();
	boolean hasExactlyOneNode();

	boolean firstNodeHasType(Class<?> clazz);
	<T> T getFirstNodeAs(Class<T> clazz);
	
	<T> List<T> getSelectedNodesByType(Class<T> clazz);

}
