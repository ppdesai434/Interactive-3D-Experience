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
public class Road extends ComplexObject
{	
	public static final float			ROAD_WIDTH = 3.0f;
	public static final float			ROAD_HEIGHT = 0.01f;
	public static final float			ROAD_LENGTH = -200.0f;

	public Road( Component comp, Group g, int nFlags )
	{
		super( comp, g, nFlags );
	}

	protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile )
	{
		// creates a segment of road 200 x 2

		QuadArray quadArray = new QuadArray( 4, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2 );

		float[] coordArray = 	{	
       							-ROAD_WIDTH, ROAD_HEIGHT, 0,
									ROAD_WIDTH, ROAD_HEIGHT, 0,
									ROAD_WIDTH, ROAD_HEIGHT, ROAD_LENGTH,
									-ROAD_WIDTH, ROAD_HEIGHT, ROAD_LENGTH
								};

		float[] texArray =	{	
       						0, 0,
								1, 0,
								1, 1,
								0, 1 
                          	};

		quadArray.setCoordinates( 0, coordArray, 0, 4 );

		if( (m_nFlags & TEXTURE) == TEXTURE )
		{
			quadArray.setTextureCoordinates( 0, 0, texArray, 0, 4 );
			setTexture( app, szTextureFile );
		}

		Shape3D sh = new Shape3D( quadArray, app );

		BranchGroup bg = new BranchGroup( );
		bg.addChild( sh );
		return bg;
	}
}
