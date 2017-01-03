/*
 * LoginServlet.java
 *
 */
 
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class DataUpdater{
    public static void updateData(ArrayList<ProdDS> elements , String path ,String path2)  throws IOException
    {
        //PrintWriter pw = new PrintWriter(path);
        //pw.close();
      //  File filename = new File(path);
        //FileWriter filestream = new FileWriter("test.txt");
        //BufferedWriter buffWriter = new BufferedWriter(filestream);
        PrintWriter pw = new PrintWriter(path2);
        pw.println("kedar");
        //buffWriter.write("kedar");
       // PrintWriter pwUpdate = new PrintWriter(path);   //throw exception
       // pwUpdate.println("kedar test");
        /*for(ProdDS pds : elements){
            String line = convertDS2String(pds);
            buffWriter.write(line);
            buffWriter.write("\n");
        }*/
    }

public static String convertDS2String(ProdDS pds)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(pds.getID());
        sb.append("=");
        sb.append(pds.getProdName());
        sb.append(",");
        sb.append(pds.getProdDesc());
        sb.append(",");
        sb.append(pds.getProdCat());
        sb.append(",");
        sb.append(pds.getProdQuant());
        sb.append(",");
        sb.append(pds.getProdPrice());
        return sb.toString();

    }
}
