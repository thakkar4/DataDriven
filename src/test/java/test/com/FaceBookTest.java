package test.com;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FaceBookTest {
	
		private WebDriver driver;
	   private String baseUrl;
	   private static Login login;

	   @BeforeClass
	    public static void setUpClass() throws FileNotFoundException, IOException {
	         //read excel file
	     FileInputStream inputStream = new FileInputStream(new File("c:\\Drivers\\facebook.xlsx"));
	         
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        //getting first worksheet
	        Sheet firstSheet = workbook.getSheetAt(0);
	        //get first row
	        Row r =  firstSheet.getRow(1);
	        Cell c = r.getCell(0); //username value
	        String username = c.getStringCellValue();
	        c = r.getCell(1); //username value
	        String password = c.getStringCellValue();
	        
	        login = new Login(username, password);
	        
	        System.out.println(login);
	        inputStream.close();
	    }

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		 System.setProperty("webdriver.chrome.driver", "c:\\Drivers\\chromedriver.exe");     
		    driver = new ChromeDriver();
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	  public void testFacebook() throws Exception {
	    driver.get("https://www.facebook.com/");
	    driver.manage().window().maximize();
	    driver.findElement(By.id("email")).click();
	    driver.findElement(By.id("email")).clear();
	    driver.findElement(By.id("email")).sendKeys(login.getUsername());
	    driver.findElement(By.id("pass")).clear();
	    driver.findElement(By.id("pass")).sendKeys(login.getPassword());
	    driver.findElement(By.id("u_0_2")).click();
	    driver.findElement(By.id("header_block")).click();
	    try {
	      assertEquals("Log Into Facebook", driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Create New Account'])[1]/following::span[3]")).getText());
	    } catch (Error e) {
	      fail(e.toString());
	    }
	  }

}
