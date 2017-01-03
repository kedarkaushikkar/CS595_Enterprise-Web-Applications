import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class Composer {

    private String id ;
    private String name ;
    private String desc ;
    private String categ ;
    private String manfName ;    
    
    public Composer (String id, String name, String desc, String categ, String manfName) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.categ = categ;
        this.manfName = manfName;
    }
    
    public String getID() {
        return id;
    }
    
    public String getProdName() {
        return name;
    }
    
    public String getProdDesc() {
        return desc;
    }

    public String getProdCat() {
        return categ;
    }

    public String getProdManfName(){
        return manfName ;
    }
}