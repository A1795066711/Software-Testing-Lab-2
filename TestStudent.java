package cn.tjucic.selenium;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;



public class TestStudent {
	
	 private static WebDriver driver;
	  private static String baseUrl;
	  public static ArrayList<Student> allStu = new ArrayList<Student> ();
	  public ArrayList<Student> notEqual = new ArrayList<Student>();
	  
	  
	  
	  
	  public static void getWebData()
		 {
			 String driverPath = System.getProperty("user.dir") + "/src/resources/geckodriver.exe";
			  System.setProperty("webdriver.gecko.driver", driverPath);
			  for (int i = 1 ; i< /*allStu.size()*/ 5 ; i++)
			  {   				  
				  driver = new FirefoxDriver();
				  baseUrl = "http://121.193.130.195:8800";
			  	  String name = allStu.get(i).name;
			  	  String gitUrl = allStu.get(i).gitUrl;
			  	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				  driver.get(baseUrl + "/");
				  String stuId = allStu.get(i).studentId;
				  WebElement we = driver.findElement(By.name("id"));
				  we.click();
				  we.clear();
				  we.sendKeys(stuId);
				  WebElement wePass = driver.findElement(By.name("password"));
				  wePass.click();;
				  wePass.clear();
			  	  String password = stuId.substring(4);
				  wePass.sendKeys(password);
				  driver.findElement(By.id("btn_login")).click();
				  String name2 = driver.findElement(By.id("student-name")).getText();
				  String gitUrl2 = driver.findElement(By.id("student-git")).getText();
				  System.out.println(name + "\n" + gitUrl);
				  driver.findElement(By.id("btn_logout")).click();
				  driver.quit();
				  allStu.get(i).nameWeb = name2;
				  allStu.get(i).gitUrlWeb = gitUrl2;
			  }
		 }
	
	  
	 public static  void readExcel(ArrayList < Student > all) throws Exception
	    {
	        //创建输入流
	        FileInputStream fis = new FileInputStream(new File("软件测试名单.xlsx"));
	        //由输入流得到工作簿
	        XSSFWorkbook workbook = new XSSFWorkbook(fis);
	        //得到工作表
	        XSSFSheet sheet = workbook.getSheetAt(0);
	        //得到行,0表示第一行
	        XSSFRow row = sheet.getRow(1);
	        //创建单元格行号由row确定,列号作为参数传递给createCell;第一列从0开始计算
	        
	        //给单元格赋值
	        
	        for ( int i = 1 ; i< sheet.getPhysicalNumberOfRows() ; i++)
	        {
	        	row = sheet.getRow(i);
	        	Student st = new Student();
	        	XSSFCell cell1 = row.getCell(1);
	        	XSSFCell cell2 = row.getCell(2);
	        	XSSFCell cell3 = row.getCell(3);
		        if (cell1 != null){
			        	cell1.setCellType(CellType.STRING);
			        	String cellValue = cell1.getStringCellValue();
			        	st.studentId = cellValue;
			        }
		        if (cell2 != null){
		        	cell2.setCellType(CellType.STRING);
		        	String cellValue = cell2.getStringCellValue();
		        	st.name = cellValue;
		        }
		        if (cell3 != null){
		        	cell3.setCellType(CellType.STRING);
		        	String cellValue = cell3.getStringCellValue();
		        	st.gitUrl = cellValue;
		        }
		        all.add(st);
	        	
	        }
	        
	        workbook.close();
	        fis.close();
	    }
	 
	 
	 
	 public void writeExcel(ArrayList<Student> all) throws Exception
	    {
	        //创建工作簿
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        //新建工作表
	        XSSFSheet sheet = workbook.createSheet();
	        //创建行,0表示第一行
	        for (int i = 0 ; i< /*all.size()*/ 5 ; i++)
	        {
		        XSSFRow row = sheet.createRow(i);
		        //创建单元格行号由row确定,列号作为参数传递给createCell;第一列从0开始计算
		        XSSFCell cell1 = row.createCell(0);
		        cell1.setCellValue(all.get(i).name);
		        XSSFCell cell2 = row.createCell(1);
		        cell2.setCellValue(all.get(i).nameWeb);
		        XSSFCell cell3 = row.createCell(2);
		        cell3.setCellValue(all.get(i).gitUrl);
		        XSSFCell cell4 = row.createCell(3);
		        cell4.setCellValue(all.get(i).gitUrlWeb);
	        }
	    }
	        
	 public static void main(String s [])
		{
			TestStudent t = new TestStudent();

			
			
			try {
			TestStudent.readExcel(TestStudent.allStu);
			getWebData();
			t.writeExcel(TestStudent.allStu);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			
		}

}
