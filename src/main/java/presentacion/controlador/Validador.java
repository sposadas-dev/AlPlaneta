package presentacion.controlador;

import java.util.Random;

public class Validador {

	
	public static String getNumeroComprobante(int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
			char c = (char)r.nextInt(255);
			if ( (c >= '0' && c <='9') || (c >='A' && c <='Z')  || (c >='a' && c <='z')) {
				cadenaAleatoria += c;
				i ++;
			}
		}
		return cadenaAleatoria;
	}	
	
	public static void main(String [] args){
		System.out.println(getNumeroComprobante(6));
	}
}