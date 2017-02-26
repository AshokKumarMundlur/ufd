package com.ufd.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class WebTableUtility {
	
	WebDriver driver;	
	WebElement table;
	public WebTableUtility(WebDriver driver, By tableLocator){
		try{	
		this.driver = driver;
		driver.findElement(tableLocator).isDisplayed();
		this.table = driver.findElement(tableLocator);
		}catch(Exception e){
			e.getMessage();
		}
	}
	
	
	public List<WebElement> getRowWebElement(){
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		return rows;
	}
	
	
	public List<WebElement> getColmWebElement(){
		List<WebElement> columns = table.findElements(By.tagName("td"));
		return columns;
	}
	
	public List<WebElement> getColmWebElement(WebElement row){
		List<WebElement> columns = row.findElements(By.tagName("td"));
		return columns;
	}
	
	
	public int getRowCount(){
		int rowCount = table.findElements(By.tagName("tr")).size();
		return rowCount;
	}

	public int getColumnCount(String rowNumber){
		int colmCount = table.findElements(By.tagName("td")).size();
		colmCount = colmCount + 1;
		return colmCount;
	}

	public int getRowWithCellText(String cellText){
		try{
		table.findElement(By.xpath("//tr/td[contains(text(),'"+ cellText + "')]")).isDisplayed();
		String intRow = table.findElement(By.xpath("//tr/td[contains(text(),'"+ cellText + "')]")).getAttribute("rowIndex");
		return Integer.parseInt(intRow);
		}catch (Exception e){
			Reporter.log("getRowWithCellText - Error " + e.getLocalizedMessage());
		}
		return -0;
		
	}

	public String getCellData(int iRow, int iColm){		
		String cellData = table.findElement(By.xpath("//tr[" + iRow + "]/td[" + iColm + "]")).getText();
		return cellData;
	}

	public String getTableColmHeaders(){
		String colHeaders="";
		List<WebElement> tableHeaders = table.findElements(By.tagName("th"));
		if(tableHeaders.size() == 0) return "-1";
		for(WebElement header : tableHeaders){
			colHeaders = colHeaders + header.getText();
		}
		return colHeaders;
	}

}
