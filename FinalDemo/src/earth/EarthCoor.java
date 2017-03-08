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

package earth;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.Billboard;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;

import org.selman.java3d.book.common.*;

/**
* This application creates a Universe with 3 Locales.
* The first Locale contains the Sun and some planets.
* The second Locale contains the planet Earth.
* The third Locale is situated on the surface of the earth
* and contains a "house"
*/
public class EarthCoor extends Java3dApplet implements ActionListener
{
	private static int 			m_kWidth = 320;
	private static int 			m_kHeight = 350;

	private final long			m_TranslateSunZ = (long) 8.0e+11;
	private final long			m_TranslateEarthZ = (long) 4.0e+7;
	private final long			m_TranslateHouseZ = (long) 4.0e+2;
	private long				m_TranslateZ = m_TranslateSunZ;

	// all units are in metres
	private final long			m_EarthOrbit = (long) 150.0e+9;
	private final long			m_EarthRadius = (long) 6.378e+6;

	private final long			m_MarsOrbit = (long) 228.6e+9;
	private final long			m_MarsRadius = (long) 3.373e+6;

	private final long			m_MercuryOrbit = (long) 58.2e+9;
	private final long			m_MercuryRadius = (long) 2.436e+6;

	private final long			m_SunRadius = (long) 7.0e+8;

	private ViewPlatform[]		m_ViewPlatformArray = new ViewPlatform[3];
	private View				m_View = null;

	public EarthCoor( )
	{
		initJava3d( );
	}

	public void actionPerformed( ActionEvent event )
	{
		if( event.getActionCommand( ) == "Sun" )
		{
			m_TranslateZ = m_TranslateSunZ;
			m_View.attachViewPlatform( m_ViewPlatformArray[0] );
		}
		else if( event.getActionCommand( ) == "Earth" && m_ViewPlatformArray[1] != null )
		{
			m_TranslateZ = m_TranslateEarthZ;
			m_View.attachViewPlatform( m_ViewPlatformArray[1] );
		}
		else if( event.getActionCommand( ) == "House" && m_ViewPlatformArray[1] != null )
		{
			m_TranslateZ = m_TranslateHouseZ;
			m_View.attachViewPlatform( m_ViewPlatformArray[2] );
		}

		m_View.setBackClipDistance( getBackClipDistance( ) );
		m_View.setFrontClipDistance( getFrontClipDistance( ) );		
	}

	protected void addCanvas3D( Canvas3D c3d )
	{
		setLayout( new BorderLayout( ) );

		Panel controlPanel = new Panel( );
		controlPanel.add( c3d );

		Button button = null;

		button = new Button( "Sun" );
		button.addActionListener( this );
		controlPanel.add( button );

		button = new Button( "Earth" );
		button.addActionListener( this );
		controlPanel.add( button );

		button = new Button( "House" );
		button.addActionListener( this );
		controlPanel.add( button );

		add( controlPanel, BorderLayout.SOUTH );
		add( c3d, BorderLayout.CENTER );

		doLayout( );
	}

	// this is the locale for the Sun
	protected Locale createLocale( VirtualUniverse u )
	{
		int[] xPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] yPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] zPos = { 0, 0, 0, 0, 0, 0, 0, 0 };

		HiResCoord hiResCoord = new HiResCoord( xPos, yPos, zPos );
		return new Locale( u, hiResCoord );
	}

	// this is the locale for the earth
	protected Locale createLocaleEarth( VirtualUniverse u )
	{
		int[] xPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] yPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] zPos = { 0, 0, 0, 1, 0, 0, 0, 0 };

		HiResCoord hiResCoord = new HiResCoord( xPos, yPos, zPos );
		return new Locale( u, hiResCoord );
	}

	// this is the locale for the house on earth
	protected Locale createLocaleHouse( VirtualUniverse u )
	{
		int[] xPos = { 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] yPos = { 0, 0, 0, 0, 100, 0, 0, 0 };
		int[] zPos = { 0, 0, 0, 1, 100, 0, 0, 0 };

		HiResCoord hiResCoord = new HiResCoord( xPos, yPos, zPos );
		return new Locale( u, hiResCoord );
	}

	public void initJava3d( )
	{		
		super.initJava3d( );

		// create a second locale for the earth and attach it to the universe
		Locale locale = createLocaleEarth( m_Universe );

		BranchGroup sceneBranchGroup = createSceneBranchGroupEarth( );
		sceneBranchGroup.addChild( createBackground( ) );

		m_ViewPlatformArray[1] = createViewPlatform( );
		BranchGroup viewBranchGroup = createViewBranchGroup( getViewTransformGroupArray( 1 ), m_ViewPlatformArray[1] );

		getJ3dTree( ).recursiveApplyCapability( sceneBranchGroup );
		getJ3dTree( ).recursiveApplyCapability( viewBranchGroup );

		locale.addBranchGraph( sceneBranchGroup );
		addViewBranchGroup( locale, viewBranchGroup ); 

		// create a third locale for the house on earth and attach it to the universe
		locale = createLocaleHouse( m_Universe );

		sceneBranchGroup = createSceneBranchGroupHouse( );
		sceneBranchGroup.addChild( createBackground( ) );

		m_ViewPlatformArray[2] = createViewPlatform( );
		viewBranchGroup = createViewBranchGroup( getViewTransformGroupArray( 2 ), m_ViewPlatformArray[2] );

		getJ3dTree( ).recursiveApplyCapability( sceneBranchGroup );
		getJ3dTree( ).recursiveApplyCapability( viewBranchGroup );

		locale.addBranchGraph( sceneBranchGroup );
		addViewBranchGroup( locale, viewBranchGroup );

		onDoneInit( );
	}

	protected ViewPlatform createViewPlatform( )
	{
		ViewPlatform vp = super.createViewPlatform( );

		// save the viewplatform for the Sun's locale
		if( m_ViewPlatformArray[0] == null )
			m_ViewPlatformArray[0] = vp;

		return vp;
	}

	protected View createView( ViewPlatform vp )
	{
		// save the view
		m_View = super.createView( vp );
		return m_View;
	}

	public TransformGroup[] getViewTransformGroupArray( )
	{
		TransformGroup[] tgArray = new TransformGroup[1];
		tgArray[0] = new TransformGroup( );

		Vector3d vTrans = new Vector3d( 0.0, 0.0, -m_TranslateSunZ );

		// move the camera BACK so we can view the scene
		// note that the coordinate directions are 
		// reversed as we are moving the view
		Transform3D t3d = new Transform3D( );
		t3d.setTranslation( vTrans );
		t3d.invert( );
		tgArray[0].setTransform( t3d );

		return tgArray;
	}


	public TransformGroup[] getViewTransformGroupArray( int nIndex )
	{
		TransformGroup[] tgArray = new TransformGroup[1];
		tgArray[0] = new TransformGroup( );

		Vector3d vTrans = null;

		if( nIndex == 1 )
			vTrans = new Vector3d( 0.0, 0.0, -m_TranslateEarthZ );
		else
			vTrans = new Vector3d( 0.0, 0.0, -m_TranslateHouseZ );

		// move the camera BACK so we can view the scene
		// note that the coordinate directions are 
		// reversed as we are moving the view
		Transform3D t3d = new Transform3D( );
		t3d.setTranslation( vTrans );
		t3d.invert( );
		tgArray[0].setTransform( t3d );

		return tgArray;
	}

	protected double getBackClipDistance( )
	{
		return 1.1 * m_TranslateZ;
	}

	protected double getFrontClipDistance( )
	{
		return 0.1 * m_TranslateZ;
	}


	private Shape3D createLabel( String szText, float x, float y, float z )
	{
		BufferedImage bufferedImage = new BufferedImage( 50, 20, BufferedImage.TYPE_INT_RGB );
		Graphics g = bufferedImage.getGraphics( );
		g.setColor( Color.white );
		g.drawString( szText, 10, 10 );

		ImageComponent2D imageComponent2D = new ImageComponent2D( ImageComponent2D.FORMAT_RGB, bufferedImage );
		imageComponent2D.setCapability( ImageComponent.ALLOW_IMAGE_READ );
		imageComponent2D.setCapability( ImageComponent.ALLOW_SIZE_READ );

		// create the Raster for the image
		javax.media.j3d.Raster renderRaster = new javax.media.j3d.Raster ( new Point3f( x, y, z ),
			javax.media.j3d.Raster.RASTER_COLOR,
			0, 0,
			bufferedImage.getWidth( ),
			bufferedImage.getHeight( ),
			imageComponent2D,
			null );

		return new Shape3D( renderRaster );
	}

	// creates the Sun
	protected BranchGroup createSceneBranchGroup( )
	{
		BranchGroup objRoot = super.createSceneBranchGroup( );

		Transform3D t3dTilt = new Transform3D( );
		t3dTilt.rotX( 0.3 );

		TransformGroup objTrans = new TransformGroup( t3dTilt );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

		TransformGroup objTransPlanets = new TransformGroup( );
		objTransPlanets.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		Transform3D yAxis = new Transform3D( );
		Alpha rotationAlpha = new Alpha( -1, Alpha.INCREASING_ENABLE,
			0, 0,
			4000, 0, 0,
			0, 0, 0 );

		RotationInterpolator rotator = new RotationInterpolator( rotationAlpha, objTransPlanets, yAxis, 0.0f, (float) Math.PI*2.0f );

		BoundingSphere bounds =	new BoundingSphere( new Point3d( 0.0,0.0,0.0 ), m_TranslateSunZ );
		rotator.setSchedulingBounds( bounds );
		objTransPlanets.addChild( rotator );

		// create the sun
		TransformGroup sunTg = createSun( );

		// create Earth
		Transform3D t3dEarth = new Transform3D( );
		t3dEarth.setScale( m_EarthRadius );		
		t3dEarth.setTranslation( new Vector3d( m_EarthOrbit, 0, 0 ) );
		objTransPlanets.addChild( createPlanet( "Earth", new Color3f( 0, 0.1f, 1.0f ), t3dEarth, null ) );

		// create Mars
		Transform3D t3dMars = new Transform3D( );
		t3dMars.setTranslation( new Vector3d( Math.sin( Math.PI * 1.5 ) * m_MarsOrbit, 0, Math.cos( Math.PI * 0.5 ) * m_MarsOrbit ) );
		t3dMars.setScale( m_MarsRadius );
		objTransPlanets.addChild( createPlanet( "Mars", new Color3f( 1, 0, 0 ), t3dMars, null ) );

		// create Mercury
		Transform3D t3dMercury = new Transform3D( );
		t3dMercury.setTranslation( new Vector3d( Math.sin( Math.PI ) * m_MercuryOrbit, 0, Math.cos( Math.PI ) * m_MercuryOrbit ) );
		t3dMercury.setScale( m_MercuryRadius );
		objTransPlanets.addChild( createPlanet( "Mercury", new Color3f( 0.5f, 0.5f, 0.5f ), t3dMercury, null ) );

		sunTg.addChild( objTransPlanets );
		objTrans.addChild( sunTg );
		objRoot.addChild( objTrans );

		return objRoot;
	}	

	private TransformGroup createSun( )
	{
		TransformGroup objTrans = new TransformGroup( );

		Appearance app = new Appearance( );
		ColoringAttributes ca = new ColoringAttributes( );
		ca.setColor( new Color3f( 1,1,0 ) );
		app.setColoringAttributes( ca );

		objTrans.addChild( createLabel( "Sun", m_SunRadius * 1.1f, m_SunRadius * 1.1f, 0 ) );
		objTrans.addChild( new Sphere( m_SunRadius, app ) );

		return objTrans;
	}

	private TransformGroup createPlanet( String szName, Color3f color, Transform3D t3d, String szTexture )
	{
		TransformGroup objTrans = new TransformGroup( t3d );

		Appearance app = new Appearance( );

		if( szTexture != null )
		{
			TextureLoader tex = new TextureLoader( szTexture, this );
			app.setTexture( tex.getTexture( ) );
		}
		else
		{
			ColoringAttributes ca = new ColoringAttributes( );
			ca.setColor( color );
			app.setColoringAttributes( ca );
		}

		objTrans.addChild( createLabel( szName, 1.2f, 1.2f, 0 ) );
		objTrans.addChild( new Sphere( 1.0f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_TEXTURE_COORDS, app ) );

		return objTrans;
	}

	// creates the Earth
	protected BranchGroup createSceneBranchGroupEarth( )
	{
		BranchGroup objRoot = super.createSceneBranchGroup( );

		TransformGroup objTrans = new TransformGroup( );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );

		Transform3D yAxis = new Transform3D( );
		yAxis.rotZ( 0.2 );
		Alpha rotationAlpha = new Alpha( -1, Alpha.INCREASING_ENABLE,
			0, 0,
			4000, 0, 0,
			0, 0, 0 );

		RotationInterpolator rotator = new RotationInterpolator( rotationAlpha, objTrans, yAxis, 0.0f, (float) Math.PI*2.0f );

		BoundingSphere bounds =	new BoundingSphere( new Point3d( 0.0,0.0,0.0 ), m_TranslateSunZ );
		rotator.setSchedulingBounds( bounds );
		objTrans.addChild( rotator );

		Transform3D t3d = new Transform3D( );
		t3d.setScale( m_EarthRadius );

		objTrans.addChild( createPlanet( "Earth", new Color3f( 0, 0.1f, 1 ), t3d, "earth.jpg" ) );
		objRoot.addChild( objTrans );

		return objRoot;
	}	

	// creates the House
	protected BranchGroup createSceneBranchGroupHouse( )
	{
		BranchGroup objRoot = super.createSceneBranchGroup( );

		Transform3D t3dTilt = new Transform3D( );
		t3dTilt.rotX( 0.3 );
		TransformGroup subTg = new TransformGroup( t3dTilt );

		subTg.addChild( new ColorCube( 50.0 ) );
		subTg.addChild( createLabel( "House", 60, 60, 0 ) );

		objRoot.addChild( subTg );

		return objRoot;
	}


	public static void main( String[] args )
	{
		EarthCoor hiResCoordTest = new EarthCoor( );
		hiResCoordTest.saveCommandLineArguments( args );

		new MainFrame( hiResCoordTest, m_kWidth, m_kHeight );
	}
}
