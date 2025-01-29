package Controller;

import View.*;
import Utility.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.*;


/**
 *
 * @author leo
 */
public class MainController implements IController{
    
    int[][][] imagePixels;
    
    public static void main(String[] args)
    {
        new MainController();
        try
        {
            String path = "C:\\users\\Leo\\Desktop\\0020.DCM";
            InputStream stream = new FileInputStream(path);
            DicomInputStream dicomStream = new DicomInputStream(stream);
            Attributes attributes = dicomStream.readDataset();
            
            java.io.File f = new java.io.File(path);
            javax.imageio.stream.ImageInputStream dicomImage = javax.imageio.ImageIO.createImageInputStream(f);
            
            Image img = ImageIO.read(dicomImage);
            
            System.out.println(attributes.getString(Tag.PatientName));
            
        }
        catch(Exception e)
        {
            
        }
        
        
    }
    
    public MainController()
    {
        MainWindowGUI newUI = new MainWindowGUI(this);
        //newUI.setVisible(true);
    }
    
    @Override
    public List<String> getOperationList()
    {
        ArrayList<String> operations = new ArrayList<>();
        
        operations.add("Greyscale");
        operations.add("Mean Filter");
        operations.add("Median Filter");
        operations.add("Edge Detection");
        operations.add("Blobbing");
        
        return operations;
    }
    
    @Override
    public void imageLoaded(File image)
    {
        imagePixels = ReadWritePNG.ReadPNG(image.toPath().toString());     
    }    
    
    
}
