package generatePDF;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;

import recursos.Mapper;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dto.ClienteDTO;
import dto.PasajeDTO;
import dto.PasajeroDTO; 


public class GeneratePDF {
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 30, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        
    Mapper mapper = new Mapper();
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    
//    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);    
//    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
//    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    public void createPDF(PasajeDTO pasaje,ClienteDTO cliente) {
    	URL url = getClass().getResource("/recursos/AlPlanetaPDF.png");
    	String urlPDF = url.toString().substring(6);
    	urlPDF = urlPDF.substring(0, urlPDF.length()-16)+"PDF.pdf";
//    	System.out.println(urlPDF);

    	String urlTemp = "C:\\Windows\\Temp\\PDF.pdf";
//    	String urlTemp = "C:\\Users\\avmni\\Desktop\\PDF.pdf";
    	
    	File pdfNewFile2 = new File(urlTemp);
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
            
            
            Chunk chunk = new Chunk("Voucher Al Planeta ", chapterFont);
//            chunk.setBackground(BaseColor.GRAY);
            // Creamos el primer capitulo
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);

//            Image image;
//            try {
//                image = Image.getInstance(url.toString());  
//                image.setAbsolutePosition(0,0);
//                chapter.add(image);
//            } catch (BadElementException ex) {
//                System.out.println("Image BadElementException" +  ex);
//            } catch (IOException ex) {
//                System.out.println("Image IOException " +  ex);
//            }
            
            chapter.add(new Paragraph("Este Voucher corresponde al cliente: \n Nombre: "
            		+ cliente.getNombre()+"\n Apellido: "+ cliente.getApellido()+"\n Email: "+
            		cliente.getMail()+"\n DNI: "+cliente.getDni()+"\n ", paragraphFont));

            Paragraph paragraphS = new Paragraph("Datos correspondientes al pasaje", subcategoryFont);
            Section paragraphMoreS = chapter.addSection(paragraphS);

            String text = String.valueOf("Código de pasaje: "+pasaje.getNumeroComprobante()); 
            List list = new List(List.UNORDERED);
            ListItem item = new ListItem(text);	
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            text ="Fecha y hora de salida: "+ mapper.parseToString(pasaje.getViaje().getFechaSalida())+" - Horario: "+pasaje.getViaje().getHoraSalida()+" hs";// "Fecha y Hora de Salida: 15-5-2019, 20:00 hs  \n ";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
           
            text = "Fecha de llegada: " + mapper.parseToString(pasaje.getViaje().getFechaLlegada());//"Hora de Llegada: 13:30 hs \n";
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
            
            
           Paragraph paragraphPasajeros = new Paragraph("Datos de los pasajeros:", subcategoryFont);
           Section paragraphMorePasajeros = chapter.addSection(paragraphPasajeros);
         
           Integer numColumns = 4;
           Integer numRows = pasaje.getPasajeros().size();
           // We create the table (Creamos la tabla).
           PdfPTable table = new PdfPTable(numColumns); 
           // Now we fill the PDF table 
           // Ahora llenamos la tabla del PDF
           PdfPCell columnHeader;
           // Fill table rows (rellenamos las filas de la tabla).                
               columnHeader = new PdfPCell(new Phrase("Nombre"));
               columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
               table.addCell(columnHeader);
               columnHeader = new PdfPCell(new Phrase("Apellido"));
               columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
               table.addCell(columnHeader);
               columnHeader = new PdfPCell(new Phrase("Fecha de nacimiento"));
               columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
               table.addCell(columnHeader);
               columnHeader = new PdfPCell(new Phrase("DNI"));
               columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
               table.addCell(columnHeader);
               table.setHeaderRows(1);
               ArrayList<PasajeroDTO> pasajeros = (ArrayList<PasajeroDTO>) pasaje.getPasajeros();

               for (int row = 0; row < numRows; row++) {
                   table.addCell(pasajeros.get(row).getNombre().toString());
                   table.addCell(pasajeros.get(row).getApellido().toString());
                   table.addCell(mapper.parseToString(pasajeros.get(row).getFechaNacimiento()));
                   table.addCell(pasajeros.get(row).getDni().toString());
               }
           
            paragraphMoreS.add(list);
            paragraphMorePasajeros.add(table);
            document.add(chapter);
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
