package correo;
 
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import generatePDF.GeneratePDF;
 
/**
 * @author datojava.blogspot.com
 */
public class EnvioDeCorreo {
	GeneratePDF pdf;
 
 public void enviarNuevaContrasena(String correoDestino, String nuevaContrasena) {
  // El correo gmail de envío
  String correoEnvia = "AlPlanetaProject";
  String claveCorreo = "alplaneta123";
 
  // La configuración para enviar correo
  Properties properties = new Properties();
  properties.put("mail.smtp.host", "smtp.gmail.com");
  properties.put("mail.smtp.starttls.enable", "true");
  properties.put("mail.smtp.port", "587");
  properties.put("mail.smtp.auth", "true");
  properties.put("mail.user", correoEnvia);
  properties.put("mail.password", claveCorreo);
 
  // Obtener la sesion
  Session session = Session.getInstance(properties, null);
 
  try {
   // Crear el cuerpo del mensaje
   MimeMessage mimeMessage = new MimeMessage(session);
 
   // Agregar quien envía el correo
   mimeMessage.setFrom(new InternetAddress(correoEnvia, "Dato Java"));
    
   // Los destinatarios
   InternetAddress[] internetAddresses = {
     new InternetAddress(correoDestino)};
 
   // Agregar los destinatarios al mensaje
   mimeMessage.setRecipients(Message.RecipientType.TO,
     internetAddresses);
 
   // Agregar el asunto al correo
   mimeMessage.setSubject("Recuperacion de contrasena");
 
   // Creo la parte del mensaje
   MimeBodyPart mimeBodyPart = new MimeBodyPart();
   mimeBodyPart.setText("Se ha solicitado la recuperacion de la contrasena, utilize: "+nuevaContrasena+" como nueva contrasena");
 
   // Crear el multipart para agregar la parte del mensaje anterior
   Multipart multipart = new MimeMultipart();
   multipart.addBodyPart(mimeBodyPart);
 
   // Agregar el multipart al cuerpo del mensaje
   mimeMessage.setContent(multipart);
   
   // Enviar el mensaje
   Transport transport = session.getTransport("smtp");
   transport.connect(correoEnvia, claveCorreo);
   transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
   transport.close();
 
  } catch (Exception ex) {
   ex.printStackTrace();
  }
 }
 
 public void enviarAdjunto(String correoDestino){
//TODO: cambiar la ruta en donde se encuentra el PDF, package= Recursos
	 this.pdf = new GeneratePDF();
	 
	 
//	 String pathAdjunto = "C:\\Users\\avmni\\Desktop\\PDF.pdf";
	 String pathAdjunto = pdf.getUrlPDF(); 
		try
     {
		String emisor = "AlPlanetaProject";
		String claveCorreo = "alplaneta123";
     	  
         Properties props = new Properties();
         props.put("mail.smtp.host", "smtp.gmail.com");
         props.setProperty("mail.smtp.starttls.enable", "true");
         props.setProperty("mail.smtp.port", "587");
         props.setProperty("mail.smtp.user", emisor);
         props.setProperty("mail.smtp.auth", "true");

         Session session = Session.getDefaultInstance(props, null);
         // session.setDebug(true);

         // Se compone la parte del texto
         BodyPart texto = new MimeBodyPart();
         texto.setText("Voucher");

         // Se compone el adjunto con la imagen
         BodyPart adjunto = new MimeBodyPart();
         adjunto.setDataHandler(
             new DataHandler(new FileDataSource(pathAdjunto)));
         adjunto.setFileName(pathAdjunto);

         // Una MultiParte para agrupar texto e imagen.
         MimeMultipart multiParte = new MimeMultipart();
         multiParte.addBodyPart(texto);
         multiParte.addBodyPart(adjunto);

         // Se compone el correo, dando to, from, subject y el
         // contenido.
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(correoDestino));
         message.addRecipient(
             Message.RecipientType.TO,
             new InternetAddress(correoDestino));
         message.setSubject("Prueba");
         message.setContent(multiParte);

         // Se envia el correo.
         Transport t = session.getTransport("smtp");
         t.connect(emisor, claveCorreo);
         t.sendMessage(message, message.getAllRecipients());
         System.out.println("se envia el correo");
         t.close();
     }
     catch (Exception e)
     {
         e.printStackTrace();
     }
	}
 
 public static void main(String[] args) {
  EnvioDeCorreo correoTexto = new EnvioDeCorreo();
//  correoTexto.enviarNuevaContrasena("av.m.nico@gmail.com", "1234");
  correoTexto.enviarAdjunto("av.m.nico@gmail.com");
  
 }
}