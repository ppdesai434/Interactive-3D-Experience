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

// creates a 2x2x2 cuboid with its base at y=0
public class Cuboid extends ComplexObject
{		
	public Cuboid( Component comp, Group g, int nFlags )
	{
		super( comp, g, nFlags );		
	}

	protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile )
	{	
		int nFlags = GeometryArray.COORDINATES | GeometryArray.NORMALS;

		if( (m_nFlags & TEXTURE) == TEXTURE )
			nFlags |= GeometryArray.TEXTURE_COORDINATE_2;

		QuadArray quadArray = new QuadArray( 24, nFlags );

		quadArray.setCoordinates( 0, verts, 0, 24 );

		for( int n = 0; n < 24; n++ )
			quadArray.setNormal( n, normals[n/4] );

		if( (m_nFlags & TEXTURE) == TEXTURE )
		{
			quadArray.setTextureCoordinates( 0, 0, tcoords, 0, 24 );
			setTexture( app, szTextureFile );
		}

		Shape3D shape = new Shape3D( quadArray, app );				

		BranchGroup bg = new BranchGroup( );
		bg.addChild( shape );
		return bg;
	}

	private static final float[] verts = {
	// front face
	1.0f, 0.0f,  1.0f,
	1.0f,  2.0f,  1.0f,
	-1.0f,  2.0f,  1.0f,
	-1.0f, 0.0f,  1.0f,
	// back face
	-1.0f, 0.0f, -1.0f,
	-1.0f,  2.0f, -1.0f,
	1.0f,  2.0f, -1.0f,
	1.0f, 0.0f, -1.0f,
	// right face
	1.0f, 0.0f, -1.0f,
	1.0f,  2.0f, -1.0f,
	1.0f,  2.0f,  1.0f,
	1.0f, 0.0f,  1.0f,
	// left face
	-1.0f, 0.0f,  1.0f,
	-1.0f,  2.0f,  1.0f,
	-1.0f,  2.0f, -1.0f,
	-1.0f, 0.0f, -1.0f,
	// top face
	1.0f,  2.0f,  1.0f,
	1.0f,  2.0f, -1.0f,
	-1.0f,  2.0f, -1.0f,
	-1.0f,  2.0f,  1.0f,
	// bottom face
	-1.0f, 0.0f,  1.0f,
	-1.0f, 0.0f, -1.0f,
	1.0f, 0.0f, -1.0f,
	1.0f, 0.0f,  1.0f,
  };

	private static final float[] tcoords = {
	// front
	1.0f, 0.0f,
	1.0f, 1.0f,
	0.0f, 1.0f,
	0.0f, 0.0f,
	// back
	1.0f, 0.0f,
	1.0f, 1.0f,
	0.0f, 1.0f,
	0.0f, 0.0f,
	//right
	1.0f, 0.0f,
	1.0f, 1.0f,
	0.0f, 1.0f,
	0.0f, 0.0f,
	// left
	1.0f, 0.0f,
	1.0f, 1.0f,
	0.0f, 1.0f,
	0.0f, 0.0f,
	// top
	1.0f, 0.0f,
	1.0f, 1.0f,
	0.0f, 1.0f,
	0.0f, 0.0f,
	// bottom
	0.0f, 1.0f,
	0.0f, 0.0f,
	1.0f, 0.0f,
	1.0f, 1.0f
  };

	private static final Vector3f[] normals = {
	new Vector3f( 0.0f,  0.0f,  1.0f),	// front face
	new Vector3f( 0.0f,  0.0f, -1.0f),	// back face
	new Vector3f( 1.0f,  0.0f,  0.0f),	// right face
	new Vector3f(-1.0f,  0.0f,  0.0f),	// left face
	new Vector3f( 0.0f,  1.0f,  0.0f),	// top face
	new Vector3f( 0.0f, -1.0f,  0.0f),	// bottom face
  };
}
