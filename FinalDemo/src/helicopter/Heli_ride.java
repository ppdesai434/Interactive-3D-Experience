/**********************************************************
  Copyright (C) 2014 Parth Desai

  This program is free software; you can redistribute it and/or
  modify it under the terms of the GNU General Public License
  as published by the Free Software Foundation, version 2.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  The license can be found on the WWW at:
  http://www.fsf.org/copyleft/gpl.html


  Authors can be contacted at:
   Desai Parth: contact@desaiparth.com

  If you make changes you think others would like, please 
  contact one of the authors or someone at the 
  www.desaiparth.com web site.
**************************************************************/

package helicopter;


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.awt.image.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;
import com.sun.j3d.utils.behaviors.interpolators.*;
import com.sun.j3d.audioengines.javasound.*;

import org.selman.java3d.book.common.*;


public class Heli_ride extends Java3dApplet
{
	// size of the 3D window - enlage on powerful systems
	private static int 			m_kWidth = 800;
	private static int 			m_kHeight = 600;

	// a shared appearance for the buildings we create
	private Appearance 			m_BuildingAppearance = null;

	private static final float	LAND_WIDTH = 180;
	private static final float	LAND_LENGTH = 180;

	// the satellite images used as textures have
	// been manually edited so that the water in the
	// images corresponds to the following RGB values.
	// this allows the application to avoid creating
	// buildings in the water!
	private static final float	WATER_COLOR_RED = 0;
	private static final float	WATER_COLOR_GREEN = 57;
	private static final float	WATER_COLOR_BLUE = 123;

	public Heli_ride( )
	{
		initJava3d( );
	}

	// scale eveything so we can use pixel coordinates
	protected double getScale( )
	{
		return 0.1;
	}

	protected int getCanvas3dWidth( Canvas3D c3d )
	{
		return m_kWidth;
	}

	protected int getCanvas3dHeight( Canvas3D c3d )
	{
		return m_kHeight;
	}

	protected Bounds createApplicationBounds( )
	{
		m_ApplicationBounds = new BoundingSphere( new Point3d( 0.0,0.0,0.0 ), 10.0 );
		return m_ApplicationBounds;
	}

	// we want a texture mapped background of a sky
	protected Background createBackground( )
	{
		// add the sky backdrop
		Background back = new Background( );
		back.setApplicationBounds( getApplicationBounds( ) );

		BranchGroup bgGeometry = new BranchGroup( );

		// create an appearance and assign the texture image
		Appearance app = new Appearance( );				
		Texture tex = new TextureLoader( "sky.gif", this ).getTexture( );
		app.setTexture( tex );

		Sphere sphere = new Sphere( 1.0f, Primitive.GENERATE_TEXTURE_COORDS | Primitive.GENERATE_NORMALS_INWARD, app );

		bgGeometry.addChild( sphere );
		back.setGeometry( bgGeometry );

		return back;
	}

	// this controls how close to a helicopter we can
	// be and still hear it. If the helicopters sound
	// scheduling bounds intersect our ViewPlatformActivationRadius
	// the sound of the helicopter is potentially audible.
	protected float getViewPlatformActivationRadius( )
	{
		return 20;
	}

	// creates the objects within our world
	protected BranchGroup createSceneBranchGroup( )
	{
		BranchGroup objRoot = super.createSceneBranchGroup( );

		// create a root TG in case we need to scale the scene
		TransformGroup objTrans = new TransformGroup( );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );

		Transform3D t3d = new Transform3D( );
		objTrans.setTransform( t3d );

		Group hiResGroup = createLodLand( objTrans );
		createBuildings( objTrans );
		createHelicopters( objTrans );

		// connect
		objRoot.addChild( objTrans );

		return objRoot;
	}

	// we create 2 TransformGroups above the ViewPlatform:
	// the first merely applies a scale, while the second
	// has a RotPosScaleTCBSplinePathInterpolator attached
	// so that the viewer of the scene is animated along
	// a spline curve.
	public TransformGroup[] getViewTransformGroupArray( )
	{
		TransformGroup[] tgArray = new TransformGroup[2];
		tgArray[0] = new TransformGroup( );
		tgArray[1] = new TransformGroup( );

		Transform3D t3d = new Transform3D( );
		t3d.setScale( getScale( ) );
		t3d.invert( );
		tgArray[0].setTransform( t3d );		

		// create an Alpha object for the Interpolator
		Alpha alpha = new Alpha( -1,
			Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE,
			0,
			0,
			25000,
			4000,
			100,
			20000,
			5000,
			50 );

		// ensure the Interpolator can access the TG
		tgArray[1].setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

		try
		{
			// create the Interpolator and load the keyframes from disk
			RotPosScaleTCBSplinePathInterpolator splineInterpolator = 
				Utils.createSplinePathInterpolator( new UiAlpha( alpha ),
				tgArray[1], 
				new Transform3D( ), 
				new URL( getWorkingDirectory( ), "rotate_viewer_spline.xls" ) );

			// set the scheduling bounds and attach to the scenegraph
			splineInterpolator.setSchedulingBounds( getApplicationBounds( ) );
			tgArray[1].addChild( splineInterpolator );
		}
		catch( Exception e )
		{
			System.err.println( e.toString( ) );
		}

		return tgArray;
	}

	// overidden so that the example can use audio
	protected AudioDevice createAudioDevice( PhysicalEnvironment pe )
	{
		return new JavaSoundMixer( pe );
	}	

	// creates a Switch group that contains two versions
	// of the world - the first is a high resolution version,
	// the second if a lower resolution version.
	public Group createLodLand( Group g )
	{	
		Switch switchNode = new Switch( );
		switchNode.setCapability( Switch.ALLOW_SWITCH_WRITE );

		Group hiResGroup = createLand( switchNode );
		createEnvirons( switchNode );

		// create a DistanceLOD that will select the child of
		// the Switch node based on distance. Here we are selecting
		// child 0 (high res) if we are closer than 180 units to
		// 0,0,0 and child 1 (low res) otherwise.
		
		float[] distanceArray = {180};

		DistanceLOD distanceLod = new DistanceLOD( distanceArray );
		distanceLod.setSchedulingBounds( getApplicationBounds( ) );
		distanceLod.addSwitch( switchNode );

		g.addChild( distanceLod );
		g.addChild( switchNode );

		return hiResGroup;
	}


	// creates a high resolution representation of the world.
	// a single texture mapped square and a larger (water colored)
	// square to act as a horizon.
	
	public Group createLand( Group g )
	{	
		Land land = new Land( this, g, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		Group hiResGroup = land.createObject( new Appearance( ), new Vector3d( ), new Vector3d( LAND_WIDTH,1,LAND_LENGTH ) , "boston.gif", null, null );

		Appearance app = new Appearance( );
		app.setColoringAttributes( new ColoringAttributes( WATER_COLOR_RED/255f, WATER_COLOR_GREEN/255f,WATER_COLOR_BLUE/255f, ColoringAttributes.FASTEST ) );

		Land base = new Land( this, hiResGroup, ComplexObject.GEOMETRY );
		base.createObject( app, new Vector3d( 0,-5,0 ), new Vector3d( 4 * LAND_WIDTH,1,4 * LAND_LENGTH ), null, null, null );

		return hiResGroup;
	}

	// creates a low resolution version of the world and
	// applies the low resolution satellite image
	
	public Group createEnvirons( Group g )
	{	
		Land environs = new Land( this, g, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );		
		return environs.createObject( new Appearance( ), new Vector3d( ), new Vector3d( 2 * LAND_WIDTH,1, 2 * LAND_LENGTH ) , "environs.gif", null, null );
	}

	// returns true if the given x,z location in the world 
	// corresponds to water in the satellite image
	
	protected boolean isLocationWater( BufferedImage image, float posX, float posZ )
	{
		Color color = null;

		float imageWidth = image.getWidth( );
		float imageHeight = image.getHeight( );

		// range from 0 to 1
		float nPixelX = (posX + LAND_WIDTH)/(2 * LAND_WIDTH);
		float nPixelY = (posZ + LAND_LENGTH)/(2 * LAND_LENGTH);

		// rescale
		nPixelX *= imageWidth;
		nPixelY *= imageHeight;

		if( nPixelX >= 0 && nPixelX < imageWidth && nPixelY >= 0 && nPixelY < imageHeight )
		{
			color = new Color( image.getRGB( (int) nPixelX, (int) nPixelY ) );							
			return ( color.getBlue( ) >= WATER_COLOR_BLUE && color.getGreen( ) <= WATER_COLOR_GREEN && color.getRed( ) <= WATER_COLOR_RED );
		}

		return false;
	}

	// creates up to 120 building objects - ensures that 
	// buildings are not positioned over water.
	
	public Group createBuildings( Group g )
	{
		m_BuildingAppearance = new Appearance( );
		BranchGroup bg = new BranchGroup( );

		Texture tex = new TextureLoader( "boston.gif", this ).getTexture( );
		BufferedImage image = ((ImageComponent2D) tex.getImage( 0 )).getImage( );

		final int nMaxBuildings = 120;

		for( int n = 0; n < nMaxBuildings; n++ )
		{
			Cuboid building = new Cuboid( this, bg, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );

			float posX = (int) Utils.getRandomNumber( 0, LAND_WIDTH );
			float posZ = (int) Utils.getRandomNumber( 0, LAND_LENGTH );

			if( isLocationWater( image, posX, posZ ) == false )
			{
				building.createObject( m_BuildingAppearance, 
					new Vector3d( posX,
					0,
					posZ ),
					new Vector3d( Utils.getRandomNumber( 3, 2 ),
					Utils.getRandomNumber( 8, 7 ),
					Utils.getRandomNumber( 3, 2 ) ),
					"house.gif",
					null,
					null );
			}
		}

		g.addChild( bg );
		return bg;
	}

	// creates three helicopters
	
	public void createHelicopters( Group g )
	{
		for( int n = 0; n < 3; n++ )
			createHelicopter( g );
	}

	// edit the positions of the clipping
	// planes so we don't clip on the front 
	// plane prematurely
	protected double getBackClipDistance( )
	{
		return 50.0;
	}

	protected double getFrontClipDistance( )
	{
		return 0.1;
	}

	// creates a single helicopter object
	public Group createHelicopter( Group g )
	{
		BranchGroup bg = new BranchGroup( );

		Helicopter heli = new Helicopter( this, bg, ComplexObject.GEOMETRY | ComplexObject.SOUND );

		heli.createObject( new Appearance( ),
			new Vector3d( Utils.getRandomNumber( 0, LAND_WIDTH ),
			Utils.getRandomNumber( 15, 5 ),
			Utils.getRandomNumber( 0, LAND_LENGTH ) ),
			new Vector3d( 10,10,10 ),
			null,
			"heli.wav",
			null );

		g.addChild( bg );

		return bg;
	}

	public static void main( String[] args )
	{
		Heli_ride splineInterpolatorTest = new Heli_ride( );
		splineInterpolatorTest.saveCommandLineArguments( args );

		new MainFrame( splineInterpolatorTest, m_kWidth, m_kHeight );
	}
}
