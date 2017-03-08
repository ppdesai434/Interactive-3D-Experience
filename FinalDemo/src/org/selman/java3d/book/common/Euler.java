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

public class Euler
{
	public static int EulOrdXYZs( )
	{
   	return EulOrd( X,EulParEven,EulRepNo,EulFrmS );
	}
	public static int EulOrdXYXs( )
	{
   	return EulOrd( X,EulParEven,EulRepYes,EulFrmS );
	}
	public static int EulOrdXZYs( )
	{
   	return EulOrd( X,EulParOdd,EulRepNo,EulFrmS );
	}
	public static int EulOrdXZXs( )
	{
   	return EulOrd( X,EulParOdd,EulRepYes,EulFrmS );
	}
	public static int EulOrdYZXs( )
	{
   	return EulOrd( Y,EulParEven,EulRepNo,EulFrmS );
	}
	public static int EulOrdYZYs( )
	{
   	return EulOrd( Y,EulParEven,EulRepYes,EulFrmS );
	}
	public static int EulOrdYXZs( )
	{
   	return EulOrd( Y,EulParOdd,EulRepNo,EulFrmS );
	}
	public static int EulOrdYXYs( )
	{
   	return EulOrd( Y,EulParOdd,EulRepYes,EulFrmS );
	}
	public static int EulOrdZXYs( )
	{
   	return EulOrd( Z,EulParEven,EulRepNo,EulFrmS );
	}
	public static int EulOrdZXZs( )
	{
   	return EulOrd( Z,EulParEven,EulRepYes,EulFrmS );
	}
	public static int EulOrdZYXs( )
	{
   	return EulOrd( Z,EulParOdd,EulRepNo,EulFrmS );
	}
	public static int EulOrdZYZs( )
	{
   	return EulOrd( Z,EulParOdd,EulRepYes,EulFrmS );
	}

	/* Rotating axes */
	public static int EulOrdZYXr( )
	{
   	return EulOrd( X,EulParEven,EulRepNo,EulFrmR );
	}
	public static int EulOrdXYXr( )
	{
   	return EulOrd( X,EulParEven,EulRepYes,EulFrmR );
	}
	public static int EulOrdYZXr( )
	{
   	return EulOrd( X,EulParOdd,EulRepNo,EulFrmR );
	}
	public static int EulOrdXZXr( )
	{
   	return EulOrd( X,EulParOdd,EulRepYes,EulFrmR );
	}
	public static int EulOrdXZYr( )
	{
   	return EulOrd( Y,EulParEven,EulRepNo,EulFrmR );
	}
	public static int EulOrdYZYr( )
	{
   	return EulOrd( Y,EulParEven,EulRepYes,EulFrmR );
	}
	public static int EulOrdZXYr( )
	{
   	return EulOrd( Y,EulParOdd,EulRepNo,EulFrmR );
	}
	public static int EulOrdYXYr( )
	{
   	return EulOrd( Y,EulParOdd,EulRepYes,EulFrmR );
	}
	public static int EulOrdYXZr( )
	{
   	return EulOrd( Z,EulParEven,EulRepNo,EulFrmR );
	}
	public static int EulOrdZXZr( )
	{
   	return EulOrd( Z,EulParEven,EulRepYes,EulFrmR );
	}
	public static int EulOrdXYZr( )
	{
   	return EulOrd( Z,EulParOdd,EulRepNo,EulFrmR );
	}
	public static int EulOrdZYZr( )
	{
   	return EulOrd( Z,EulParOdd,EulRepYes,EulFrmR );
	}

	public static int EulFrm( int ord )
	{
		// DCS, was unsigned
		return ( (ord)&1 );
	}

	public static int EulRep( int ord )
	{
		// DCS, was unsigned
		return ( ((ord)>>1)&1 );
	}

	public static int EulPar( int ord )
	{
		return ( ((ord)>>2)&1 );
	}

	public static int EulAxI( int ord )
	{
		// DCS, was unsigned
		return ( (int)(EulSafe( (((ord)>>3)&3) )) );
	}

	public static int EulAxJ( int ord )
	{
		int i = 0;

		if( EulPar( ord )==EulParOdd )
			i = 1;

		return ( (int)(EulNext( EulAxI( ord )+i )) );
	}

	public static int EulAxK( int ord )
	{
		int i = 0;

		if( EulPar( ord )!=EulParOdd )
			i = 1;

		return ( (int)(EulNext( EulAxI( ord )+i )) );
	}

	public static int EulAxH( int ord )
	{
		if( EulRep( ord )==EulRepNo )
			return EulAxK( ord );

		return EulAxI( ord );
	}

	public static int EulOrd( int i, int p, int r, int f )
	{
		return ( ((((((i)<<1)+(p))<<1)+(r))<<1)+(f) );
	}

	// enum
	static final int X = 0;
	static final int Y = 1;
	static final int Z = 2;
	static final int W = 3;

	static final int EulRepNo = 0;
	static final int EulRepYes = 1;

	static final int EulParEven = 0;
	static final int EulParOdd = 1;

	static final int EulFrmS = 0;
	static final int EulFrmR = 1;

	static final float FLT_EPSILON = 1.192092896e-07F;

	static EulGetOrdInfo EulGetOrd( int ord )
	{
		EulGetOrdInfo info = new EulGetOrdInfo( );

		// note, used to be unsigned!
		int o = ord;
		info.f=o&1;o>>=1;
		info.s=o&1;o>>=1;
		info.n=o&1;o>>=1;
		info.i=EulSafe( o&3 );
		info.j=EulNext( info.i+info.n );
		info.k=EulNext( info.i+1-info.n );

		if( info.s != 0 )
			info.h = info.k;
		else
			info.h = info.i;

		return info;
	}

	static int EulSafe( int val )
	{
		int[] valArray = {0,1,2,0};
		return valArray[val];
	}

	static int EulNext( int val )
	{
		int[] valArray = {1,2,0,1};
		return valArray[val];
	}

	// float HMatrix[4][4];

	/* Convert matrix to Euler angles (in radians). */
	public static EulerAngles Eul_FromMatrix( float[][] M, int order )
	{
		EulerAngles ea = new EulerAngles( );

		EulGetOrdInfo info = EulGetOrd( order );

		int i = info.i;
		int j = info.j;
		int k = info.k;
		int h = info.h;
		int n = info.n;
		int s = info.s;
		int f = info.f;

		if (s==EulRepYes)
		{
			double sy = Math.sqrt( M[i][j]*M[i][j] + M[i][k]*M[i][k] );
			if (sy > 16*FLT_EPSILON)
			{
				ea.x = (float) Math.atan2( M[i][j], M[i][k] );
				ea.y = (float) Math.atan2( sy, M[i][i] );
				ea.z = (float) Math.atan2( M[j][i], -M[k][i] );
			}
			else
			{
				ea.x = (float) Math.atan2( -M[j][k], M[j][j] );
				ea.y = (float) Math.atan2( sy, M[i][i] );
				ea.z = 0;
			}
		}
		else
		{
			double cy = Math.sqrt( M[i][i]*M[i][i] + M[j][i]*M[j][i] );
			if (cy > 16*FLT_EPSILON)
			{
				ea.x = (float) Math.atan2( M[k][j], M[k][k] );
				ea.y = (float) Math.atan2( -M[k][i], cy );
				ea.z = (float) Math.atan2( M[j][i], M[i][i] );
			}
			else
			{
				ea.x = (float) Math.atan2( -M[j][k], M[j][j] );
				ea.y = (float) Math.atan2( -M[k][i], cy );
				ea.z = 0;
			}
		}
		if (n==EulParOdd)
		{ea.x = -ea.x; ea.y = - ea.y; ea.z = -ea.z;
		}
		if (f==EulFrmR)
		{float t = ea.x; ea.x = ea.z; ea.z = t;
		}
		ea.w = order;
		return ( ea );
	}


	/* Convert quaternion to Euler angles (in radians). */
	public static EulerAngles Eul_FromQuat( Quat q, int order )
	{
		float[][] M = new float[4][4];
		double Nq = q.x*q.x+q.y*q.y+q.z*q.z+q.w*q.w;
		double s = (Nq > 0.0) ? (2.0 / Nq) : 0.0;
		double xs = q.x*s,	  ys = q.y*s,	 zs = q.z*s;
		double wx = q.w*xs,	  wy = q.w*ys,	 wz = q.w*zs;
		double xx = q.x*xs,	  xy = q.x*ys,	 xz = q.x*zs;
		double yy = q.y*ys,	  yz = q.y*zs,	 zz = q.z*zs;
		M[X][X] = (float) (1.0 - (yy + zz)); 
		M[X][Y] = (float) (xy - wz ); 
		M[X][Z] = (float) (xz + wy );
		M[Y][X] = (float) (xy + wz ); 
		M[Y][Y] = (float) (1.0 - (xx + zz) ); 
		M[Y][Z] = (float) (yz - wx );
		M[Z][X] = (float) (xz - wy ); 
		M[Z][Y] = (float) (yz + wx ); 
		M[Z][Z] = (float) (1.0 - (xx + yy) );
		M[W][X]=M[W][Y]=M[W][Z]=M[X][W]=M[Y][W]=M[Z][W]=0.0f; M[W][W]=1.0f;
		return ( Eul_FromMatrix( M, order ) );
	}

	public static Point3d getEulerRotation( Transform3D t3d )
	{
		Point3d Rotation = new Point3d( );

		Matrix3d m1 = new Matrix3d( );
		t3d.get( m1 );

		// extract the rotation angles from the upper 3x3 rotation
		// component of the 4x4 transformation matrix
		Rotation.y = -java.lang.Math.asin( m1.getElement( 2, 0 ) );
		double c = java.lang.Math.cos( Rotation.y );
		double tRx, tRy, tRz;

		if( java.lang.Math.abs( Rotation.y ) > 0.00001 )
		{
			tRx = m1.getElement( 2, 2 ) / c;
			tRy = -m1.getElement( 2, 1 )  / c;

			Rotation.x = java.lang.Math.atan2( tRy, tRx );

			tRx = m1.getElement( 0, 0 ) / c;
			tRy = -m1.getElement( 1, 0 ) / c;

			Rotation.z = java.lang.Math.atan2( tRy, tRx );
		}
		else
		{
			Rotation.x  = 0.0;

			tRx = m1.getElement( 1, 1 );
			tRy = m1.getElement( 0, 1 );

			Rotation.z = java.lang.Math.atan2( tRy, tRx );
		}

		Rotation.x = -Rotation.x;
		Rotation.z = -Rotation.z;

		// now try to ensure that the values are positive by adding 2PI if necessary...		
		if( Rotation.x < 0.0 )
			Rotation.x += 2 * java.lang.Math.PI;

		if( Rotation.y < 0.0 )
			Rotation.y += 2 * java.lang.Math.PI;

		if( Rotation.z < 0.0 )
			Rotation.z += 2 * java.lang.Math.PI;

		return Rotation;
	}
}
