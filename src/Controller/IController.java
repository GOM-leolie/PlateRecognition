package Controller;

import java.io.File;
import java.util.List;

public interface IController {
    void imageLoaded(File image);
    List<String> getOperationList();
}
