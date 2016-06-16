package com.eclipserunner.views.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.INodeSelection;

/**
 * Opens selected item in editor, when possible.
 * 
 * @author lwachowi
 */
public class OpenItemAction extends Action {

	private final INodeSelection selection;

	public OpenItemAction(INodeSelection selection, String launchGroupId) {
		super(launchGroupId);
		this.selection = selection;
	}

	public void run() {
		ILaunchConfiguration configuration = selection.getFirstNodeAs(ILaunchNode.class).getLaunchConfiguration();
		try {
			for (IResource res : configuration.getMappedResources()) {
				if (res instanceof IFile) {
					IFile file = (IFile) res;
					
					IWorkbench workbench = PlatformUI.getWorkbench();
					IEditorDescriptor editor = workbench.getEditorRegistry().getDefaultEditor(file.getName());
					
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = window.getActivePage();
					
					page.openEditor(new FileEditorInput(file), editor.getId());
					return;
				}
			}
		} catch (CoreException e) {
			// do nothing, open failed
		}
	};

}
