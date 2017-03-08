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

import java.awt.event.*;
import java.awt.AWTEvent;
import java.util.Enumeration;

import javax.vecmath.*;
import javax.media.j3d.*;

/**
* This class is a simple behavior that invokes the KeyNavigator
* to modify the view platform transform.
*/
public class CollisionBehavior extends Behavior 
{
	private WakeupOnCollisionEntry 					wakeupOne = null;
	private WakeupOnCollisionExit 					wakeupTwo = null;

	private WakeupCriterion[] 						wakeupArray = new WakeupCriterion[2];
	private WakeupCondition 						wakeupCondition = null;

	private ComplexObject							m_Owner = null;

	public CollisionBehavior( Node node, ComplexObject owner )
	{
		wakeupOne = new WakeupOnCollisionEntry( node, WakeupOnCollisionEntry.USE_GEOMETRY );
		wakeupTwo = new WakeupOnCollisionExit( node, WakeupOnCollisionExit.USE_GEOMETRY );

		wakeupArray[0] = wakeupOne;
		wakeupArray[1] = wakeupTwo;

		wakeupCondition = new WakeupOr( wakeupArray );

		m_Owner = owner;
	}


	/**
	*  Override Behavior's initialize method to setup wakeup criteria.
	*/
	public void initialize( )
	{
		// Establish initial wakeup criteria
		wakeupOn( wakeupCondition );
	}

	/**
	*  Override Behavior's stimulus method to handle the event.
	*/
	public void processStimulus( Enumeration criteria )
	{
		WakeupCriterion genericEvt;

		while (criteria.hasMoreElements( ))
		{
			genericEvt = (WakeupCriterion) criteria.nextElement( );

			if (genericEvt instanceof WakeupOnCollisionEntry)
			{
				m_Owner.onCollide( true );
			}
			else if(genericEvt instanceof WakeupOnCollisionExit)
			{
				m_Owner.onCollide( false );
			}
		}

		// Set wakeup criteria for next time
		wakeupOn( wakeupCondition );
	}	
}
