
import java.util.HashMap;
import java.io.*;
import java.util.Map;
import java.util.*;

public class ComposerData {
    
    private HashMap composers = new HashMap();

    public HashMap getComposers() {
        return composers;
    }

    public ComposerData() {

        String path = "C:\\apache-tomcat-7.0.34\\webapps\\csj\\productdetails.txt";
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(path));
            String currentLine;
            while((currentLine =buffReader.readLine())!=null)
            {
                String [] prods = currentLine.split("=");
                String [] prodVars = prods[1].split(",");
                composers.put(prods[0],new Composer(prods[0],prodVars[0],prodVars[1],prodVars[2],prodVars[3]));
            }

        } catch(FileNotFoundException e) {
            // Code to handle the exception.
        }
        catch (IOException e){
            // Code to handle the exception.
        }
    }

}
