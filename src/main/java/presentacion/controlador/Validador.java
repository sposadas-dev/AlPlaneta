package presentacion.controlador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validador {


	public static boolean esDniValido(String text) {
		return Pattern.matches("[0-9]{8}", text);
	}
	
	public static boolean esTelefonoCelularValido(String text) {
		return Pattern.matches("[0-9]{10}", text);
	}
	
	public static boolean esTelefonoFijoValido(String text) {
		return Pattern.matches("[0-9]{8}", text);
	}
	
	public static boolean esSoloLetras(String text) {
		return Pattern.matches("[a-zA-Z]+", text);
	}

	public static boolean esMailValido(String string) {
		Pattern pattern = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		String email = string;
		Matcher mather = pattern.matcher(email);
		return mather.find();
	}

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
	
	public static String primeraLetraMayuscula(String cadena) {
		cadena = cadena.toLowerCase(); //pasamos todo a minuscula
		char[] caracteres = cadena.toCharArray();
		caracteres[0] = Character.toUpperCase(caracteres[0]);
		if (cadena.length() > 1) {
			for (int i = 0; i < cadena.length() - 2; i++) {  // el -2 es para evitar una excepcion al caernos del arreglo
				if (caracteres[i] == ' ') {
					caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
				}
			}
		}
		return new String(caracteres);
	}
	
	public static String eliminarEspacios(String cadena) {
		cadena = cadena.replaceAll(" +", " ");
		return cadena.trim();
	}
	
	public static String validarCampo(String cadena) {
		String campoValido = primeraLetraMayuscula(cadena);
		return eliminarEspacios(campoValido);
	}
}