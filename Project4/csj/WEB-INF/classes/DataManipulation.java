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

public class DataManipulation{
    public static void setData(String line,ProdDS pds){
        String[] firstSplit = line.trim().split("=");
        pds.setID(Integer.valueOf(firstSplit[0]));
        String[] elements = firstSplit[1].trim().split(",");
        pds.setProdName(elements[0]);
        pds.setProdDesc(elements[1]);
        pds.setProdCat(elements[2]);
        pds.setProdManfName(elements[3]);
        pds.setProdPrice(Double.valueOf(elements[4]));
        pds.setProdManfReb(elements[5]);
        pds.setProdRetDisc(elements[6]);
    }
}
