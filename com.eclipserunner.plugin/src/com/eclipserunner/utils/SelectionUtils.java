package com.eclipserunner.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Utility methods related to object selection.
 * 
 * @author vachacz
 */
public class SelectionUtils {

	public static <T> T getFirstSelectedItemByType(ISelection selection, Class<T> instanceClass) {
		if (selection instanceof IStructuredSelection) {
			return getFirstSelectedItemByType((IStructuredSelection) selection, instanceClass);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFirstSelectedItemByType(IStructuredSelection selection, Class<T> instanceClass) {
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object item = iterator.next();
			if (instanceClass.isInstance(item)) {
				return (T) item;
			}
		}
		return null;
	}

	public static <T> List<T> getAllSelectedItemsByType(ISelection selection, Class<T> instanceClass) {
		if (selection instanceof IStructuredSelection) {
			return getAllSelectedItemsByType((IStructuredSelection) selection, instanceClass);
		}
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getAllSelectedItemsByType(IStructuredSelection selection, Class<T> instanceClass) {
		List<T> selectedOfType = new ArrayList<T>();
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object item = iterator.next();
			if (instanceClass.isInstance(item)) {
				selectedOfType.add((T) item);
			}
		}
		return selectedOfType;
	}

	@SuppressWarnings("rawtypes")
	public static List<Class> getSelectedItemTypes(IStructuredSelection selection) {
		List<Class> selectedItemTypes = new ArrayList<Class>();
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext()) {
			Object item = iterator.next();
			if (!selectedItemTypes.contains(item.getClass())) {
				selectedItemTypes.add(item.getClass());
			}
		}
		return selectedItemTypes;
	}

	public static boolean isSameTypeNodeSelection(IStructuredSelection selection) {
		return getSelectedItemTypes(selection).size() == 1;
	}

}
