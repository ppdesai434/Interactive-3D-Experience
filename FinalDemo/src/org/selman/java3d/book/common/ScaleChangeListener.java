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

import javax.vecmath.*;

//*****************************************************************************
/**
*	Interface to listen for changes in translation from the 
*	TornadoMouseScale class.
*
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************
public abstract interface ScaleChangeListener extends TornadoChangeListener
{
	//*****************************************************************************
	/**
	*	Callback to notify of new scale being applied.
	* 
	*	@param target the Object being manipulated
	*	@param scale the new scale being applied
	*/
	//*****************************************************************************
	public void onScale( Object target, Vector3d scale );
}
