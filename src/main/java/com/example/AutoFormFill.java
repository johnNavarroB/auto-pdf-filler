package com.example;
// iText7: DOCUMENTS
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
// iText7: TEXT
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
// iText7: IMAGES
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
// exceptions
import java.net.MalformedURLException;
import java.io.IOException;


public class AutoFormFill
{
    // OUTPUT .PDF PATH
    private final String OUTPUT_FOLDER = "output-pdf-files\\";
    public String PDF_NAME = OUTPUT_FOLDER + "test" + ".pdf";
    // PICTURE PATHS
    private final String LOGO_PIC_PATH = ".\\pictures\\sample_logo.png";
    // TEXT DEFAULT VALUES
    private final String FONT = FontConstants.TIMES_ROMAN;
    private final float FONT_SIZE = 12;
    private final Color FONT_COLOR = Color.BLACK;
    // DOCUMENT DATA: INVOICE NUMBER
    private final String INVOICE_NUMBER_LABEL = "INVOICE Nº ";
    private String INVOICE_NUMBER = INVOICE_NUMBER_LABEL + "70";
    // DOCUMENT DATA: BODY
    private final String DATE_LABEL = "DATE: ";
    private String CURRENT_DATE = DATE_LABEL + "28/01/2022";
    private final String HEADER_1 = "ITEM";
    private final String HEADER_2 = "AMOUNT";
    private final String HEADER_3 = "PRICE";
    private final String HEADER_4 = "SUBTOTAL";
    // DOCUMENT DATA: FOOTER
    private final String SUBTOTAL = "Subtotal";
    private double SUBTOTAL_AMOUNT = 500;
    private final String DISCOUNT = "Discount (%)";
    private String DISCOUNT_AMOUNT = "50";
    private final String IVA = "IVA (21%)";
    private double IVA_AMOUNT = 105.99;
    private final String TOTAL = "Total";
    private double TOTAL_AMOUNT = 605.56;
    // DOCUMENT DATA: CONTACT DATA
    private final String CONTACT_NAME = "NAME: ";
    private String NAME = "Yardena Branislava Cesário Siena Faramond"; // MAX 42 CHARACTERS
    private final String CONTACT_ID = "ID: ";
    private String ID = "12345678Z"; // MAX 30 CHARACTERS
    private final String CONTACT_ADDRESS = "ADDRESS: ";
    private String ADDRESS = "Av. Cidade de Hayward. Bl. C1 e D2, Caves, R. Vale de Carneiros, 8000-"; // MAX 70 CHARACTERS
    // DOCUMENT DATA: COMPANY DATA
    private final String COMPANY_NAME = "AGILE PROGRAMMING";
    private final String COMPANY_OWNER = "Yardena Branislava Cesário Siena Faramond";
    private final String COMPANY_ADDRESS = "Av. Cidade de Hayward. Bl. C1 e D2";
    private final String COMPANY_PHONE = "(+01) 123 123 123";
    private final String COMPANY_CITY = "EUROPE";
    // DOCUMENT
    public Document PDF_DOCUMENT;
    /*
     * SET METHODS
     */
    public void setFileName( String fileName ) {PDF_NAME = OUTPUT_FOLDER + fileName + ".pdf";}
    public void setInvoiceNumber( String invoiceNumber ) {INVOICE_NUMBER = INVOICE_NUMBER_LABEL + invoiceNumber;}
    public void setDate( String date ) {CURRENT_DATE = DATE_LABEL + date;}
    public void setSubTotal( double subTotal ) {SUBTOTAL_AMOUNT = subTotal;}
    public void setDiscount( String discount ) {DISCOUNT_AMOUNT = discount;}
    public void setIVA( double iva ) {IVA_AMOUNT = iva;}
    public void setTotal( double total ) {TOTAL_AMOUNT = total;}
    public void setName( String name ) {NAME = name;}
    public void setID( String id ) {ID = id;}
    public void setAddress( String address ) {ADDRESS = address;}
    /**
     * Dynamically generates a specific image, sets its absolute position and dimensions.
     * <p>
     * 
     * @param document Refers to the .PDF document in which the image will be added
     * @param pathToPicture Refers to the location of the picture as a String
     * @param coordinates An array of float that must have 4 values explained below:
     *     - The first and second values are the absolute X and Y positions of the image
     *     - The third and fourth values are the absolute width and height dimensions of the image
     */
    private void addImage( Document document, String pathToPicture, float[] coordinates ) throws MalformedURLException
    {
        // Initialize ImageData object
        ImageData imageData = ImageDataFactory.create( pathToPicture );
        // Initialize Image object, requires a ImageData
        Image image = new Image( imageData );
        // Set the absolute horizontal and vertical position of the image
        image.setFixedPosition( coordinates[ 0 ], coordinates[ 1 ] );
        // Scale the image to absolute values
        image.scaleAbsolute( coordinates[ 2 ], coordinates[ 3 ] );
        // Add the Image to the .PDF file
        document.add( image );
    }
    /**
     * Dynamically generates a paragraph with specific properties.
     * <p>
     * 
     * @param document Refers to the .PDF document in which the paragraph will be generated
     * @param paragraphText The text of the paragraph
     * @param font The font of the paragraph
     * @param fontColor The color of the text
     * @param underlined A boolean value, determines if the text is underlined
     * @param coordinates An array of floats of three values. The X and Y absolute positions and the width of the paragraph respectively
     * @param fontSize The size of the text
     * @param textAlignment A TextAlignment object. If the text is not aligned, it can be "null" for no effect
     */
    private void addBasicParagraph( Document document, String paragraphText, String font, Color fontColor, boolean underlined, float[] coordinates, float fontSize, TextAlignment textAlignment ) throws IOException
    {
        // Initialize Text object
        Text text = new Text( paragraphText );
        // Set the font and color of the Text object
        text.setFont( PdfFontFactory.createFont( font ) );
        text.setFontColor( fontColor );
        // UNDERLINES TEXT
        if ( underlined ) text.setUnderline();
        // Initialize Paragraph object
        Paragraph paragraph = new Paragraph();
        // Add Text, set absolute position and font size to the Paragraph
        paragraph.add( text ); //                X                 Y               WIDTH
        paragraph.setFixedPosition( coordinates[ 0 ], coordinates[ 1 ], coordinates[ 2 ] );
        paragraph.setFontSize( fontSize );
        if ( textAlignment != null ) paragraph.setTextAlignment( textAlignment );
        // Add the Paragraph to the .PDF file
        document.add( paragraph );
    }
    /*
     * Adds the footer data.
     */
    private void addFooterData( Document document ) throws IOException
    {
        // SUBTOTAL
        float[] footerCoordinates1 = new float[]{ 90, 105, 100 };
        addBasicParagraph( document, String.valueOf( SUBTOTAL_AMOUNT ).replace( '.', ',' ) + "€", FONT, FONT_COLOR, false, footerCoordinates1, FONT_SIZE, null );
        // DISCOUNT
        float[] footerCoordinates2 = new float[]{ 210, 105, 100 };
        addBasicParagraph( document, DISCOUNT_AMOUNT, FONT, FONT_COLOR, false, footerCoordinates2, FONT_SIZE, null );
        // IVA
        float[] footerCoordinates3 = new float[]{ 315, 105, 100 };
        addBasicParagraph( document, String.valueOf( IVA_AMOUNT ).replace( '.', ',' ) + "€", FONT, FONT_COLOR, false, footerCoordinates3, FONT_SIZE, null );
        // TOTAL
        float[] footerCoordinates4 = new float[]{ 440, 100, 100 };
        addBasicParagraph( document, String.valueOf( TOTAL_AMOUNT ).replace( '.', ',' ) + "€", FONT, FONT_COLOR, false, footerCoordinates4, 20, null );
    }
    /**
     * Adds the footer at the bottom of the .PDF document
     */
    private void addFooter( Document document ) throws IOException
    {
        // SUBTOTAL
        float[] footerCoordinates1 = new float[]{ 80, 135, 100 };
        addBasicParagraph( document, SUBTOTAL, FONT, FONT_COLOR, false, footerCoordinates1, FONT_SIZE, null );
        // DISCOUNT
        float[] footerCoordinates2 = new float[]{ 180, 135, 100 };
        addBasicParagraph( document, DISCOUNT, FONT, FONT_COLOR, false, footerCoordinates2, FONT_SIZE, null );
        // IVA
        float[] footerCoordinates3 = new float[]{ 300, 135, 100 };
        addBasicParagraph( document, IVA, FONT, FONT_COLOR, false, footerCoordinates3, FONT_SIZE, null );
        // TOTAL
        float[] footerCoordinates4 = new float[]{ 430, 130, 100 };
        addBasicParagraph( document, TOTAL, FONT, FONT_COLOR, false, footerCoordinates4, 24, null );
    }
    /*
     * If the object has not amount, price and total values, returns true.
     */
    private boolean isEmpty( String amount, String price, String total )
    {
        return amount.equals( "0" ) && price.equals( "0,0€" ) && total.equals( "0,0€" );
    }
    /**
     * Adds the desired object to the Document specified.
     * An object is composed of: height, object, amount, price and total.
     *     @param document represents the document where the object will be written
     *     @param height represents the y axis where the object will be written
     *     @param object its description. A text that will be written to the left
     *     @param amount a numeric value that it's used to calculate the "total" value
     *     @param price same as "amount"
     *     @param total this value is calculated automatically
     */
    public void addPDFObject( Document document, float height, String object, String amount, String price, String total )
    {
        if ( isEmpty( amount, price, total ) )
        {
            amount = "";
            price = "";
            total = "";
        }
        try
        {
            // ITEM
            float[] headerCoordinates1 = new float[]{ 50, height, 270 };
            addBasicParagraph( document, object, FONT, FONT_COLOR, false, headerCoordinates1, 10, null );
            // AMOUNT
            float[] headerCoordinates2 = new float[]{ 280, height, 100 };
            addBasicParagraph( document, amount, FONT, FONT_COLOR, false, headerCoordinates2, FONT_SIZE, TextAlignment.CENTER );
            // PRICE
            float[] headerCoordinates3 = new float[]{ 370, height, 100 };
            addBasicParagraph( document, price, FONT, FONT_COLOR, false, headerCoordinates3, FONT_SIZE, TextAlignment.CENTER );
            // SUBTOTAL
            float[] headerCoordinates4 = new float[]{ 460, height, 100 };
            addBasicParagraph( document, total, FONT, FONT_COLOR, false, headerCoordinates4, FONT_SIZE, TextAlignment.CENTER );

        } catch ( IOException exception ) {exception.printStackTrace();}
    }
    /**
     * Adds the header at the center of the .PDF document
     */
    private void addHeader( Document document ) throws IOException
    {
        // ITEM
        float[] headerCoordinates1 = new float[]{ 50, 540, 100 };
        addBasicParagraph( document, HEADER_1, FONT, FONT_COLOR, false, headerCoordinates1, FONT_SIZE, null );
        // AMOUNT
        float[] headerCoordinates2 = new float[]{ 300, 540, 100 };
        addBasicParagraph( document, HEADER_2, FONT, FONT_COLOR, false, headerCoordinates2, FONT_SIZE, null );
        // PRICE
        float[] headerCoordinates3 = new float[]{ 400, 540, 100 };
        addBasicParagraph( document, HEADER_3, FONT, FONT_COLOR, false, headerCoordinates3, FONT_SIZE, null );
        // SUBTOTAL
        float[] headerCoordinates4 = new float[]{ 480, 540, 100 };
        addBasicParagraph( document, HEADER_4, FONT, FONT_COLOR, false, headerCoordinates4, FONT_SIZE, null );
    }
    /**
     * Adds the date to the right at the center of the .PDF document
     */
    private void addDate( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 390, 570, 120 };
        addBasicParagraph( document, CURRENT_DATE, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, null );
    }


    private void addContactAddress( Document document ) throws IOException
    {
        float[] contactAddressCoordinates = new float[]{ 50, 610, 100 };
        addBasicParagraph( document, CONTACT_ADDRESS, FONT, Color.BLUE, true, contactAddressCoordinates, FONT_SIZE, TextAlignment.LEFT );
        float[] addressCoordinates = new float[]{ 50, 595, 300 };
        addBasicParagraph( document, ADDRESS, FONT, FONT_COLOR, false, addressCoordinates, 10, TextAlignment.LEFT );
    }


    private void addContactID( Document document ) throws IOException
    {
        float[] contactNameCoordinates = new float[]{ 50, 625, 100 };
        addBasicParagraph( document, CONTACT_ID, FONT, Color.BLUE, true, contactNameCoordinates, FONT_SIZE, TextAlignment.LEFT );
        float[] nameCoordinates = new float[]{ 85, 625, 270 };
        addBasicParagraph( document, ID, FONT, FONT_COLOR, false, nameCoordinates, FONT_SIZE, TextAlignment.LEFT );
    }


    private void addContactName( Document document ) throws IOException
    {
        float[] contactNameCoordinates = new float[]{ 50, 640, 100 };
        addBasicParagraph( document, CONTACT_NAME, FONT, Color.BLUE, true, contactNameCoordinates, FONT_SIZE, TextAlignment.LEFT );
        float[] nameCoordinates = new float[]{ 110, 640, 270 };
        addBasicParagraph( document, NAME, FONT, FONT_COLOR, false, nameCoordinates, FONT_SIZE, TextAlignment.LEFT );
    }
    /**
     * Adds the contact details to the left at the center of the .PDF document
     */
    private void addContactDetails( Document document ) throws IOException
    {
        addContactName( document );
        addContactID( document );
        addContactAddress( document );
    }


    private void addCompanyCity( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 320, 740, 220 };
        addBasicParagraph( document, COMPANY_CITY, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, TextAlignment.CENTER );
    }


    private void addCompanyPhone( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 320, 755, 220 };
        addBasicParagraph( document, COMPANY_PHONE, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, TextAlignment.CENTER );
    }


    private void addCompanyAddress( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 320, 770, 220 };
        addBasicParagraph( document, COMPANY_ADDRESS, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, TextAlignment.CENTER );
    }


    private void addCompanyOwner( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 320, 785, 220 };
        addBasicParagraph( document, COMPANY_OWNER, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, TextAlignment.CENTER );
    }


    private void addCompanyName( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 320, 800, 220 };
        addBasicParagraph( document, COMPANY_NAME, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, TextAlignment.CENTER );
    }
    /**
     * Adds the company text on top right of the .PDF document
     */
    private void addCompanyText( Document document ) throws IOException
    {
        addCompanyName( document );
        addCompanyOwner( document );
        addCompanyAddress( document );
        addCompanyPhone( document );
        addCompanyCity( document );
    }
    /**
     * Adds the invoice text on top left of the .PDF document
     */
    private void addInvoiceText( Document document ) throws IOException
    {
        float[] coordinates = new float[]{ 85, 790, 100 };
        addBasicParagraph( document, INVOICE_NUMBER, FONT, FONT_COLOR, false, coordinates, FONT_SIZE, null );
    }
    /*
    private static void addAreaBreak( Document document )
    {
        // Initialize AreaBreak object: terminates the current content area and creates a new one
        AreaBreak areaBreak = new AreaBreak();
        // Add the AreaBreak to the .PDF file
        document.add( areaBreak );
    }
    */
    private Document generateDocument()
    {
        try 
        {
            // Initialize PdfWriter object: represents the Document Writer for a .PDF file
            PdfWriter pdfWriter = new PdfWriter( PDF_NAME );
            /* 
            * Initialize PdfDocument object: represents the .PDF document. Requires a PdfWriter.
            * Various elements can be added to it, like: page, font, file attachment...
            */
            PdfDocument pdfDocument = new PdfDocument( pdfWriter );
            // Initialize Document object: represents the .PDF file. Uses a PdfDocument
            return new Document( pdfDocument );
        } catch ( IOException exception ) {exception.printStackTrace();}
        return null;
    }
    /*
     * Instantiates the PDF document.
     */
    public void instantiatePDFDocument( String documentName )
    {
        setFileName( documentName );
        PDF_DOCUMENT = generateDocument();
    }
    /*
     * Closes the PDF document.
     */
    public void closePDFDocument()
    {
        // Closes the .PDF file
        PDF_DOCUMENT.close();
    }
    /*
     * Fills the PDF document with the images, constant and dynamic values.
     */
    public void addPDFDocumentData()
    {
        try 
        {
            // X, Y positions, width and height of LOGO_PIC_PATH
            float[] logoCoordinates = new float[]{ 40, 660, 230, 120 };
            // Adds the LOGO_PIC_PATH image
            addImage( PDF_DOCUMENT, LOGO_PIC_PATH, logoCoordinates );
            // Add text to the .PDF file
            addInvoiceText( PDF_DOCUMENT );
            addCompanyText( PDF_DOCUMENT );
            addContactDetails( PDF_DOCUMENT );
            addDate( PDF_DOCUMENT );
            addHeader( PDF_DOCUMENT );
            addFooter( PDF_DOCUMENT );
            addFooterData( PDF_DOCUMENT );
        } catch ( IOException exception ) {exception.printStackTrace();}

        System.out.println( "Portable Document Format initialization completed\n" );
    }


    public AutoFormFill()
    {}
}