/**********************************************************
  Copyright (C) 2001 	Daniel Selman

  First distributed with the book "Java 3D Programming"
  by Daniel Selman and published by Manning Publications.
  http://manning.com/selman

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation, version 2.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  The license can be found on the WWW at:
  http://www.fsf.org/copyleft/gpl.html

  Or by writing to:
  Free Software Foundation, Inc.,
  59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

  Authors can be contacted at:
   Daniel Selman: daniel@selman.org

  If you make changes you think others would like, please 
  contact one of the authors or someone at the 
  www.j3d.org web site.
**************************************************************/

package org.selman.java3d.book.common;

//*****************************************************************************
/**
*	Interface to listen for changes affected by in the TornadoMouseBehaviors.
*
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************

public abstract interface TornadoChangeListener
{
	//*****************************************************************************
	/**
	*	Callback to notify of a start drag event.
	* 
	*	@param target the Object being manipulated
	*/
	//*****************************************************************************
	public void onStartDrag( Object target );

	//*****************************************************************************
	/**
	*	Callback to notify of an end drag event.
	* 
	*	@param target the Object being manipulated
	*/
	//*****************************************************************************	
	public void onEndDrag( Object target );

	//*****************************************************************************
	/**
	*	Notification that the Transform is being updated
	* 
	*	@param target the Object being manipulated
	*/
	//*****************************************************************************	
	public void onApplyTransform( Object target );

	//*****************************************************************************
	/**
	*	Notification that a new Transform is being calculated
	* 
	*	@param target the Object being manipulated
	*	@param xpos the mouse x position
	*	@param ypos the mouse y position
	*/
	//*****************************************************************************		
	public void onAdjustTransform( Object target, int xpos, int ypos );
}
