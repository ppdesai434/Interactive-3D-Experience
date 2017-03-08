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

import javax.media.j3d.*;
import javax.vecmath.*;
import javax.swing.*;
import javax.swing.event.*;

// this class defines an Alpha class that returns a random
// value every N milliseconds.
public class UiAlpha extends Alpha implements ChangeListener
{
	protected Alpha					m_Alpha = null;
	protected float 				m_AlphaValue = 0.5f;
	JButton							m_Button = null;
	boolean							m_bAuto = true;

	public UiAlpha( Alpha alpha )
	{
		m_Alpha = alpha;

		Frame frame = new Frame( "Alpha Control Panel" );
		JPanel panel = new JPanel( );
		frame.add( panel );		
		addUiToPanel( panel );
		frame.pack( );
		frame.setSize( new Dimension( 400, 80 ) );
		frame.validate( );
		frame.setVisible( true );
	}

	protected void addUiToPanel( JPanel panel )
	{
		JSlider slider = new JSlider( );
		slider.addChangeListener( this );
		panel.add( slider );

		m_Button = new JButton( "Auto" );
		m_Button.addChangeListener( this );
		panel.add( m_Button );
	}

	public void stateChanged( ChangeEvent e )
	{
		if( e.getSource( ) instanceof JSlider )
		{
			m_AlphaValue =  ((JSlider) e.getSource( )).getValue( ) / 100.0f;
			m_bAuto = false;
		}
		else
		{
			m_bAuto = true;
		}
	}

	// core method override
	// returns the Alpha value for a given time
	public float value( long time )
	{
		if( m_bAuto == true )
			return m_Alpha.value( time );

		return m_AlphaValue;
	}
}
