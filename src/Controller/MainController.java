package Controller;

import View.*;
import Utility.*;
import Function.*;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.*;


/**
 *
 * @author leo
 */
public class MainController{

    private final int MODE_NONE = 100;
    private final int MODE_BLOBBING = 101;
    private final int MODE_REGION_SPLIT = 102;
    private final int MODE_OBJECT_CLASSIFICATION = 103;

    int[][][] imagePixels;
    int selectedOutputMode;
    MainWindowGUI UI;

    int[][] blobbingResultDuplicate;
    ArrayList<int[][][]> firstResult = new ArrayList<>(); //blob, 3
    ArrayList<int[][][]> secondResult = new ArrayList<>(); //sobel , 2
    ArrayList<int[][][]> thirdResult = new ArrayList<>(); //greyscale, 4
    
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
        selectedOutputMode = MODE_NONE;
    }
        
    public void imageLoaded(File image)
    {
        imagePixels = ReadWritePNG.ReadPNG(image.toPath().toString());     
    }    

    public void listItemOnClick(String selectedItem)
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

                for (int i = 0 ; i < width ; i++)
                    for (int j = 0 ; j < height ; j++)
                        if (blobbingResultDuplicate[i][j] == Integer.parseInt(selectedItem))
                        {
                            outputImage[i][j][0] = sliderValue[0];
                            outputImage[i][j][1] = sliderValue[1];
                            outputImage[i][j][2] = sliderValue[2];
                        }

                UI.populateOutputImage(outputImage);
                break;
            case MODE_REGION_SPLIT:
                cmbImageSelectedIndex = UI.getComboBoxSelectedItemPosition();
                outputImage = new int[0][0][0];

                switch (cmbImageSelectedIndex)
                {
                    case 0:
                        outputImage = firstResult.get(cmbImageSelectedIndex - 1);
                        break;
                    case 1:
                        outputImage = thirdResult.get(cmbImageSelectedIndex - 1);
                        break;
                    case 2:
                        outputImage = secondResult.get(cmbImageSelectedIndex - 1);
                        break;
                }

                //OutputImage(2);
                break;
            case MODE_OBJECT_CLASSIFICATION:
                cmbImageSelectedIndex = UI.getComboBoxSelectedItemPosition();
                outputImage = new int[0][0][0];

                switch (cmbImageSelectedIndex)
                {
                    case 0:
                        outputImage = firstResult.get(cmbImageSelectedIndex);
                        break;
                    case 1:
                        outputImage = thirdResult.get(cmbImageSelectedIndex);
                        break;
                    case 2:
                        outputImage = secondResult.get(cmbImageSelectedIndex);
                        break;
                }

                //OutputImage(2);
                break;

        }
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

    public void featureExtraction(int[][][] sobelPixels)
    {
        ArrayList<Integer> registeredObjects = new ArrayList<>();
        int[][] blobbingResult = blobbing(sobelPixels, registeredObjects);

        regionSplitting(sobelPixels, blobbingResult, registeredObjects, firstResult, secondResult, thirdResult);
        objectClassification(firstResult, secondResult, thirdResult);
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
            registeredObjectsString.add(registeredObjects.get(i).toString());

        selectedOutputMode = MODE_BLOBBING;

        UI.showList(registeredObjectsString);
        UI.showSlider(true);

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
    }
    
    void objectClassification(ArrayList<int[][][]> firstResult, ArrayList<int[][][]> secondResult, ArrayList<int[][][]> thirdResult)
    {
        String outputResult = "";
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
            outputResult += identifiedName.get(i);

        for (int i = 0 ; i < name.size() ; i++)
            listItem.add(name.get(i));


        BufferedImage resultImage = null;
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

            //ImageIcon newImg = new ImageIcon(image);
            //img_src_output.setIcon(newImg);
        }

        catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "Terjadi kesalahan di dalam sistem, silahkan mencoba me-reload kembali gambar dan ulangi memproses !", "Error", JOptionPane.ERROR_MESSAGE );
        }

        return image;
    }
}
