package Controller;

import java.io.File;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author leo
 */
public interface IController {
    void imageLoaded(File image);
    void mnuSaveImage_OnClick();
    List<String> getOperationList();
}
