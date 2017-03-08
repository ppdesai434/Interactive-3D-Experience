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

import javax.vecmath.*;
import javax.media.j3d.*;
import java.awt.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import org.selman.java3d.book.common.*;

/**
* This class is a simple behavior that invokes the KeyNavigator
* to modify the view platform transform.
*/
public class Car extends ComplexObject
{	
	public static final float				CAR_WIDTH = 0.2f;
	public static final float				CAR_HEIGHT = 0.2f;
	public static final float				CAR_LENGTH = 0.6f;

	public Car( Component comp, Group g, int nFlags )
	{
		super( comp, g, nFlags );
	}

	private float getRandomNumber( float basis, float random )
	{
		return basis + ( (float) Math.random( ) * random * 2 ) - (random);
	}

	public Bounds getGeometryBounds( )
	{
		return new BoundingSphere( new Point3d( 0,0,0 ), 0.2 );
	}

	protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile )
	{
		int nPrimFlags = 0;

		if( (m_nFlags & ComplexObject.TEXTURE) == ComplexObject.TEXTURE )
		{
			nPrimFlags |= Primitive.GENERATE_TEXTURE_COORDS;
			setTexture( app, szTextureFile );
		}

		return new Box( CAR_WIDTH, (float) position.y, getRandomNumber( CAR_LENGTH, 0.01f ), nPrimFlags, app );		
	}
}
