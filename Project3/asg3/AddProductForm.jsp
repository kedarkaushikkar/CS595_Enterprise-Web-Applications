<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "DTD/xhtml1-transitional.dtd">

<!--
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
-->

<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Game Center Login Page</title>
    <link rel="stylesheet" href="styles.css" type="text/css" />
</head>
 <body>
<div id="container">
<header>
<h1><a href='/'>Game<span>Center</span></a></h1>
</header>
	<form id='adminScreen' action='AddProductPage.jsp' method='post'
	    accept-charset='UTF-8'>
	<fieldset >
	<span style="color:red;">Note:</span> Please Enter all the fields</br></br>
	<legend>Add Products</legend>
	<label for='id' >ProductID: </label>
	<input type='text' name='id' id='id' maxlength="10" />
	<br><br>
	<label for='prodname' >Product Name:</label>
	<input type='text' name='prodname' id='prodname' maxlength="30" />
	 <br><br>
	<label for='proddesc' >Product Description:</label>
	<input type='text' name='proddesc' id='proddesc' maxlength="50" />
	<br><br>


	<label for='prodcat' >Product Category:</label>
	<select name="prodcat">
	<option value="Game-Consoles">Game Consoles</option>
	<option value="Accessories">Accessories</option>
	<option value="WarrantyCard">Warranty Cards</option>
	<option value="Games">Games</option>
	</select>
	<br><br>

	<label for='manfname' >Product Company:</label>
	<select name="manfname">
	<option value="Microsoft">Microsoft</option>
	<option value="Sony">Sony</option>
	<option value="Nitendo">Nitendo</option>
	<option value="Logitech">Logitech</option>
	<option value="Game Center Ltd">Game Center Ltd</option>
	<option value="Logitech">Logitech</option>
	<option value="Electronic-Arts">Electronic Arts</option>
	<option value="Activision">Activision</option>
	<option value="Take-Two-Interactive">Take-Two Interactive</option>
	</select>
	<br><br>

	<label for='prodprice' >Product Price:</label>
	<input type='text' name='prodprice' id='prodprice' maxlength="6" />
	<br><br>
	<label for='manfreb' >Manufacturer Rebate:</label>
	<input type='text' name='manfreb' id='manfreb' maxlength="3" value="0%" /><span style="font-size:'4'; color:'red';">Enter values in percentage. Ex: 5% , 10%</span>
	<br><br>
	<label for='retdisc' >Retailer Discount:</label>
	<input type='text' name='retdisc' id='retdisc' maxlength="3" value="0%" /><span style="font-size:'4'; color:'red';">Enter values in percentage. Ex: 5% , 10%</span>
	<br><br>
	<input type='submit' name='AddProduct' value='Submit' />
	 </fieldset>
	</form>
	</center>
	<br>
    <br>
    <a href='login.jsp'>Go Back To Login</a>
<footer>
    <div class="footer-bottom">
        <p>CSP 595 - Enterprise Web Application - Assignment 3</p>
    </div>
</footer>
</div>
 </body>
</html>