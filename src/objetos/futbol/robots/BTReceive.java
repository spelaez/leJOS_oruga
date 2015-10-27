package objetos.futbol.robots;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class BTReceive {

	public static void main(String [] args)  throws Exception 
	{
		String connected = "Connected";
        String waiting = "Waiting...";
        String closing = "Closing...";
        
		while (true)
		{
			LCD.drawString(waiting,0,0);
			LCD.refresh();

	        BTConnection btc = Bluetooth.waitForConnection();
	        
			LCD.clear();
			LCD.drawString(connected,0,0);
			LCD.refresh();	

			DataInputStream dis = btc.openDataInputStream();
			DataOutputStream dos = btc.openDataOutputStream();
			
			for(int i=0;i<100;i++) {
				char n = dis.readChar();
				LCD.drawChar(n, 5, 5);
				LCD.clear();
				LCD.drawString("si llegue hasta aca", 0, 0);
				try{
				dos.writeChars("receptor"+n);
				dos.flush();
				}catch(IOException e){
					LCD.clear();
					LCD.drawString(e.getMessage(), 0, 0);
				}
			}
			LCD.drawString("Tratare de cerrar el dis", 0, 0);
			Thread.sleep(2000);
			dis.close();
			LCD.drawString("Tratare de cerrar el dos", 0, 0);
			Thread.sleep(2000);
			dos.close();
			Thread.sleep(2000); // wait for data to drain
			LCD.clear();
			LCD.drawString(closing,0,0);
			LCD.refresh();
			LCD.drawString("Tratare de cerrar la conexion", 0, 0);
			Thread.sleep(2000);
			btc.close();
			LCD.clear();
			
		   
		}
		
	}
}
