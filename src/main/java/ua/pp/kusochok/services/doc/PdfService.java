package ua.pp.kusochok.services.doc;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.List;

@Service
public class PdfService {

    public ByteArrayOutputStream generatePdfFromImgLinks(List<String> imgLinks) throws IOException, DocumentException {
        Document document = new Document();

        //Create OutputStream instance.
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();

        //Create PDFWriter instance.
        PdfWriter.getInstance(document, outputStream);

        //Open the document.
        document.open();

        //Create Image object
        imgLinks.forEach(imgLink -> {
            Image image = null;
            try {
                image = Image.getInstance(new URL(imgLink));
            } catch (BadElementException | IOException e) {
                throw new RuntimeException(e);
            }
            image.setAbsolutePosition(0, 0);
            image.scaleToFit(document.getPageSize());

            try {
                document.add(image);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }

            document.newPage();
        });

        document.close();
//        outputStream.close();

        return outputStream;
    }
}
