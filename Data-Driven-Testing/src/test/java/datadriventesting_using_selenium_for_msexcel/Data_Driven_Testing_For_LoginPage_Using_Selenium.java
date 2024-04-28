package datadriventesting_using_selenium_for_msexcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//script to perform data driven testing for login page using selenium
public class Data_Driven_Testing_For_LoginPage_Using_Selenium {

	public static void main(String[] args) throws IOException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		String excel_file_path = ".\\Test_Data\\LoginData.xlsx";
		FileInputStream fis = new FileInputStream(excel_file_path);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet s = wb.getSheet("Sheet1");

		int rows_count = s.getLastRowNum();
		System.out.println(rows_count);
		int columns_count = s.getRow(1).getLastCellNum();
		System.out.println(columns_count);

		for (int r = 1; r <= rows_count; r++) {
			XSSFRow row = s.getRow(r);
			for (int c = 0; c < columns_count; c++) {
				XSSFCell cell_value = row.getCell(c);

				WebElement username = driver.findElement(
						By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[1]/div/div[2]/input"));

				username.sendKeys(cell_value.getStringCellValue());

				cell_value = row.getCell(++c);

				WebElement password = driver.findElement(
						By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[2]/div/div[2]/input"));
				password.sendKeys(cell_value.getStringCellValue());

				WebElement login = driver.findElement(
						By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button"));
				login.click();

				WebElement user_id = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/p"));
				System.out.println(user_id.getText());
				user_id.click();

				WebElement logout = driver
						.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/ul/li[4]"));
				logout.click();
			}
			System.out.println();
		}

		driver.quit();
	}
}
