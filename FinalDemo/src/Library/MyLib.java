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

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.*;

import org.selman.java3d.book.common.*;

/**
* Simple DOOM style navigation of a 3D scene using Java 3D.
* The scene description is loaded from a GIF file where different
* colors denote objects in the 3D scene. The example includes:
* simple (manual) collision detection, texture animation, keyboard
* navigation.
*/
public class MyLib extends Java3dApplet implements CollisionDetector
{
	private static int 				m_kWidth = 300;
	private static int 				m_kHeight = 300;

	private static final String		m_szMapName = new String( "small_map.gif" );

	private float					FLOOR_WIDTH = 256;
	private float					FLOOR_LENGTH = 256;

	private final int 				m_ColorWall = new Color( 0,0,0 ).getRGB( );
	private final int 				m_ColorGuard = new Color( 255,0,0 ).getRGB( );
	private final int 				m_ColorLight = new Color( 255,255,0 ).getRGB( );
	private final int 				m_ColorBookcase = new Color( 0,255,0 ).getRGB( );
	private final int 				m_ColorWater = new Color( 0,0,255 ).getRGB( );

	private Vector3d				m_MapSquareSize = null;
	private Appearance				m_WallAppearance = null;
	private Appearance				m_GuardAppearance = null;
	private Appearance				m_BookcaseAppearance = null;
	private Appearance				m_WaterAppearance = null;

	private BufferedImage			m_MapImage = null;

	private final double			m_kFloorLevel = -20;
	private final double			m_kCeilingHeight = 50;
	private final double			m_kCeilingLevel = m_kFloorLevel + m_kCeilingHeight;

	private Vector3d				m_Translation = new Vector3d( );	

	public MyLib( )
	{
		initJava3d( );
	}

	protected double getScale( )
	{
		return 0.05;
	}

	protected int getCanvas3dWidth( Canvas3D c3d )
	{
		return m_kWidth - 10;
	}

	protected int getCanvas3dHeight( Canvas3D c3d )
	{
		return m_kHeight - 10;
	}

	// edit the positions of the clipping
	// planes so we don't clip on the front 
	// plane prematurely
	protected double getBackClipDistance( )
	{
		return 20.0;
	}

	protected double getFrontClipDistance( )
	{
		return 0.05;
	}


	// we create 2 TransformGroups above the ViewPlatform:
	// the first merely applies a scale, while the second is 
	// manipulated using the KeyBehavior
	public TransformGroup[] getViewTransformGroupArray( )
	{
		TransformGroup[] tgArray = new TransformGroup[2];
		tgArray[0] = new TransformGroup( );
		tgArray[1] = new TransformGroup( );

		Transform3D t3d = new Transform3D( );
		t3d.setScale( getScale( ) );
		t3d.invert( );
		tgArray[0].setTransform( t3d );		

		// ensure the Behavior can access the TG
		tgArray[1].setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

		// create the KeyBehavior and attach
		KeyCollisionBehavior keyBehavior = new KeyCollisionBehavior( tgArray[1], this );
		keyBehavior.setSchedulingBounds( getApplicationBounds( ) );
		keyBehavior.setMovementRate( 0.7 );
		tgArray[1].addChild( keyBehavior );

		return tgArray;
	}


	protected BranchGroup createSceneBranchGroup( )
	{
		BranchGroup objRoot = super.createSceneBranchGroup( );

		TransformGroup objTrans = new TransformGroup( );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );

		createMap( objTrans );
		createFloor( objTrans );
		createCeiling( objTrans );

		objRoot.addChild( objTrans );

		return objRoot;
	}

	public Group createFloor( Group g )
	{	
		System.out.println( "Creating floor" );

		Group floorGroup = new Group( );
		Land floorTile = null;

		// use a shared Appearance so we only store 1 copy of the texture
		Appearance app = new Appearance( );		
		g.addChild( floorGroup );

		final double kNumTiles = 6;

		for( double x = -FLOOR_WIDTH + FLOOR_WIDTH/(2 * kNumTiles); x < FLOOR_WIDTH; x = x + FLOOR_WIDTH/kNumTiles )
		{
			for( double z = -FLOOR_LENGTH + FLOOR_LENGTH/(2 * kNumTiles); z < FLOOR_LENGTH; z = z + FLOOR_LENGTH/kNumTiles )
			{
				floorTile = new Land( this, g, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
				floorTile.createObject( app, 
					new Vector3d( x,m_kFloorLevel,z ), 
					new Vector3d( FLOOR_WIDTH/(2*kNumTiles),1, FLOOR_LENGTH/(2*kNumTiles) ),
					"floor.gif", null, null );
			}
		}

		return floorGroup;
	}

	public Group createCeiling( Group g )
	{	
		System.out.println( "Creating ceiling" );

		Group ceilingGroup = new Group( );
		Land ceilingTile = null;

		// because we are technically viewing the ceiling from below
		// we want to switch the normals using a PolygonAttributes.
		Appearance app = new Appearance( );
		app.setPolygonAttributes( new PolygonAttributes( PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0, false ) );

		g.addChild( ceilingGroup );

		final double kNumTiles = 6;

		for( double x = -FLOOR_WIDTH + FLOOR_WIDTH/(2 * kNumTiles); x < FLOOR_WIDTH; x = x + FLOOR_WIDTH/kNumTiles )
		{
			for( double z = -FLOOR_LENGTH + FLOOR_LENGTH/(2 * kNumTiles); z < FLOOR_LENGTH; z = z + FLOOR_LENGTH/kNumTiles )
			{
				ceilingTile = new Land( this, g, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
				ceilingTile.createObject( app, 
					new Vector3d( x,m_kCeilingLevel,z ), 
					new Vector3d( FLOOR_WIDTH/(2*kNumTiles),1, FLOOR_LENGTH/(2*kNumTiles) ) , 
					"ceiling.gif", null, null );
			}
		}

		return ceilingGroup;
	}

	Point3d convertToWorldCoordinate( int nPixelX, int nPixelY )
	{
		Point3d point3d = new Point3d( );
		Vector3d squareSize = getMapSquareSize( );

		// range from 0 to 1
		point3d.x = nPixelX * squareSize.x;
		point3d.x -= FLOOR_WIDTH;

		point3d.z = nPixelY * squareSize.z;
		point3d.z -= FLOOR_LENGTH;

		point3d.y = 0;

		return point3d;
	}


	Point3d convertToWorldCoordinatesPixelCenter( int nPixelX, int nPixelY )
	{
		Point3d point3d = convertToWorldCoordinate( nPixelX, nPixelY );
		Vector3d squareSize = getMapSquareSize( );

		point3d.x += squareSize.x/2;
		point3d.z += squareSize.z/2;

		return point3d;
	}


	Point2d convertToMapCoordinate( Vector3d worldCoord )
	{
		Point2d point2d = new Point2d( );

		Vector3d squareSize = getMapSquareSize( );

		point2d.x = (worldCoord.x + FLOOR_WIDTH)/ squareSize.x;
		point2d.y = (worldCoord.z + FLOOR_LENGTH)/ squareSize.z;		

		return point2d;					
	}

	public boolean isCollision( Transform3D t3d, boolean bViewSide )
	{
		// get the translation
		t3d.get( m_Translation );

		// we need to scale up by the scale that was
		// applied to the root TG on the view side of the scenegraph
		if( bViewSide != false )
			m_Translation.scale( 1.0 / getScale( ) );

		Vector3d mapSquareSize = getMapSquareSize( );

		// first check that we are still inside the "world"
		if( 	m_Translation.x < -FLOOR_WIDTH + mapSquareSize.x ||
			m_Translation.x > FLOOR_WIDTH - mapSquareSize.x ||
			m_Translation.y < -FLOOR_LENGTH + mapSquareSize.y ||
			m_Translation.y > FLOOR_LENGTH - mapSquareSize.y )
			return true;

		if( bViewSide != false )
			// then do a pixel based look up using the map
			return isCollision( m_Translation );

		return false;
	}

	// returns true if the given x,z location in the world corresponds to a wall section
	protected boolean isCollision( Vector3d worldCoord )
	{
		Point2d point = convertToMapCoordinate( worldCoord );
		int nImageWidth = m_MapImage.getWidth( );
		int nImageHeight = m_MapImage.getHeight( );

		// outside of image
		if( point.x < 0 || point.x >= nImageWidth ||
			point.y < 0 || point.y >= nImageHeight )
			return true;

		int color = m_MapImage.getRGB( (int) point.x, (int) point.y );

		// we can't walk through walls or bookcases
		return( color == m_ColorWall || color == m_ColorBookcase );		
	}

	public Group createMap( Group g )
	{	
		System.out.println( "Creating map items" );

		Group mapGroup = new Group( );
		g.addChild( mapGroup );

		Texture tex = new TextureLoader( m_szMapName, this ).getTexture( );
		m_MapImage = ((ImageComponent2D) tex.getImage( 0 )).getImage( );

		float imageWidth = m_MapImage.getWidth( );
		float imageHeight = m_MapImage.getHeight( );

		FLOOR_WIDTH = imageWidth * 8;
		FLOOR_LENGTH = imageHeight * 8;

		for( int nPixelX = 1; nPixelX < imageWidth-1; nPixelX++ )
		{
			for( int nPixelY = 1; nPixelY < imageWidth-1; nPixelY++ )
				createMapItem( mapGroup, nPixelX, nPixelY );

			float percentDone = 100 * (float) nPixelX / (float) (imageWidth-2);
			System.out.println( "   " + (int) (percentDone) + "%" );
		}

		createExternalWall( mapGroup );

		return mapGroup;
	}

	void createMapItem( Group mapGroup, int nPixelX, int nPixelY )
	{
		int color = m_MapImage.getRGB( (int) nPixelX, (int) nPixelY );

		if( color == m_ColorWall )
			createWall( mapGroup, nPixelX, nPixelY );

		else if( color == m_ColorGuard )
			createGuard( mapGroup, nPixelX, nPixelY );

		else if( color == m_ColorLight )
			createLight( mapGroup, nPixelX, nPixelY );

		else if( color == m_ColorBookcase )
			createBookcase( mapGroup, nPixelX, nPixelY );

		else if( color == m_ColorWater )
			createWater( mapGroup, nPixelX, nPixelY );
	}	

	Vector3d getMapSquareSize( )
	{
		if( m_MapSquareSize == null )
		{
			double imageWidth = m_MapImage.getWidth( );
			double imageHeight = m_MapImage.getHeight( );
			m_MapSquareSize = new Vector3d( 2 * FLOOR_WIDTH / imageWidth, 0, 2 * FLOOR_LENGTH / imageHeight );
		}

		return m_MapSquareSize;
	}

	void createWall( Group mapGroup, int nPixelX, int nPixelY )
	{
		Point3d point = convertToWorldCoordinatesPixelCenter( nPixelX, nPixelY );

		if( m_WallAppearance == null )
			m_WallAppearance = new Appearance( );

		Vector3d squareSize = getMapSquareSize( );

		Cuboid wall = new Cuboid( this, mapGroup, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		wall.createObject( m_WallAppearance, 
			new Vector3d( point.x, m_kFloorLevel, point.z ),
			new Vector3d( squareSize.x/2, m_kCeilingHeight/2, squareSize.z/2 ) ,
			"wall.gif", null, null );
	}


	void createExternalWall( Group mapGroup )
	{
		Vector3d squareSize = getMapSquareSize( );

		Appearance app = new Appearance( );
		app.setColoringAttributes( new ColoringAttributes( new Color3f( 132f/255f,0,66f/255f ), ColoringAttributes.FASTEST ) );

		int imageWidth = m_MapImage.getWidth( );
		int imageHeight = m_MapImage.getHeight( );

		Point3d topLeft = convertToWorldCoordinatesPixelCenter( 0,0 );
		Point3d bottomRight = convertToWorldCoordinatesPixelCenter( imageWidth-1, imageHeight-1 );

		// top
		Cuboid wall = new Cuboid( this, mapGroup, ComplexObject.GEOMETRY );
		wall.createObject( app, 
			new Vector3d( 0, m_kFloorLevel, topLeft.z ),
			new Vector3d( squareSize.x * imageWidth/2, m_kCeilingHeight/2, squareSize.z/2 ),
			null, null, null );

		// bottom
		wall = new Cuboid( this, mapGroup, ComplexObject.GEOMETRY );
		wall.createObject( app, 
			new Vector3d( 0, m_kFloorLevel, bottomRight.z ),
			new Vector3d( squareSize.x * imageWidth/2, m_kCeilingHeight/2, squareSize.z/2 ),
			null, null, null );

		// left
		wall = new Cuboid( this, mapGroup, ComplexObject.GEOMETRY );
		wall.createObject( app, 
			new Vector3d( topLeft.x, m_kFloorLevel, 0 ),
			new Vector3d( squareSize.x/2, m_kCeilingHeight/2, squareSize.z * imageHeight/2 ) ,
			null, null, null );

		// right
		wall = new Cuboid( this, mapGroup, ComplexObject.GEOMETRY );
		wall.createObject( app, 
			new Vector3d( bottomRight.x, m_kFloorLevel, 0 ),
			new Vector3d( squareSize.x/2, m_kCeilingHeight/2, squareSize.z * imageHeight/2 ) ,
			null, null, null );
	}


	void createGuard( Group mapGroup, int nPixelX, int nPixelY )
	{
		Point3d point = convertToWorldCoordinatesPixelCenter( nPixelX, nPixelY );

		if( m_GuardAppearance == null )
			m_GuardAppearance = new Appearance( );

		Vector3d squareSize = getMapSquareSize( );

		Guard guard = new Guard( this, mapGroup, ComplexObject.GEOMETRY, this );
		guard.createObject( m_GuardAppearance,
			new Vector3d( point.x, (m_kFloorLevel+m_kCeilingLevel)/2, point.z ),
			new Vector3d( 1, 1, 1 ),
			null, null, null );
	}

	void createLight( Group mapGroup, int nPixelX, int nPixelY )
	{
		Point3d point = convertToWorldCoordinatesPixelCenter( nPixelX, nPixelY );

		// we do not share an Appearance for lights
		// or they all animate in synch...
		final double lightHeight = m_kCeilingHeight/1.5;

		Light light = new Light( this, mapGroup, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		light.createObject( new Appearance( ), 
			new Vector3d( point.x, m_kFloorLevel+lightHeight/2, point.z ),
			new Vector3d( 5, lightHeight, 5 ) ,
			"light.gif", null, null );
	}

	void createWater( Group mapGroup, int nPixelX, int nPixelY )
	{
		Point3d point = convertToWorldCoordinatesPixelCenter( nPixelX, nPixelY );

		if( m_WaterAppearance == null )
		{
			m_WaterAppearance = new Appearance( );
			m_WaterAppearance.setPolygonAttributes( new PolygonAttributes( PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0, false ) );
			m_WaterAppearance.setTransparencyAttributes( new TransparencyAttributes( TransparencyAttributes.BLENDED, 1.0f ) );
			m_WaterAppearance.setTextureAttributes( new TextureAttributes( TextureAttributes.REPLACE, new Transform3D( ), new Color4f( 0,0,0,1 ), TextureAttributes.FASTEST ) );

		}

		Land water = new Land( this, mapGroup, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		water.createObject( m_WaterAppearance, 
			new Vector3d( point.x, m_kFloorLevel+0.1, point.z ),
			new Vector3d( 40, 1, 40 ) ,
			"water.gif", null, null );
	}

	void createBookcase( Group mapGroup, int nPixelX, int nPixelY )
	{
		Point3d point = convertToWorldCoordinatesPixelCenter( nPixelX, nPixelY );

		if( m_BookcaseAppearance == null )
			m_BookcaseAppearance = new Appearance( );

		Vector3d squareSize = getMapSquareSize( );

		Cuboid bookcase = new Cuboid( this, mapGroup, ComplexObject.GEOMETRY | ComplexObject.TEXTURE );
		bookcase.createObject( m_BookcaseAppearance,
			new Vector3d( point.x, m_kFloorLevel, point.z ),
			new Vector3d( squareSize.x/2, m_kCeilingHeight/2.7, squareSize.z/2 ),
			"bookcase.gif", null, null );

	}

	public static void main( String[] args )
	{
		MyLib keyTest = new MyLib( );
		keyTest.saveCommandLineArguments( args );

		new MainFrame( keyTest, m_kWidth, m_kHeight );
	}
}
