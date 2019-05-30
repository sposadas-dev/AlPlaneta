package GeneratePDFFileIText;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.io.*; 

/**
 * Example of using the iText library to work with PDF documents on Java, 
 * lets you create, analyze, modify and maintain documents in this format.
 * Ejemplo de uso de la librerÃ­a iText para trabajar con documentos PDF en Java, 
 * nos permite crear, analizar, modificar y mantener documentos en este formato.
 *
 * @author xules You can follow me on my website http://www.codigoxules.org/en
 * Puedes seguirme en mi web http://www.codigoxules.org
 */
public class GeneratePDFFileIText {
    // Fonts definitions (DefiniciÃ³n de fuentes).
    private static final Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 26, Font.BOLDITALIC);
    private static final Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
        
    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);    
    private static final Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    
    private static final String iTextExampleImage = "C:\\Users\\avmni\\Desktop\\AlPlaneta.png";
    
    public void createPDF(File pdfNewFile) {
        // We create the document and set the file name.        
        // Creamos el documento e indicamos el nombre del fichero.
        try {
            Document document = new Document();
            try {

                PdfWriter.getInstance(document, new FileOutputStream(pdfNewFile));

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
            // Let's create de first Chapter (Creemos el primer capÃ­tulo)
            Chapter chapter = new Chapter(new Paragraph(chunk), 1);
            chapter.setNumberDepth(0);
            chapter.add(new Paragraph("Este Voucher corresponde a \n"
            		+ "Juan Perez, email: juanitoPerez@gmail.com \n"
            		+ "y alguna otra cosa mas que se pueda agregar...", paragraphFont));

            Image image;
            try {
                image = Image.getInstance(iTextExampleImage);  
                image.setAbsolutePosition(0,10);
                chapter.add(image);
            } catch (BadElementException ex) {
                System.out.println("Image BadElementException" +  ex);
            } catch (IOException ex) {
                System.out.println("Image IOException " +  ex);
            }
            document.add(chapter);
            
            // Second page - some elements
            // Segunda pÃ¡gina - Algunos elementos
            Chapter chapSecond = new Chapter(new Paragraph(new Anchor("Datos correspondiente al voucher ...")), 1);
            Paragraph paragraphS = new Paragraph("Datos correspondiente al voucher ...", subcategoryFont);
            
            // Underline a paragraph by iText (subrayando un pÃ¡rrafo por iText)
            Paragraph paragraphE = new Paragraph("aqui nose que poner pero se podria agregar algunas cosas \n");
            DottedLineSeparator dottedline = new DottedLineSeparator();
            dottedline.setOffset(-2);
            dottedline.setGap(2f);
            paragraphE.add(dottedline);
            chapSecond.addSection(paragraphE);
            
            Section paragraphMoreS = chapSecond.addSection(paragraphS);
            // List by iText (listas por iText)
            String text = "Numero de Reserva: 002541155756 \n";
            List list = new List(List.UNORDERED);
            ListItem item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            text = "Fecha y Hora de Salida: 15-5-2019, 20:00 hs  \n";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
           
            text = "Hora de Llegada: 13:30 hs \n";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            text = "Origen y Destino: Argentina.Buenos Aires  - Argentina.Mendoza \n";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            text = "Punto de Embarque: Valla uno a saber que es eso... \n";
            item = new ListItem(text);
            item.setAlignment(Element.ALIGN_JUSTIFIED);
            list.add(item);
            
            paragraphMoreS.add(list);
            document.add(chapSecond);
            document.close();
            System.out.println("se ha generado su hoja de PDF");
        } catch (DocumentException documentException) {
            System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
        }
    }

    public static void main(String args[]) {
        GeneratePDFFileIText generatePDFFileIText = new GeneratePDFFileIText();
        generatePDFFileIText.createPDF(new File("C:\\Users\\avmni\\Desktop\\GeneratePDFFileIText.pdf"));
    }
}
