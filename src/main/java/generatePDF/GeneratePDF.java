package generatePDF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import dto.ClienteDTO;
import dto.PasajeDTO; 


public class GeneratePDF {
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        

    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final String iTextExampleImage = "C:\\Users\\avmni\\Desktop\\AlPlanetaPDF.png";

//    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);    
//    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
//    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    public void createPDF(PasajeDTO pasaje,ClienteDTO cliente) {
    	URL url = getClass().getResource("/recursos/AlPlanetaPDF.png");
    	String urlPDF = url.toString().substring(6);
    	urlPDF = urlPDF.substring(0, urlPDF.length()-16)+"PDF.pdf";
    	System.out.println(urlPDF);

    	System.out.println(" - - - -Generamos el PDF en C:/user/PDFFile.pdf - - - - ");

//    	File pdfNewFile2 = new File("C:\\Users\\avmni\\Desktop\\PDF.pdf");
//    	File pdfNewFile2 = new File("C:\\Users\\avmni\\git\\AlPlaneta-Project\\target\\classes\\img\\PDF.pdf");
   
    	File pdfNewFile2 = new File(urlPDF);
        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile2));

            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("No such file was found to generate the PDF "
                        + "(No se encontro el fichero para generar el pdf)" + fileNotFoundException);
            }
            document.open();
            document.addTitle("Table export to PDF (Exportamos la tabla a PDF)");
            document.addSubject("Using iText (usando iText)");
            document.addKeywords("Java, PDF, iText");
            document.addAuthor("Al Planeta");
            document.addCreator("Administrador");
            
            Chunk chunk = new Chunk(" Voucher Al Planeta ", chapterFont);
//            chunk.setBackground(BaseColor.GRAY);
            // Creamos el primer capitulo
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            
            chapter.add(new Paragraph("Este Voucher corresponde a \n"
            		+ cliente.getNombre()+" "+ cliente.getApellido()+" "+cliente.getMail()+"\n", paragraphFont));

            Image image;
            
            try {
                image = Image.getInstance(url.toString());  
                image.setAbsolutePosition(0,0);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }
            document.add(chapter);
            
            // Second page - some elements
            Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Voucher")), 1);
            Paragraph paragraphS = new Paragraph("Datos correspondientes al voucher", subcategoryFont);
            
            // Underline a paragraph by iText (subrayando un pÃ¡rrafo por iText)
            DottedLineSeparator dottedline = new DottedLineSeparator();
            dottedline.setOffset(-2);
            dottedline.setGap(2f);
            
            Section paragraphMoreS = chapSecond.addSection(paragraphS);
            // List by iText (listas por iText)
            
            // TODO: CAMBIAR POR NUMERO DE RESERVA = 002541155756
            String text = String.valueOf(pasaje.getIdPasaje()); 
            List list = new List(List.UNORDERED);
            ListItem item = new ListItem(text);	
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            text ="Fecha y hora de salida: "+ pasaje.getViaje().getFechaSalida()+" - Horario: "+pasaje.getViaje().getHoraSalida()+"hs";// "Fecha y Hora de Salida: 15-5-2019, 20:00 hs  \n ";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
           
            text = "Fecha de llegada: " + String.valueOf(pasaje.getViaje().getFechaLlegada());//"Hora de Llegada: 13:30 hs \n";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            
            text = "Origen del viaje: " + pasaje.getViaje().getPaisOrigen().getNombre()+" - "+pasaje.getViaje().getProvinciaOrigen().getNombre()+
            		" - " + pasaje.getViaje().getCiudadOrigen().getNombre();//"Origen y Destino: Argentina.Buenos Aires  - Argentina.Mendoza \n ";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            text = "Destino del viaje: "  + pasaje.getViaje().getPaisDestino().getNombre()+" - "+pasaje.getViaje().getProvinciaDestino().getNombre()+
            		 " - " + pasaje.getViaje().getCiudadDestino().getNombre();//"Origen y Destino: Argentina.Buenos Aires  - Argentina.Mendoza \n ";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            paragraphMoreS.add(list);
            document.add(chapSecond);
            document.close();
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }

    public String getUrlPDF() {
    	URL url = getClass().getResource("/recursos/AlPlanetaPDF.png");
    	String urlPDFaux = url.toString().substring(6);
    	urlPDFaux = urlPDFaux.substring(0, urlPDFaux.length()-16)+"PDF.pdf";
    	return urlPDFaux;
	}

	public static void main(String args[]) {
        GeneratePDF generatePDFFileIText = new GeneratePDF();
        generatePDFFileIText.createPDF(new PasajeDTO(), new ClienteDTO());
    }
}
