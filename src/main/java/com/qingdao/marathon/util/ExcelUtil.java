package com.qingdao.marathon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qingdao.marathon.express.model.CarrierModel;
import com.qingdao.marathon.thread.model.ProgressbarModel;

public class ExcelUtil {
	/**
	 * 生成excel文件
	 * 
	 * @param list 数据
	 * @param nameArray 表头中文
	 * @param colArray 需要生成的列
	 * @param clazz list中对象的类型
	 * @return 
	 */
	public static void createExcel(List dataList,String[] nameArray,String[] colArray,String filePath, int index, String sheetName, SXSSFWorkbook swb) throws Exception{
		File dir = new File(filePath.substring(0,filePath.lastIndexOf("/")));
		int size = dataList.size();//list长度
		Class clazz = null;//list中数据类型
		Sheet sheet = null;//工作表
		Row row = null;
		index++;
		
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		sheet = swb.getSheet(sheetName);
		if(sheet == null){
			sheet = swb.createSheet(sheetName);
			row = sheet.createRow(0);
			for (int i = 0; i < nameArray.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(nameArray[i]);
			}
		}
		
		if (size>0) {
			clazz = dataList.get(0).getClass();
			Method[] methods = new Method[colArray.length];//方法数组
			for (int i = 0; i < colArray.length; i++) {
				Method m = clazz.getMethod("get" + colArray[i], null);
				methods[i] = m;
			}
			
			int j = index;
			for (Object o : dataList) {
				row = sheet.createRow(j);
				for (int i = 0; i < nameArray.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(methods[i].invoke(o, null) + "");
				}
				j++;
			} 
		}
		
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void createExcel(List dataList,Class clazz,String filePath,ProgressbarModel model , HSSFWorkbook swb) throws Exception{
		File dir = new File(filePath.substring(0,filePath.lastIndexOf("/")));
		int size = dataList.size();//list长度
		HSSFSheet sheet = null;//工作表
		HSSFRow row = null;
		
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		sheet = swb.getSheet("sheet1");
		
		Field[] fileds = clazz.getDeclaredFields();
		List<String> nameList = MarathonUtil.getDeclaredFields(fileds);//获取对象所有属性
		
		if(sheet == null){
			sheet = swb.createSheet("sheet1");
			row = sheet.createRow(0);
			
			for (int i = 0; i < nameList.size(); i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(nameList.get(i));
			}
		}
		
		if (size>0) {
			Method[] methods = MarathonUtil.getMethod(clazz);
			Method[] childMethods = null;
			boolean flag = false;
			List list = new ArrayList<>();
			List<HSSFRow> rowList = new ArrayList<HSSFRow>();
			for (int i=0;i<dataList.size();i++) {
				rowList.clear();
				model.setCurrentNum(model.getCurrentNum()+1);
				flag = false;
				row = sheet.createRow(sheet.getLastRowNum()+1);
				//如果最后一个属性是list
				if(methods[fileds.length-1].invoke(dataList.get(i), null).getClass().isAssignableFrom(ArrayList.class)){
					list = (List) methods[fileds.length-1].invoke(dataList.get(i), null);
					childMethods = MarathonUtil.getMethod(list.get(0).getClass());
					flag = true;
					
					for(int n = 1; n<list.size();n++){
						rowList.add(sheet.createRow(sheet.getLastRowNum()+1));
					}
				}
				for (int j = 0; j < nameList.size(); j++) {
					
					if(flag){
						if(j >= fileds.length-1){
							
							for(int k = 0 ;k < list.size();k++){
								
								if(k == 0){
									HSSFCell cell = row.createCell(j);
									String val = childMethods[j+1-fileds.length].invoke(list.get(k),null)+"";
									cell.setCellValue(val);
								}else{
									HSSFCell cell = rowList.get(k-1).createCell(j);
									String val = childMethods[j+1-fileds.length].invoke(list.get(k),null)+"";
									cell.setCellValue(val);
								}
							}
						}else{
							int t = sheet.getLastRowNum()-list.size()+1;
							sheet.addMergedRegion(new Region(t,(short)j,t+list.size()-1,(short)j));      
							HSSFCell cell = row.createCell(j);
							String value = methods[j].invoke(dataList.get(i), null)+"";
							cell.setCellValue(value);
						}
						
					}else{
						Cell cell = row.createCell(j);
					}
					
					
				}
			} 
		}
		
	}
	
	public static void writeToExcel(SXSSFWorkbook swb, String filePath) throws Exception{
		FileOutputStream fileOut = new FileOutputStream(filePath);
		swb.write(fileOut);
		fileOut.close();
	}
	
	public static void writeToExcel(HSSFWorkbook swb, String filePath) throws Exception{
		FileOutputStream fileOut = new FileOutputStream(filePath);
		swb.write(fileOut);
		fileOut.close();
	}
	
	
	private static <T> List<T> readExcel(String path, Class clazz,String[] colArray) throws IOException {
		if (path == null || "".equals(path)) {
			return null;
		} else {
			String postfix = path.substring(path.lastIndexOf(".") + 1, path.length());
			if (!"".equals(postfix)) {
				if ("xls".equals(postfix)) {
					return readXls(path, clazz, colArray);
				} else if ("xlsx".equals(postfix)) {
					return readXlsx(path, clazz, colArray);
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> readXlsx(String path, Class clazz,String[] colArray) throws IOException {
		List<T> list = new ArrayList<T>();
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		T hSModel = null;
		// Read the Sheet
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		if (xssfSheet == null) {
			return null;
		}

		// Read the Row
		List<String> colNameList = new ArrayList<>();
		for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow != null && rowNum == 0) {
				Short start = xssfRow.getFirstCellNum();
				Short end = xssfRow.getLastCellNum();
				//String[] colArray = {"MatchId","MatchName","GroupId","GroupName","Name","IDNumber","CompetitionNo","Achievement","Ranking"};
				for (int i = start; i < end; i++) {
					colNameList.add(colArray[i]);
				}
			}else if(xssfRow != null && rowNum > 0){
				try {
					hSModel = (T) clazz.newInstance();
					for(int i=0;i<colNameList.size();i++){
						Method method = clazz.getMethod("set" + colNameList.get(i),String.class);
						method.invoke(hSModel, getValue(xssfRow.getCell(i)));
					}
					list.add(hSModel);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> readXls(String path, Class clazz,String[] colArray) throws IOException {
		List<T> list = new ArrayList<T>();
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		T hSModel = null;
		// Read the Sheet
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		if (hssfSheet == null) {
			return null;
		}

		// Read the Row
		List<String> colNameList = new ArrayList<>();
		for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow != null && rowNum == 0) {
				Short start = hssfRow.getFirstCellNum();
				Short end = hssfRow.getLastCellNum();
//				for (int i = start; i <= end; i++) {
//					colNameList.add(getValue(hssfRow.getCell(i)));
//				}
				for (int i = start; i < end; i++) {
					colNameList.add(colArray[i]);
				}
			}else if(hssfRow != null && rowNum > 0){
				try {
					hSModel = (T) clazz.newInstance();
					for(int i=0;i<colNameList.size();i++){
						Method method = clazz.getMethod("set" + colNameList.get(i),String.class);
						method.invoke(hSModel, getValue(hssfRow.getCell(i)));
					}
					list.add(hSModel);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		return list;
	}

	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "";
		}
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING) {
			return String.valueOf(hssfCell.getStringCellValue());
		} else {
			return "";
		}
	}

	public static <T> List<T> getCache(String path, Class clazz,String[] colArray) {
		try {
			return readExcel(path, clazz, colArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ExcelUtil instance = null;


	public static ExcelUtil instance() {
		if (instance == null) {
			instance = new ExcelUtil();
		}
		
		return instance;
	}
	
	/**
	 * read the Excel file
	 * 
	 * @param path
	 *            the path of the Excel file
	 * @return
	 * @throws IOException
	 */
	private List<CarrierModel> readExcel(String path) throws IOException {
		if (path == null || "".equals(path)) {
			return null;
		} else {
			String postfix = path.substring(path.lastIndexOf(".") + 1,
					path.length());
			if (!"".equals(postfix)) {
				if ("xls".equals(postfix)) {
					return readXls(path);
				} else if ("xlsx".equals(postfix)) {
					return readXlsx(path);
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	private List<CarrierModel> readXlsx(String path) throws IOException {
		List<CarrierModel> list = new ArrayList<CarrierModel>();
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		CarrierModel hSModel = null;
		// Read the Sheet
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
		if (xssfSheet == null) {
			return null;
		}
		
		// Read the Row
		for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			XSSFRow xssfRow = xssfSheet.getRow(rowNum);
			if (xssfRow != null) {
				hSModel = new CarrierModel();
				XSSFCell carrierID = xssfRow.getCell(0);
				XSSFCell carrierName = xssfRow.getCell(1);
				
				
				hSModel.setCarrierID(getValue(carrierID).trim());
				hSModel.setCarrierName(getValue(carrierName).trim());
				list.add(hSModel);

			}
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	private List<CarrierModel> readXls(String path) throws IOException {
		List<CarrierModel> list = new ArrayList<CarrierModel>();
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		CarrierModel hSModel = null;
		// Read the Sheet
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
		if (hssfSheet == null) {
			return null;
		}
		// Read the Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow != null) {
				hSModel = new CarrierModel();
				HSSFCell carrierID = hssfRow.getCell(0);
				HSSFCell carrierName = hssfRow.getCell(1);

				hSModel.setCarrierID(getValue(carrierID).trim());
				hSModel.setCarrierName(getValue(carrierName).trim());
			}
			list.add(hSModel);
		}
		return list;
	}
	
	public  List<CarrierModel> getCache(String path){
		try {
			return readExcel(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
