package Controller;

import View.*;
import Utility.*;
import Function.*;
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
public class MainController{
    
    int[][][] imagePixels;
    MainWindowGUI UI;
    
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
        UI = new MainWindowGUI(this);
    }
        
    public void imageLoaded(File image)
    {
        imagePixels = ReadWritePNG.ReadPNG(image.toPath().toString());     
    }    
    
    public void imageProcessing()
    {
        //GreyScale
        int[][][] resultPixel = ImageProcessing.greyScale(imagePixels);
        
        //Median Filter
        resultPixel = ImageProcessing.medianFilter(resultPixel);
        
        //Mean Filter
        resultPixel = ImageProcessing.meanFilter(resultPixel);
        
        //Sobel Detection Filter
        resultPixel = ImageProcessing.sobelEdgeDetection(resultPixel);
        
        
        
        //UI.populateOutputImage(resultPixel);
    }
    
    void blobbing(int[][][] sobelPixels)
    {
        ArrayList<Integer> registeredObjects = new ArrayList<Integer>();
        int[][] blobbingResult = FeatureExtraction.Blobbing(sobelPixels);
        
        ObjectClassification.LabelCounting(blobbingResult, registeredObjects);
        ObjectClassification.LabelElimination(blobbingResult, registeredObjects);
            
        if (registeredObjects.size() > 13)
            ObjectClassification.LabelElimination(blobbingResult, registeredObjects);

        final int[][] blobbingResultDuplicate = new int[blobbingResult.length][blobbingResult[0].length];

        for (int i = 0 ; i < blobbingResult.length ; i++)
           for (int j = 0 ; j < blobbingResult[0].length ; j++)
               blobbingResultDuplicate[i][j] = blobbingResult[i][j];

        /*Below is UI operation*/
        lbl_list.setVisible(true);
        lbl_list.setText("List of Region");
        list_label.setVisible(true);
        list_label_scroll.setVisible(true);
        
        for (int i = 0 ; i < registeredObjects.size() ; i++)
           model.addElement(Integer.toString(registeredObjects.get(i)));

        if (!chkRegionSplitting.isSelected())
        {
               slider_red.setVisible(true);
               slider_green.setVisible(true);
               slider_blue.setVisible(true);
               lbl_red_color.setVisible(true);
               lbl_green_color.setVisible(true);
               lbl_blue_color.setVisible(true);
                
               listener = new ListSelectionListener()
               {
                  public void valueChanged(ListSelectionEvent ev)
                  {
                     int option;
                     Object[] list = ((JList)ev.getSource()).getSelectedValues();
                     option = Integer.parseInt((String)list[0]);
                  
                     for (int i = 0 ; i < blobbingResultDuplicate.length ; i++)
                        for (int j = 0 ; j < blobbingResultDuplicate[0].length ; j++)
                           if (blobbingResultDuplicate[i][j] == option)
                           {
                              split[i][j][0] = slider_red.getValue();
                              split[i][j][1] = slider_green.getValue();
                              split[i][j][2] = slider_blue.getValue();
                           }
                  
                     imagePixelCopy = split;
                     OutputImage(1);
                  
                   }
               }; 
                
               list_label.addListSelectionListener(listener);              
            }
    }
    
    void regionSplitting()
    {
        ObjectClassification.ImageSplit(tempPixel, imagePixelCopy, label, exist, imageBlob, imageSobel, imageGreyscale);
        model.removeAllElements();
            
            for (int i = 0 ; i < imageBlob.size() ; i++)
                model.addElement(Integer.toString(i+1));
            
            cmb_image.setVisible(true);
            
            final ArrayList<int[][][]> image2 = new ArrayList<int[][][]>(imageSobel);
            final ArrayList<int[][][]> image3 = new ArrayList<int[][][]>(imageBlob);
            final ArrayList<int[][][]> image4 = new ArrayList<int[][][]>(imageGreyscale);
            
            if (!chkFeatureExtraction.isSelected())
            {                
               listener = new ListSelectionListener()
               {
               public void valueChanged(ListSelectionEvent ev)
               {
                     int option;
                     Object[] list = ((JList)ev.getSource()).getSelectedValues();
                     option = Integer.parseInt((String)list[0]);
                  
                     if (cmb_image.getSelectedIndex() == 0)
                        imagePixelCopy = image3.get(option-1);
                     else if (cmb_image.getSelectedIndex() == 1)
                        imagePixelCopy = image4.get(option-1);
                     else if (cmb_image.getSelectedIndex() == 2)
                         imagePixelCopy = image2.get(option-1);
                     
                     OutputImage(2);
                  
               }
               };
                
               list_label.addListSelectionListener(listener);
            }
    }
    
    void featureExtraction()
    {
        if (chkFeatureExtraction.isSelected())
        {
            ArrayList<String> name = new ArrayList<String>(); 
            final ArrayList<String> identifiedName = new ArrayList<String>();
            model.removeAllElements();
            
            /*New section to add image together*/            
            model2.addElement("Result");
            
            for (int i = 0 ; i < imageSobel.size() ; i++)
            {
               String tempName;
               tempName = ObjectClassification.ImageClassification(imageBlob, imageSobel, imageGreyscale, tempPixel.length, tempPixel[0].length, i);
               name.add(tempName);
               
               if (!tempName.equals("Unidentified") && (!tempName.equals("")))
                   identifiedName.add(tempName);
            }
            
            for (int i = 0 ; i < identifiedName.size() ; i++)
                output += identifiedName.get(i);
            
            for (int i = 0 ; i < name.size() ; i++)
                model.addElement(name.get(i));
            
            for (int i = 0 ; i < name.size() ; i++)
            {
                if ((!name.get(i).equals("Unidentified")) && (!name.get(i).equals("")))
                {
                    imagePixelResult = imageGreyscale.get(i);
                    if (resultImage == null)
                    {
                        try{
                        String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
                        ReadWritePNG.WritePNG(s, imagePixelResult);
                        resultImage = ImageIO.read(new File(s));}
                        catch(Exception e) {System.out.println("Error");}
                    }
                    else
                       resultImage = test(resultImage);
                }
            }
            
            /*end of new section*/
            
            cmb_image.setVisible(true);
            
            final ArrayList<int[][][]> image2 = new ArrayList<int[][][]>(imageSobel);
            final ArrayList<int[][][]> image3 = new ArrayList<int[][][]>(imageBlob);
            final ArrayList<int[][][]> image4 = new ArrayList<int[][][]>(imageGreyscale);
             
            listener = new ListSelectionListener()
            {
               public void valueChanged(ListSelectionEvent ev)
               {
                  int option;
                  option = ((JList)ev.getSource()).getSelectedIndex();
                  
                  if (cmb_image.getSelectedIndex() == 0)  
                     imagePixelCopy = image3.get(option);
                  else if (cmb_image.getSelectedIndex() == 1)
                     imagePixelCopy = image4.get(option);
                  else if (cmb_image.getSelectedIndex() == 2)
                     imagePixelCopy = image2.get(option);
                  
                  
                  OutputImage(2);
                  
                  //test();
                  
               }
            };
            
            list_label.addListSelectionListener(listener);
        }
    }
}
