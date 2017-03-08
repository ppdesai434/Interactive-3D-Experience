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

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.media.j3d.*;
import javax.vecmath.*;

//*****************************************************************************
/**
*	TornadoMouseScale
*
*	Custon scaling behaviour
*
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************

public class TornadoMouseScale extends TornadoMouseBehavior
{
	// private data
	protected float							m_Delta = 0;
	protected float							m_Threshold = 0;

	protected Point3d						m_MinScale;
	protected Point3d						m_MaxScale;


	//*****************************************************************************
	/**
	*	@param threshold the amount the mouse must be moved before an object is moved
	*	@param delta the step size to use for object scaling bigger = faster 
	*	scaling.
	*
	*	Default minimum scale: 0.1,0.1,0.1
	*	Default maximum scale: 5,5,5
	*/
	//*****************************************************************************	
	public TornadoMouseScale( float threshold, float delta )
	{
		m_Delta = delta;
		m_Threshold = threshold;

		m_MinScale = new Point3d( 0.1,0.1,0.1 );
		m_MaxScale = new Point3d( 5,5,5 );
	}

	//*****************************************************************************
	/**
	*	@param minScale the minimum x,y,z scale
	*/
	//*****************************************************************************	
	public void setMinScale( Point3d minScale )
	{
		m_MinScale = minScale;
	}

	//*****************************************************************************
	/**
	*	@param maxScale the maximum x,y,z scale
	*/
	//*****************************************************************************	
	public void setMaxScale( Point3d maxScale )
	{
		m_MaxScale = maxScale;
	}

	// this behavior is relative to the *screen*
	// the current rotation of the object etc. is ignored
	protected boolean isRelativeToObjectCoordinates( )
	{
		return true;
	}


	protected boolean isStartBehaviorEvent( java.awt.event.MouseEvent evt )
	{
		int nId = evt.getID( );
		return ( (nId == MouseEvent.MOUSE_DRAGGED) && (evt.isAltDown( ) != false) && (evt.isMetaDown( ) == false) );
	}

	protected void applyVectorToObject( Vector3f vector )
	{
		TransformGroup tg = getTransformGroup( );

		if( tg != null )
		{
			tg.getTransform( m_Transform3D );

			Vector3d vScale = new Vector3d( );
			m_Transform3D.getScale( vScale );

			Vector3f delta = new Vector3f( );

			if( vector.x > m_Threshold )
				delta.x = m_Delta;
			else if ( vector.x < -m_Threshold )
				delta.x = -m_Delta;

			if( vector.y > m_Threshold )
				delta.y = m_Delta;
			else if ( vector.y < -m_Threshold )
				delta.y = -m_Delta;

			if( vector.z > m_Threshold )
				delta.z = m_Delta;
			else if ( vector.z < -m_Threshold )
				delta.z = -m_Delta;

			Vector3d objectScale = new Vector3d( vScale.x + delta.x,
				vScale.y + delta.y,
				vScale.z + delta.z );

			if( objectScale.x >= m_MinScale.x && objectScale.y >= m_MinScale.y && objectScale.z >= m_MinScale.z )
			{
				if( objectScale.x <= m_MaxScale.x && objectScale.y <= m_MaxScale.y && objectScale.z <= m_MaxScale.z )
				{
					m_Transform3D.setScale( objectScale );

					// save the new Transform3D
					applyTransform( );

					if( m_Listener != null )
						((ScaleChangeListener) m_Listener).onScale( m_Object, objectScale );
				}
			}					
		}
	}
} // TornadoMouseScale
