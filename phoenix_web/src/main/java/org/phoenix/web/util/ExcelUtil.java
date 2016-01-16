package org.phoenix.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.phoenix.web.dto.SheetContentDTO;

import com.google.common.io.Files;

/**
 * Excel导入导出的工具类
 * @author mengfeiyang
 *
 */
public class ExcelUtil {
	
	public static final String HEADERINFO="headInfo";
	public static final String DATAINFON="dataInfo";
	public short ROW_HIGHT = 500;
	public int Col_WIDTH = 20;
	private String exportExcelPath;
	private String importExcelPath;
	
	public String getExportExcelPath() {
		return exportExcelPath;
	}
	public void setExportExcelPath(String exportExcelPath) {
		this.exportExcelPath = exportExcelPath;
	}
	public String getImportExcelPath() {
		return importExcelPath;
	}
	public void setImportExcelPath(String importExcelPath) {
		this.importExcelPath = importExcelPath;
	}
	/**
	 * 
	 * @Title: getWeebWork
	 * @Description: TODO(根据传入的文件名获取工作簿对象(Workbook))
	 * @param filename
	 * @return
	 * @throws IOException 
	 */
	public Workbook getWeebWork() throws IOException{
		Workbook workbook=null;
		if(null!=importExcelPath){
			String fileType=Files.getFileExtension(importExcelPath);
			FileInputStream fileStream = new FileInputStream(new File(importExcelPath));
			if("xls".equals(fileType.trim().toLowerCase())){
				workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
			}else if("xlsx".equals(fileType.trim().toLowerCase())){
				workbook = new XSSFWorkbook(fileStream);//创建 Excel 2007 工作簿对象
			}
		}
		return workbook;
	}
	
	/**
	 * 导出指定格式的excel数据表
	 * @param sheetDTO
	 * @throws IOException 
	 */
	public void exportExcel(SheetContentDTO sheetDTO) throws IOException{
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(sheetDTO.getCaseName());
			sheet.setDefaultColumnWidth(Col_WIDTH);
			sheet.setDefaultRowHeight(ROW_HIGHT);
			
			Row caseName =sheet.createRow(0);
			caseName.setRowStyle(getCellStyle(workbook));
			Cell caseNameCell = caseName.createCell(0);
			caseNameCell.setCellType(Cell.CELL_TYPE_STRING);
			caseNameCell.setCellValue("用例名称：");
			Cell caseDataCell = caseName.createCell(1);
			caseDataCell.setCellType(Cell.CELL_TYPE_STRING);
			caseDataCell.setCellValue(sheetDTO.getCaseName());
			
			Row caseId =sheet.createRow(1);
			caseId.setRowStyle(getCellStyle(workbook));
			Cell caseIdNameCell = caseId.createCell(0);
			caseIdNameCell.setCellType(Cell.CELL_TYPE_STRING);
			caseIdNameCell.setCellValue("用例ID：");
			Cell caseIdDataCell = caseId.createCell(1);
			caseIdDataCell.setCellType(Cell.CELL_TYPE_STRING);
			caseIdDataCell.setCellValue(sheetDTO.getCaseId());
			
			Row paramName = sheet.createRow(2);
			paramName.setRowStyle(getCellStyle(workbook));
			for(int i=0;i<sheetDTO.getParamNames().length;i++){
				Cell paramNameCell = paramName.createCell(i);
				paramNameCell.setCellType(Cell.CELL_TYPE_STRING);
				paramNameCell.setCellValue(sheetDTO.getParamNames()[i]);
			}
			
			for(int j=3;j<sheetDTO.getParameters().size()+3;j++){
				Row paramContentRow = sheet.createRow(j);
				paramContentRow.setRowStyle(getCellStyle(workbook));
				for(int m = 0;m < sheetDTO.getParameters().get(j-3).length;m++){
					Cell paramContentCell = paramContentRow.createCell(m);
					paramContentCell.setCellType(Cell.CELL_TYPE_STRING);
					paramContentCell.setCellValue(sheetDTO.getParameters().get(j-3)[m]);
				}
			}
			File file = new File(exportExcelPath);
			OutputStream os = new FileOutputStream(file);
			os.flush();
			workbook.write(os);
			workbook.close();
			os.close();
	}
	
	/**
	 * 
	 * @Title: writeExcel
	 * @Description: TODO(导出Excel表)
	 * @param pathname:导出Excel表的文件路径
	 * @param map：封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap)
	 * 例如： map.put(ExcelUtil.HEADERINFO,List<String> headList);
	 * 		 map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>>  dataList);
	 * @param wb
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(Map<String,Object> map) throws IOException{
		if(null!=map && null!=exportExcelPath){
			Workbook workbook = new XSSFWorkbook(new FileInputStream(new File(exportExcelPath)));
			List<Object> headList = (List<Object>) map.get(ExcelUtil.HEADERINFO);
			List<TreeMap<String,Object>> dataList =  (List<TreeMap<String, Object>>) map.get(ExcelUtil.DATAINFON);
			CellStyle style = getCellStyle(workbook);
			Sheet sheet = workbook.createSheet();
			/**
			 * 设置Excel表的第一行即表头
			 */
			Row row =sheet.createRow(0);
			for(int i=0;i<headList.size();i++){
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);//设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}
			
			for (int i = 0; i < dataList.size(); i++) {
				Row rowdata = sheet.createRow(i+1);//创建数据行
				TreeMap<String, Object> mapdata =dataList.get(i);
				Iterator<String> it = mapdata.keySet().iterator();
				int j=0;
				while(it.hasNext()){
					String strdata = String.valueOf(mapdata.get(it.next()));
					Cell celldata = rowdata.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
					j++;
				}
			}
			File file = new File(exportExcelPath);
			OutputStream os = new FileOutputStream(file);
			os.flush();
			workbook.write(os);
			os.close();
		}
	}
	/**
	 * 
	 * @Title: getCellStyle
	 * @Description: TODO（设置表头样式）
	 * @param wb
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)12);//设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//加粗
		//style.setFillForegroundColor(HSSFColor.LIME.index);// 设置背景色
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//让单元格居左
		style.setShrinkToFit(true);//自适应
		//style.setWrapText(true);//设置自动换行
		style.setFont(font);
		return style;
	}
	
	/**
	 * 获取所有的sheet页名称
	 * @return
	 */
	public List<String> getSheetNames(){
		List<String> sheetNames = new ArrayList<String>();
		Workbook workbook;
		try {
			workbook = getWeebWork();
			for(int i=0;i<workbook.getNumberOfSheets();i++){
				sheetNames.add(workbook.getSheetAt(i).getSheetName());
			}
			return sheetNames;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @Title: readerExcelDemo
	 * @Description: TODO(读取Excel表中的数据)
	 * @throws IOException
	 */
	public SheetContentDTO getExcelContent(String sheetName) throws IOException{
		SheetContentDTO sheetDTO = new SheetContentDTO();
		List<String[]> params = new ArrayList<String[]>();
		Sheet sheet =getWeebWork().getSheet(sheetName);
		sheetDTO.setSheetName(sheetName);
		sheetDTO.setCaseName(sheet.getRow(0).getCell(1).getStringCellValue());
		try{
			sheetDTO.setCaseId(String.valueOf(new BigDecimal(sheet.getRow(1).getCell(1).getNumericCellValue())));
		}catch(Exception e){
			sheetDTO.setCaseId(sheet.getRow(1).getCell(1).getStringCellValue());
		}
		if(sheetDTO.getCaseId().equals("0"))sheetDTO.setCaseId("");
		Row paramNameRow = sheet.getRow(2);
		int maxColNum = paramNameRow.getLastCellNum();
		String[] paramNames = new String[maxColNum];
		for(int i=0;i<maxColNum;i++){
			paramNames[i] = paramNameRow.getCell(i).getStringCellValue();
		}
		sheetDTO.setParamNames(paramNames);
		
		int rownum=sheet.getLastRowNum()+1;//获取总行数
		for (int i = 3; i < rownum; i++) {
			Row row =sheet.getRow(i);
			String[] cellData = new String[maxColNum];
			for(int j=0;j< maxColNum;j++){
				try{
					cellData[j] = row.getCell(j).getStringCellValue();
				}catch(NullPointerException e){
					cellData[j] = "";
				}catch(IllegalStateException ee){
					cellData[j] = String.valueOf(new BigDecimal(row.getCell(j).getNumericCellValue()));
				}
			}
			params.add(cellData);
		}
		sheetDTO.setParameters(params);
		return sheetDTO;
	}
	
	public static void main(String[] args) throws IOException {
		ExcelUtil e = new ExcelUtil();
		e.setImportExcelPath("F:\\1.xlsx");
		SheetContentDTO s = e.getExcelContent("Sheet1");
		
		System.out.println(s.getCaseName());
		System.out.println(s.getCaseId());
		System.out.println(Arrays.toString(s.getParamNames()));
		List<String[]> ls = s.getParameters();
		for(String[] l : ls){
			System.out.println(Arrays.deepToString(l));
		}
		e.setExportExcelPath("F:\\2.xlsx");
		e.exportExcel(s);
/*		Workbook wb = new XSSFWorkbook();
		Map<String,Object> map = new HashMap<String,Object>();
		List headList = new ArrayList();//表头数据
		headList.add("下单时间");
		headList.add("结账时间");
		headList.add("订单编号");
		headList.add("订单金额");
		headList.add("用户名");
		
		List dataList = new ArrayList();//表格内的数据
		for (int i = 0; i < 15; i++) {
			TreeMap<String,Object> treeMap = new TreeMap<String, Object>();//此处的数据必须为有序数据，所以使用TreeMap进行封装
			treeMap.put("m1", "2013-10-"+i+1);
			treeMap.put("m2", "2013-11-"+i+1);
			treeMap.put("m3", "20124"+i+1);
			treeMap.put("m4", 23.5+i+1);
			treeMap.put("m5", "张三_"+i);
			dataList.add(treeMap);
		}
		TreeMap<String,Object> treeMap1 = new TreeMap<String, Object>();
		treeMap1.put("asd", null);
		treeMap1.put("猪头", "zhutou");
		dataList.add(treeMap1);
		map.put(ExcelUtil.HEADERINFO, headList);
		map.put(ExcelUtil.DATAINFON, dataList);
		writeExcel("E:/test.xlsx", map, wb);*/
	}
}
