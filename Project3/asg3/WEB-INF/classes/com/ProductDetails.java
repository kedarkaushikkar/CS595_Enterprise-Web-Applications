package com;

public class ProductDetails {

	public ProductDetails() {

    }
    private int id;
    private String prodname;
    private String proddesc;
    private String prodcat;
    private String manfname;
    private String prodprice;
    private String manfreb;
    private String retdisc;

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	
	public String getProddesc() {
		return proddesc;
	}
	public void setProddesc(String proddesc) {
		this.proddesc = proddesc;
	}

	public String getProdcat() {
		return prodcat;
	}
	public void setProdcat(String prodcat) {
		this.prodcat = prodcat;
	}

	public String getManfname() {
		return manfname;
	}
	public void setManfname(String manfname) {
		this.manfname = manfname;
	}

	public String getProdprice() {
		return prodprice;
	}
	public void setProdprice(String prodprice) {
		this.prodprice = prodprice;
	}

	public String getManfreb() {
		return manfreb;
	}
	public void setManfreb(String manfreb) {
		this.manfreb = manfreb;
	}

	public String getRetdisc() {
		return retdisc;
	}
	public void setRetdisc(String retdisc) {
		this.retdisc = retdisc;
	}
}