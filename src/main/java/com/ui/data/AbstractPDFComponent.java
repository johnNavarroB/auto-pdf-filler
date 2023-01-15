package com.ui.data;
/*
 * This class is the data layer of every PDF object.
 * Its meaning is to save the user input of each row in the UI.
 */
public class AbstractPDFComponent
{   // PDF object description
    private String object = "";
    // PDF object amount
    private int amount;
    // PDF object price
    private double price;
    // PDF object total
    private double total;
    /*
     * Set methods.
     */
    public void setObject( String object ) {this.object = object;}
    public void setAmount( int amount ) {this.amount = amount;}
    public void setPrice( double price ) {this.price = price;}
    public void setTotal( double total ) {this.total = total;}
    /*
     * Get methods.
     */
    public String getObject() {return object;}
    public int getAmount() {return amount;}
    public double getPrice() {return price;}
    public double getTotal() {return total;}
    /*
     * Unique constructor of a PDF object abstraction.
     */
    public AbstractPDFComponent()
    {}
}