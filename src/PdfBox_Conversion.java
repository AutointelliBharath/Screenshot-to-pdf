
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class PdfBox_Conversion {
	public static void main(String[] args) throws Exception {
		List<String> imageFiles = List.of("screenshot1.png", "screenshot2.png"); // Add your image files here
		String outputPdfFile = "screenshots.pdf"; // Output PDF file

		try {
			PDDocument document = new PDDocument();
			PDRectangle pd = new PDRectangle(595, 842);
			PDRectangle pagesize = new PDRectangle(595, 842);

			for (String imageFile : imageFiles) {
				PDPage page = new PDPage(pagesize);
				document.addPage(page);

//	                PDImageXObject image = LosslessFactory.createFromImage(document, ImageIO.read(new File(imageFile)));
//	                org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject image = org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromFile(imageFile, document);
//	                PDPageContentStream contentStream = new PDPageContentStream(document, page);
//	                contentStream.drawImage(image, 0, 0, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
//	                contentStream.close();
			}

			document.save(outputPdfFile);
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
