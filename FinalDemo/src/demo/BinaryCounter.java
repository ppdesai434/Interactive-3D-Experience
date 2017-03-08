package demo;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.values.Orientation;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

public class BinaryCounter implements WiimoteListener {

	static int Count=0;
	/**
	 * @param args
	 */
	static Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
	static Wiimote wiimote;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        wiimote = wiimotes[0];
       // wiimote.activateIRTRacking();
       // wiimote.activateMotionSensing();
        for(int i=0;i!=10;i++)
        {
        sequence();
        }
        wiimote.setLeds(false,false,false,false);
        wiimote.addWiiMoteEventListeners(new BinaryCounter());
    	
		
		
       
	}

	private static void sequence() {
		// TODO Auto-generated method stub
		wiimote.setLeds(true,false,false,true);		
		wiimote.setLeds(true,false,false,true);
		wiimote.setLeds(true,false,false,true);
		wiimote.setLeds(true,false,false,true);
		wiimote.setLeds(true,false,false,true);
		wiimote.setLeds(true,false,false,true);
		
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
		
		wiimote.setLeds(false,true,true,false);
		wiimote.setLeds(false,true,true,false);
		wiimote.setLeds(false,true,true,false);
		wiimote.setLeds(false,true,true,false);
		wiimote.setLeds(false,true,true,false);
		wiimote.setLeds(false,true,true,false);
		
		
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
		wiimote.setLeds(false,false,false,false);
	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent e) {
		// TODO Auto-generated method stub
		if (e.isButtonAPressed()){
			for(int i=0;i!=10;i++)
	        {
	        sequence();
	        }
	        WiiUseApiManager.shutdown();
	        wiimote.setLeds(false,false,false,false);

			
        }
		
		
		
		if(e.isButtonOnePressed())
		{
			Count+=1;
		}
		else if(e.isButtonTwoPressed())
		{
			Count+=2;
		}
		
		
		
		
		switch(Count%16)
		{
		
		case 0:
			wiimote.setLeds(false,false,false,false);
			break;
			
		case 1:
			wiimote.setLeds(false,false,false,true);
			break;
		case 2:
			wiimote.setLeds(false,false,true,false);
			break;
		case 3:
			wiimote.setLeds(false,false,true,true);
			break;
		case 4:
			wiimote.setLeds(false,true,false,false);
			break;
			
		case 5:
			wiimote.setLeds(false,true,false,true);
			break;
				
		case 6:
			wiimote.setLeds(false,true,true,false);
			break; 
		case 7:
			wiimote.setLeds(false,true,true,true);
			break;	
		case 8:
			wiimote.setLeds(true,false,false,false);
			break;
		case 9:
			wiimote.setLeds(true,false,false,true);
			break;
		case 10:
			wiimote.setLeds(true,false,true,false);
			break;
		case 11:
			wiimote.setLeds(true,false,true,true);
			break;	
		case 12:
			wiimote.setLeds(true,true,false,false);
			break;
		case 13:
			wiimote.setLeds(true,true,false,true);
			break;
		case 14:
			wiimote.setLeds(true,true,true,false);
			break;
		case 15:
			wiimote.setLeds(true,true,true,true);
			break;	
		
			
		}
		
		
	}

	@Override
	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClassicControllerRemovedEvent(ClassicControllerRemovedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExpansionEvent(ExpansionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onIrEvent(IREvent e) {
		
		
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent e) {
		// TODO Auto-generated method stub
		//lighting();
		
	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusEvent(StatusEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
