package com.eclipserunner.views.impl;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.eclipserunner.RunnerPlugin;
import com.eclipserunner.model.ICategoryNode;
import com.eclipserunner.model.ILaunchNode;
import com.eclipserunner.model.ILaunchTypeNode;
import com.eclipserunner.model.IRunnerModel;

/**
 * Launch configuration tree decorator.
 * 
 * @author vachacz, bary
 */
public class LaunchTreeLabelProvider extends LabelProvider {

	private static final String IMG_CATEGORY         = "category.gif";
	private static final String IMG_RUN              = "run.gif";
	private static final String IMG_DEFAULT_CATEGORY = "category-archive.gif";
	private static final String IMG_DECORATION       = "star_min.gif";

	private IDebugModelPresentation debugModelPresentation;
	private final IRunnerModel runnerModel;

	public LaunchTreeLabelProvider(IRunnerModel runnerModel) {
		this.debugModelPresentation = DebugUITools.newDebugModelPresentation();
		this.runnerModel = runnerModel;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ICategoryNode) {
			return ((ICategoryNode) element).getName();
		}
		else if (element instanceof ILaunchNode) {
			ILaunchNode launchConfiguration = (ILaunchNode) element;
			return debugModelPresentation.getText(launchConfiguration.getLaunchConfiguration());
		}
		else if (element instanceof ILaunchTypeNode) {
			return debugModelPresentation.getText(((ILaunchTypeNode) element).getLaunchConfigurationType());
		}
		return debugModelPresentation.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		try {
			if (element instanceof ICategoryNode) {
				return getCategoryImage((ICategoryNode) element);
			}
			else if (element instanceof ILaunchNode) {
				return getLaunchConfigurationImage((ILaunchNode) element);
			}
			else if (element instanceof ILaunchTypeNode) {
				return getLaunchConfigurationTypeImage((ILaunchTypeNode) element);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return RunnerPlugin.getDefault().getImage(ImageDescriptor.getMissingImageDescriptor());
	}

	private Image getCategoryImage(ICategoryNode launchConfigurationCategory) {
		if (runnerModel.getDefaultCategoryNode() == launchConfigurationCategory) {
			return getImage(IMG_DEFAULT_CATEGORY);
		} else {
			return getImage(IMG_CATEGORY);
		}
	}

	private Image getLaunchConfigurationImage(ILaunchNode launchConfiguration) {
		if(!launchConfiguration.isExisting()) {
			// do not cause errors if the launch config does not exist
			return getImage(IMG_RUN);
		}
		Image image = debugModelPresentation.getImage(launchConfiguration.getLaunchConfiguration());
		launchConfiguration.isExisting();
		if (launchConfiguration.isBookmarked()) {
			return overlyBookmarkIcon(image, IMG_DECORATION);
		}
		return image;
	}
	
	private Image getLaunchConfigurationTypeImage(ILaunchTypeNode typeNode) {
		return debugModelPresentation.getImage(typeNode.getLaunchConfigurationType());
	}
	
	private Image getImage(String imageName) {
		return RunnerPlugin.getDefault().getImage(imageName);
	}

	private Image overlyBookmarkIcon(Image image, String decoration) {
		ImageDescriptor decorationDescriptor = RunnerPlugin.getDefault().getImageDescriptor(decoration);
		return RunnerPlugin.getDefault().getImage(new DecorationOverlayIcon(image, decorationDescriptor, IDecoration.TOP_RIGHT));
	}

}
