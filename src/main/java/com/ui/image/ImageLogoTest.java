package com.ui.image;
/*
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
*/

public class ImageLogoTest
{
    // THIS WAS EXPERIMENTAL, THE LOGO SCALING IS VERY INEFFECTIVE
    //JLabel HEADER_LOGO = new JLabel();

    /*
     * DEPRECATED: image is not being used
     *//*
    private BufferedImage resizeImage( BufferedImage bufferedImage, int width, int height ) throws java.io.IOException
    {
        Image image = bufferedImage.getScaledInstance( width, height, Image.SCALE_DEFAULT );
        BufferedImage resizedImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB );
        resizedImage.getGraphics().drawImage( image, 0, 0, null );
        return resizedImage;
    }*/
    /*
     * DEPRECATED: image is not being used
     *//*
    private ImageIcon getResizedImage( int width, int height ) throws java.io.IOException
    {
        File file = new File( "IMAGE_PATH" );
        BufferedImage bufferedImage = ImageIO.read( file );
        
        return new ImageIcon( resizeImage( bufferedImage, width, height ) );
    }*/
    //
    // DEPRECATED: image is not being used
    /* 
    private void setImageBounds()
    {
        try
        {
            int width = getContentPane().getWidth() / 4;
            int height = getHeaderHeight() - 5;

            HEADER_LOGO.setBounds( 0, 0, width, height );

            HEADER_LOGO.setIcon( getResizedImage( width, height ) );

        } catch ( java.io.IOException exception ) {exception.printStackTrace();}
    }*/
}