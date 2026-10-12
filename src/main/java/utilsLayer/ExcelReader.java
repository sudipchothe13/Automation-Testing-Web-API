package utilsLayer;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	public XSSFWorkbook workbook;

	public ExcelReader(String filePath) {
		File f = new File(filePath);
	
			try {
				FileInputStream fis = new FileInputStream(f);
				workbook = new XSSFWorkbook(fis);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		
	}

	public String getValue(int sheetIndex, int rows, int cells) {

		try {
			return workbook.getSheetAt(sheetIndex).getRow(rows).getCell(cells).getStringCellValue();
		} catch (Exception e) {
			double d = workbook.getSheetAt(sheetIndex).getRow(rows).getCell(cells).getNumericCellValue();
			return Long.toString((long) d);
		}
	}

	public int rowCount(int sheetIndex) {
		return workbook.getSheetAt(sheetIndex).getLastRowNum()+1;
	}

	public int cellCount(int sheetIndex) {
		return workbook.getSheetAt(sheetIndex).getRow(0).getLastCellNum();
	}
	
	public static Object [][] dataProvide(String filePath, int sheetIndex){

		ExcelReader excel = new ExcelReader(filePath);
		int rows = excel.rowCount(sheetIndex);
		int cells = excel.cellCount(sheetIndex);
		
		Object [][] data = new Object[rows][cells];
		
		for(int i=0; i<rows; i++) {
			
			for(int j=0; j<cells; j++) {
				
				data [i][j]=excel.getValue(sheetIndex, i, j);
			}
		}
		return data;
		
	}

}











