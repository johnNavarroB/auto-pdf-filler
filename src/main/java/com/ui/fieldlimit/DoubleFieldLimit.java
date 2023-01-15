package com.ui.fieldlimit;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
/*
 * This object is intended to limit the maximum numbers input 
 * of a textfield through the instantiation of a PlainDocument.
 */
public class DoubleFieldLimit extends PlainDocument
{// LIMIT OF NUMBERS ALLOWED
    private int limit;
    /*
     * Constructor of the "TextFieldLimit" sets the 
     * maximum amount of numbers allowed.
     */
    public DoubleFieldLimit( int limit )
    {
        super();
        this.limit = limit;
    }
    /*
     * Checks if a given string is a number.
     */
    private boolean isDouble( String string )
    {
        for ( char character : string.toCharArray() )
        {
            if ( character != '0' && character != '1' && character != '2' && character != '3' &&
                 character != '4' && character != '5' && character != '6' && character != '7' &&
                 character != '8' && character != '9' && character != ',' ) return false;
        }
        return true;
    }
    /*
     * Limits the maximum amount of numbers allowed 
     * by the amount specified in the "limit" variable.
     */
    public void insertString( int offset, String string, AttributeSet attributeSet ) throws BadLocationException
    {
        if ( string == null ) return;

        if ( (getLength() + string.length()) <= limit && isDouble( string ) ) super.insertString( offset, string, attributeSet );
    }
}