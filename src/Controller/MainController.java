package Controller;

import View.*;
import Utility.*;
import Function.*;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.*;


/**
 *
 * @author leo
 */
public class MainController{

    /**
     * Constant for the last technique performed.
     * This is used as the list view in UI funcions depending on the technique performed.
     */
    private final int MODE_NONE = 100;
    private final int MODE_BLOBBING = 101;
    private final int MODE_REGION_SPLIT = 102;
    private final int MODE_OBJECT_CLASSIFICATION = 103;

    int[][][] imagePixels;
    int selectedOutputMode;
    MainWindowGUI UI;

    int[][] blobbingResultDuplicate;
    ArrayList<int[][][]> firstResult = new ArrayList<>();
    ArrayList<int[][][]> secondResult = new ArrayList<>();
    ArrayList<int[][][]> thirdResult = new ArrayList<>();
    
    String plateNumberOutput = "";
    BufferedImage resultImage = null;
    
    public static void main(String[] args)
    {
        new MainController();   
    }
    
    public MainController()
    {
        UI = new MainWindowGUI(this);
        selectedOutputMode = MODE_NONE;
    }
        
    public void resetVariableValue()
    {
        selectedOutputMode = MODE_NONE;
        blobbingResultDuplicate = null;
        firstResult = new ArrayList<>();
        secondResult = new ArrayList<>();
        thirdResult = new ArrayList<>();
        plateNumberOutput = "";
        resultImage = null;
    }
    
    public void loadDICOMImage(String path)
    {
        BufferedImage img = ReadWritePNG.readDicomImageFile(path);
        DicomImageViewer viewer = new DicomImageViewer();
        viewer.populateImageInput(img);
        viewer.setVisible(true);
    }
    
    /**
     * Function to read image file into pixels.
     * Fired when user selected the image location.
     * @param image 
     */
    public void imageLoaded(File image)
    {
        imagePixels = ReadWritePNG.ReadPNG(image.toPath().toString());     
        UI.populateInputImage(imagePixels);
    }    

    /**
     * Function to output the detected plate number.
     * Can be called only if the core operations have been performed.
     */
    public void showRecognisedPlateNumber()
    {
        imagePixels = new int[resultImage.getWidth()][resultImage.getHeight()][3];
        
        for (int i = 0 ; i < resultImage.getHeight() ; i++)
        {
           for (int j = 0 ; j < resultImage.getWidth() ; j++)
           {
              Color pixel = new Color(resultImage.getRGB(j,i));
              imagePixels[j][i][0] = pixel.getRed();
              imagePixels[j][i][1] = pixel.getGreen();
              imagePixels[j][i][2] = pixel.getBlue();
           }
        }
        
        ArrayList<String> newOutput = new ArrayList();
        newOutput.add(plateNumberOutput);
        
        UI.populateOutputImage(imagePixels, true);
        UI.showList(newOutput);
    }
    
    public void listItemOnClick(String selectedItem, int selectedIndex)
    {
        /**
         * Depending on which item:
         * - Blobbing
         * - Region Splitting
         * - Feature Extraction
         */
        int width = 0;
        int height = 0;
        int[][][] outputImage = new int[width][height][3];
        int cmbImageSelectedIndex = 0;

        switch (selectedOutputMode)
        {
            case MODE_BLOBBING:
                int[] sliderValue = UI.getSliderValue();
                width = blobbingResultDuplicate.length;
                height = blobbingResultDuplicate[0].length;
                outputImage = new int[width][height][3];

                /**
                 * Iterating each pixel of the blobbed object of the given label
                 * and changing the colour of it to be populated as an output image.
                 */
                for (int i = 0 ; i < width ; i++)
                    for (int j = 0 ; j < height ; j++)
                        if (blobbingResultDuplicate[i][j] == Integer.parseInt(selectedItem))
                        {
                            outputImage[i][j][0] = sliderValue[0];
                            outputImage[i][j][1] = sliderValue[1];
                            outputImage[i][j][2] = sliderValue[2];
                        }

                UI.populateOutputImage(outputImage, true);
                break;
            case MODE_REGION_SPLIT:
                cmbImageSelectedIndex = UI.getComboBoxSelectedItemPosition();
                outputImage = new int[0][0][0];

                /**
                 * 0 -> Blobbed Image
                 * 1 -> Greyscaled Image
                 * 2 -> Sobel Edge Detection Image
                 */
                switch (cmbImageSelectedIndex)
                {
                    case 0:
                        outputImage = firstResult.get(Integer.parseInt(selectedItem) - 1);
                        break;
                    case 1:
                        outputImage = thirdResult.get(Integer.parseInt(selectedItem) - 1);
                        break;
                    case 2:
                        outputImage = secondResult.get(Integer.parseInt(selectedItem) - 1);
                        break;
                }

                UI.populateOutputImage(outputImage, false);
                break;
            case MODE_OBJECT_CLASSIFICATION:
                cmbImageSelectedIndex = UI.getComboBoxSelectedItemPosition();
                outputImage = new int[0][0][0];

                /**
                 * 0 -> Blobbed Image
                 * 1 -> Greyscaled Image
                 * 2 -> Sobel Edge Detection Image
                 */
                switch (cmbImageSelectedIndex)
                {
                    case 0:
                        outputImage = firstResult.get(selectedIndex);
                        break;
                    case 1:
                        outputImage = thirdResult.get(selectedIndex);
                        break;
                    case 2:
                        outputImage = secondResult.get(selectedIndex);
                        break;
                }

                UI.populateOutputImage(outputImage, false);
                break;

        }
    }

    /**
     * Performing the image processing technique depending on the selected process by users.
     * Some technique has pre-requisite.
     * @param isGrayScale
     * @param isMeanFilter
     * @param isMedianFilter
     * @param isSobelEdgeDetection
     * @param isBlobbing
     * @param isRegionSplitting
     * @param isFeatureExtraction 
     */
    public void performImageProcessing(boolean isGrayScale, 
                                        boolean isMeanFilter,
                                        boolean isMedianFilter,
                                        boolean isSobelEdgeDetection,
                                        boolean isBlobbing,
                                        boolean isRegionSplitting,
                                        boolean isFeatureExtraction)
    {
        resetVariableValue();
        
        if (isGrayScale)
        {
            int[][][] resultPixel = ImageProcessing.greyScale(imagePixels);
            
            if (isMeanFilter)
                resultPixel = ImageProcessing.meanFilter(resultPixel);
            
            if (isMedianFilter)
                resultPixel = ImageProcessing.medianFilter(resultPixel);
            
            if (isSobelEdgeDetection)
            {
                resultPixel = ImageProcessing.sobelEdgeDetection(resultPixel);
                
                if (isBlobbing)
                {
                    ArrayList<Integer> registeredObjects = new ArrayList<>();
                    int[][] blobbingResult = blobbing(resultPixel, registeredObjects);

                    if (isRegionSplitting)
                    {
                        regionSplitting(resultPixel, blobbingResult, registeredObjects, firstResult, secondResult, thirdResult);
                        
                        if (isFeatureExtraction)
                        {
                            objectClassification(firstResult, secondResult, thirdResult);
                        }
                    }
                }
            }   
            
            UI.populateOutputImage(resultPixel, true);
        }
    }
    
    int[][] blobbing(int[][][] sobelPixels, ArrayList<Integer> registeredObjects)
    {
        ArrayList<String> registeredObjectsString = new ArrayList<>();
        int[][] blobbingResult = FeatureExtraction.Blobbing(sobelPixels);
        
        ObjectClassification.LabelCounting(blobbingResult, registeredObjects);
        ObjectClassification.LabelElimination(blobbingResult, registeredObjects);
            
        if (registeredObjects.size() > 13)
            ObjectClassification.LabelElimination(blobbingResult, registeredObjects);

        blobbingResultDuplicate = new int[blobbingResult.length][blobbingResult[0].length];

        for (int i = 0 ; i < blobbingResult.length ; i++)
           for (int j = 0 ; j < blobbingResult[0].length ; j++)
               blobbingResultDuplicate[i][j] = blobbingResult[i][j];

        for (int i = 0 ; i < registeredObjects.size() ; i++)
            registeredObjectsString.add(Integer.toString(registeredObjects.get(i)));

        UI.showList(registeredObjectsString);
        UI.showSlider(true);
        
        selectedOutputMode = MODE_BLOBBING;

        return blobbingResult;
    }
    
    void regionSplitting(int[][][] sobelPixels, int[][] blobbingResult, ArrayList<Integer> registeredObjects,
                         ArrayList<int[][][]> firstResult, ArrayList<int[][][]> secondResult, ArrayList<int[][][]> thirdResult)
    {
        /**
         * Temp Pixel: imagePixel
         * imagePixelCopy: sobelImagePixel
         * label: get from blobbing() -> blobbingResult
         * exist: get from blobbing() -> registeredObject
         * imageblob, soble, greyscale: just new arrayList
         * */
        ObjectClassification.ImageSplit(imagePixels, sobelPixels, blobbingResult, registeredObjects, firstResult, secondResult, thirdResult);

        ArrayList<String> listItem = new ArrayList<>();
        for (int i = 0 ; i < firstResult.size() ; i++)
            listItem.add(Integer.toString(i+1));

        ArrayList<String> cmbOptions = new ArrayList<>();
        cmbOptions.add("Blob Image");
        cmbOptions.add("Grayscale Image");
        cmbOptions.add("Sobel Image");

        UI.showList(listItem);
        UI.showComboBox(cmbOptions);
        selectedOutputMode = MODE_REGION_SPLIT;
    }
    
    void objectClassification(ArrayList<int[][][]> firstResult, ArrayList<int[][][]> secondResult, ArrayList<int[][][]> thirdResult)
    {
        ArrayList<String> listItem = new ArrayList<>();
        ArrayList<String> name = new ArrayList<String>();
        final ArrayList<String> identifiedName = new ArrayList<String>();
        int width = imagePixels.length;
        int height = imagePixels[0].length;

        for (int i = 0 ; i < secondResult.size() ; i++)
        {
            String tempName;
            tempName = ObjectClassification.ImageClassification(firstResult, secondResult, thirdResult, width, height, i);
            name.add(tempName);

            if (!tempName.equals("Unidentified") && (!tempName.equals("")))
                identifiedName.add(tempName);
        }

        for (int i = 0 ; i < identifiedName.size() ; i++)
            plateNumberOutput += identifiedName.get(i);

        for (int i = 0 ; i < name.size() ; i++)
            listItem.add(name.get(i));


        
        for (int i = 0 ; i < name.size() ; i++)
        {
            if ((!name.get(i).equals("Unidentified")) && (!name.get(i).equals("")))
            {
                int[][][] pixelsToBeWritten = thirdResult.get(i);
                if (resultImage == null)
                {
                    try{
                        String s = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev.png";
                        ReadWritePNG.WritePNG(s, pixelsToBeWritten);
                        resultImage = ImageIO.read(new File(s));}
                    catch(Exception e) {System.out.println("Error");}
                }
                else
                    resultImage = combineImage(resultImage, pixelsToBeWritten);
            }
        }
        ArrayList<String> cmbOptions = new ArrayList<>();
        cmbOptions.add("Blob Image");
        cmbOptions.add("Grayscale Image");
        cmbOptions.add("Sobel Image");
        cmbOptions.add("Result");

        UI.showList(listItem);
        UI.showComboBox(cmbOptions);

        selectedOutputMode = MODE_OBJECT_CLASSIFICATION;
        
    }

    BufferedImage combineImage(BufferedImage temp, int[][][] pixelsToBeWritten)
    {
        BufferedImage image = null;

        try{
            String s2 = Paths.get(".").toAbsolutePath().normalize().toString() + "\\prev2.png";
            ReadWritePNG.WritePNG(s2, pixelsToBeWritten);
            Image rawOutputImage = (Image) temp;
            Image rawOutputImage2 = ImageIO.read(new File(s2));

            Image outputImage = rawOutputImage.getScaledInstance(rawOutputImage.getWidth(UI)*1, rawOutputImage.getHeight(UI)*1,Image.SCALE_SMOOTH);
            Image outputImage2 = rawOutputImage2.getScaledInstance(rawOutputImage2.getWidth(UI)*1, rawOutputImage2.getHeight(UI)*1,Image.SCALE_SMOOTH);

            int w = outputImage.getWidth(UI) + outputImage2.getWidth(UI);
            int h = Math.max(outputImage.getHeight(UI), outputImage2.getHeight(UI));
            image = new BufferedImage(w, h,  BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.drawImage(outputImage, 0, 0, null);
            g2.drawImage(outputImage2, outputImage.getWidth(UI), 0, null);
            g2.dispose();
        }

        catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Terjadi kesalahan di dalam sistem, silahkan mencoba me-reload kembali gambar dan ulangi memproses !", "Error", JOptionPane.ERROR_MESSAGE );
        }

        return image;
    }
    
    public void WriteImage(File Location, Icon icon)
    {
        try
        {
            /**
             * Icon to buffered image copied from: https://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
             * Credit to: https://stackoverflow.com/users/462963/werner-kvalem-vester%c3%a5s
             */
            BufferedImage bi = new BufferedImage(
                                    icon.getIconWidth(),
                                    icon.getIconHeight(),
                                    BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
                                // paint the Icon to the BufferedImage.
                                icon.paintIcon(null, g, 0,0);
                                g.dispose();
         
            int[][][] imagePixels = new int[bi.getWidth()][bi.getHeight()][3];

            /*Going through the image stream to extract the Pixel and split it to RGB*/
            for (int i = 0 ; i < bi.getHeight() ; i++)
            {
               for (int j = 0 ; j < bi.getWidth() ; j++)
               {
                  Color PixelColor = new Color(bi.getRGB(j,i));
                  imagePixels[j][i][0] = PixelColor.getRed();
                  imagePixels[j][i][1] = PixelColor.getGreen();
                  imagePixels[j][i][2] = PixelColor.getBlue();
               }
            }
            
            
           ReadWritePNG.WritePNG(Location.toPath().toString()+".png", imagePixels);
        }
        catch (Exception e)
        {
        }
    }
    
    public boolean isImageLoaded()
    {
        if (imagePixels == null)
            return false;
        else
            return true;
    }
}
