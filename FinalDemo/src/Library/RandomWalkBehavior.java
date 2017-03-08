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

  Author can be contacted at:
  Daniel Selman: daniel@selman.org

  If you make changes you think others would like please
  contact Daniel Selman.
**************************************************************/

package Library;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import org.selman.java3d.book.common.*;

// this class implements a simple behavior that
// calculates and prints the size of an object
// based on the vertices in its GeometryArray

public class RandomWalkBehavior extends Behavior
{
	// the wake up condition for the behavior
	protected WakeupCondition		m_WakeupCondition = null;
	protected TransformGroup		m_TransformGroup = null;

	protected Transform3D			m_Transform3D = null;

	protected Vector3d				TargetVector3d = null;
	protected Vector3d				CurrentVector3d = null;

	private final double 			m_MovementX = 2;
	private final double				m_MovementY = 0;
	private final double				m_MovementZ = 2;

	private int 						m_nFrameCount = 0;

	private CollisionChecker		m_CollisionChecker = null;


	public RandomWalkBehavior( TransformGroup tg, CollisionDetector detector )
	{
		m_TransformGroup = tg;

		m_CollisionChecker = new CollisionChecker( tg, detector, false );

		m_Transform3D = new Transform3D( );

		TargetVector3d = new Vector3d( );
		CurrentVector3d = new Vector3d( );

		// create the WakeupCriterion for the behavior
		WakeupCriterion criterionArray[] = new WakeupCriterion[1];
		criterionArray[0] = new WakeupOnElapsedTime( 100 );

		// save the WakeupCriterion for the behavior
		m_WakeupCondition = new WakeupOr( criterionArray );
	}

	public void initialize( )
	{
		// apply the initial WakeupCriterion
		wakeupOn( m_WakeupCondition );
	}

	public void processStimulus( java.util.Enumeration criteria )
	{				
		while( criteria.hasMoreElements( ) )
		{
			WakeupCriterion wakeUp = (WakeupCriterion) criteria.nextElement( );			

			if( wakeUp instanceof WakeupOnElapsedTime )
			{
				if( m_nFrameCount % 100 == 0 )
				{
					// generate a random direction for movement
					TargetVector3d.x = m_MovementX * Utils.getRandomNumber( 0, 1 );
					TargetVector3d.y = m_MovementY * Utils.getRandomNumber( 0, 1 );
					TargetVector3d.z = m_MovementZ * Utils.getRandomNumber( 0, 1 );
				}

				CurrentVector3d.x += TargetVector3d.x * Utils.getRandomNumber( 1, 0.1 );
				CurrentVector3d.y += TargetVector3d.y * Utils.getRandomNumber( 1, 0.1 );
				CurrentVector3d.z += TargetVector3d.z * Utils.getRandomNumber( 1, 0.1 );

				m_Transform3D.setTranslation( CurrentVector3d );

				if( m_CollisionChecker.isCollision( m_Transform3D ) == false )
					m_TransformGroup.setTransform( m_Transform3D );		

				m_nFrameCount++;
			}			
		}

		// assign the next WakeUpCondition, so we are notified again
		wakeupOn( m_WakeupCondition );
	}
}
