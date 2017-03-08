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

import javax.media.j3d.*;
import javax.vecmath.*;

import org.selman.java3d.book.common.*;

public class TextureAnimationBehavior extends Behavior
{
	// the wake up condition for the behavior
	protected WakeupCondition		m_WakeupCondition = null;
	protected Transform3D			m_Transform3D = null;
	protected TextureAttributes	m_TextureAttributes = null;
    protected double rotY = 0;

	public TextureAnimationBehavior( TextureAttributes texAttribs )
	{
		m_TextureAttributes = texAttribs;
		m_Transform3D = new Transform3D( );
		m_TextureAttributes.setCapability( TextureAttributes.ALLOW_TRANSFORM_WRITE );

		// create the WakeupCriterion for the behavior
		WakeupCriterion criterionArray[] = new WakeupCriterion[1];
		criterionArray[0] = new WakeupOnElapsedTime( 300 );

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
                rotY += Utils.getRandomNumber( 0.01, 0.01 );
				m_Transform3D.rotY( rotY );
				m_TextureAttributes.setTextureTransform( m_Transform3D );
			}			
		}

		// assign the next WakeUpCondition, so we are notified again
		wakeupOn( m_WakeupCondition );
	}
}
