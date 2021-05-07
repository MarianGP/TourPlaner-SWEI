package org.garcia.layerdataaccess.fileaccess;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import org.garcia.model.Tour;
import org.garcia.model.TourLog;
import org.garcia.model.enums.Sport;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PDFBuilder {
    public static final String DATA = "src/main/resources/org/garcia/report/united_states.csv";

    public static final String DEST = "results/chapter01/united_states.pdf";



    public static void createPdf(String dest) throws IOException, IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1}))
                .useAllAvailableWidth();

        List<TourLog> foundTourLogs = new ArrayList<>();
        foundTourLogs.add(new TourLog(0, 1, LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        foundTourLogs.add(new TourLog(2, 1, LocalDate.of(2018, 9, 30), 30, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));
        foundTourLogs.add(new TourLog(0, 2, LocalDate.of(2012, 6, 30), 25, 60, 1, Sport.BIKE, 20, LocalTime.now(), LocalTime.now(), 120, 1));

        BufferedReader br = new BufferedReader(new FileReader(DATA));
        String line = br.readLine();
        process(table, line, bold, true);
        while ((line = br.readLine()) != null) {
            process(table, line, font, false);
        }
        br.close();
        document.add(table);

        //Close document
        document.close();
    }

    public static void createSummaryPdf(String url) {
    }

    public static void createTourPdf(Tour currentTour, String url) {
    }

    public void createReportFromHtml(String fileName, String dest) throws IOException {
        Path configDirectory = Paths.get("src", "main", "resources", "org", "garcia", dest);
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        String TARGET = "target/results/ch01/";
        String DEST = TARGET + fileName;
        String html = "<p>Hello</p>";
        File file = new File(TARGET);
        file.mkdirs();
        createPdf(html, DEST, absolutePath);
    }

    public void createPdf(String html, String dest, String absolutePath) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri(absolutePath);
        HtmlConverter.convertToPdf(html, new FileOutputStream(dest), properties);

    }

    public static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }

    // IMAGE EXAMPLE

        public static final String IMG1 = "./src/main/resources/img/javaone2013.jpg";
        public static final String IMG2 = "./src/main/resources/img/berlin2013.jpg";

        public void init2(String[] args) throws Exception {
            File file = new File(DEST);
            file.getParentFile().mkdirs();

            new PDFBuilder().manipulatePdf(DEST);
        }

        protected void manipulatePdf(String dest) throws Exception {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
            Document doc = new Document(pdfDoc);

            Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
            table.addCell(createImageCell(IMG1));
            table.addCell(createImageCell(IMG2));

            doc.add(table);

            doc.close();
        }

        private static Cell createImageCell(String path) throws MalformedURLException {
            Image img = new Image(ImageDataFactory.create(path));
            return new Cell().add(img.setAutoScale(true).setWidth(UnitValue.createPercentValue(100)));
        }


}
