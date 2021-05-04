package org.garcia.layerdataaccess.common.fileaccess;

public class PDFBuilder {
//    public static final String DATA = "src/main/resources/data/united_states.csv";
//
//    public static final String DEST = "results/chapter01/united_states.pdf";
//
//    public static void main(String args[]) throws IOException {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//        new PDFBuilder().createPdf(DEST);
//    }
//
//    public void createPdf(String dest) throws IOException {
//        //Initialize PDF writer
//        PdfWriter writer = new PdfWriter(dest);
//
//        //Initialize PDF document
//        PdfDocument pdf = new PdfDocument(writer);
//
//        // Initialize document
//        Document document = new Document(pdf, PageSize.A4.rotate());
//        document.setMargins(20, 20, 20, 20);
//
//        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
//        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
//        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1}))
//                .useAllAvailableWidth();
//        BufferedReader br = new BufferedReader(new FileReader(DATA));
//        String line = br.readLine();
//        process(table, line, bold, true);
//        while ((line = br.readLine()) != null) {
//            process(table, line, font, false);
//        }
//        br.close();
//        document.add(table);
//
//        //Close document
//        document.close();
//    }
//
//    public void process(Table table, String line, PdfFont font, boolean isHeader) {
//        StringTokenizer tokenizer = new StringTokenizer(line, ";");
//        while (tokenizer.hasMoreTokens()) {
//            if (isHeader) {
//                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
//            } else {
//                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
//            }
//        }
//    }
//
//    // IMAGE EXAMPLE
//    public class ImagesNextToEachOther {
//        public static final String DEST = "./target/sandbox/tables/image_next_to_each_other.pdf";
//
//        public static final String IMG1 = "./src/main/resources/img/javaone2013.jpg";
//        public static final String IMG2 = "./src/main/resources/img/berlin2013.jpg";
//
//        public static void main(String[] args) throws Exception {
//            File file = new File(DEST);
//            file.getParentFile().mkdirs();
//
//            new ImagesNextToEachOther().manipulatePdf(DEST);
//        }
//
//        protected void manipulatePdf(String dest) throws Exception {
//            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
//            Document doc = new Document(pdfDoc);
//
//            Table table = new Table(UnitValue.createPercentArray(2)).useAllAvailableWidth();
//            table.addCell(createImageCell(IMG1));
//            table.addCell(createImageCell(IMG2));
//
//            doc.add(table);
//
//            doc.close();
//        }
//
//        private static Cell createImageCell(String path) throws MalformedURLException {
//            Image img = new Image(ImageDataFactory.create(path));
//            return new Cell().add(img.setAutoScale(true).setWidth(UnitValue.createPercentValue(100)));
//        }
//    }

}
