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

import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.interpolators.*;

//*****************************************************************************
/**
*	Utils
*
*	@author Daniel Selman
*	@version 1.0
*/
//*****************************************************************************

public class Utils
{
	// convert an angular rotation about an axis to a Quaternion
	static Quat4f createQuaternionFromAxisAndAngle( Vector3d axis, double angle )
	{
		double sin_a = Math.sin( angle / 2 );
		double cos_a = Math.cos( angle / 2 );

		// use a vector so we can call normalize
		Vector4f q = new Vector4f( );

		q.x = (float) (axis.x * sin_a);
		q.y = (float) (axis.y * sin_a);
		q.z = (float) (axis.z * sin_a); 
		q.w = (float) cos_a;

		// It is necessary to normalise the quaternion 
		// in case any values are very close to zero.
		q.normalize( );

		// convert to a Quat4f and return
		return new Quat4f( q );
	}

	// convert three rotations about the Euler axes to a Quaternion
	static Quat4f createQuaternionFromEuler( double angleX, double angleY, double angleZ )
	{
		// simply call createQuaternionFromAxisAndAngle
		// for each axis and multiply the results
		Quat4f qx = createQuaternionFromAxisAndAngle( new Vector3d( 1,0,0 ), angleX );
		Quat4f qy = createQuaternionFromAxisAndAngle( new Vector3d( 0,1,0 ), angleY );
		Quat4f qz = createQuaternionFromAxisAndAngle( new Vector3d( 0,0,1 ), angleZ );

		// qx = qx * qy
		qx.mul( qy );

		// qx = qx * qz
		qx.mul( qz );

		return qx;
	}

	static public double getRandomNumber( double basis, double random )
	{
		return basis + ( (float) Math.random( ) * random * 2f ) - (random);
	}

	static public double getRandomNumber( double basis, double random, double scale )
	{
		double value = basis + ( (float) Math.random( ) * random * 2f) - (random);
		return value * scale;
	}


	static public StringBuffer readFile( URL urlFile )
	{
		// allocate a temporary buffer to store the input file
		StringBuffer szBufferData = new StringBuffer( );
		Vector keyFramesVector = new Vector( );

		try
		{
			InputStream inputStream = urlFile.openStream( );

			int nChar = 0;

			// read the entire file into the StringBuffer
			while( true )
			{
				nChar = inputStream.read( );

				// if we have not hit the end of file 
				// add the character to the StringBuffer
				if( nChar != -1 )
					szBufferData.append( (char) nChar );
				else
					// EOF
					break;
			}

			inputStream.close( );
		}
		catch( Exception e )
		{
			System.err.println( e.toString( ) );
			return null;
		}

		return szBufferData;
	}

	static public RotPosScaleTCBSplinePathInterpolator createSplinePathInterpolator( Alpha alpha, TransformGroup tg, Transform3D axis, URL urlKeyframes )
	{
		TCBKeyFrame[] keyFrames = readKeyFrames( urlKeyframes );

		if( keyFrames != null )
			return new RotPosScaleTCBSplinePathInterpolator( alpha, tg, axis, keyFrames );

		return null;
	}

	static public TCBKeyFrame[] readKeyFrames( URL urlKeyframes )
	{
		StringBuffer szBufferData = readFile( urlKeyframes );

		if( szBufferData == null )
			return null;

		Vector keyFramesVector = new Vector( );

		// create a tokenizer to tokenize the input file at whitespace
		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer( szBufferData.toString( ) );

		// each keyframe is defined as follows
		// - knot (0 >= k <= 1)
		// - position (x,y,z)
		// - rotation (rx,ry,rz)
		// - scale (x,y,z)

		// - tension (-1 >= t <= 1)
		// - continuity (-1 >= c <= 1)
		// - bias (-1 >= b <= 1)
		// - linear (int - 0 or 1)

		while( true )
		{
			try
			{
				float knot = Float.parseFloat( tokenizer.nextToken( ) );

				float posX = Float.parseFloat( tokenizer.nextToken( ) );
				float posY = Float.parseFloat( tokenizer.nextToken( ) );
				float posZ = Float.parseFloat( tokenizer.nextToken( ) );

				float rotX = Float.parseFloat( tokenizer.nextToken( ) );
				float rotY = Float.parseFloat( tokenizer.nextToken( ) );
				float rotZ = Float.parseFloat( tokenizer.nextToken( ) );

				float scaleX = Float.parseFloat( tokenizer.nextToken( ) );
				float scaleY = Float.parseFloat( tokenizer.nextToken( ) );
				float scaleZ = Float.parseFloat( tokenizer.nextToken( ) );

				float tension = Float.parseFloat( tokenizer.nextToken( ) );
				float continuity = Float.parseFloat( tokenizer.nextToken( ) );
				float bias = Float.parseFloat( tokenizer.nextToken( ) );

				int linear = Integer.parseInt( tokenizer.nextToken( ) );

				TCBKeyFrame keyframe = new TCBKeyFrame( knot,
					linear,
					new Point3f( posX, posY, posZ ),
					createQuaternionFromEuler( rotX, rotY, rotZ ),
					new Point3f( scaleX, scaleY, scaleZ ),
					tension,
					continuity,
					bias );

				keyFramesVector.add( keyframe );
			}
			catch( Exception e )
			{
				break;
			}
		}

		// create the return structure and populate
		TCBKeyFrame[] keysReturn = new TCBKeyFrame[ keyFramesVector.size( ) ];

		for( int n = 0; n < keysReturn.length; n++ )
			keysReturn[n] = (TCBKeyFrame) keyFramesVector.get( n );

		// return the array
		return keysReturn;
	}
}
