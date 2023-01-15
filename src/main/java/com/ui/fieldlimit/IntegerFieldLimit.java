package com.ui.fieldlimit;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
/*
 * This object is intended to limit the maximum numbers input 
 * of a textfield through the instantiation of a PlainDocument.
 */
public class IntegerFieldLimit extends PlainDocument
{// LIMIT OF NUMBERS ALLOWED
    private int limit;
    /*
     * Constructor of the "TextFieldLimit" sets the 
     * maximum amount of numbers allowed.
     */
    public IntegerFieldLimit( int limit )
    {
        super();
        this.limit = limit;
    }
    /*
     * Checks if a given string is a number.
     */
    public static boolean isNumber( String string )
    {
        for ( char character : string.toCharArray() ) if ( !Character.isDigit( character ) ) return false;
        return true;
    }
    /*
     * Limits the maximum amount of numbers allowed 
     * by the amount specified in the "limit" variable.
     */
    public void insertString( int offset, String string, AttributeSet attributeSet ) throws BadLocationException
    {
        if ( string == null ) return;

        if ( (getLength() + string.length()) <= limit && isNumber( string ) ) super.insertString( offset, string, attributeSet );
    }
}