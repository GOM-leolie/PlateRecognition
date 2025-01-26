import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
/**
 *
 * @author leo
 */
public class HistogramUI {
    
    static int[] initHistogramData(int[] histogramData)
    {
        histogramData = new int[200];
        
        for (int i = 0 ; i < 200 ; i++)
            histogramData[i] = (int) Math.floor(Math.random() * 100);
        
        return histogramData;
    }
    
    public static HistogramBin[] initialiseHistogram(int[] histogramData, int totalBin)
    {
        histogramData = initHistogramData(histogramData);
        totalBin = 9;        
                
        int binSize = (int) Math.ceil((double)histogramData.length/(double)totalBin);
        HistogramBin[] bins = new HistogramBin[totalBin];
                
        int indexCounter = 0;
        for (int i = 0 ; i < histogramData.length ; i += binSize)
        {
            String binCaption = Integer.toString(i) + " to " + Integer.toString(i + binSize - 1);
            int binValue = 0;
            
            for (int j = i ; j < i + binSize ; j++)
                if (j < histogramData.length)
                    binValue += histogramData[j];
            
            HistogramBin newBin = new HistogramBin(binValue, binCaption);
            bins[indexCounter] = newBin;
            
            indexCounter++;
        }
        
        return bins;
    }
    
    static JPanel drawHistogram(int containerWidth, int containerHeight)
    {
        int[] histogramData = new int[0];
        int totalBin = 0;
        
        HistogramBin[] bins = initialiseHistogram(histogramData, totalBin);
        
        /*Initialise Frame*/
        int frameHeight = (int)(containerHeight * 0.98);
        int frameWidth = (int) (containerWidth * 0.98);
        int frameXCoordinate = (int) (containerWidth * 0.01);
        int frameYCoordinate = (int) (containerHeight * 0.01);
        
        /*Initialise Axis*/
        int axisWidth = (int) (frameWidth * 0.8);
        int axisHeight = (int) (frameHeight * 0.8);
        int xCoordinate = (frameWidth - axisWidth) / 2;
        int yCoordinate = ((frameHeight - axisHeight) / 2) + axisHeight;
        
        /*Draw Frame*/
        JPanel mainFrame = createHistogramFrame(frameWidth, frameHeight, frameXCoordinate, frameYCoordinate);
        
        /*Draw Axises*/
        JPanel xAxis = createAxis(axisWidth, 5, xCoordinate, yCoordinate);
        JPanel yAxis = createAxis(5, axisHeight, xCoordinate, (yCoordinate - axisHeight));
        
        /*Consolidate everything together*/
        mainFrame.add(xAxis);
        mainFrame.add(yAxis);
        
        /*Draw Bins*/
        mainFrame = createHistogramBins(bins, axisWidth, axisHeight, xCoordinate, yCoordinate, mainFrame);
        
        
        
        return mainFrame;
    }
    
    public static JPanel createHistogramFrame(int width, int height, int x, int y)
    {        
        /*Draw Frame*/
        JPanel mainFrame = new JPanel();
        mainFrame.setLayout(null);
        
        mainFrame.setSize(width, height);
        mainFrame.setLocation(x, y);
        
        /*Initialise Border and Background*/
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.decode("#d2d2d2"), 1);
        mainFrame.setBorder(border);
        mainFrame.setBackground(Color.WHITE);

        return mainFrame;
    }
    
    public static JPanel createAxis(int width, int height, int x, int y)
    {
        JPanel xAxis = createPanel(width, height, Color.BLACK);
        xAxis.setLocation(x, y);
        
        return xAxis;
    }
        
    public static JPanel createHistogramBins(HistogramBin[] bins, int axisWidth, int axisHeight, int axisX, int axisY, JPanel basePanel)
    {        
        HistogramBin[] copyBins = new HistogramBin[bins.length];
        System.arraycopy(bins, 0, copyBins, 0, bins.length);
        
        java.util.List<HistogramBin> binArrayList = java.util.Arrays.asList(copyBins);
        binArrayList.sort(new HistogramBin.BinComparator());
        
        int maxValue = binArrayList.getLast().value;
        int binWidth = (int) ((double)axisWidth/ (double)bins.length) - 10;
        int x = axisX + 5;
                
        for (HistogramBin bin : bins)
        {
            int height = (int) (((double) bin.value/(double) maxValue) * axisHeight); 
            int y = axisY - height;   
            
            JPanel bar = createPanel(binWidth, height, Color.CYAN);        
            bar.setLocation(x, y);
            x += binWidth + 10;
            
            basePanel.add(bar);
        }
        
        return basePanel;
    }
        
    public static JPanel createPanel(int width, int height, Color colour)
    {
        JPanel panel = new JPanel();
        panel.setSize(width, height);
        panel.setBackground(colour);
        
        return panel;        
    }
    
    static class HistogramBin{
        
        public int value;
        public String caption;
               
        public HistogramBin(int newValue, String newCaption)
        {
            value = newValue;
            caption = newCaption;
        }
        
        static class BinComparator implements java.util.Comparator<HistogramBin> {
        @Override
        public int compare(HistogramBin a, HistogramBin b) {
            return a.value - b.value;
        }
}
        
    }
    
    static class AxisData{
        public int width;
        public int height;
        public int xCoordinate;
        public int yCoordinate;
        
        public AxisData(int newWidth, int newHeight, int newXCoordinate, int newYCoordinate)
        {
            width = newWidth;
            height = newHeight;
            xCoordinate = newXCoordinate;
            yCoordinate = newYCoordinate;
        }
    }
}
