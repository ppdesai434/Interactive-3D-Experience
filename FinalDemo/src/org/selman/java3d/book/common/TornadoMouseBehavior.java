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

import com.sun.j3d.utils.behaviors.*;

//*****************************************************************************
/**
*	Base class for the Tornado Mouse Behaviors (Rotate, Translate, Scale).
*
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************
public abstract class TornadoMouseBehavior extends Behavior
{
	// private data
	protected Object							m_Object = null;

	protected Point3f							m_NewPos = null;
	protected Point3f							m_OldPos = null;
	protected Vector3f						m_TranslationVector = null;
	protected Transform3D					m_Translation = null;
	protected boolean							m_bDragging = false;
	protected WakeupOr 						m_MouseCriterion = null;
	protected int								m_nLastY = 0;

	protected Transform3D					m_Transform3D = null;

	protected TornadoChangeListener		m_Listener = null;

	public TornadoMouseBehavior( )
	{	
		m_Object = null;
		m_NewPos = new Point3f( );
		m_OldPos = new Point3f( );

		m_Translation = new Transform3D( );
		m_TranslationVector = new Vector3f( );
		m_bDragging = false;

		m_Transform3D = new Transform3D( );
	}

	//*****************************************************************************
	/**
	*	Register a listener for the behavior.
	*	@param listener the listener to add or null to remove the listener
	*/
	//*****************************************************************************	
	public void setChangeListener( TornadoChangeListener listener )
	{
		m_Listener = listener;
	}


	//*****************************************************************************
	/**
	*	Apply a delta vector (in the object's local coordinates) to the object.
	*/
	//*****************************************************************************	
	protected abstract void applyVectorToObject( Vector3f v );

	//*****************************************************************************
	/**
	*	@return true is this the mouse event that starts the tracking behaviour
	*/
	//*****************************************************************************	
	protected abstract boolean isStartBehaviorEvent( java.awt.event.MouseEvent evt );


	//*****************************************************************************
	/**
	*	Dispatches mouse events as appropriate. Should not need to overide this
	*	method.
	*/
	//*****************************************************************************	
	protected void processMouseEvent( java.awt.event.MouseEvent evt )
	{
		if( m_Object != null )
		{
			if( isStartBehaviorEvent( evt ) != false )
				adjustTransform( evt.getX( ), evt.getY( ) );

			else if( isStopBehaviorEvent( evt ) != false )
				onEndDrag( );
		}
	}

	//*****************************************************************************
	/**
	*	@return true if this is the event that stops drag tracking behviour
	*	the default uses MOUSE_RELEASED.
	*/
	//*****************************************************************************	
	protected boolean isStopBehaviorEvent( java.awt.event.MouseEvent evt )
	{
		int nId = evt.getID( );
		return ( m_bDragging != false && nId == MouseEvent.MOUSE_RELEASED || nId == MouseEvent.MOUSE_EXITED );
	}

	//*****************************************************************************
	/**
	*	@return true if this behaviours change vector is relative to the starting
	*	mouse click. The default is a behaviour that generates delta change vectors 
	*	as the user moves the mouse.
	*/
	//*****************************************************************************	
	protected boolean isRelativeToStartDrag( )
	{
		return false;
	}

	//*****************************************************************************
	/**
	*	@return true if the mouse coordinates should be converted to local object
	*	coordinates before being processed by applyVectorToObject
	*/
	//*****************************************************************************	
	protected boolean isRelativeToObjectCoordinates( )
	{
		return true;
	}

	//*****************************************************************************
	/**
	*	Allows custom start drag processing. Default does nothing.
	*/
	//*****************************************************************************	
	protected void onStartDrag( )
	{
		if( m_Listener != null )
			m_Listener.onStartDrag( m_Object );
	}	

	//*****************************************************************************
	/**
	*	Allows custom end drag processing. ** Call this base class! **
	*/
	//*****************************************************************************	
	protected void onEndDrag( )
	{
		m_bDragging = false;

		if( m_Listener != null )
			m_Listener.onEndDrag( m_Object );
	}

	//*****************************************************************************
	/**
	*	Gets the Transform3D to convert from the Objects coordinate system to 
	*	the world coordinate system.
	*	@param t3d the Transform3D to populate
	*/
	//*****************************************************************************	
	protected void getObjectLocalToVworld( Transform3D t3d )
	{
		if( getTransformGroup( ) != null )
			getTransformGroup( ).getLocalToVworld( t3d );
	}

	//*****************************************************************************
	/**
	*	Gets the Transform3D to convert from the Image plate coordinate system
	*	to the world coordinate system.
	*	@param t3d the Transform3D to populate
	*/
	//*****************************************************************************	
	protected void getImagePlateToVworld( Transform3D t3d )
	{
		getView( ).getCanvas3D( 0 ).getImagePlateToVworld( t3d );
	}

	//*****************************************************************************
	/**
	*	@return the TransformGroup if a TG Object is associated with the behavior
	*	or null otherwise.
	*/
	//*****************************************************************************	
	protected TransformGroup getTransformGroup( )
	{
		if( m_Object instanceof TransformGroup )
			return ( TransformGroup ) m_Object;

		return null;
	}


	//*****************************************************************************
	/**
	*	Saves the behaviors Transform3D into its TransformGroup (if present).
	*	Catches any exceptions (bad transform) that might be thrown.
	*/
	//*****************************************************************************	
	protected void applyTransform( )
	{
		TransformGroup tg = getTransformGroup( );

		if( tg != null )
		{
			try
			{
				// save the new Transform3D
				tg.setTransform( m_Transform3D );

				if( m_Listener != null )
					m_Listener.onApplyTransform( m_Object );
			}
			catch( Exception e )
			{
				System.err.println( e.toString( ) );
			}
		}
	}

	//*****************************************************************************
	/**
	*	Transforms the x,y mouse coordinates to coordinates relative to the object.
	*	Calculates a "delta vector" in object coordinates and calls 
	*	ApplyVectorToObject().
	*
	*	Thanks to: 	A.R. van Ballegooy.
	*					Simon McMullen [simonmc@mincom.com]
	*/
	//*****************************************************************************	
	protected void adjustTransform( int xpos, int ypos )
	{
		if( m_Listener != null )
			m_Listener.onAdjustTransform( m_Object, xpos, ypos );

		if( m_bDragging == false )
		{
			// initialise the starting position
			m_OldPos.x = xpos;
			m_OldPos.y = ypos;
			m_OldPos.z = 0.0f;
			m_nLastY = ypos;

			onStartDrag( );
		}

		m_bDragging = true;

		// save the current position and invert the tracking in the Y direction
		// (positive upwards)
		m_NewPos.x = xpos;
		m_NewPos.y = m_nLastY + (m_nLastY - ypos);
		m_NewPos.z = 0.0f;
		m_nLastY = ypos;

		// transform points to Virtual World Coordinates
		getImagePlateToVworld( m_Translation );

		if( isRelativeToStartDrag( ) == false )
			m_Translation.transform( m_OldPos );

		m_Translation.transform( m_NewPos );

		// transform coordinates to Object Space Coordinates
		// Make sure capability ALLOW_LOCAL_TO_VWORLD_READ is set....

		if( isRelativeToObjectCoordinates( ) != false )
		{
			getObjectLocalToVworld( m_Translation );
			m_Translation.transpose( );

			// transform points to local coordinate system
			if( isRelativeToStartDrag( ) == false )
				m_Translation.transform( m_OldPos );

			m_Translation.transform( m_NewPos );
		}

		// Calculate change and scale
		m_TranslationVector.sub( m_NewPos, m_OldPos );

		applyVectorToObject( m_TranslationVector );

		if( isRelativeToStartDrag( ) == false )
		{
			// store the new positions
			m_OldPos.x = xpos;
			m_OldPos.y = ypos;
			m_OldPos.z = 0.0f;
		}		
	}

	//*****************************************************************************
	/**
	*	Dispatches events based on the behaviours criteria
	*/
	//*****************************************************************************	
	public void processStimulus( Enumeration criteria )
	{
		WakeupCriterion wakeup;
		AWTEvent[] event;
		int id;
		int dx, dy;

		if( m_Object != null )
		{
			while( criteria.hasMoreElements( ) )
			{
				wakeup = (WakeupCriterion) criteria.nextElement( );

				if( wakeup instanceof WakeupOnAWTEvent )
				{
					event = ( (WakeupOnAWTEvent) wakeup).getAWTEvent( );

					for( int i=0; i < event.length; i++ )
					{ 
						processMouseEvent( (MouseEvent) event[i] );
					}
				}
			}
		}

		// tell the behaviour when to wake up again...
		wakeupOn( m_MouseCriterion );
	}

	//*****************************************************************************
	/**
	*	Registers which AWT events are of interest to the behaviour
	*/
	//*****************************************************************************	
	public void initialize( )
	{
		WakeupCriterion[] mouseEvents = new WakeupCriterion[3];

		mouseEvents[0] = new WakeupOnAWTEvent( MouseEvent.MOUSE_DRAGGED );
		mouseEvents[1] = new WakeupOnAWTEvent( MouseEvent.MOUSE_PRESSED );
		mouseEvents[2] = new WakeupOnAWTEvent( MouseEvent.MOUSE_RELEASED );

		m_MouseCriterion = new WakeupOr( mouseEvents );
		wakeupOn( m_MouseCriterion );
	}

	//*****************************************************************************
	/**
	*	void setObject( Object obj )
	*
	*	@param obj the Objectto manipulate.
	*	A null object disables the behaviour.
	*/
	//*****************************************************************************	
	public void setObject( Object obj )
	{
		m_Object = obj;
	}

} // TornadoMouseBehavior
