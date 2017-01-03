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

public class ProdDS{

    public  int id ;
    public  String name ;
    public  String desc ;
    public  String categ ;
    public  String manfName ;
    public  double price ;
    public  String manfReb ;
    public  String retDiscount ;

    public void setID(int id){
        this.id = id ;
    } 

    public void setProdName(String name){
        this.name = name ;
    }

    public void setProdDesc(String desc){
        this.desc = desc ;
    }

    public void setProdCat(String categ){
        this.categ = categ ;
    }

    public void setProdManfName(String manfName){
        this.manfName = manfName ;
    }

    public void setProdPrice(double price){
        this.price = price ;
    }

    public void setProdManfReb(String manfReb){
        this.manfReb = manfReb ;
    }

    public void setProdRetDisc(String retDiscount){
        this.retDiscount = retDiscount ;
    }    

    public int getID(){
        return id ;
    }

    public String getProdName(){
        return name ;
    }

    public String getProdDesc(){
        return desc ;
    }

    public String getProdCat(){
        return categ ;
    }

    public String getProdManfName(){
        return manfName ;
    }

    public double getProdPrice(){
        return price;
    }

    public String getProdManfReb(){
        return manfReb ;
    }

    public String getProdRetDisc(){
        return retDiscount ;
    } 
}
