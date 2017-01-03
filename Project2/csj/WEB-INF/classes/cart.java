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

public class cart{


    HashMap<String, List<String>> cartItems;
    public cart(){
     cartItems = new HashMap<String,List<String>>();
      
    }
    public HashMap getCartItems(){
        return cartItems;
    }
    public void addToCart(String itemId, List<String> vals){
        cartItems.put(itemId, vals);
    }

    public void deleteFromCart(String itemId){
        cartItems.remove(itemId);
    }

}
