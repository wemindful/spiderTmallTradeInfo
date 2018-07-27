package com.dgw.bimgcollection.domain;

import com.xuxueli.poi.excel.annotation.ExcelField;
import com.xuxueli.poi.excel.annotation.ExcelSheet;

@ExcelSheet(name = "Sheet1")
public class ShopDTO {

    @ExcelField(name = "商家名字")
    private String shopId;

    @ExcelField(name = "URL")
    private String shopName;

    public ShopDTO() {
    }
  
	public ShopDTO(String shopId, String shopName) {
		this.shopId = shopId;
		this.shopName = shopName;
	}

	
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "ShopDTO [shopId=" + shopId + ", shopName=" + shopName + "]";
	}
}