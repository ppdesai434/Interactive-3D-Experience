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

import javax.vecmath.*;
import javax.media.j3d.*;
import java.awt.*;
import java.net.*;

import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.geometry.*;
	
import org.selman.java3d.book.common.*;

public class Guard extends ComplexObject
{	
	private CollisionDetector		m_CollisionDetector = null;

	public Guard( Component comp, Group g, int nFlags, CollisionDetector detector )
	{
		super( comp, g, nFlags );

		m_CollisionDetector = detector;
	}

	protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile )
	{						
		TransformGroup tg = new TransformGroup( );
		tg.addChild( new Cone( 5, 30 ) );

		attachBehavior( new RandomWalkBehavior( getBehaviorTransformGroup( ), m_CollisionDetector ) );

		return tg;
	}
}
