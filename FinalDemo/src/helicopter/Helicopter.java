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

package helicopter;

import javax.vecmath.*;
import javax.media.j3d.*;
import java.awt.*;
import java.net.*;

import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.geometry.*;
	
import org.selman.java3d.book.common.*;

public class Helicopter extends ComplexObject
{	
	public static final float		WIDTH = 2.0f;
	public static final float		HEIGHT = 2.0f;
	public static final float		LENGTH = 2.0f;

	public Helicopter( Component comp, Group g, int nFlags )
	{
		super( comp, g, nFlags );		
	}

	protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile )
	{						
		TransformGroup tg = new TransformGroup( );

		// we need to flip the helicopter model 
		// 90 degrees about the X axis
		Transform3D t3d = new Transform3D( );
		t3d.rotX( Math.toRadians( -90 ) );
		tg.setTransform( t3d );

		try
		{
			tg.addChild( loadGeometryGroup( "heli.obj", app ) );

			// create an Alpha object for the Interpolator
			Alpha alpha = new Alpha( -1,
				Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
				(long) Utils.getRandomNumber( 0, 500 ),
				(long)Utils.getRandomNumber( 0, 500 ),
				(long)Utils.getRandomNumber( 20000, 5000 ),
				4000,
				100,
				(long) Utils.getRandomNumber( 20000, 5000 ),
				5000,
				50 );

			attachSplinePathInterpolator( alpha, 
				new Transform3D( ),
				new URL( ((Java3dApplet) m_Component).getWorkingDirectory( ), "heli_spline.xls" ) );
		}
		catch( Exception e )
		{
			System.err.println( e.toString( ) );
		}

		return tg;
	}

	protected int getSoundLoop( boolean bCollide )
	{
		return -1;
	}

	protected float getSoundPriority( boolean bCollide )
	{
		return 1.0f;
	}

	protected float getSoundInitialGain( boolean bCollide )
	{
		return 3.0f;
	}

	protected Point2f[] getSoundDistanceGain( boolean bCollide )
	{
		Point2f[] gainArray = new Point2f[2];

		gainArray[0] = new Point2f( 2, 0.2f );
		gainArray[1] = new Point2f( 20, 0.05f );

		return gainArray;
	}

	protected boolean getSoundInitialEnable( boolean bCollide )
	{
		return true;
	}

	protected boolean getSoundContinuousEnable( boolean bCollide )
	{
		return false;
	}

	protected Bounds getSoundSchedulingBounds( boolean bCollide )
	{
		return new BoundingSphere( new Point3d( 0,0,0 ), 20 );
	}

	protected boolean getSoundReleaseEnable( boolean bCollide )
	{
		return true;
	}
}
