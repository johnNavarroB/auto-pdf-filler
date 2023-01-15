package com.ui.data;

import javax.swing.*;
/*
 * This class represents the visual representation of a PDF object.
 */
public class PDFComponent
{   // PDF object panel
    private JPanel objectsPanel = new JPanel();
    // PDF object description
    private JTextField objectTextField = new JTextField();
    // PDF object amount
    private JTextField amountTextField = new JTextField();
    // PDF object price
    private JTextField priceTextField = new JTextField();
    // PDF object total
    private JTextField totalTextField = new JTextField();
    /*
     * Gets the object panel.
     */
    public JPanel getObjectsPanel()
    {
        return objectsPanel;
    }
    /*
     * Gets the object textfield.
     */
    public JTextField getObjectTextField()
    {
        return objectTextField;
    }
    /*
     * Gets the amount textfield.
     */
    public JTextField getAmountTextField()
    {
        return amountTextField;
    }
    /*
     * Gets the price textfield.
     */
    public JTextField getPriceTextField()
    {
        return priceTextField;
    }
    /*
     * Gets the total textfield.
     */
    public JTextField getTotalTextField()
    {
        return totalTextField;
    }
    /*
     * Unique PDFComponent constructor.
     * Every Swing component is instantiated as this object attributes.
     */
    public PDFComponent()
    {}
}