package com.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MessageWindow extends JDialog implements ActionListener
{
    Window PARENT_WINDOW;
    // TITLE BAR PROPERTIES
    private final String IMAGE_PATH = ".\\pictures\\sample_logo.png";
    private final Image IMAGE = Toolkit.getDefaultToolkit().getImage( IMAGE_PATH );
    private final String TITLE = "Message";
    // LAYOUT COMPONENTS: LABELS
    private final Font FONT_SMALL = new Font( "Serif", Font.BOLD, 12 );
    private final Font FONT = new Font( "Serif", Font.BOLD, 18 );
    private JLabel MESSAGE = new JLabel( "PDF generated at:" );
    private JLabel PATH_MESSAGE = new JLabel( "" );
    // LAYOUT COMPONENTS: BUTTONS
    private JButton BUTTON = new JButton( "CLOSE" );


    private void initializeWindowProperties()
    {// "null" PARAMETER = ABSOLUTE WINDOW POSITION DIMENSIONS
        setLayout( null );
        // SCREEN SPAWNS ON THE CENTER
        setBounds( (int) (Window.X_DIMENSION * .3), (int) (Window.Y_DIMENSION * .4), (int) (Window.WIDTH * .55), (int) (Window.HEIGHT * .23) );
        // SETS THE IMAGE OF THE TITLE BAR
        setIconImage( IMAGE );
        // SETS THE TITLE OF THE MAIN WINDOW
        setTitle( TITLE );
        // ITS SIZE CAN NOT BE CHANGED
        setResizable( false );
        // SETS BACKGROUND COLOR
        getContentPane().setBackground( Window.WHITE );
        // ENABLES THE MAIN WINDOW TO BE DISPLAYED
        // WARNING!! MAIN WINDOW WIDTH AND HEIGHT ARE ZERO UNTIL THE WINDOW IS VISIBLE
        setVisible( true );
    }
    /*
     * Adds the button.
     */
    private void addButton()
    {
        BUTTON.addActionListener( this );

        int windowWidth = getContentPane().getWidth();
        int windowHeight = getContentPane().getHeight();

        double buttonWidth = windowWidth * .2;

        BUTTON.setBounds( (int) (windowWidth * .5) - (int) (buttonWidth / 2), (int) (windowHeight * .65), (int) buttonWidth, (int) (windowHeight * .25) );
        BUTTON.setFont( FONT_SMALL );
        BUTTON.setFocusable( false );

        add( BUTTON );
    }
    /*
     * Adds the path to the message.
     */
    private void addPathToMessage()
    {
        PATH_MESSAGE.setFont( FONT_SMALL );
        PATH_MESSAGE.setBounds( (int) (getContentPane().getWidth() * .05), (int) (getContentPane().getHeight() * .2), (int) (getContentPane().getWidth() * .9), (int) (getContentPane().getHeight() * .3) );
        add( PATH_MESSAGE );
    }
    /*
     * Adds the message and sets its position.
     */
    private void addMessage()
    {
        MESSAGE.setFont( FONT );
        MESSAGE.setBounds( (int) (getContentPane().getWidth() * .05), (int) (getContentPane().getHeight() * .05), (int) (getContentPane().getWidth() * .3), (int) (getContentPane().getHeight() * .3) );
        add( MESSAGE );
    }
    /*
     * Constructor of the object "MessageWindow", initializes 
     * a pop up window to show a message.
     */
    public MessageWindow( Window parentWindow, boolean modal, String absolutePath )
    {// CREATES THE MESSAGE WINDOW
        super( parentWindow, modal );
        initializeWindowProperties();
        // ADDS THE MESSAGE
        addMessage();
        addPathToMessage();
        PATH_MESSAGE.setText( absolutePath );
        // ADDS THE BUTTON
        addButton();
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        if ( actionEvent.getSource() == BUTTON )
        {
            // DELETE THE MESSAGE WINDOW
            dispose();
        }
    }
}