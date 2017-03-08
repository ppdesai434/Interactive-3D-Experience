package demo;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
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

public class Console implements WiimoteListener{

    public void onButtonsEvent(WiimoteButtonsEvent arg0) {
        System.out.println(arg0);
       /* if (arg0.isButtonAPressed()){
            WiiUseApiManager.shutdown();
        }*/
        
       
    }

    public void onIrEvent(IREvent arg0) {
        System.out.println(arg0);
       
    }

    public void onMotionSensingEvent(MotionSensingEvent arg0) {
        System.out.println(arg0);
        
    }

    public void onExpansionEvent(ExpansionEvent arg0) {
        System.out.println(arg0);
    }

    public void onStatusEvent(StatusEvent arg0) {
        System.out.println(arg0);
    }

    public void onDisconnectionEvent(DisconnectionEvent arg0) {
        System.out.println(arg0);
    }

    public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
        System.out.println(arg0);
    }

    public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
        System.out.println(arg0);
       
        
    }

    public static void main(String[] args) {
        Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
        Wiimote wiimote = wiimotes[0];
        wiimote.activateIRTRacking();
        wiimote.activateMotionSensing();
        wiimote.addWiiMoteEventListeners(new Console());
        
        
    }

	@Override
	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClassicControllerRemovedEvent(
			ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
