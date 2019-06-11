package recursos;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Mapper {

	public Mapper() {
		super();
	}
	public String parseToString (java.sql.Date date){
		String fechaelegida = date.toString();
		String mes = fechaelegida.substring(5, 7) ;
		String dia = fechaelegida.substring(8, 10);
		String ano = fechaelegida.substring(0, 4);
		fechaelegida = dia + "-" + mes + "-" + ano;
		return fechaelegida;
	}
	
	public Date parseToSQLDate (String date){
		String mes = date.substring(2, 4) ;
		String dia = date.substring(0, 2);
		String ano = date.substring(4, 8);
		java.sql.Date utilDate = null;
		try {
			utilDate = (Date) new SimpleDateFormat("dd-MM-yyyy").parse(dia+mes+ano);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return utilDate;
	}
	
	
	public static void main(String[] args) {
		Mapper map = new Mapper();
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		String fecha = map.parseToString(date);
		
		
		System.out.println(map.parseToString(date));
		System.out.println(map.parseToSQLDate(fecha));
//		 SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//	     java.util.Date parsed = null;
//		try {
//			parsed = format.parse("20110210");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	     java.sql.Date sql = new java.sql.Date(parsed.getTime());
//	     System.out.println(sql.toString());
		
	}
}