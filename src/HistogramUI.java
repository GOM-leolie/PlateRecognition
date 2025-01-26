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
    
    int maxValue;
    int medianValue;
    int totalBin;
    int totalData;
    int[] histogramData;
    
    HistogramBin[] histogramBins;
    
    public static void showHistogram(int[] newHistogramData, int newTotalBin)
    {
        JFrame newFrame = new JFrame();
        newFrame.setSize(800, 500);
        newFrame.setLocation(100, 100);
        
        HistogramUI newUI = new HistogramUI(new int[0], 9);
        JPanel histogram = newUI.drawHistogram(newFrame.getWidth(), newFrame.getHeight());
        
        newFrame.add(histogram);
        newFrame.setVisible(true);
    }
    
    public HistogramUI(int[] newHistogramData, int newTotalBin)
    {
        histogramData = newHistogramData;
        totalBin = newTotalBin;
     
        initHistogramData(histogramData);
        
        findMedianAndMaxValue();
        generateHistogramBin();
    }
    
    private void findMedianAndMaxValue()
    {
        totalData = histogramData.length;
        int medianIndex = 0;
        
        if (totalData % 2 == 0)
            medianIndex = (totalData - 1) / 2;
        else
            medianIndex = totalData / 2;
            
        int[] histogramDataDuplicate = new int[totalData];
        System.arraycopy(histogramData, 0, histogramDataDuplicate, 0, totalData);      
        Arrays.sort(histogramDataDuplicate);
        
        maxValue = histogramDataDuplicate[totalData - 1];
        medianValue = histogramDataDuplicate[medianIndex];
    }
    
    private void generateHistogramBin()
    {
        int binSize = (int) Math.ceil((double)totalData/(double)totalBin);
        histogramBins = new HistogramBin[totalBin];
                
        int indexCounter = 0;
        for (int i = 0 ; i < totalData ; i += binSize)
        {
            String binCaption = Integer.toString(i) + " to " + Integer.toString(i + binSize - 1);
            int binValue = 0;
            
            for (int j = i ; j < i + binSize ; j++)
                if (j < totalData)
                    binValue += histogramData[j];
            
            HistogramBin newBin = new HistogramBin(binValue, binCaption);
            histogramBins[indexCounter] = newBin;
            
            indexCounter++;
        }
        
        //for (HistogramBin bin : histogramBins)
            //System.out.println(bin.caption + ": " + bin.value);
    }
    
    /*Test Only Function*/
    private void initHistogramData(int[] histogramData)
    {
        histogramData = new int[200];
        
        for (int i = 0 ; i < 200 ; i++)
            histogramData[i] = (int) Math.floor(Math.random() * 100);
        
        this.histogramData = histogramData;
    }
    
    private JPanel drawHistogram(int containerWidth, int containerHeight)
    {        
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
        mainFrame = createHistogramBins(axisWidth, axisHeight, xCoordinate, yCoordinate, mainFrame);
        
        return mainFrame;
    }
    
    private JPanel createHistogramFrame(int width, int height, int x, int y)
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
    
    private JPanel createAxis(int width, int height, int x, int y)
    {
        JPanel xAxis = createPanel(width, height, Color.BLACK);
        xAxis.setLocation(x, y);
        
        return xAxis;
    }
        
    private JPanel createHistogramBins(int axisWidth, int axisHeight, int axisX, int axisY, JPanel basePanel)
    {        
        /*First pass to find maximum bin value to calculate the bin height*/
        int maxBinValue = 0;
        for (HistogramBin bin : histogramBins)
            if (bin.value > maxBinValue)
                maxBinValue = bin.value;
        
        int binWidth = (int) ((double)axisWidth/ (double)totalBin) - 10;
        int x = axisX + 10;
                
        for (HistogramBin bin : histogramBins)
        {
            int height = (int) (((double) bin.value/(double) maxBinValue) * axisHeight); 
            int y = axisY - height;   
            
            JPanel bar = createPanel(binWidth, height, Color.CYAN);        
            bar.setLocation(x, y);
            x += binWidth + 10;
            
            basePanel.add(bar);
        }
        
        return basePanel;
    }
            
    private JPanel createPanel(int width, int height, Color colour)
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
}
