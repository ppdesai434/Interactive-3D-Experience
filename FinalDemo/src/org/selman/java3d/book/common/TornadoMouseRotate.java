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
*	TornadoMouseRotate
*
*	Custom mouse rotation behaviour
*
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************

public class TornadoMouseRotate extends TornadoMouseBehavior
{	
	protected double 						m_FactorX = 0.001;
	protected double 						m_FactorY = 0.001;

	protected Transform3D 				m_TransformX = null;
	protected Transform3D 				m_TransformY = null;

	protected boolean						m_bInvert = false;

	//*****************************************************************************
	/**
	*	@param xf the x rotation scale factor
	*	@param yf the y rotation scale factor
	*/
	//*****************************************************************************	
	public TornadoMouseRotate( double xf, double yf )
	{
		m_FactorX = xf;
		m_FactorY = yf;

		m_TransformX = new Transform3D( );
		m_TransformY = new Transform3D( );

		m_bInvert = false;
	}

	protected boolean isStartBehaviorEvent( java.awt.event.MouseEvent evt )
	{
		int nId = evt.getID( );
		return ( (nId == MouseEvent.MOUSE_DRAGGED) && (evt.isAltDown( ) == false) && (evt.isMetaDown( ) == false) );
	}

	//*****************************************************************************
	/**
	*	@param bInvert true to invert the Y axis
	*/
	//*****************************************************************************	
	public void setInvert( boolean bInvert )
	{
		m_bInvert = bInvert;
	}

	// this behavior is relative to the *screen*
	// the current rotation of the object etc. is ignored
	protected boolean isRelativeToObjectCoordinates( )
	{
		return false;
	}

	protected void applyVectorToObject( Vector3f vector )
	{
		TransformGroup tg = getTransformGroup( );

		if( tg != null )
		{
			tg.getTransform( m_Transform3D );

			double x_angle = vector.y * m_FactorX;
			double y_angle = vector.x * m_FactorY;

			m_TransformX.rotX( x_angle );
			m_TransformY.rotY( y_angle );

			Matrix4d mat = new Matrix4d( );

			// Remember old matrix
			m_Transform3D.get( mat );

			// Translate to origin
			m_Transform3D.setTranslation( new Vector3d( 0.0,0.0,0.0 ) );

			if ( m_bInvert != false )
			{
				m_Transform3D.mul( m_Transform3D, m_TransformX );
				m_Transform3D.mul( m_Transform3D, m_TransformY );
			}
			else
			{
				m_Transform3D.mul( m_TransformX, m_Transform3D );
				m_Transform3D.mul( m_TransformY, m_Transform3D );
			}

			// Set old translation back
			Vector3d translation = new Vector3d( mat.m03, mat.m13, mat.m23 );
			m_Transform3D.setTranslation( translation );

			// save the new Transform3D
			applyTransform( );

			if( m_Listener != null )
			{
				Point3d rotate = Euler.getEulerRotation( m_Transform3D );
				((RotationChangeListener) m_Listener ).onRotate( m_Object, rotate );
			}
		}
	}	
} // TornadoMouseRotate
