package com.ui;

import com.example.AutoFormFill;
import com.ui.data.PDFComponent;
import com.ui.data.AbstractPDFComponent;
import com.ui.fieldlimit.TextFieldLimit;
import com.ui.fieldlimit.IntegerFieldLimit;
import java.io.File;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Window extends JFrame implements ActionListener
{   // GETS THE SIZE OF THE SYSTEM SCREEN
    private static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    // GETS THE WIDTH AND HEIGHT OF THE SYSTEM SCREEN
    public static final double X_DIMENSION = DIMENSION.getWidth();
    public static final double Y_DIMENSION = DIMENSION.getHeight();
    // SETS RELATIVE VALUES FOR THE MAIN WINDOW WIDTH AND HEIGHT
    public static final int WIDTH = (int) ((X_DIMENSION / 2) + (X_DIMENSION / 4));
    public static final int HEIGHT = (int) ((Y_DIMENSION / 2) + (Y_DIMENSION / 4));
    // THE NAME OF THE DEFAULT FONT DISPLAYED
    public static final String FONT_TYPE = "Serif";
    public static final int INPUT_FONT_SIZE = 25;
    private final Font FONT_BOLD = new Font( FONT_TYPE, Font.BOLD, getFontSize() );
    public static final Font FONT_INPUT = new Font( FONT_TYPE, Font.PLAIN, INPUT_FONT_SIZE );
    public static final Font TEXT_AREA_FONT = new Font( FONT_TYPE, Font.PLAIN, 12 );
    // TITLE BAR PROPERTIES
    private final String IMAGE_PATH = ".\\pictures\\sample_logo.png";
    private final Image IMAGE = Toolkit.getDefaultToolkit().getImage( IMAGE_PATH );
    private final String TITLE = "Automatic PDF Filler";
    // COLORS
    public static final java.awt.Color BLACK = new java.awt.Color( 70, 70, 70 );
    public static final java.awt.Color WHITE = new java.awt.Color( 200, 200, 200 );
    public static final java.awt.Color DISABLED_FIELD = new java.awt.Color( 190, 190, 190 );
    public static final java.awt.Color GRAY = new java.awt.Color( 100, 100, 100 );
    public static final java.awt.Color LIGHT_GRAY = new java.awt.Color( 120, 120, 120 );
    public static final java.awt.Color YELLOW = new java.awt.Color( 255, 255, 0 );
    // LAYOUT COMPONENTS: BUTTONS
    public JButton OBJECTS_BUTTON = new JButton();
    private JButton GENERATE_FILE_BUTTON = new JButton();
    private JButton UPDATE_BUTTON = new JButton();
    // LAYOUT COMPONENTS: PANELS
    private JPanel HEADER = new JPanel();
    private JPanel HEADER_SEPARATOR = new JPanel();
    private JPanel FOOTER = new JPanel();
    private JPanel BODY = new JPanel();
    // LAYOUT COMPONENTS: LABELS
    private JLabel INVOICE_LABEL = new JLabel( "INVOICE Nº:" );
    private JLabel DISCOUNT_LABEL = new JLabel( "DISCOUNT (%):" );
    private JLabel CLIENT_NAME = new JLabel( "NAME:" );
    private JLabel CLIENT_ID = new JLabel( "ID:" );
    private JLabel CLIENT_ADDRESS = new JLabel( "ADDRESS:" );
    // LAYOUT COMPONENTS: TEXTFIELDS
    private JTextField INVOICE_TEXTFIELD = new JTextField();
    private JTextField DISCOUNT_TEXTFIELD = new JTextField();
    private JTextField NAME_TEXTFIELD = new JTextField();
    private JTextField ID_TEXTFIELD = new JTextField();
    private JTextField ADDRESS_TEXTFIELD = new JTextField();
    public JTextArea JTEXTAREA = new JTextArea();
    // OBJECTS
    public static ArrayList<PDFComponent> OBJECTS = new ArrayList<>();
    public static ArrayList<AbstractPDFComponent> DATA = new ArrayList<>();
    // AUTOFORMFILL
    public AutoFormFill AUTOFORMFILL;
    /*
     * Iterates for each object to add it and returns the total price of all.
     */
    private double addPDFObjects( AutoFormFill autoFormFill )
    {
        double total = 0;
        float height = 510;
        for ( AbstractPDFComponent abstractPDFComponent : DATA )
        {
            String object = abstractPDFComponent.getObject();
            String amount = String.valueOf( abstractPDFComponent.getAmount() );
            String price = String.valueOf( abstractPDFComponent.getPrice() ).replace( ".", "," ) + "€";
            double subtotal = abstractPDFComponent.getTotal();
            subtotal = Math.round( subtotal * 100.0 ) / 100.0;

            total += subtotal;
            total = Math.round( total * 100.0 ) / 100.0;

            autoFormFill.addPDFObject( autoFormFill.PDF_DOCUMENT, height, object, amount, price, (subtotal + "€").replace( '.', ',' ) );
            height -= 30;
        }
        return total;
    }
    /*
     * Gets the current date as a String.
     */
    private String getCurrentDate( String pattern )
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( pattern );
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format( localDateTime );
    }
    /*
     * Applies a discount and returns the value discounted as a double.
     */
    private double applyDiscount( String discount, double total )
    {
        if ( IntegerFieldLimit.isNumber( discount ) ) 
        {
            double discountValue = Double.valueOf( discount );
            if ( discountValue > 100 ) discountValue = 100;
            total = total * ( (100 - discountValue) / 100 );
        }
        return total;
    }
    /*
     * Fills all the fields of the PDF document.
     */
    private void fillPDFDocumentFields( AutoFormFill autoFormFill, String name, String id, String address, String discount, String invoice, double total )
    {
        // INVOICE NUMBER AND DATE
        autoFormFill.setInvoiceNumber( invoice );
        autoFormFill.setDate( getCurrentDate( "dd/MM/yyyy" ) );
        // CLIENT DATA
        autoFormFill.setName( name );
        autoFormFill.setID( id );
        autoFormFill.setAddress( address );
        // FOOTER DATA
        autoFormFill.setSubTotal( total );
        autoFormFill.setDiscount( discount );
        // APPLY DISCOUNT IF ANY
        total = applyDiscount( discount, total );
        // APPLY 24% IVA
        double iva = total * .21;
        iva = Math.round( iva * 100.0 ) / 100.0;
        // ADD THE IVA AND TOTAL TO THE DOCUMENT
        double finalTotal = total + iva;
        finalTotal = Math.round( finalTotal * 100.0 ) / 100.0;
        autoFormFill.setIVA( iva );
        autoFormFill.setTotal( finalTotal );
    }
    /*
     * Instantiates the PDF document and adds the data.
     */
    private void setPDFDocumentData( String name, String id, String address, String discount, String invoice )
    {
        if ( discount.length() == 0 ) discount = "-";

        String fileName = name.split( " " )[ 0 ].replaceAll( "[^\\p{Alpha}\\p{Digit}]+", "" );

        fileName = invoice + " " + fileName + "-" + getCurrentDate( "yyyyMMdd" );

        AUTOFORMFILL = new AutoFormFill();
        AUTOFORMFILL.instantiatePDFDocument( fileName );

        double total = addPDFObjects( AUTOFORMFILL );
        
        fillPDFDocumentFields( AUTOFORMFILL, name, id, address, discount, invoice, total );

        AUTOFORMFILL.addPDFDocumentData();

        AUTOFORMFILL.closePDFDocument();
    }
    /*
     * Sets the data from the user input into 
     * the PDF fields and generates the file.
     */
    private void generateFile()
    {   // RETRIEVE DATA
        String name = NAME_TEXTFIELD.getText();
        String id = ID_TEXTFIELD.getText();
        String address = ADDRESS_TEXTFIELD.getText();
        String discount = DISCOUNT_TEXTFIELD.getText();
        String invoice = INVOICE_TEXTFIELD.getText();
        // SETS THE DATA AND GENERATES THE PDF DOCUMENT
        setPDFDocumentData( name, id, address, discount, invoice );
        // OPEN MESSAGE WINDOW
        String absolutePath = new File( AUTOFORMFILL.PDF_NAME ).getAbsolutePath();
        MessageWindow messageWindow = new MessageWindow( this, false, absolutePath );
        messageWindow.setVisible( true );
    }
    /*
     * Opens a new window to add objects to the PDF file.
     */
    private void openObjectsWindow()
    {
        ObjectsWindow objectsWindow = new ObjectsWindow( this, false );
        objectsWindow.setVisible( true );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource() == OBJECTS_BUTTON ) openObjectsWindow();
        if ( actionEvent.getSource() == GENERATE_FILE_BUTTON ) generateFile();
        if ( actionEvent.getSource() == UPDATE_BUTTON ) updateCoordinates();
    }
    /*
     * THIS METHOD IS UNUSED, SINCE THE WINDOW SIZE CAN NO LONGER BE CHANGED
     * Adds a "listener" component that executes a piece of code 
     * automatically every time the main window is resized.
     *//*
    private void setOnResizeListener()
    {
        addComponentListener( new ComponentAdapter()
        {
            @Override
            public void componentResized( ComponentEvent componentEvent )
            {// SET COMPONENT BOUNDS HERE TO CHANGE WHEN THE WINDOW IS RESIZED
                
                int headerHeight = getHeaderHeight();
                HEADER.setBounds( 0, 0, getContentPane().getWidth(), headerHeight - 5 );
                HEADER_SEPARATOR.setBounds( 0, 0, getContentPane().getWidth(), headerHeight );
                FOOTER.setBounds( 0, getContentPane().getHeight() - (headerHeight - 5), getContentPane().getWidth(), headerHeight - 5 );
                BODY.setBounds( getContentPane().getWidth() / 4, headerHeight, getContentPane().getWidth() / 2, getContentPane().getHeight() - ( (headerHeight * 2) - 5 ) );
                
            }
        });
    }*/
    /*
     * Gets a dynamic value for the height and limits its maximum.
     */
    private int getHeaderHeight()
    {
        int height = (int) Math.round( getContentPane().getHeight() / 3.8 );
        if ( height >= 120 ) height = 120;
        return height;
    }
    /*
     * Gets a value for the font size dynamically based on the main window's width.
     * This value is capped with a maximum and minimum values of 60 and 20 respectively.
     * @return An integer value calculated dynamically based on the main window's current width
     */
    private int getFontSize()
    {
        int size = (int) Math.round( getContentPane().getWidth() * .05 );
        if ( size >= 60 ) size = 60;
        if ( size <= 20 ) size = 20;
        return size;
    }
    /*
     * Sets the position and size of the body components.
     */
    private void setBodyComponentsCoordinates( int contentPaneWidth, int contentPaneHeight, int headerHeight )
    {
        HEADER.setBounds( 0, 0, contentPaneWidth, headerHeight - 5 );
        HEADER_SEPARATOR.setBounds( 0, headerHeight - 5, contentPaneWidth, (int) (headerHeight * .04) );
        FOOTER.setBounds( 0, contentPaneHeight - (headerHeight - 5), contentPaneWidth, headerHeight - 5 );
        BODY.setBounds( contentPaneWidth / 4, headerHeight, contentPaneWidth / 2, contentPaneHeight - ( (headerHeight * 2) - 5 ) );
    }
    /*
     * Sets the color of the body components.
     */
    private void setBodyComponentsColor()
    {
        HEADER.setBackground( BLACK );
        HEADER_SEPARATOR.setBackground( YELLOW );
        FOOTER.setBackground( BLACK );
        BODY.setBackground( LIGHT_GRAY );
    }
    /*
     * Adds the window JPanels to create the basic structure.
     */
    private void addBodyComponents()
    {
        add( HEADER_SEPARATOR );
        add( HEADER );
        add( FOOTER );
        add( BODY );
    }
    /*
     * Adds the client input fields.
     */
    private void addClientFields()
    {
        CLIENT_NAME.setFont( FONT_BOLD );
        CLIENT_ID.setFont( FONT_BOLD );
        CLIENT_ADDRESS.setFont( FONT_BOLD );
        BODY.add( CLIENT_NAME );
        BODY.add( NAME_TEXTFIELD );
        BODY.add( CLIENT_ID );
        BODY.add( ID_TEXTFIELD );
        BODY.add( CLIENT_ADDRESS );
        BODY.add( ADDRESS_TEXTFIELD );
    }
    /*
     * Adds the discount field.
     */
    private void addDiscount()
    {
        DISCOUNT_LABEL.setFont( FONT_BOLD );
        BODY.add( DISCOUNT_LABEL );
        BODY.add( DISCOUNT_TEXTFIELD );
    }
    /*
     * Adds the invoice number.
     */
    private void addInvoiceNumber()
    {
        INVOICE_LABEL.setFont( FONT_BOLD );
        add( INVOICE_LABEL );
        add( INVOICE_TEXTFIELD );
    }
    /*
     * From the previous text values, returns the text in each textfield.
     */
    private void loadTextFieldsData( String[] savedData )
    {
        INVOICE_TEXTFIELD.setText( savedData[ 0 ] );
        NAME_TEXTFIELD.setText( savedData[ 1 ] );
        ID_TEXTFIELD.setText( savedData[ 2 ] );
        ADDRESS_TEXTFIELD.setText( savedData[ 3 ] );
        DISCOUNT_TEXTFIELD.setText( savedData[ 4 ] );
    }
    /*
     * Saves the text of each textfield and returns it as an array of 5 values.
     */
    private String[] saveTextFieldsData()
    {
        return new String[] { INVOICE_TEXTFIELD.getText(), NAME_TEXTFIELD.getText(), ID_TEXTFIELD.getText(),
                              ADDRESS_TEXTFIELD.getText(), DISCOUNT_TEXTFIELD.getText() };
    }
    /*
     * Sets all the coordinates again, restoring the data on the fields.
     */
    public void updateCoordinates()
    {
        String[] savedData = saveTextFieldsData();
        setTextFieldsCoordinates();
        setButtonsCoordinates();
        loadTextFieldsData( savedData );
    }
    /*
     * Sets the position and size of the buttons.
     */
    public void setButtonsCoordinates()
    {
        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();
        UPDATE_BUTTON.setBounds( (int) (width * .025), (int) (height * .3), (int) (width * .2), (int) (height * .08) );
        OBJECTS_BUTTON.setBounds( (int) (width * .1), (int) (height * .23), (int) (width * .3), (int) (height * .08) );
        GENERATE_FILE_BUTTON.setBounds( (int) (width * .13), (int) (height * .55), (int) (width * .25), (int) (height * .08) );
    }
    /*
     * Adds the button.
     */
    private void addUpdateButton()
    {// SETS THE TASK TO BE PERFORMED BY THE BUTTON
        UPDATE_BUTTON.addActionListener( this );
        UPDATE_BUTTON.setText( "REFRESH" );
        UPDATE_BUTTON.setFont( FONT_BOLD );
        UPDATE_BUTTON.setFocusable( false );
        add( UPDATE_BUTTON );
    }
    /*
     * Adds the button.
     */
    private void addGenerateFileButton()
    {// SETS THE TASK TO BE PERFORMED BY THE BUTTON
        GENERATE_FILE_BUTTON.addActionListener( this );
        GENERATE_FILE_BUTTON.setText( "GENERATE INVOICE" );
        GENERATE_FILE_BUTTON.setFont( FONT_BOLD );
        GENERATE_FILE_BUTTON.setFocusable( false );
        BODY.add( GENERATE_FILE_BUTTON );
    }
    /*
     * Adds the button.
     */
    private void addObjectsButton()
    {// SETS THE TASK TO BE PERFORMED BY THE BUTTON
        OBJECTS_BUTTON.addActionListener( this );
        OBJECTS_BUTTON.setText( "ADD ITEMS" );
        OBJECTS_BUTTON.setFont( FONT_BOLD );
        OBJECTS_BUTTON.setFocusable( false );
        BODY.add( OBJECTS_BUTTON );
        //JBUTTON.setEnabled( false );
    }
    /*
     * Sets the initial values for the elemental properties of the main window.
     * The properties are the absolute window dimensions and starting position, 
     * the main window's title, default close operation and sets it visible.
     */
    private void initializeWindowProperties()
    {// "null" PARAMETER = ABSOLUTE WINDOW POSITION DIMENSIONS
        setLayout( null );
        // SCREEN SPAWNS ON THE CENTER
        //setBounds( (int) X_DIMENSION / 2 - WIDTH / 2, (int) Y_DIMENSION / 2 - HEIGHT / 2, WIDTH, HEIGHT );
        // SCREEN SPAWNS ON THE LEFT
        setBounds( (int) X_DIMENSION / 30, (int) Y_DIMENSION / 2 - HEIGHT / 2, WIDTH, HEIGHT );
        // SETS THE IMAGE OF THE TITLE BAR
        setIconImage( IMAGE );
        // SETS THE TITLE OF THE MAIN WINDOW
        setTitle( TITLE );
        // ITS SIZE CAN NOT BE CHANGED
        setResizable( false );
        // OPERATION WHEN THE WINDOW IS CLOSED, TERMINATES EXECUTION
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        // SETS A MINIMUM SIZE (NOT NEEDED IF THE WINDOW IS NOT RESIZABLE)
        //setMinimumSize( new Dimension( 600, 300 ) );
        // SETS BACKGROUND COLOR
        getContentPane().setBackground( GRAY );
        // ENABLES THE MAIN WINDOW TO BE DISPLAYED
        // WARNING!! MAIN WINDOW WIDTH AND HEIGHT ARE ZERO UNTIL THE WINDOW IS VISIBLE
        setVisible( true );
    }
    /*
     * Sets the position and size of the address.
     */
    private void setClientAddressCoordinates()
    {
        ADDRESS_TEXTFIELD.setBounds( (int) (BODY.getWidth() * .27), (int) (BODY.getHeight() * .24), (int) (BODY.getWidth() * .7), (int) (BODY.getHeight() * .08) );
        ADDRESS_TEXTFIELD.setFont( FONT_INPUT );
        // LIMITS THE TEXTFIELD MAXIMUM CHARACTERS
        ADDRESS_TEXTFIELD.setDocument( new TextFieldLimit( 70 ) );
        CLIENT_ADDRESS.setBounds( (int) (BODY.getWidth() * .1), (int) (BODY.getHeight() * .24), (int) (BODY.getWidth() * .3), (int) (BODY.getHeight() * .08) );
    }
    /*
     * Sets the position and size of the client ID.
     */
    private void setClientIDCoordinates()
    {
        ID_TEXTFIELD.setBounds( (getContentPane().getWidth() / 2) - (getContentPane().getWidth() / 3), (int) (BODY.getHeight() * .14), (int) (BODY.getWidth() * .5), (int) (BODY.getHeight() * .08) );
        ID_TEXTFIELD.setFont( FONT_INPUT );
        // LIMITS THE TEXTFIELD MAXIMUM CHARACTERS
        ID_TEXTFIELD.setDocument( new TextFieldLimit( 30 ) );
        CLIENT_ID.setBounds( (int) (BODY.getWidth() * .25), (int) (BODY.getHeight() * .14), (int) (BODY.getWidth() * .3), (int) (BODY.getHeight() * .08) );
    }
    /*
     * Sets the position and size of the client name.
     */
    private void setClientNameCoordinates()
    {
        NAME_TEXTFIELD.setBounds( (getContentPane().getWidth() / 2) - (getContentPane().getWidth() / 3), (int) (BODY.getHeight() * .04), (int) (BODY.getWidth() * .5), (int) (BODY.getHeight() * .08) );
        NAME_TEXTFIELD.setFont( FONT_INPUT );
        // LIMITS THE TEXTFIELD MAXIMUM CHARACTERS
        NAME_TEXTFIELD.setDocument( new TextFieldLimit( 42 ) );
        CLIENT_NAME.setBounds( (int) (BODY.getWidth() * .2), (int) (BODY.getHeight() * .04), (int) (BODY.getWidth() * .3), (int) (BODY.getHeight() * .08) );
    }
    /*
     * Sets the position and size of the discount textfield.
     */
    private void setDiscountCoordinates()
    {
        DISCOUNT_TEXTFIELD.setBounds( (int) (BODY.getWidth() * .735), (int) (BODY.getHeight() * .48), (int) (BODY.getWidth() * .1), (int) (BODY.getHeight() * .08) );
        DISCOUNT_TEXTFIELD.setFont( FONT_INPUT );
        // LIMITS THE TEXTFIELD MAXIMUM CHARACTERS
        DISCOUNT_TEXTFIELD.setDocument( new IntegerFieldLimit( 3 ) );
        DISCOUNT_LABEL.setBounds( (int) (BODY.getWidth() * .49), (int) (BODY.getHeight() * .48), (int) (BODY.getWidth() * .4), (int) (BODY.getHeight() * .08) );
    }
    /*
     * Adds the text area.
     */
    private void addTextArea()
    {
        JTEXTAREA.setEditable( false );
        JTEXTAREA.setBackground( WHITE );
        add( JTEXTAREA );
    }
    /*
     * Sets the position and size of the text area.
     */
    private void setTextAreaCoordinates()
    {
        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();
        JTEXTAREA.setBounds( (int) (width * .75), /*(int) (height * .145)*/ HEADER_SEPARATOR.getY() + HEADER_SEPARATOR.getHeight(), (int) (width * .25), (int) (height * .70) );
        JTEXTAREA.setFont( TEXT_AREA_FONT );
    }
    /*
     * Sets the position and size of the invoice number.
     */
    private void setInvoiceNumberCoordinates()
    {
        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();
        INVOICE_TEXTFIELD.setBounds( (int) (width * .17), (int) (height * .2), 60, 50 );
        INVOICE_TEXTFIELD.setFont( FONT_INPUT );
        // LIMITS THE TEXTFIELD MAXIMUM CHARACTERS
        INVOICE_TEXTFIELD.setDocument( new IntegerFieldLimit( 3 ) );
        INVOICE_LABEL.setBounds( (int) (width * .07), (int) (height * .2), (int) (width * .2), (int) (height * .08) );
    }
    /*
     * Sets position and size to the textfields.
     */
    public void setTextFieldsCoordinates()
    {
        // INVOICE NUMBER
        setInvoiceNumberCoordinates();
        // ADDS THE DISCOUNT TEXTFIELD
        setDiscountCoordinates();
        // CLIENT NAME
        setClientNameCoordinates();
        // CLIENT ID
        setClientIDCoordinates();
        // CLIENT ADDRESS
        setClientAddressCoordinates();
        // TEXT AREA
        setTextAreaCoordinates();
    }
    /*
     * Sets the initial values for the elemental properties 
     * of the main window and adds the layout components.
     */
    private void initializeWindowData()
    {// SETS THE MAIN WINDOW PROPERTIES
        initializeWindowProperties();
        // ADDS THE INVOICE NUMBER
        addInvoiceNumber();
        // ADDS THE DISCOUNT TEXTFIELD
        addDiscount();
        // ADDS THE BODY CONTENT
        addClientFields();
        // ADDS THE TEXTAREA
        addTextArea();
        // ADDS BODY JPANELS
        addBodyComponents();
        // ADDS THE BUTTONS
        addUpdateButton();
        addObjectsButton();
        addGenerateFileButton();
        // SETS THE BODY JPANELS COLORS
        setBodyComponentsColor();
        // SETS THE BODY JPANELS COORDINATES
        setBodyComponentsCoordinates( getContentPane().getWidth(), getContentPane().getHeight(), getHeaderHeight() );
    }
    /*
     * The unique constructor of the "Window" object.
     * Generates the main window and sets its properties.
     */
    public Window()
    {   // Spawns the main window, sets a title and basic properties
        initializeWindowData();
        // SETS THE TEXTFIELDS A POSITION AND SIZE
        setTextFieldsCoordinates();
        setButtonsCoordinates();
    }
}