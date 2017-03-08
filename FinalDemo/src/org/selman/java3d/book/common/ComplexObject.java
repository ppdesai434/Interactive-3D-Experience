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

import javax.vecmath.*;
import javax.media.j3d.*;
import java.io.*;
import java.net.*;
import java.awt.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.behaviors.interpolators.*;

public abstract class ComplexObject extends BranchGroup
{	
	protected Group					m_ParentGroup = null;
	protected int					m_nFlags = 0;
	protected BackgroundSound 		m_CollideSound = null;
	protected Component				m_Component = null;
	protected TransformGroup 		m_TransformGroup = null;
	protected TransformGroup 		m_BehaviorTransformGroup = null;

	public static final int			SOUND = 0x001;
	public static final int			GEOMETRY = 0x002;
	public static final int			TEXTURE = 0x004;
	public static final int			COLLISION = 0x008;
	public static final int			COLLISION_SOUND = 0x010;

	public ComplexObject( Component comp, Group group, int nFlags )
	{
		m_ParentGroup = group;
		m_nFlags = nFlags;
		m_Component = comp;
	}

	public Bounds getGeometryBounds( )
	{
		return new BoundingSphere( new Point3d( 0,0,0 ), 100 );
	}

	private MediaContainer loadSoundFile( String szFile )
	{
		try
		{
			File file = new File( System.getProperty( "user.dir" ) );
			URL url = file.toURL( );		

			URL soundUrl = new URL( url, szFile );
			return new MediaContainer( soundUrl );
		}
		catch( Exception e )
		{
			System.err.println( "Error could not load sound file: " + e );
			System.exit( -1 );
		}

		return null;
	}

	protected void setTexture( Appearance app, String szFile )
	{
		Texture tex = new TextureLoader( szFile, m_Component ).getTexture( );
		app.setTexture( tex );
	}

	abstract protected Group createGeometryGroup( Appearance app, Vector3d position, Vector3d scale, String szTextureFile, String szSoundFile );

	protected Group loadGeometryGroup( String szModel, Appearance app )
	throws java.io.FileNotFoundException
	{
		// load the object file
		Scene scene = null;
		Shape3D shape = null;

		// read in the geometry information from the data file
		ObjectFile objFileloader = new ObjectFile( ObjectFile.RESIZE );

		scene = objFileloader.load( szModel );

		// retrieve the Shape3D object from the scene
		BranchGroup branchGroup = scene.getSceneGroup( );
		shape = (Shape3D) branchGroup.getChild( 0 );
		shape.setAppearance( app );

		return branchGroup;
	}


	protected int getSoundLoop( boolean bCollide )
	{
		return 1;
	}

	protected float getSoundPriority( boolean bCollide )
	{
		return 1.0f;
	}

	protected float getSoundInitialGain( boolean bCollide )
	{
		return 1.0f;
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
		return new BoundingSphere( new Point3d( 0,0,0 ), 1.0 );
	}

	protected boolean getSoundReleaseEnable( boolean bCollide )
	{
		return true;
	}

	protected Point2f[] getSoundDistanceGain( boolean bCollide )
	{
		return null;
	}

	protected void setSoundAttributes( Sound sound, boolean bCollide )
	{
		sound.setCapability( Sound.ALLOW_ENABLE_WRITE );
		sound.setCapability( Sound.ALLOW_ENABLE_READ );

		sound.setSchedulingBounds( getSoundSchedulingBounds( bCollide ) );
		sound.setEnable( getSoundInitialEnable( bCollide ) );
		sound.setLoop( getSoundLoop( bCollide ) );
		sound.setPriority( getSoundPriority( bCollide ) );
		sound.setInitialGain( getSoundInitialGain( bCollide ) );

		sound.setContinuousEnable( getSoundContinuousEnable( bCollide ) );
		sound.setReleaseEnable( bCollide );

		if( sound instanceof PointSound )
		{
			PointSound pointSound = (PointSound) sound;
			pointSound.setInitialGain( getSoundInitialGain( bCollide ) );

			Point2f[] gainArray = getSoundDistanceGain( bCollide );

			if( gainArray != null )
				pointSound.setDistanceGain( gainArray );
		}
	}


	public Group createObject( Appearance app,
		Vector3d position, 
		Vector3d scale, 
		String szTextureFile,
		String szSoundFile, 
		String szCollisionSound )
	{
		m_TransformGroup = new TransformGroup( );
		Transform3D t3d = new Transform3D( );

		t3d.setScale( scale );
		t3d.setTranslation( position );

		m_TransformGroup.setTransform( t3d );

		m_BehaviorTransformGroup = new TransformGroup( );

		if( (m_nFlags & GEOMETRY) == GEOMETRY)
			m_BehaviorTransformGroup.addChild( createGeometryGroup( app, position, scale, szTextureFile, szSoundFile ) );

		if( (m_nFlags & SOUND) == SOUND)
		{
			MediaContainer media = loadSoundFile( szSoundFile );
			PointSound pointSound  = new PointSound( media, getSoundInitialGain( false ), 0, 0, 0 );
			setSoundAttributes( pointSound, false );			
			m_BehaviorTransformGroup.addChild( pointSound );
		}

		if( (m_nFlags & COLLISION) == COLLISION)
		{	
			m_BehaviorTransformGroup.setCapability( Node.ENABLE_COLLISION_REPORTING );
			m_BehaviorTransformGroup.setCollidable( true );
			m_BehaviorTransformGroup.setCollisionBounds( getGeometryBounds( ) );

			if( (m_nFlags & COLLISION_SOUND) == COLLISION_SOUND )
			{	
				MediaContainer collideMedia = loadSoundFile( szCollisionSound );

				m_CollideSound = new BackgroundSound( collideMedia, 1 );
				setSoundAttributes( m_CollideSound, true );
				m_TransformGroup.addChild( m_CollideSound );
			}

			CollisionBehavior collision = new CollisionBehavior( m_BehaviorTransformGroup, this );
			collision.setSchedulingBounds( getGeometryBounds( ) );

			m_BehaviorTransformGroup.addChild( collision );
		}

		m_TransformGroup.addChild( m_BehaviorTransformGroup );
		m_ParentGroup.addChild( m_TransformGroup );

		return m_BehaviorTransformGroup;
	}	

	public void onCollide( boolean bCollide )
	{
		System.out.println( "Collide: " + bCollide );

		if( m_CollideSound != null && bCollide == true )
			m_CollideSound.setEnable( true );
	}

	public void attachBehavior( Behavior beh )
	{
		m_BehaviorTransformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		beh.setSchedulingBounds( getGeometryBounds( ) );
		m_BehaviorTransformGroup.addChild( beh );
	}

	public TransformGroup getBehaviorTransformGroup( )
	{
		return m_BehaviorTransformGroup;
	}

	public void attachSplinePathInterpolator( Alpha alpha, Transform3D axis, URL urlKeyframes )
	{
		// read a spline path definition file and 
		// add a Spline Path Interpolator to the TransformGroup for the object.

		m_BehaviorTransformGroup.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

		RotPosScaleTCBSplinePathInterpolator splineInterpolator = 
		Utils.createSplinePathInterpolator( alpha, m_BehaviorTransformGroup, axis, urlKeyframes );

		if( splineInterpolator != null )
		{
			splineInterpolator.setSchedulingBounds( getGeometryBounds( ) );
			m_BehaviorTransformGroup.addChild( splineInterpolator );
		}
		else
		{
			System.out.println( "attachSplinePathInterpolator failed for: " + urlKeyframes );
		}
	}
}
