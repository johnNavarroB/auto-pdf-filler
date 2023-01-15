package com.ui.fieldlimit;

import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
/*
 * This object is intended to limit the maximum characters input 
 * of a textfield through the instantiation of a PlainDocument.
 */
public class TextFieldLimit extends PlainDocument
{// LIMIT OF CHARACTERS ALLOWED
    private int limit;
    /*
     * Constructor of the "TextFieldLimit" sets the 
     * maximum amount of characters allowed.
     */
    public TextFieldLimit( int limit )
    {
        super();
        this.limit = limit;
    }
    /*
     * Limits the maximum amount of characters allowed 
     * by the amount specified in the "limit" variable.
     */
    public void insertString( int offset, String string, AttributeSet attributeSet ) throws BadLocationException
    {
        if ( string == null ) return;

        if ( (getLength() + string.length()) <= limit ) super.insertString( offset, string, attributeSet );
    }
}