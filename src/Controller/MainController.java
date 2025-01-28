package Controller;

import View.*;
import Utility.*;
import java.io.File;
import java.util.*;

/**
 *
 * @author leo
 */
public class MainController implements IController{
    
    public static void main(String[] args)
    {
        new MainController();
    }
    
    public MainController()
    {
        MainUI newUI = new MainUI(this);
        newUI.setVisible(true);
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
        
    }
    
    @Override
    public void mnuSaveImage_OnClick()
    {
        
    }
    
}
