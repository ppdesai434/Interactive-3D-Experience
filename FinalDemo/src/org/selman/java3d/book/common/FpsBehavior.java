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

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;

// this class implements a simple behavior that
// output the rendered Frames Per Second.

public class FpsBehavior extends Behavior
{
	// the wake up condition for the behavior
	protected WakeupCondition		m_WakeupCondition = null;
	protected long					m_StartTime = 0;

	private final int				m_knReportInterval = 100;


	public FpsBehavior( )
	{
		// save the WakeupCriterion for the behavior
		m_WakeupCondition = new WakeupOnElapsedFrames( m_knReportInterval );
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

			// every N frames, update position of the graphic
			if( wakeUp instanceof WakeupOnElapsedFrames )
			{
				if( m_StartTime > 0 )
				{
					final long interval = System.currentTimeMillis( ) - m_StartTime;
					System.out.println( "FPS: " + (m_knReportInterval * 1000) / interval );
				}

				m_StartTime = System.currentTimeMillis( );
			}
		}

		// assign the next WakeUpCondition, so we are notified again
		wakeupOn( m_WakeupCondition );
	}
}
