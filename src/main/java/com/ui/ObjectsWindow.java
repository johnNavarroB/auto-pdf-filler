package com.ui;

import com.ui.data.AbstractPDFComponent;
import com.ui.data.PDFComponent;
import com.ui.data.PDFComponentListener;
import com.ui.fieldlimit.DoubleFieldLimit;
import com.ui.fieldlimit.IntegerFieldLimit;
import com.ui.fieldlimit.TextFieldLimit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ObjectsWindow extends JDialog implements ActionListener
{
    Window PARENT_WINDOW;
    // TITLE BAR PROPERTIES
    private final String IMAGE_PATH = ".\\pictures\\sample_logo.png";
    private final Image IMAGE = Toolkit.getDefaultToolkit().getImage( IMAGE_PATH );
    private final String TITLE = "Automatic PDF Filler";
    // LAYOUT COMPONENTS: PANELS
    private JPanel HEADER = new JPanel();
    // LAYOUT COMPONENTS: LABELS
    private final Font FONT = new Font( "Serif", Font.BOLD, 28 );
    private JLabel OBJECT_LABEL = new JLabel( "ITEM" );
    private JLabel AMOUNT_LABEL = new JLabel( "AMOUNT" );
    private JLabel PRICE_LABEL = new JLabel( "PRICE" );
    private JLabel TOTAL_LABEL = new JLabel( "SUBTOTAL" );
    // LAYOUT COMPONENTS: BUTTONS
    //private JButton ADD_BUTTON = new JButton( "+" );
    private JButton ACCEPT_BUTTON = new JButton( "ACCEPT" );


    private void initializeWindowProperties()
    {// "null" PARAMETER = ABSOLUTE WINDOW POSITION DIMENSIONS
        setLayout( null );
        // SCREEN SPAWNS ON THE CENTER
        setBounds( (int) Window.X_DIMENSION / 2 - Window.WIDTH / 2, (int) Window.Y_DIMENSION / 2 - Window.HEIGHT / 2, Window.WIDTH, Window.HEIGHT );
        // SETS THE IMAGE OF THE TITLE BAR
        setIconImage( IMAGE );
        // SETS THE TITLE OF THE MAIN WINDOW
        setTitle( TITLE );
        // ITS SIZE CAN NOT BE CHANGED
        setResizable( false );
        // SETS BACKGROUND COLOR
        getContentPane().setBackground( Window.GRAY );
        // ENABLES THE MAIN WINDOW TO BE DISPLAYED
        // WARNING!! MAIN WINDOW WIDTH AND HEIGHT ARE ZERO UNTIL THE WINDOW IS VISIBLE
        setVisible( true );
    }
    /*
     * Sets a text field size, position, font, text limit and adds it to the window.
     */
    private void configureTotalTextfield( int heightOffset, int windowWidth, int windowHeight, PDFComponent pdfComponent )
    {
        int widthOffset = (int) (windowWidth * .84);
        int XAxis = HEADER.getX() + widthOffset;
        int YAxis = (int) (HEADER.getY() + ((HEADER.getHeight() + heightOffset) + (windowHeight * .01) ));
        int textFieldWidth = (int) (windowWidth * .065);
        int textFieldHeight = (int) (windowHeight * .07);
        pdfComponent.getTotalTextField().setBounds( XAxis, YAxis, textFieldWidth, textFieldHeight );
        pdfComponent.getTotalTextField().setFont( Window.FONT_INPUT );
        pdfComponent.getTotalTextField().setDocument( new DoubleFieldLimit( 7 ) );
        pdfComponent.getTotalTextField().setEditable( false );
        pdfComponent.getTotalTextField().setBackground( Window.DISABLED_FIELD );
        add( pdfComponent.getTotalTextField() );
    }
    /*
     * Sets a text field size, position, font, text limit and adds it to the window.
     */
    private void configurePriceTextfield( int heightOffset, int windowWidth, int windowHeight, PDFComponent pdfComponent )
    {
        int widthOffset = (int) (windowWidth * .70);
        int XAxis = HEADER.getX() + widthOffset;
        int YAxis = (int) (HEADER.getY() + ((HEADER.getHeight() + heightOffset) + (windowHeight * .01) ));
        int textFieldWidth = (int) (windowWidth * .065);
        int textFieldHeight = (int) (windowHeight * .07);
        pdfComponent.getPriceTextField().setBounds( XAxis, YAxis, textFieldWidth, textFieldHeight );
        pdfComponent.getPriceTextField().setFont( Window.FONT_INPUT );
        pdfComponent.getPriceTextField().setDocument( new DoubleFieldLimit( 7 ) );
        // ADDS A LISTENER TO CALCULATE TOTAL
        PDFComponentListener pdfComponentListener = new PDFComponentListener( pdfComponent );
        pdfComponent.getPriceTextField().getDocument().addDocumentListener( pdfComponentListener );
        add( pdfComponent.getPriceTextField() );
    }
    /*
     * Sets a text field size, position, font, text limit and adds it to the window.
     */
    private void configureAmountTextfield( int heightOffset, int windowWidth, int windowHeight, PDFComponent pdfComponent )
    {
        int widthOffset = (int) (windowWidth * .57);
        int XAxis = HEADER.getX() + widthOffset;
        int YAxis = (int) (HEADER.getY() + ((HEADER.getHeight() + heightOffset) + (windowHeight * .01) ));
        int textFieldWidth = (int) (windowWidth * .06);
        int textFieldHeight = (int) (windowHeight * .07);
        pdfComponent.getAmountTextField().setBounds( XAxis, YAxis, textFieldWidth, textFieldHeight );
        pdfComponent.getAmountTextField().setFont( Window.FONT_INPUT );
        pdfComponent.getAmountTextField().setDocument( new IntegerFieldLimit( 3 ) );
        // ADDS A LISTENER TO CALCULATE TOTAL
        PDFComponentListener pdfComponentListener = new PDFComponentListener( pdfComponent );
        pdfComponent.getAmountTextField().getDocument().addDocumentListener( pdfComponentListener );
        add( pdfComponent.getAmountTextField() );
    }
    /*
     * Sets a text field size, position, font, text limit and adds it to the window.
     */
    private void configureObjectTextfield( int heightOffset, int windowWidth, int windowHeight, PDFComponent pdfComponent )
    {
        int widthOffset = (int) (windowWidth * .02);
        int XAxis = HEADER.getX() + widthOffset;
        int YAxis = (int) (HEADER.getY() + ((HEADER.getHeight() + heightOffset) + (windowHeight * .01) ));
        int textFieldWidth = (int) (windowWidth * .48);
        int textFieldHeight = (int) (windowHeight * .07);
        pdfComponent.getObjectTextField().setBounds( XAxis, YAxis, textFieldWidth, textFieldHeight );
        pdfComponent.getObjectTextField().setFont( Window.FONT_INPUT );
        pdfComponent.getObjectTextField().setDocument( new TextFieldLimit( 43 ) );
        add( pdfComponent.getObjectTextField() );
    }
    /*
     * Sets a panel background, size, position and adds it to the window.
     */
    private void configureJPanel( int heightOffset, PDFComponent pdfComponent )
    {
        int YAxis = HEADER.getY() + (HEADER.getHeight() + heightOffset);
        pdfComponent.getObjectsPanel().setBounds( HEADER.getX(), YAxis, HEADER.getWidth(), HEADER.getHeight() );
        pdfComponent.getObjectsPanel().setBackground( Window.BLACK );
        add( pdfComponent.getObjectsPanel() );
    }
    /*
     * Adds an object panel and add its PDFComponent to the OBJECTS ArrayList.
     */
    private void addObjectPanel( int heightOffset )
    {
        int windowWidth = getContentPane().getWidth();
        int windowHeight = getContentPane().getHeight();
        PDFComponent pdfComponent = new PDFComponent();
        // CONFIGURE JPANEL
        configureJPanel( heightOffset, pdfComponent );
        // CONFIGURE JTEXTFIELDS: OBJECT
        configureObjectTextfield( heightOffset, windowWidth, windowHeight, pdfComponent );
        // CONFIGURE JTEXTFIELDS: AMOUNT
        configureAmountTextfield( heightOffset, windowWidth, windowHeight, pdfComponent );
        // CONFIGURE JTEXTFIELDS: PRICE
        configurePriceTextfield( heightOffset, windowWidth, windowHeight, pdfComponent );
        // CONFIGURE JTEXTFIELDS: TOTAL
        configureTotalTextfield( heightOffset, windowWidth, windowHeight, pdfComponent );
        // ADD THE PDFCOMPONENT TO THE ARRAYLIST
        Window.OBJECTS.add( pdfComponent );
    }
    /*
     * Adds the accept button.
     */
    private void addAcceptButton()
    {
        ACCEPT_BUTTON.addActionListener( this );

        int windowWidth = getContentPane().getWidth();
        int windowHeight = getContentPane().getHeight();

        double buttonWidth = windowWidth * .2;

        ACCEPT_BUTTON.setBounds( (int) (windowWidth * .5) - (int) (buttonWidth / 2), (int) (windowHeight * .9), (int) buttonWidth, (int) (windowHeight * .07) );
        ACCEPT_BUTTON.setFont( FONT );
        ACCEPT_BUTTON.setFocusable( false );

        add( ACCEPT_BUTTON );
    }
    /*
     * Adds the object button.
     *//*
    private void addObjectButton()
    {
        ADD_BUTTON.addActionListener( this );

        int width = getContentPane().getWidth();
        int height = getContentPane().getHeight();

        ADD_BUTTON.setBounds( (int) (width * .94), (int) (height * .01), (int) (width * .04), (int) (height * .07) );
        ADD_BUTTON.setFont( FONT );
        ADD_BUTTON.setFocusable( false );

        add( ADD_BUTTON );
    }*/
    /*
     * Adds the header panel.
     */
    private void addHeaderPanel()
    {
        HEADER.setBounds( 0, 0, getContentPane().getWidth(), (int) (getContentPane().getHeight() * .09) );
        HEADER.setBackground( Window.WHITE );
        add( HEADER );
    }
    /*
     * Adds the object label and sets its position.
     */
    private void addObjectLabel()
    {
        OBJECT_LABEL.setFont( FONT );
        OBJECT_LABEL.setBounds( (int) (getContentPane().getWidth() * .24), (int) (getContentPane().getHeight() * .01), (int) (getContentPane().getWidth() * .2), (int) (getContentPane().getHeight() * .1) );
        add( OBJECT_LABEL );
    }
    /*
     * Adds the amount label and sets its position.
     */
    private void addAmountLabel()
    {
        AMOUNT_LABEL.setFont( FONT );
        AMOUNT_LABEL.setBounds( (int) (getContentPane().getWidth() * .555), (int) (getContentPane().getHeight() * .01), (int) (getContentPane().getWidth() * .2), (int) (getContentPane().getHeight() * .1) );
        add( AMOUNT_LABEL );
    }
    /*
     * Adds the price label and sets its position.
     */
    private void addPriceLabel()
    {
        PRICE_LABEL.setFont( FONT );
        PRICE_LABEL.setBounds( (int) (getContentPane().getWidth() * .70), (int) (getContentPane().getHeight() * .01), (int) (getContentPane().getWidth() * .2), (int) (getContentPane().getHeight() * .1) );
        add( PRICE_LABEL );
    }
    /*
     * Adds the total label and sets its position.
     */
    private void addTotalLabel()
    {
        TOTAL_LABEL.setFont( FONT );
        TOTAL_LABEL.setBounds( (int) (getContentPane().getWidth() * .82), (int) (getContentPane().getHeight() * .01), (int) (getContentPane().getWidth() * .2), (int) (getContentPane().getHeight() * .1) );
        add( TOTAL_LABEL );
    }
    /*
     * If the object has not amount, price and total values, returns true.
     */
    private boolean isEmpty( String amount, String price, String total )
    {
        return amount.equals( "0" ) && price.equals( "0,0" ) && total.equals( "0,0" );
    }
    /*
     * Loads data into the object panels.
     */
    private void loadData()
    {
        for ( int i = 0 ; i < Window.DATA.size() ; i ++ )
        {
            AbstractPDFComponent abstractPDFComponent = Window.DATA.get( i );

            String objectDescription = abstractPDFComponent.getObject();
            String amount = String.valueOf( abstractPDFComponent.getAmount() );
            String price = String.valueOf( abstractPDFComponent.getPrice() ).replace( ".", "," );
            String total = String.valueOf( abstractPDFComponent.getTotal() ).replace( ".", "," );

            Window.OBJECTS.get( i ).getObjectTextField().setText( objectDescription );
            if ( !isEmpty( amount, price, total ) )
            {
                Window.OBJECTS.get( i ).getAmountTextField().setText( amount );
                Window.OBJECTS.get( i ).getPriceTextField().setText( price );
                Window.OBJECTS.get( i ).getTotalTextField().setText( total );
            }
        }
    }
    /*
     * Adds the object panels and loads data.
     */
    private void addObjectPanels()
    {
        Window.OBJECTS.clear();

        int heightOffset = (int) (getContentPane().getHeight() * .01);
        addObjectPanel( heightOffset );
 
        for ( int i = 1 ; i <= 7 ; i ++ )
        {
            heightOffset = (int) (getContentPane().getHeight() * (.01 * (i + 1))) + (i * HEADER.getHeight());
            addObjectPanel( heightOffset );
        }

        if ( Window.DATA.size() > 0 ) loadData();
    }
    /*
     * Adds all the components which compose the header.
     */
    private void addHeader()
    {
        // ADDS A TOTAL LABEL
        addTotalLabel();
        // ADDS A PRICE LABEL
        addPriceLabel();
        // ADDS AN AMOUNT LABEL
        addAmountLabel();
        // ADDS AN OBJECT LABEL
        addObjectLabel();
        // ADDS THE ACCEPT BUTTON
        addAcceptButton();
        // ADDS THE HEADER PANEL
        addHeaderPanel();
    }
    /*
     * Constructor of the object "ObjectsWindow", initializes 
     * a pop up window to add a certain amount of objects.
     */
    public ObjectsWindow( Window parentWindow, boolean modal )
    {// CREATES THE SECONDARY WINDOW
        super( parentWindow, modal );
        PARENT_WINDOW = parentWindow;
        initializeWindowProperties();
        // ADDS THE HEADER
        addHeader();
        // ADDS THE OBJECT PANELS
        addObjectPanels();
    }
    /*
     * Returns true if the PDF component contains description but nothing else.
     */
    private boolean containsDescriptionOnly( PDFComponent pdfComponent )
    {
        int object = pdfComponent.getObjectTextField().getText().length();
        int amount = pdfComponent.getAmountTextField().getText().length();
        int price = pdfComponent.getPriceTextField().getText().length();
        int total = pdfComponent.getTotalTextField().getText().length();
        return object > 0 && amount == 0 && price == 0 && total == 0;
    }
    /*
     * Returns true if the PDF component is full of data.
     */
    private boolean containsData( PDFComponent pdfComponent )
    {
        int object = pdfComponent.getObjectTextField().getText().length();
        int amount = pdfComponent.getAmountTextField().getText().length();
        int price = pdfComponent.getPriceTextField().getText().length();
        int total = pdfComponent.getTotalTextField().getText().length();
        return object > 0 && amount > 0 && price > 0 && total > 0;
    }
    /*
     * Adds the object description of an AbstractPDFComponent to the DATA ArrayList.
     */
    private void addDescriptionOnly( StringBuilder displayText, String objectDescription )
    {
        AbstractPDFComponent abstractComponent = new AbstractPDFComponent();

        abstractComponent.setObject( objectDescription );

        displayText.append( objectDescription + "\n\n" );

        Window.DATA.add( abstractComponent );
    }
    /*
     * Adds an AbstractPDFComponent to the DATA ArrayList.
     */
    private void addAbstractPDFComponent( StringBuilder displayText, String objectDescription, String amount, String price, String total )
    {
        AbstractPDFComponent abstractComponent = new AbstractPDFComponent();

        abstractComponent.setObject( objectDescription );
        abstractComponent.setAmount( Integer.valueOf( amount ) );
        abstractComponent.setPrice( Double.valueOf( price.replace( ",", "." ) ) );
        abstractComponent.setTotal( Double.valueOf( total.replace( ",", "." ) ) );

        displayText.append( objectDescription + ",\namount: " + amount + ", price: " + price + ", total: " + total + "\n" );

        Window.DATA.add( abstractComponent );
    }
    /*
     * Saves all the components in the DATA ArrayList.
     */
    private void saveData()
    {
        Window.DATA.clear();
        StringBuilder displayText = new StringBuilder();
        for ( PDFComponent pdfComponent : Window.OBJECTS )
        {
            String objectDescription = pdfComponent.getObjectTextField().getText();
            String amount = pdfComponent.getAmountTextField().getText();
            String price = pdfComponent.getPriceTextField().getText();
            String total = pdfComponent.getTotalTextField().getText();

            if ( containsDescriptionOnly( pdfComponent ) ) addDescriptionOnly( displayText, objectDescription );
            else if ( containsData( pdfComponent ) && price.charAt( 0 ) != ',' ) addAbstractPDFComponent( displayText, objectDescription, amount, price, total );
            else
            {
                Window.DATA.add( new AbstractPDFComponent() );
                displayText.append( "[WHITESPACE]\n\n" );
            }
        }
        PARENT_WINDOW.JTEXTAREA.setText( displayText.toString() );
    }
    /*
     * Checks if an AbstractPDFComponent contains description and values.
     */
    private boolean containsData( String objectDescription, int amount, double price, double total )
    {
        return objectDescription.length() > 0 && amount > 0 && price > 0 && total > 0;
    }
    /*
     * Counts the real amount of added objects and returns an integer value.
     */
    private int getAddedObjects()
    {
        int addedObjects = 0;
        for ( AbstractPDFComponent abstractComponent : Window.DATA )
        {
            String objectDescription = abstractComponent.getObject();
            int amount = abstractComponent.getAmount();
            double price = abstractComponent.getPrice();
            double total = abstractComponent.getTotal();

            if ( containsData( objectDescription, amount, price, total ) ) addedObjects ++;
        }
        return addedObjects;
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource() == ACCEPT_BUTTON )
        {   // ITERATE OVER COMPONENTS TO SAVE THEM
            saveData();
            PARENT_WINDOW.OBJECTS_BUTTON.setText( "ITEMS ADDED (" + getAddedObjects() + ")" );
            PARENT_WINDOW.updateCoordinates();
            // DELETE THE SECONDARY WINDOW
            dispose();
        }
    }
}