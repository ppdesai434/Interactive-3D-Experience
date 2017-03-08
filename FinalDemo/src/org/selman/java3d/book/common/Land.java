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

import java.awt.*;

import javax.vecmath.*;
import javax.media.j3d.*;

public class Land extends ComplexObject
{	
	public static final float				WIDTH = 1.0f;
	public static final float				LENGTH = 1.0f;
	public static final float				HEIGHT = 0.0f;

	public Land( Component comp, Group g, int nFlags )
	{
		super( comp, g, nFlags );		
	}

	protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile )
	{
		int nFlags = GeometryArray.COORDINATES | GeometryArray.NORMALS;

		if( (m_nFlags & TEXTURE) == TEXTURE )
			nFlags |= GeometryArray.TEXTURE_COORDINATE_2;

		QuadArray quadArray = new QuadArray( 4, nFlags );

		float[] coordArray = {	-WIDTH, HEIGHT, LENGTH,
										WIDTH, HEIGHT, LENGTH,
										WIDTH, HEIGHT, -LENGTH,
										-WIDTH, HEIGHT, -LENGTH
									};

		quadArray.setCoordinates( 0, coordArray, 0, coordArray.length/3 );

		for( int n = 0; n < coordArray.length/3; n++ )
			quadArray.setNormal( n, new Vector3f( 0,1,0 ) );			

		if( (m_nFlags & TEXTURE) == TEXTURE )
		{
			float[] texArray = {	0, 0,
										1, 0,
										1, 1,
										0, 1 };

			quadArray.setTextureCoordinates( 0, 0, texArray, 0, coordArray.length/3 );
			setTexture( app, szTextureFile );
		}

		BranchGroup bg = new BranchGroup( );
		Shape3D shape = new Shape3D( quadArray, app );				
		bg.addChild( shape );

		return bg;
	}
}
