package com.ui.data;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/*
 * This class implements the DocumentListener to 
 * calculate the value of a field when a field changes.
 */
public class PDFComponentListener implements DocumentListener
{
    PDFComponent pdfComponent;

    public PDFComponentListener( PDFComponent pdfComponent ) {this.pdfComponent = pdfComponent;}
    /*
     * Just take away IDE warnings.
     */
    private void doNothing( double d ) {}
    /*
     * Returns true if the string is a double value.
     */
    private boolean isValidDouble( String string )
    {
        try
        {
            double doubleValue = Double.valueOf( string.replace( ',', '.' ) );
            doNothing( doubleValue );
            return true;
        } catch ( NumberFormatException ignore ) { return false; }
    }
    /*
     * Calculates the value of a text field.
     */
    private void changeText( DocumentEvent documentEvent )
    {
        String price = pdfComponent.getPriceTextField().getText();
        String amount = pdfComponent.getAmountTextField().getText();

        if ( amount.length() > 0 && price.length() > 0 && price.charAt( 0 ) != ',' && isValidDouble( amount ) ) 
        {
            double total = Double.valueOf( price.replace( ",", "." ) ) * Integer.valueOf( amount );
            total = Math.round( total * 100.0 ) / 100.0;
            
            pdfComponent.getTotalTextField().setText( String.valueOf( total ).replace( ".", "," ) );
        }
        else pdfComponent.getTotalTextField().setText( "" );
    }
    /*
     * 
     */
    public void changedUpdate( DocumentEvent documentEvent )
    {
        changeText( documentEvent );
    }
    /*
     * 
     */
    public void removeUpdate( DocumentEvent documentEvent )
    {
        changeText( documentEvent );
    }
    /*
     * 
     */
    public void insertUpdate( DocumentEvent documentEvent )
    {
        changeText( documentEvent );
    }
}