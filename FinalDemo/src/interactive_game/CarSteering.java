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

package interactive_game;

import java.awt.event.*;
import java.awt.AWTEvent;
import java.util.Enumeration;
import javax.vecmath.*;
import javax.media.j3d.*;

/**
* This class is a simple behavior that invokes the KeyNavigator
* to modify the view platform transform.
*/
public class CarSteering extends Behavior 
{
	private WakeupOnAWTEvent 							wakeupOne = null;
	private WakeupCriterion[] 							wakeupArray = new WakeupCriterion[1];
	private WakeupCondition 							wakeupCondition = null;

	private final float									TRANSLATE_LEFT = -0.10f;
	private final float									TRANSLATE_RIGHT = 0.10f;

	TransformGroup											m_TransformGroup = null;

	public CarSteering( TransformGroup tg )
	{
		m_TransformGroup = tg;
       
       try
       {
			m_TransformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
			m_TransformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
			}
       catch( Exception e )
       {
       }
		
		wakeupOne = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
		wakeupArray[0] = wakeupOne;
		wakeupCondition = new WakeupOr( wakeupArray );
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
		WakeupOnAWTEvent ev;
		WakeupCriterion genericEvt;
		AWTEvent[] events;

		while (criteria.hasMoreElements( ))
		{
			genericEvt = (WakeupCriterion) criteria.nextElement( );

			if (genericEvt instanceof WakeupOnAWTEvent)
			{
				ev = (WakeupOnAWTEvent) genericEvt;
				events = ev.getAWTEvent( );
				processAWTEvent( events );
			} 
		}

		// Set wakeup criteria for next time
		wakeupOn( wakeupCondition );
	}

	/**
	*  Process a keyboard event
	*/
	private void processAWTEvent( AWTEvent[] events )
	{
		for( int n = 0; n < events.length; n++)
		{
			if( events[n] instanceof KeyEvent)
			{
				KeyEvent eventKey = (KeyEvent) events[n];

				if( eventKey.getID( ) == KeyEvent.KEY_PRESSED )
				{
					int keyCode = eventKey.getKeyCode( );
					int keyChar = eventKey.getKeyChar( );

					Vector3f translate = new Vector3f( );

					Transform3D t3d = new Transform3D( );
					m_TransformGroup.getTransform( t3d );
					t3d.get( translate );

					switch (keyCode)
					{
					case KeyEvent.VK_LEFT:
						translate.x += TRANSLATE_LEFT;
						break;

					case KeyEvent.VK_A:
						translate.x += TRANSLATE_LEFT;
						break;	
						
					case KeyEvent.VK_D:
						translate.x += TRANSLATE_RIGHT;
						break;
						
					case KeyEvent.VK_RIGHT:
						translate.x += TRANSLATE_RIGHT;
						break;
					}

					// System.out.println( "Steering: " + translate.x );                   
					translate.y = 0.5f;

					t3d.setTranslation( translate );
					m_TransformGroup.setTransform( t3d );
				}
			}
		}
	}
}
