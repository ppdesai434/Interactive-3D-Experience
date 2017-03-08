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
*	TornadoMouseTranslate
*
*	Custom translation behavior.
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************

public class TornadoMouseTranslate extends TornadoMouseBehavior
{
	// private data
	private float					m_Scale = 1;
	protected Point3d				m_MinTranslate = null;
	protected Point3d				m_MaxTranslate = null;


	// protected data

	// public data

	//*****************************************************************************
	/**
	*	@param scale the translation scale factor (bigger = faster)
	*
	*	Default minimum translation: -10,-10,-10
	*	Default maximum translation: 10,10,10
	*/
	//*****************************************************************************	
	public TornadoMouseTranslate( float scale )
	{
		m_Scale = scale;	

		m_MinTranslate = new Point3d( -10,-10,-10 );
		m_MaxTranslate = new Point3d( 10,10,10 );
	}

	//*****************************************************************************
	/**
	*	@param minTrans the minimum x,y,z translation
	*/
	//*****************************************************************************	
	public void setMinTranslate( Point3d minTrans )
	{
		m_MinTranslate = minTrans;
	}

	//*****************************************************************************
	/**
	*	@param maxTrans the maximum x,y,z translation
	*/
	//*****************************************************************************	
	public void setMaxTranslate( Point3d maxTrans )
	{
		m_MaxTranslate = maxTrans;
	}

	protected boolean isStartBehaviorEvent( java.awt.event.MouseEvent evt )
	{
		int nId = evt.getID( );
		return ( (nId == MouseEvent.MOUSE_DRAGGED) && (evt.isAltDown( ) == false) && (evt.isMetaDown( ) != false) );
	}

	protected void applyVectorToObject( Vector3f vector )
	{
		TransformGroup tg = getTransformGroup( );

		if( tg != null )
		{
			// scale the mouse movements so the objects roughly tracks with the mouse
			vector.scale( m_Scale );

			Vector3d vTranslation = new Vector3d( );
			tg.getTransform( m_Transform3D );
			m_Transform3D.get( vTranslation );

			vTranslation.x += vector.x;
			vTranslation.y += vector.y;
			vTranslation.z += vector.z;

			if( vTranslation.x >= m_MinTranslate.x && vTranslation.y >= m_MinTranslate.y && vTranslation.z >= m_MinTranslate.z )
			{
				if( vTranslation.x <= m_MaxTranslate.x && vTranslation.y <= m_MaxTranslate.y && vTranslation.z <= m_MaxTranslate.z )
				{
					m_Transform3D.setTranslation( vTranslation );
					applyTransform( );

					if( m_Listener != null )
						((TranslationChangeListener) m_Listener).onTranslate( m_Object, vTranslation );
				}
			}					
		}
	}
} // TornadoMouseTranslate
