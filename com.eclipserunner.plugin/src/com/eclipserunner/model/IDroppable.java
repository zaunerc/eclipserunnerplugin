package com.eclipserunner.model;

import java.util.List;

/**
 * Interface for tree elements supporting droppable.
 *
 * @author tonyq
 */
public interface IDroppable {

	public boolean supportsDrop(int currentLocation) ;

	public boolean drop(List<ILaunchNode> launchNodesToMove);
}
