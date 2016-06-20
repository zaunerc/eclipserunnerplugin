package com.eclipserunner.ui.dnd;

import static com.eclipserunner.utils.SelectionUtils.getFirstSelectedItemByType;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

import com.eclipserunner.model.ICategoryNode;

/**
 * Listener for handling drag events.
 * 
 * @author bary
 */
public class RunnerViewDragListener implements DragSourceListener {

	private ISelection currentSelection;

	private final ISelectionProvider selectionProvider;

	public RunnerViewDragListener(ISelectionProvider selectionProvider) {
		this.selectionProvider = selectionProvider;
	}

	public void dragStart(DragSourceEvent event) {
		ISelection selection = selectionProvider.getSelection();
		if (selection instanceof IStructuredSelection && !selection.isEmpty()) {

			currentSelection = (IStructuredSelection) selection;
			ICategoryNode categoryNode = getFirstSelectedItemByType(currentSelection, ICategoryNode.class);
			if (categoryNode != null) {
				event.doit = false;
			}
		} else {
			this.currentSelection = null;
			event.doit = false;
		}
	}

	public void dragSetData(DragSourceEvent event) {
		if (isDragSelectionEmpty()) {
			return;
		}

		if (getSelectionTransfer().isSupportedType(event.dataType)) {
			getSelectionTransfer().setSelection(currentSelection);
		}
	}

	public void dragFinished(DragSourceEvent event) {
		if (getSelectionTransfer().isSupportedType(event.dataType)) {
			getSelectionTransfer().setSelection(null);
		}
	}

	private boolean isDragSelectionEmpty() {
		return currentSelection == null || currentSelection.isEmpty();
	}

	private LocalSelectionTransfer getSelectionTransfer() {
		return LocalSelectionTransfer.getTransfer();
	}
	
}
