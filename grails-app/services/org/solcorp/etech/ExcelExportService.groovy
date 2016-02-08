package org.solcorp.etech

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFFont
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.DateUtil
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFDataFormat
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.web.util.WebUtils
import org.solcorp.etech.utils.FileUtils
import org.springframework.web.context.request.RequestContextHolder

class ExcelExportService {
	
	
	def createWorkBook(){
		XSSFWorkbook  wb = new XSSFWorkbook ()
		return wb
	}
	
	def createSheet(XSSFWorkbook wb){
		return wb.createSheet("result")
	}
	
	def createTitleFont(XSSFWorkbook wb){
		
		XSSFFont font = wb.createFont()
		font.setColor(HSSFColor.DARK_TEAL.index)
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)
		return font
	}
	
	def createResultFont(XSSFWorkbook wb){
		
		XSSFFont font = wb.createFont()
		font.setColor(HSSFColor.BLACK.index)
		return font
	}
	
	def createLeftAlignStyle(XSSFWorkbook wb, XSSFFont resultFont){
		
		XSSFCellStyle leftAlignStyle = wb.createCellStyle()
		leftAlignStyle.setFont(resultFont)
		leftAlignStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT)
		return leftAlignStyle
	}
	
	def createRightAlignStyle(XSSFWorkbook wb, XSSFFont resultFont){
		
		XSSFCellStyle rightAlignStyle = wb.createCellStyle()
		rightAlignStyle.setFont(resultFont)
		rightAlignStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT)
		return rightAlignStyle
		
	}
	
	def createTitleStyle(XSSFWorkbook wb, XSSFFont titleFont){
		
		XSSFCellStyle titleStyle = wb.createCellStyle()
		titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index)
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER)
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN )
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN)
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN)
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN)
		titleStyle.setFont(titleFont)
		return titleStyle
	}
	
	def createDateFormatStyle(XSSFWorkbook wb){
		
		CreationHelper createHelper = wb.getCreationHelper()
		XSSFCellStyle cellDateStyle = wb.createCellStyle()
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyyy"))
		
		return cellDateStyle
	}
	
	def getResponseObject(){
		
		def utils = WebUtils.retrieveGrailsWebRequest()
		def response = utils.getCurrentResponse()
		return response
	}
	
	def addHeaderColumn(def headerRow, def includeDetail, def printActualCost, def isStdCostView, def isActualRateView, def hoursOnly, def cellNo, def row, def titleStyle){
		
		if(includeDetail){
			headerRow.addAll("Date")
		}
		
		headerRow.addAll("Hours")
		
		if(hoursOnly == false){
			if(isStdCostView){
				if(includeDetail) {
					headerRow.addAll("Std Rate")
				}
				headerRow.addAll("Std Cost","O/H","Total Cost")
			}
			
			if(isActualRateView){
				if(printActualCost){
					if(includeDetail){
						headerRow.addAll("Actual Rate")
					}
					headerRow.addAll("Actual Wage")
				}
			}
		}
		 
		headerRow.each{
			def cell = row.createCell(cellNo)
			cell.setCellValue(it)
			cell.setCellStyle(titleStyle)
			cellNo++
		 }
	}
	
	/**
	 * Export as Excel file for Labor History By COE
	 * @param coeLaborHistoryList
	 * @param params
	 * @param reportTotalMap
	 * @return
	 */
	def laborHistoryByCOE(coeLaborHistoryList, params, reportTotalMap){
		
		FileOutputStream out
		FileOutputStream os
		try {
			
			log.info "::START:::laborHistoryByCOE::::::::"+new Date()
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Labor History By COE");
	
			
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			//name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
			String sheetRef = sheet.getPackagePart().getPartName().getName();
	
			//save the template
			
			File tmpXls = FileUtils.createTempFile("projectByActivity", ".xlsx");
			os = new FileOutputStream("template.xlsx");
			wb.write(os);
			os.close();
	
			//Step 2. Generate XML file.
			File tmp = FileUtils.createTempFile("sheet", ".xml");
			Writer fw = new FileWriter(tmp)
			
			SpreadsheetWriter sw = new SpreadsheetWriter(fw)
			sw.beginSheet();
	
			def session = RequestContextHolder.currentRequestAttributes().getSession()
			
			def isActualRateView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name())
			def isStdCostView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name())
			
			def includeDetail = params.includeDetail
			def printActualCost = params.printActualCost
			def hoursOnly = session['logedInUser']?.hoursOnly
			
			
			def headerRow = []
	 	    headerRow.addAll("COE Code", "Project Name", "Project Code", "Customer Code", "Account Executive Code", "Activity", "Employee Name", "Hours")
		   
		   if(hoursOnly == false){
				if(isStdCostView){
				 	headerRow.addAll("Std Cost","O/H","Total Cost")
				}
				
				if(printActualCost){
					headerRow.addAll("Actual Cost")
				}
		   }
		   
		   def filterStyleInx = styles.get("filterStyle").getIndex()
		   sw.insertRow(1)
		   sw.createCell(1, "Filter By", filterStyleInx);
		   sw.createCell(2, "From Date: ", filterStyleInx);
		   sw.createCell(3, params?.fromDate, filterStyleInx);		   
		   sw.createCell(4, "To Date: ", filterStyleInx);
		   sw.createCell(5, params?.toDate, filterStyleInx);
		   sw.endRow()
		   
		   sw.insertRow(4)
		   int styleIndex = styles.get("header").getIndex();
		   
		   def cellNo = 0
		   headerRow?.each {
			   sw.createCell(cellNo++, it, styleIndex);
		   }
		   sw.endRow()
		   
		  
		   cellNo = 0
		   int rownum = 6
		  
		   coeLaborHistoryList?.each {laborHistoryCOEInstance ->
			   laborHistoryCOEInstance?.code = replaceSpecialCharacters(laborHistoryCOEInstance?.code)
			   laborHistoryCOEInstance?.activityByEmployeeReportDTO?.each{actualLaborInstance->
				   
				   actualLaborInstance?.projectName = replaceSpecialCharacters(actualLaborInstance?.projectName)
				   actualLaborInstance?.projectCode = replaceSpecialCharacters(actualLaborInstance?.projectCode)
				   actualLaborInstance?.customerCode = replaceSpecialCharacters(actualLaborInstance?.customerCode)
				   actualLaborInstance?.acctExecutiveCode = replaceSpecialCharacters(actualLaborInstance?.acctExecutiveCode)
				   
				   actualLaborInstance?.operationDetailDTOList?.each {operationDetailDTO ->
					   
					   operationDetailDTO?.laborActivityCode = replaceSpecialCharacters(operationDetailDTO?.laborActivityCode)
					   
					      operationDetailDTO?.employeeDetailList?.each { employeeDetailDTO ->
							  
							  employeeDetailDTO?.employeeName = replaceSpecialCharacters(employeeDetailDTO?.employeeName)
							  
							  sw.insertRow(rownum)
							  cellNo = 0
							   
							   sw.createCell(cellNo++, laborHistoryCOEInstance?.code?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.projectName ?:"", styles.get("leftAlignStyle").getIndex())
							  
							   sw.createCell(cellNo++, actualLaborInstance?.projectCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.customerCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.acctExecutiveCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, operationDetailDTO?.laborActivityCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeDetailDTO?.employeeName  ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeDetailDTO?.hours  ?:"", styles.get("rightAlignStyle").getIndex())
							   
							   if(hoursOnly == false){
								   
								   if(isStdCostView){
									     //STD Cost START
									   sw.createCell(cellNo++, employeeDetailDTO?.standardCost  ?:"", styles.get("rightAlignStyle").getIndex())
									   //STD Cost END
									   
									   // O/H START
									   sw.createCell(cellNo++, employeeDetailDTO?.standardOverheadCost  ?:"", styles.get("rightAlignStyle").getIndex())
									   // O/H ENd
									   
									   // Standard Total Cost START
									   sw.createCell(cellNo++, employeeDetailDTO?.standardTotalCost  ?:"", styles.get("rightAlignStyle").getIndex())
									   // Standard Total Cost END
								   }
								   
								   if(printActualCost){
								      // Actual Total Cost START
									   sw.createCell(cellNo++, employeeDetailDTO?.actualCost  ?:"", styles.get("rightAlignStyle").getIndex())
									   // Actual Total Cost END
								   }
							   }
							   sw.endRow()
							   rownum ++
						  }
				   }
			   }
		   }
			 
			
			rownum = rownum + 1
			sw.insertRow(rownum)
			
			cellNo = 0
			
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++, "Report Grand Total:", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			
			// HOURS Final Total START
			sw.createCell(cellNo++, reportTotalMap?.reportHoursTotal, styles.get("rightAlignStyle").getIndex())
			// HOURS Final Total END
	   
			if(hoursOnly == false){
		   
				if(isStdCostView){
					
					//STD Cost Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardCost, styles.get("rightAlignStyle").getIndex())
					//STD Cost Final Total END
	   
					// O/H Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
					 // O/H Final Total START
		
					// Total Employee Project START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardTotalCost, styles.get("rightAlignStyle").getIndex())
				 	// Total Final Total Employee END
				}
	   
				if(printActualCost){
				   // Actual Final Total Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportActualCost, styles.get("rightAlignStyle").getIndex())
				  // Actual Final Total Employee END
				}
			}
					
			sw.endRow()
			sw.endSheet()
			
			fw.close();
			
			
		   //Step 3. Substitute the template entry with the generated data
		   out = new FileOutputStream(tmpXls);
		   substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
		   out.close();
			   
		   log.info  ":::::::::laborHistoryByCOE:::::END:::::::::::"+new Date()
		   
		   return tmpXls?.getAbsolutePath()
	   
		} catch(Exception e) {
		   e.printStackTrace()
	   }finally{
		
			if(out){
				out.close()
			}
			
			if(os){
				os.close()
			}
		
		}
			 
	}
	
	/**
	 * Export as Excel file for Employee by Activity Report
	 * @param employeeByActivityReportList
	 * @param params
	 * @param reportTotalMap
	 * @return
	 */
	def employeeByActivty(employeeByActivityReportList, params, reportTotalMap){
		
	   FileOutputStream out
	   FileOutputStream os
	   try {
		   
		   log.info "::START:::employeeByActivty::::::::"+new Date()
		   XSSFWorkbook wb = new XSSFWorkbook();
		   XSSFSheet sheet = wb.createSheet("Employee By Activty");
   
		   
		   Map<String, XSSFCellStyle> styles = createStyles(wb);
		   //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
		   String sheetRef = sheet.getPackagePart().getPartName().getName();
   
		   //save the template
		   
		   File tmpXls = FileUtils.createTempFile("projectByActivity", ".xlsx");
		   os = new FileOutputStream("template.xlsx");
		   wb.write(os);
		   os.close();
   
		   //Step 2. Generate XML file.
		   File tmp = FileUtils.createTempFile("sheet", ".xml");
		   Writer fw = new FileWriter(tmp)
		   
		   
		   
		   SpreadsheetWriter sw = new SpreadsheetWriter(fw)
		   sw.beginSheet();
   
		   def session = RequestContextHolder.currentRequestAttributes().getSession()
		   
		   def isActualRateView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name())
		   def isStdCostView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name())
		   
		   def includeDetail = params.includeDetail
		   def printActualCost = params.printActualCost
		   def hoursOnly = session['logedInUser']?.hoursOnly
		   
		   
		   def headerRow = []
		   headerRow.addAll("Project Name","Project Code", "Employee Name", "Employee Code", "Activity")
		   
		   def cellNo = 0
		   if(includeDetail){
			  headerRow.addAll("Date")
		  }
		  
		  headerRow.addAll("Hours")
		  
		  if(hoursOnly == false){
			  if(isStdCostView){
				  if(includeDetail) {
					  headerRow.addAll("Std Rate")
				  }
				  headerRow.addAll("Std Cost","O/H","Total Cost")
			  }
			  
			  if(isActualRateView){
				  if(printActualCost){
					  if(includeDetail){
						  headerRow.addAll("Actual Rate")
					  }
					  headerRow.addAll("Actual Wage")
				  }
			  }
		  }
		  
		  def filterStyleInx = styles.get("filterStyle").getIndex()
		  sw.insertRow(1)
		  sw.createCell(1, "Filter By", filterStyleInx);
		  sw.createCell(2, "From Date: ", filterStyleInx);
		  sw.createCell(3, params?.fromDate, filterStyleInx);
		  sw.createCell(4, "To Date: ", filterStyleInx);
		  sw.createCell(5, params?.toDate, filterStyleInx);
		  sw.endRow()
		  
		  sw.insertRow(4);
		  int styleIndex = styles.get("header").getIndex();
		  
		   cellNo = 0
		  headerRow?.each {
			  sw.createCell(cellNo++, it, styleIndex);
		  }
		  sw.endRow()
		  int rownum = 6
		 
		   cellNo = 0
		   employeeByActivityReportList?.each {employeeByActivityReportDTO ->
			   
			   employeeByActivityReportDTO?.projectName = replaceSpecialCharacters(employeeByActivityReportDTO?.projectName)
			   employeeByActivityReportDTO?.projectCode = replaceSpecialCharacters(employeeByActivityReportDTO?.projectCode)
			   
			   employeeByActivityReportDTO?.groupByEmployeeList?.each{empGrpDTO->
				   
				   empGrpDTO?.employeeName = replaceSpecialCharacters(empGrpDTO?.employeeName)
				   empGrpDTO?.employeeCode = replaceSpecialCharacters(empGrpDTO?.employeeCode)
				   
				   
				   empGrpDTO?.groupByActivityList?.each {activityDTO ->
					   
					   activityDTO?.activityName = replaceSpecialCharacters(activityDTO?.activityName)
					   
					   if(includeDetail){
						   activityDTO?.employeeDetailList?.each { empDetailDTO ->
							   
							   empDetailDTO?.employeeCode = replaceSpecialCharacters(empDetailDTO?.employeeCode)
							   empDetailDTO?.laborActivityCode = replaceSpecialCharacters(empDetailDTO?.laborActivityCode)
							   
							   sw.insertRow(rownum)
							   cellNo = 0
							   
							   sw.createCell(cellNo++, employeeByActivityReportDTO?.projectName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeByActivityReportDTO?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							  
							   sw.createCell(cellNo++, empGrpDTO?.employeeName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, empDetailDTO?.employeeCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, empDetailDTO?.laborActivityCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, empDetailDTO?.transactionDate?:"", styles.get("date").getIndex())
							   
							   sw.createCell(cellNo++, empDetailDTO?.hours?:"", styles.get("rightAlignStyle").getIndex())
							   
							   if(hoursOnly == false){
								   
								   if(isStdCostView){
									   sw.createCell(cellNo++, empDetailDTO.standardRate?:"", styles.get("rightAlignStyle").getIndex())
									   
									   //STD Cost START
									   sw.createCell(cellNo++, empDetailDTO.standardCost?:"", styles.get("rightAlignStyle").getIndex())
									   //STD Cost END
									   
									   // O/H START
									   sw.createCell(cellNo++, empDetailDTO.standardOverheadCost?:"", styles.get("rightAlignStyle").getIndex())
									   // O/H ENd
									   
									   // Standard Total Cost START
									   sw.createCell(cellNo++, empDetailDTO.standardTotalCost?:"", styles.get("rightAlignStyle").getIndex())
									   // Standard Total Cost END
								   }
								   
								   if(isActualRateView){
									   if(printActualCost){
										   sw.createCell(cellNo++, empDetailDTO.actualRate?:"", styles.get("rightAlignStyle").getIndex())
										   
										   // Actual Total Cost START
										   sw.createCell(cellNo++, empDetailDTO.actualCost?:"", styles.get("rightAlignStyle").getIndex())
										   // Actual Total Cost END
									   }
								   }
								}
							   sw.endRow()
							   rownum ++
						   }
					   } else {
							   
							   sw.insertRow(rownum)
							   cellNo = 0
							   
							   sw.createCell(cellNo++, employeeByActivityReportDTO?.projectName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeByActivityReportDTO?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							  
							   sw.createCell(cellNo++, empGrpDTO?.employeeName ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, empGrpDTO?.employeeCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, activityDTO?.activityName ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   if(includeDetail){
								   sw.createCell(cellNo++, "", styles.get("leftAlignStyle").getIndex())
							   }
							   
							   // HOURS ACtivity START
							   sw.createCell(cellNo++, activityDTO?.hours , styles.get("rightAlignStyle").getIndex())
							   // HOURS ACtivity END
							   
							   if(hoursOnly == false){
								   
								   if(isStdCostView){
									   if(includeDetail){
										   sw.createCell(cellNo++, "" , styles.get("rightAlignStyle").getIndex())
									   }
									   
									   //STD Cost Activity START
									   sw.createCell(cellNo++,activityDTO?.standardCost , styles.get("rightAlignStyle").getIndex())
									   //STD Cost ACtivity END
									   
									   // O/H ACtivity START
									   sw.createCell(cellNo++,activityDTO?.standardOverheadCost , styles.get("rightAlignStyle").getIndex())
									   // O/H ACtivity START
										
										// Total Cost Activity START
										sw.createCell(cellNo++,activityDTO?.standardTotalCost , styles.get("rightAlignStyle").getIndex())
										// Total Cost Activity END
								   }
								
								   if(isActualRateView){
									   if(printActualCost){
										   if(includeDetail){
											   sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
										   }
											
										   // Actual Cost Activity START
										   sw.createCell(cellNo++,activityDTO?.actualCost , styles.get("rightAlignStyle").getIndex())
										   // Actual Cost Activity END
									   }
								   }
							   }
							   sw.endRow()
							   rownum++
						   }
					   }
				}
			   }
			
			rownum = rownum + 1
			sw.insertRow(rownum)
			cellNo = 0
			
			sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++,"Report Grand Total:", styles.get("rightAlignStyle").getIndex())
			sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
			
			if(includeDetail){
				sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
			}
		
			sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
			
			// HOURS Final Total START
			sw.createCell(cellNo++,reportTotalMap?.reportHoursTotal, styles.get("rightAlignStyle").getIndex())
			// HOURS Final Total END
	   
			if(hoursOnly == false){
		   
				if(isStdCostView){
					if(includeDetail){
						sw.createCell(cellNo++,"", styles.get("rightAlignStyle").getIndex())
					}
	   
					//STD Cost Final Total START
					sw.createCell(cellNo++,reportTotalMap?.reportStandardCost, styles.get("rightAlignStyle").getIndex())
					//STD Cost Final Total END
	   
					// O/H Final Total START
					sw.createCell(cellNo++,reportTotalMap?.reportStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
					 // O/H Final Total START
		
					// Total Employee Project START
					sw.createCell(cellNo++,reportTotalMap?.reportStandardTotalCost, styles.get("rightAlignStyle").getIndex())
				  	// Total Final Total Employee END
				}
	   
				if(isActualRateView){
						   
				   if(printActualCost){
							   
					   if(includeDetail){
						   sw.createCell(cellNo++, "" , styles.get("rightAlignStyle").getIndex())
						   
					   }
					
					   // Actual Final Total Final Total START
					   sw.createCell(cellNo++,reportTotalMap?.reportActualCost, styles.get("rightAlignStyle").getIndex())
						// Actual Final Total Employee END
				   }
				}
			}
			sw.endRow()
			sw.endSheet()
			
			fw.close();
			
		   //Step 3. Substitute the template entry with the generated data
		   out = new FileOutputStream(tmpXls);
		   substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
		   out.close();
			   
		   log.info  "::::::::::::::END::::employeeByActivty:::::::"+new Date()
		   
		   return tmpXls?.getAbsolutePath()
	   
		} catch(Exception e) {
		   e.printStackTrace()
	   }finally{
		
			if(out){
				out.close()
			}
			
			if(os){
				os.close()
			}
		
		}
	}
	
	/**
	 * Export as Excel file for Activity By Employee Report
	 * @param activityByEmployeeReportList
	 * @param params
	 * @param reportTotalMap
	 * @return
	 */
	def activityByEmployee(activityByEmployeeReportList, params, reportTotalMap){
		FileOutputStream out
		FileOutputStream os
	   try {
		   
		   log.info "::START:::::activityByEmployee::::::"+new Date()
		   XSSFWorkbook wb = new XSSFWorkbook();
		   XSSFSheet sheet = wb.createSheet("Activity By Employee");
   
		   
		   Map<String, XSSFCellStyle> styles = createStyles(wb);
		   //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
		   String sheetRef = sheet.getPackagePart().getPartName().getName();
   
		   //save the template
		   
		   File tmpXls = FileUtils.createTempFile("projectByActivity", ".xlsx");
		   os = new FileOutputStream("template.xlsx");
		   wb.write(os);
		   os.close();
   
		   //Step 2. Generate XML file.
		   File tmp = FileUtils.createTempFile("sheet", ".xml");
		   Writer fw = new FileWriter(tmp)
		   
		   
		   
		   SpreadsheetWriter sw = new SpreadsheetWriter(fw);
		   sw.beginSheet();
   
		   def session = RequestContextHolder.currentRequestAttributes().getSession()
		   
		   def isActualRateView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name())
		   def isStdCostView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name())
		   
		   def includeDetail = params.includeDetail
		   def printActualCost = params.printActualCost
		   def hoursOnly = session['logedInUser']?.hoursOnly
		   
		   
		   def headerRow = []
		   headerRow.addAll("Project Name","Project Code", "Customer Code", "Executive Code", "Activity", "Employee Name", "Employee Code")
		   
		   def cellNo = 0
			if(includeDetail){
			   headerRow.addAll("Date")
		   }
		   
		   headerRow.addAll("Hours")
		   
		   if(hoursOnly == false){
			   if(isStdCostView){
				   if(includeDetail) {
					   headerRow.addAll("Std Rate")
				   }
				   headerRow.addAll("Std Cost","O/H","Total Cost")
			   }
			   
			   if(isActualRateView){
				   if(printActualCost){
					   if(includeDetail){
						   headerRow.addAll("Actual Rate")
					   }
					   headerRow.addAll("Actual Wage")
				   }
			   }
		   }
		   
		   def filterStyleInx = styles.get("filterStyle").getIndex()
		   sw.insertRow(1)
		   sw.createCell(1, "Filter By", filterStyleInx);
		   sw.createCell(2, "From Date: ", filterStyleInx);
		   sw.createCell(3, params?.fromDate, filterStyleInx);
		   sw.createCell(4, "To Date: ", filterStyleInx);
		   sw.createCell(5, params?.toDate, filterStyleInx);
		   sw.endRow()
		   
		   sw.insertRow(4);
		   int styleIndex = styles.get("header").getIndex();
		   
			cellNo = 0
		   headerRow?.each {
			   sw.createCell(cellNo++, it, styleIndex);
		   }
		   sw.endRow()
		   int rownum = 6
		  
		   activityByEmployeeReportList?.each {actualLaborInstance ->
			   
			   actualLaborInstance?.projectName = replaceSpecialCharacters(actualLaborInstance?.projectName)
			   actualLaborInstance?.projectCode = replaceSpecialCharacters(actualLaborInstance?.projectCode)
			   actualLaborInstance?.customerCode = replaceSpecialCharacters(actualLaborInstance?.customerCode)
			   actualLaborInstance?.acctExecutiveCode = replaceSpecialCharacters(actualLaborInstance?.acctExecutiveCode)
			   
			   actualLaborInstance?.operationDetailDTOList?.each{operationDetailDTO->
				   
				   operationDetailDTO?.laborActivityCode = replaceSpecialCharacters(operationDetailDTO?.laborActivityCode)
				   
				   operationDetailDTO?.employeeDetailList?.each {employeeDetailDTO ->
					   
					   employeeDetailDTO?.employeeName = replaceSpecialCharacters(employeeDetailDTO?.employeeName)
					   employeeDetailDTO?.employeeCode = replaceSpecialCharacters(employeeDetailDTO?.employeeCode)
					   if(includeDetail){
						   employeeDetailDTO?.activityDetailList?.each { activityDetailDTO ->
							   
							   activityDetailDTO?.employeeCode = replaceSpecialCharacters(activityDetailDTO?.employeeCode)
							   
							   sw.insertRow(rownum)
							   cellNo = 0
							   
							   sw.createCell(cellNo++, actualLaborInstance?.projectName?:"", styles.get("leftAlignStyle").getIndex())  
							   
							   sw.createCell(cellNo++, actualLaborInstance?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.customerCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.acctExecutiveCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, operationDetailDTO?.laborActivityCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeDetailDTO?.employeeName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, activityDetailDTO?.employeeCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, activityDetailDTO.transactionDate?:"", styles.get("date").getIndex())
							 
							   sw.createCell(cellNo++, activityDetailDTO?.hours?:"", styles.get("rightAlignStyle").getIndex())
							   
							   if(hoursOnly == false){
								   if(isStdCostView){
									   
									   sw.createCell(cellNo++, activityDetailDTO.standardRate ?:"", styles.get("rightAlignStyle").getIndex())
									   
									   //STD Cost START
									   sw.createCell(cellNo++, activityDetailDTO.standardCost ?:"", styles.get("rightAlignStyle").getIndex())
									   //STD Cost END
									   
									   // O/H START
									   sw.createCell(cellNo++, activityDetailDTO.standardOverheadCost ?:"", styles.get("rightAlignStyle").getIndex())
									   // O/H ENd
									   
									   // Standard Total Cost START
									   sw.createCell(cellNo++, activityDetailDTO.standardTotalCost ?:"", styles.get("rightAlignStyle").getIndex())
									   // Standard Total Cost END
								   }
								   
								   if(isActualRateView){
									   if(printActualCost){
										   sw.createCell(cellNo++, activityDetailDTO.actualRate ?:"", styles.get("rightAlignStyle").getIndex())
										   
										   // Actual Total Cost START
										   sw.createCell(cellNo++, activityDetailDTO.actualCost ?:"", styles.get("rightAlignStyle").getIndex())
										   // Actual Total Cost END
									   }
								   }
								}
							   sw.endRow()
							   rownum ++
						   }
					   } else {
							   
							   sw.insertRow(rownum)
							   cellNo = 0
							   
							   sw.createCell(cellNo++, actualLaborInstance?.projectName?:"", styles.get("leftAlignStyle").getIndex())  
							   
							   sw.createCell(cellNo++, actualLaborInstance?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.customerCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actualLaborInstance?.acctExecutiveCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, operationDetailDTO?.laborActivityCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeDetailDTO?.employeeName ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, employeeDetailDTO?.employeeCode ?:"", styles.get("leftAlignStyle").getIndex())
							   
							   if(includeDetail){
								   sw.createCell(cellNo++, "", styles.get("leftAlignStyle").getIndex())
								}
							   
							   // HOURS ACtivity START
							   sw.createCell(cellNo++, employeeDetailDTO?.hours, styles.get("rightAlignStyle").getIndex())
							   // HOURS ACtivity END
							   
							   if(hoursOnly == false){
								   
								   if(isStdCostView){
									   if(includeDetail){
										   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
									   }
									   
									   //STD Cost Activity START
									   sw.createCell(cellNo++, employeeDetailDTO?.standardCost, styles.get("rightAlignStyle").getIndex())
									   //STD Cost ACtivity END
									   
									   // O/H ACtivity START
									   sw.createCell(cellNo++, employeeDetailDTO?.standardOverheadCost, styles.get("rightAlignStyle").getIndex())
									   // O/H ACtivity START
										
										// Total Cost Activity START
									   sw.createCell(cellNo++, employeeDetailDTO?.standardTotalCost, styles.get("rightAlignStyle").getIndex())
										// Total Cost Activity END
								   }
								
								   if(isActualRateView){
									   if(printActualCost){
										   if(includeDetail){
											   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
										   }
											
										   // Actual Cost Activity START
										   sw.createCell(cellNo++, employeeDetailDTO?.actualCost, styles.get("rightAlignStyle").getIndex())
										   // Actual Cost Activity END
									   }
								   }
							   }
							   sw.endRow()
							   rownum++
						   }
					   }
			   }
		   }
			
		   rownum = rownum + 1
		  
		   sw.insertRow(rownum)
		   cellNo = 0
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "Report Grand Total:", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			
		   if(includeDetail){
			   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   }
		
			sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			// HOURS Final Total START
			sw.createCell(cellNo++, reportTotalMap?.reportHoursTotal , styles.get("rightAlignStyle").getIndex())
			// HOURS Final Total END
	   
			if(hoursOnly == false){
		   
				if(isStdCostView){
					if(includeDetail){
						sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
					}
	   
					//STD Cost Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardCost, styles.get("rightAlignStyle").getIndex())
					//STD Cost Final Total END
	   
					// O/H Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
					 // O/H Final Total START
		
					// Total Employee Project START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardTotalCost, styles.get("rightAlignStyle").getIndex())
					// Total Final Total Employee END
				}
	   
				if(isActualRateView){
						   
				   if(printActualCost){
							   
					   if(includeDetail){
						   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
					   }
					
					   // Actual Final Total Final Total START
					   sw.createCell(cellNo++, reportTotalMap?.reportActualCost, styles.get("rightAlignStyle").getIndex())
						// Actual Final Total Employee END
				   }
				}
			}
		
			sw.endRow()
			sw.endSheet()
			
			fw.close();
			
		   //Step 3. Substitute the template entry with the generated data
		   out = new FileOutputStream(tmpXls);
		   substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
		   out.close();
			   
		   log.info  "::::::::::::::END::::::activityByEmployee:::::"+new Date()
		   
		   return tmpXls?.getAbsolutePath()
	   
		} catch(Exception e) {
		   e.printStackTrace()
	   }finally{
		
			if(out){
				out.close()
			}
			
			if(os){
				os.close()
			}
		
		}
	}
	
	def replaceSpecialCharacters(def value){
		
		if(value){
			if(value.contains("&")){
				value = value.replaceAll("&","&amp;")
			}
		}else {
			value = ""
		}
		return value
		
	}
	/**
	 * Export as Excel file for Activity By Project Report
	 * @param employeeByActivityByProjectReportList
	 * @param params
	 * @param reportTotalMap
	 * @return
	 */
	def activityByProject(employeeByActivityByProjectReportList, params, reportTotalMap){
	
		FileOutputStream out
		FileOutputStream os
	   try {
		   
		   log.info "::START::::::activityByProject:::::"+new Date()
		   XSSFWorkbook wb = new XSSFWorkbook();
		   XSSFSheet sheet = wb.createSheet("Activity By Project");
   
		   
		   Map<String, XSSFCellStyle> styles = createStyles(wb);
		   //name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
		   String sheetRef = sheet.getPackagePart().getPartName().getName();
   
		   //save the template
		   
		   File tmpXls = FileUtils.createTempFile("projectByActivity", ".xlsx");
		   os = new FileOutputStream("template.xlsx");
		   wb.write(os);
		   os.close();
   
		   //Step 2. Generate XML file.
		   File tmp = FileUtils.createTempFile("sheet", ".xml");
		   Writer fw = new FileWriter(tmp)
		   
		   
		   
		   SpreadsheetWriter sw = new SpreadsheetWriter(fw)
		   sw.beginSheet();
   
		   def session = RequestContextHolder.currentRequestAttributes().getSession()
		   
		   def isActualRateView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name())
		   def isStdCostView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name())
		   
		   def includeDetail = params.includeDetail
		   def printActualCost = params.printActualCost
		   def hoursOnly = session['logedInUser']?.hoursOnly
		   
		   
		   def headerRow = []
		   def headerRowDesc = []
		   headerRow.addAll("Employee Name","Employee Code", "Supervisor","Activity", "Project Name","Project Code")
		   
		   def cellNo = 0
			if(includeDetail){
			   headerRow.addAll("Date")
		   }
		   
		   headerRow.addAll("Hours")
		   
		   if(hoursOnly == false){
			   if(isStdCostView){
				   if(includeDetail) {
					   headerRow.addAll("Std Rate")
				   }
				   headerRow.addAll("Std Cost","O/H","Total Cost")
			   }
			   
			   if(isActualRateView){
				   if(printActualCost){
					   if(includeDetail){
						   headerRow.addAll("Actual Rate")
					   }
					   headerRow.addAll("Actual Wage")
				   }
			   }
		   }
		   
		   def filterStyleInx = styles.get("filterStyle").getIndex()
		   sw.insertRow(1)
		   sw.createCell(1, "Filter By", filterStyleInx);
		   sw.createCell(2, "From Date: ", filterStyleInx);
		   sw.createCell(3, params?.fromDate, filterStyleInx);
		   sw.createCell(4, "To Date: ", filterStyleInx);
		   sw.createCell(5, params?.toDate, filterStyleInx);
		   sw.endRow()
		   
		   sw.insertRow(4);
		   int styleIndex = styles.get("header").getIndex();
		   
			cellNo = 0
		   headerRow?.each {
			   sw.createCell(cellNo++, it, styleIndex);
		   }
		   sw.endRow();
   
		   //write data rows
		   
		   def rownum = 6
		   employeeByActivityByProjectReportList?.each {employeeByProjectByActivityReportDTO ->
			   
			  employeeByProjectByActivityReportDTO.employeeName =  replaceSpecialCharacters(employeeByProjectByActivityReportDTO.employeeName)
			  employeeByProjectByActivityReportDTO.employeeCode =  replaceSpecialCharacters(employeeByProjectByActivityReportDTO.employeeCode)
			  employeeByProjectByActivityReportDTO.supervisorCode =  replaceSpecialCharacters(employeeByProjectByActivityReportDTO.supervisorCode)
			   
			   employeeByProjectByActivityReportDTO?.groupByActivityList?.each{grpByActivity->
				   
				   grpByActivity?.activityName = replaceSpecialCharacters(grpByActivity?.activityName)
				    
				   grpByActivity?.groupByProjectList?.each {grpByProject ->
					   
					   grpByProject?.projectName = replaceSpecialCharacters(grpByProject?.projectName)
					   grpByProject?.projectCode  = replaceSpecialCharacters(grpByProject?.projectCode)
					   
					   if(includeDetail){
						   grpByProject?.activityDetailList?.each { actDtl ->
							   
							   actDtl?.laborActivityCode = replaceSpecialCharacters(actDtl?.laborActivityCode)
							   actDtl?.projectCode = replaceSpecialCharacters(actDtl?.projectCode)
							   
							   sw.insertRow(rownum)
							   cellNo = 0
							   
							   sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeName?:"", styles.get("leftAlignStyle").getIndex())//employeeName
							   
							   sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeCode?:"", styles.get("leftAlignStyle").getIndex())//Employee Code
							   
							   sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.supervisorCode?:"", styles.get("leftAlignStyle").getIndex())//supervisor code
							   
							   sw.createCell(cellNo++, actDtl?.laborActivityCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, grpByProject?.projectName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, actDtl.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							   if(includeDetail){
								   sw.createCell(cellNo++, actDtl.transactionDate?:"", styles.get("date").getIndex())
								}
							   
							   //Hours START
							   sw.createCell(cellNo++, actDtl.hours?:"", styles.get("rightAlignStyle").getIndex())
							   //Hours END
							   
							   if(hoursOnly == false){
								   if(isStdCostView){
									   sw.createCell(cellNo++, actDtl.standardRate?:"", styles.get("rightAlignStyle").getIndex())
									   
									   //STD Cost START
									   sw.createCell(cellNo++, actDtl.standardCost?:"", styles.get("rightAlignStyle").getIndex())
									   //STD Cost END
									   
									   // O/H START
									   sw.createCell(cellNo++, actDtl.standardOverheadCost?:"", styles.get("rightAlignStyle").getIndex())
									   // O/H ENd
									   
									   // Standard Total Cost START
									   sw.createCell(cellNo++, actDtl.standardTotalCost?:"", styles.get("rightAlignStyle").getIndex())
									   // Standard Total Cost END
								   }
								   
								   if(isActualRateView){
									   if(printActualCost){
										   sw.createCell(cellNo++, actDtl.actualRate?:"", styles.get("rightAlignStyle").getIndex())
										   
										   // Actual Total Cost START
										   sw.createCell(cellNo++, actDtl.actualCost?:"", styles.get("rightAlignStyle").getIndex())
										   // Actual Total Cost END
									   }
								   }
								}
							   sw.endRow()
							   rownum ++
						   }
					   } else {
							   
							   sw.insertRow(rownum)
							   cellNo = 0
							   
							   sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeName?:"", styles.get("leftAlignStyle").getIndex()) //employeeName
							   
							   sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeCode?:"", styles.get("leftAlignStyle").getIndex())//Employee Code
							   
							   sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.supervisorCode?:"", styles.get("leftAlignStyle").getIndex())//supervisor code
							   
							   sw.createCell(cellNo++, grpByActivity?.activityName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, grpByProject?.projectName?:"", styles.get("leftAlignStyle").getIndex())
							   
							   sw.createCell(cellNo++, grpByProject?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							   
							 
							   
							   if(includeDetail){
								   sw.createCell(cellNo++, "", styles.get("leftAlignStyle").getIndex())
							   }
							   
							   // HOURS ACtivity START
							   sw.createCell(cellNo++, grpByProject?.projectHours, styles.get("rightAlignStyle").getIndex())
							   // HOURS ACtivity END
							   
							   if(hoursOnly == false){
								   
								   if(isStdCostView){
									   
									   if(includeDetail){
										   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
									   }
									   
									   //STD Cost Activity START
									   sw.createCell(cellNo++, grpByProject?.projectStandardCost, styles.get("rightAlignStyle").getIndex())
									   //STD Cost ACtivity END
									   
									   // O/H ACtivity START
										sw.createCell(cellNo++, grpByProject?.projectStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
									   // O/H ACtivity START
										
										// Total Cost Activity START
										sw.createCell(cellNo++, grpByProject?.projectStandardTotalCost, styles.get("rightAlignStyle").getIndex())
										// Total Cost Activity END
								   }
								
								   if(isActualRateView){
									   if(printActualCost){
										   if(includeDetail){
											   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
										   }
											
										   // Actual Cost Activity START
										   sw.createCell(cellNo++, grpByProject?.projectActualCost, styles.get("rightAlignStyle").getIndex())
										   // Actual Cost Activity END
									   }
								   }
							   }
							   sw.endRow()
							   rownum++
					   }
				   }
			   }
		   }
			
		   rownum = rownum + 1
		   sw.insertRow(rownum)
			
		   cellNo = 0
		   
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			
		   sw.createCell(cellNo++, "Report Grand Total:", styles.get("leftAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   
		   if(includeDetail){
				sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			}
		
			 // HOURS Final Total START
			sw.createCell(cellNo++, reportTotalMap?.reportHoursTotal, styles.get("rightAlignStyle").getIndex())
			// HOURS Final Total END
	   
			if(hoursOnly == false){
		   
				if(isStdCostView){
					
					if(includeDetail){
						sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
					}
	   
					//STD Cost Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardCost, styles.get("rightAlignStyle").getIndex())
					//STD Cost Final Total END
	   
					// O/H Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
					// O/H Final Total START
		
					// Total Employee Project START
					sw.createCell(cellNo++, reportTotalMap?.reportStandardTotalCost, styles.get("rightAlignStyle").getIndex())
				  	// Total Final Total Employee END
				}
	   
				if(isActualRateView){
						   
				   if(printActualCost){
							   
					   if(includeDetail){
						   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
					   }
					
					   // Actual Final Total Final Total START
					   sw.createCell(cellNo++, reportTotalMap?.reportActualCost, styles.get("rightAlignStyle").getIndex())
					  // Actual Final Total Employee END
				   }
				}
			}
		
		   sw.endRow()
		   sw.endSheet()
		   
		   fw.close();
		   
		   //Step 3. Substitute the template entry with the generated data
		   out = new FileOutputStream(tmpXls);
		   substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
		   out.close();
			   
		   log.info  "::::::::::::::END:::::activityByProject::::::"+new Date()
		   
		   return tmpXls?.getAbsolutePath()
	   
		} catch(Exception e) {
		   e.printStackTrace()
	   }finally{
		
			if(out){
				out.close()
			}
			
			if(os){
				os.close()
			}
		
		}
	}
	
	/**
	 * Export as Excel file for Project by Activity Report
	 * @param employeeByProjectReportList
	 * @param params
	 * @param reportTotalMap
	 * @return
	 */
	
	def projectByActivity(employeeByProjectReportList, params, reportTotalMap){
		 
		FileOutputStream out
		FileOutputStream os
		try {
			
			log.info "::START:::projectByActivity::::::::"+new Date()
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("Project By Activity");
	
			
			Map<String, XSSFCellStyle> styles = createStyles(wb);
			//name of the zip entry holding sheet data, e.g. /xl/worksheets/sheet1.xml
			String sheetRef = sheet.getPackagePart().getPartName().getName();
	
			//save the template
			
			File tmpXls = FileUtils.createTempFile("projectByActivity", ".xlsx");
			os = new FileOutputStream("template.xlsx");
			wb.write(os);
			os.close();
	
			//Step 2. Generate XML file.
			File tmp = FileUtils.createTempFile("sheet", ".xml");
			Writer fw = new FileWriter(tmp);
			generate(fw, styles, employeeByProjectReportList, params, reportTotalMap);
			fw.close()
	
			//Step 3. Substitute the template entry with the generated data
			out = new FileOutputStream(tmpXls);
			substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
			out.close();
				
			log.info  "::::::::::::::END::::projectByActivity:::::::"+new Date()
			
			return tmpXls?.getAbsolutePath()
		} catch(Exception e) {
			e.printStackTrace()
		}finally{
		
			if(out){
				out.close()
			}
			
			if(os){
				os.close()
			}
		
		}
	}
	
	
	
	
	
	
	
	/**
	 * Create a library of cell styles.
	 */
	private static Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb){
		Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
				
		XSSFFont titleFont = wb.createFont()
		titleFont.setColor(HSSFColor.DARK_TEAL.index)
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)
		
		XSSFFont resultFont = wb.createFont()
		resultFont.setColor(HSSFColor.BLACK.index)
		
		CreationHelper createHelper = wb.getCreationHelper()
		XSSFCellStyle cellDateStyle = wb.createCellStyle()
		cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyyy"))
		styles.put("date", cellDateStyle)
		
		XSSFCellStyle titleStyle = wb.createCellStyle()
		titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index)
		titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER)
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN )
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN)
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN)
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN)
		titleStyle.setFont(titleFont)
		
		styles.put("header", titleStyle);
		
		
		
		XSSFCellStyle leftAlignStyle = wb.createCellStyle()
		
		leftAlignStyle.setFont(resultFont)
		leftAlignStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT)
		 styles.put("leftAlignStyle", leftAlignStyle);

		 
		 XSSFCellStyle rightAlignStyle = wb.createCellStyle()
		 rightAlignStyle.setFont(resultFont)
		 rightAlignStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT)
		 styles.put("rightAlignStyle", rightAlignStyle);
		 
		 XSSFCellStyle filterStyle = wb.createCellStyle()
		 filterStyle.setFillForegroundColor(HSSFColor.WHITE.index)
		 filterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
		 filterStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN )
		 filterStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN)
		 filterStyle.setBorderRight(HSSFCellStyle.BORDER_THIN)
		 filterStyle.setBorderTop(HSSFCellStyle.BORDER_THIN)
		 filterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER)
		 filterStyle.setFont(titleFont)
		 
		 
		 styles.put("filterStyle", filterStyle);
		return styles;
	}

	def generate(Writer out, Map<String, XSSFCellStyle> styles, employeeByProjectReportList, params, reportTotalMap) throws Exception {

		SpreadsheetWriter sw = new SpreadsheetWriter(out);
		sw.beginSheet();

		def session = RequestContextHolder.currentRequestAttributes().getSession()
		
		def isActualRateView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_ACTUAL_RATE.name())
		def isStdCostView = SecurityUtils.subject.isPermitted(PermissionType.PERMISSION_CAN_VIEW_STANDARD_COST.name())
		
		def includeDetail = params.includeDetail
		def printActualCost = params.printActualCost
		def hoursOnly = session['logedInUser']?.hoursOnly
		
		
		def headerRow = []
		def headerRowDesc = []
		headerRow.addAll("Employee Name","Employee Code", "Supervisor","Project Name","Project Code","Activity")
		
		def cellNo = 0
	 	if(includeDetail){
			headerRow.addAll("Date")
		}
		
		headerRow.addAll("Hours")
		
		if(hoursOnly == false){
			if(isStdCostView){
				if(includeDetail) {
					headerRow.addAll("Std Rate")
				}
				headerRow.addAll("Std Cost","O/H","Total Cost")
			}
			
			if(isActualRateView){
				if(printActualCost){
					if(includeDetail){
						headerRow.addAll("Actual Rate")
					}
					headerRow.addAll("Actual Wage")
				}
			}
		}
		
		def filterStyleInx = styles.get("filterStyle").getIndex()
		sw.insertRow(1)
		sw.createCell(1, "Filter By", filterStyleInx);
		sw.createCell(2, "From Date: ", filterStyleInx);
		sw.createCell(3, params?.fromDate, filterStyleInx);
		sw.createCell(4, "To Date: ", filterStyleInx);
		sw.createCell(5, params?.toDate, filterStyleInx);
		sw.endRow()
		
		sw.insertRow(4);
		int styleIndex = styles.get("header").getIndex();
		
		 cellNo = 0
		headerRow?.each {
			sw.createCell(cellNo++, it, styleIndex);
		}
		sw.endRow();

		//write data rows
		
		def rownum = 6
		employeeByProjectReportList?.each {employeeByProjectByActivityReportDTO ->
			
			
			employeeByProjectByActivityReportDTO.employeeName = replaceSpecialCharacters(employeeByProjectByActivityReportDTO.employeeName)
			employeeByProjectByActivityReportDTO.employeeCode = replaceSpecialCharacters(employeeByProjectByActivityReportDTO.employeeCode)
			employeeByProjectByActivityReportDTO.supervisorCode = replaceSpecialCharacters(employeeByProjectByActivityReportDTO.supervisorCode)
			
			employeeByProjectByActivityReportDTO?.groupByProjectList?.each{prjGrpByEmpDTO->
				
				prjGrpByEmpDTO?.projectName = replaceSpecialCharacters(prjGrpByEmpDTO?.projectName)
				prjGrpByEmpDTO?.projectCode = replaceSpecialCharacters(prjGrpByEmpDTO?.projectCode)
				
				prjGrpByEmpDTO?.groupByActivityList?.each {groupByActivity ->
					
					groupByActivity?.activityName = replaceSpecialCharacters(groupByActivity?.activityName)
					
					if(includeDetail){
						groupByActivity?.projectDetailList?.each { prjDtl ->
							
							prjDtl.laborActivityCode = replaceSpecialCharacters(prjDtl.laborActivityCode)
							
							sw.insertRow(rownum);
							cellNo = 0
							
							sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeName?:"", styles.get("leftAlignStyle").getIndex());//employeeName
							
							sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeCode?:"", styles.get("leftAlignStyle").getIndex()); //Employee Code
							
							sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.supervisorCode?:"", styles.get("leftAlignStyle").getIndex());//supervisor code
							
							sw.createCell(cellNo++, prjGrpByEmpDTO?.projectName?:"", styles.get("leftAlignStyle").getIndex())
							
							sw.createCell(cellNo++, prjGrpByEmpDTO?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							
							sw.createCell(cellNo++, prjDtl.laborActivityCode?:"", styles.get("leftAlignStyle").getIndex())
							
							if(includeDetail){
								
								if(prjDtl.transactionDate){
									sw.createCell(cellNo++, prjDtl.transactionDate?:"", styles.get("date").getIndex())
								}else{
									sw.createCell(cellNo++, prjDtl.transactionDate?:"")
								}
							}
							
							//Hours START
							sw.createCell(cellNo++, prjDtl.hours, styles.get("rightAlignStyle").getIndex())
							//Hours END
							
							if(hoursOnly == false){
								
								if(isStdCostView){
									
									sw.createCell(cellNo++, prjDtl.standardRate, styles.get("rightAlignStyle").getIndex())
									
									//STD Cost START
									sw.createCell(cellNo++, prjDtl.standardCost, styles.get("rightAlignStyle").getIndex())
									//STD Cost END
									
									// O/H START
									sw.createCell(cellNo++, prjDtl.standardOverheadCost, styles.get("rightAlignStyle").getIndex())
									// O/H ENd
									
									// Standard Total Cost START
									sw.createCell(cellNo++, prjDtl.standardTotalCost, styles.get("rightAlignStyle").getIndex())
									// Standard Total Cost END
								}
								
								if(isActualRateView){
									if(printActualCost){
										sw.createCell(cellNo++, prjDtl.actualRate, styles.get("rightAlignStyle").getIndex())
										
										// Actual Total Cost START
										sw.createCell(cellNo++, prjDtl.actualCost, styles.get("rightAlignStyle").getIndex())
										// Actual Total Cost END
									}
								}
							}
							sw.endRow()
							rownum ++
						}
					} else {
							
							sw.insertRow(rownum)
							cellNo = 0
							
							sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeName?:"", styles.get("leftAlignStyle").getIndex())//employeeName
							
							sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.employeeCode?:"", styles.get("leftAlignStyle").getIndex())//Employee Code
							
							sw.createCell(cellNo++, employeeByProjectByActivityReportDTO.supervisorCode?:"", styles.get("leftAlignStyle").getIndex())//supervisor code
							
							sw.createCell(cellNo++, prjGrpByEmpDTO?.projectName?:"", styles.get("leftAlignStyle").getIndex())
							
							sw.createCell(cellNo++, prjGrpByEmpDTO?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
							
							sw.createCell(cellNo++, groupByActivity?.activityName?:"", styles.get("leftAlignStyle").getIndex())
							
							if(includeDetail){
								sw.createCell(cellNo++, "", styles.get("leftAlignStyle").getIndex())
							}
							
							// HOURS ACtivity START
							sw.createCell(cellNo++, groupByActivity?.activityHours, styles.get("rightAlignStyle").getIndex())
							// HOURS ACtivity END
							
							if(hoursOnly == false){
								
								if(isStdCostView){
									
									if(includeDetail){
										sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
									}
									
									//STD Cost Activity START
									sw.createCell(cellNo++, groupByActivity?.activityStandardCost, styles.get("rightAlignStyle").getIndex())
									//STD Cost ACtivity END
									
									// O/H ACtivity START
									sw.createCell(cellNo++, groupByActivity?.activityStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
									// O/H ACtivity START
									 
									 // Total Cost Activity START
									sw.createCell(cellNo++, groupByActivity?.activityStandardTotalCost, styles.get("rightAlignStyle").getIndex())
									 // Total Cost Activity END
								}
							 
								if(isActualRateView){
									
									if(printActualCost){
										
										if(includeDetail){
											sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
										}
										 
										// Actual Cost Activity START
										sw.createCell(cellNo++, groupByActivity?.activityActualCost, styles.get("rightAlignStyle").getIndex())
										// Actual Cost Activity END
									}
								}
							}
							sw.endRow()
							rownum++
					}
				}
			}
		 }
		 rownum = rownum + 1
		 sw.insertRow(rownum)
		 cellNo = 0
		 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		 sw.createCell(cellNo++, "Report Total", styles.get("leftAlignStyle").getIndex())
		 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		 if(includeDetail){
			 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		 }
	 
  
		 // HOURS Final Total START
		 sw.createCell(cellNo++, reportTotalMap?.reportHoursTotal, styles.get("rightAlignStyle").getIndex())
		 // HOURS Final Total END
	
		 if(hoursOnly == false){
		
			 if(isStdCostView){
				 if(includeDetail){
					 sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
				 }
	
				 //STD Cost Final Total START
				 sw.createCell(cellNo++, reportTotalMap?.reportStandardCost, styles.get("rightAlignStyle").getIndex())
				 //STD Cost Final Total END
	
				 // O/H Final Total START
				 sw.createCell(cellNo++, reportTotalMap?.reportStandardOverheadCost, styles.get("rightAlignStyle").getIndex())
				  // O/H Final Total START
	 
				 // Total Employee Project START
				 sw.createCell(cellNo++, reportTotalMap?.reportStandardTotalCost, styles.get("rightAlignStyle").getIndex())
				 // Total Final Total Employee END
			 }
	
			 if(isActualRateView){
						
				if(printActualCost){
							
					if(includeDetail){
						sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
					}
				 
					// Actual Final Total Final Total START
					sw.createCell(cellNo++, reportTotalMap?.reportActualCost, styles.get("rightAlignStyle").getIndex())
					 // Actual Final Total Employee END
				}
			 }
		 }
		 sw.endRow()
		sw.endSheet()
	}
	 	 
	/**
	 * Export as Excel file for Project By Expense Report
	 * @param projectByExpenseList
	 * @param params
	 * @param reportTotalMap
	 * @return
	 */
	def projectByExpense(projectByExpenseList, params, reportTotalMap){
		FileOutputStream out
		FileOutputStream os
	   try {
		   
		   log.info "::START:::::projectByExpense::::::"+new Date()
		   XSSFWorkbook wb = new XSSFWorkbook();
		   XSSFSheet sheet = wb.createSheet("Project Expenses by Expense");
		   
		   Map<String, XSSFCellStyle> styles = createStyles(wb);
		   String sheetRef = sheet.getPackagePart().getPartName().getName();
		   
		   File tmpXls = FileUtils.createTempFile("projectExpensesByExpense", ".xlsx");
		   os = new FileOutputStream("template.xlsx");
		   wb.write(os);
		   os.close();
   
		   //Step 2. Generate XML file.
		   File tmp = FileUtils.createTempFile("sheet", ".xml");
		   Writer fw = new FileWriter(tmp)
		   
		   SpreadsheetWriter sw = new SpreadsheetWriter(fw);
		   sw.beginSheet();
   	   		   
		   def includeDetail = params.includeDetail		   
		   
		   def headerRow = []
		   if(includeDetail) {
			   headerRow.addAll("Project Name", "Project Code", "Customer Code", "Executive Code", "Expense Code", "Date", "Vendor Id", "Vendor Name", "GL Account", "Invoice", "Amount")
		   } else {
		   	headerRow.addAll("Project Name", "Project Code", "Customer Code", "Executive Code", "Expense Code", "Amount")
		   }
		   def cellNo = 0
					   
		   def filterStyleInx = styles.get("filterStyle").getIndex()
		   sw.insertRow(1)
		   sw.createCell(1, "Filter By", filterStyleInx);
		   sw.createCell(2, "From Date: ", filterStyleInx);
		   sw.createCell(3, params?.fromDate, filterStyleInx);
		   sw.createCell(4, "To Date: ", filterStyleInx);
		   sw.createCell(5, params?.toDate, filterStyleInx);
		   sw.endRow()
		   
		   sw.insertRow(4);
		   int styleIndex = styles.get("header").getIndex();
		   
			cellNo = 0
		   headerRow?.each {
			   sw.createCell(cellNo++, it, styleIndex);
		   }
		   sw.endRow()
		   
		   int rownum = 6
		  
		   projectByExpenseList?.each {projectExpenseInstance ->
			   
			   projectExpenseInstance?.projectName = replaceSpecialCharacters(projectExpenseInstance?.projectName)
			   projectExpenseInstance?.projectCode = replaceSpecialCharacters(projectExpenseInstance?.projectCode)
			   projectExpenseInstance?.customerCode = replaceSpecialCharacters(projectExpenseInstance?.customerCode)
			   projectExpenseInstance?.acctExecutiveCode = replaceSpecialCharacters(projectExpenseInstance?.acctExecutiveCode)
			   
			   projectExpenseInstance?.expenseDetailDTOList?.each{expenseCodeInst->
				   
				 expenseCodeInst?.actualExpenseDetailDTOList?.each{ expenseCodeDTO ->
				   					   
				   expenseCodeDTO?.expenseCode = replaceSpecialCharacters(expenseCodeDTO?.expenseCode)
				   expenseCodeDTO?.vendorName = replaceSpecialCharacters(expenseCodeDTO?.vendorName)
					   
				   if(includeDetail) {							   
					   sw.insertRow(rownum)
					   cellNo = 0					  
							   
					   sw.createCell(cellNo++, projectExpenseInstance?.projectName?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++, projectExpenseInstance?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++, projectExpenseInstance?.customerCode?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++, projectExpenseInstance?.acctExecutiveCode?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++,  expenseCodeDTO?.expenseCode?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++, expenseCodeDTO?.postedDate?:"", styles.get("date").getIndex())
					   sw.createCell(cellNo++, expenseCodeDTO?.vendorId?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++, expenseCodeDTO.vendorName?:"", styles.get("leftAlignStyle").getIndex())
					   sw.createCell(cellNo++, expenseCodeDTO?.glAmount?:"", styles.get("rightAlignStyle").getIndex())
					   sw.createCell(cellNo++, expenseCodeDTO?.invoice?:"", styles.get("rightAlignStyle").getIndex())
					   sw.createCell(cellNo++, expenseCodeDTO?.amount?:"", styles.get("rightAlignStyle").getIndex())
							   						   
					   sw.endRow()
					   rownum ++						   
				   } 
				 }
				 if(!includeDetail) {
					 sw.insertRow(rownum)
					 cellNo = 0
					 sw.createCell(cellNo++, projectExpenseInstance?.projectName?:"", styles.get("leftAlignStyle").getIndex())
					 sw.createCell(cellNo++, projectExpenseInstance?.projectCode?:"", styles.get("leftAlignStyle").getIndex())
					 sw.createCell(cellNo++, projectExpenseInstance?.customerCode?:"", styles.get("leftAlignStyle").getIndex())
					 sw.createCell(cellNo++, projectExpenseInstance?.acctExecutiveCode?:"", styles.get("leftAlignStyle").getIndex())
					 sw.createCell(cellNo++,  expenseCodeInst?.expenseCode?:"", styles.get("leftAlignStyle").getIndex())
					 sw.createCell(cellNo++, expenseCodeInst?.amount?:"", styles.get("rightAlignStyle").getIndex())
					 
					 sw.endRow()
					 rownum++
				 }
			  }
		   }
			
		   rownum = rownum + 1
		  
		   sw.insertRow(rownum)
		   cellNo = 0
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "Report Grand Total:", styles.get("rightAlignStyle").getIndex())
		   
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   if(includeDetail) {
			   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
			   sw.createCell(cellNo++, "", styles.get("rightAlignStyle").getIndex())
		   }
		   sw.createCell(cellNo++, reportTotalMap?.reportActualTotalAmount , styles.get("rightAlignStyle").getIndex())
					
			sw.endRow()
		   
			sw.endSheet()
			
			fw.close();
			
		   //Step 3. Substitute the template entry with the generated data
		   out = new FileOutputStream(tmpXls);
		   substitute(new File("template.xlsx"), tmp, sheetRef.substring(1), out);
		   out.close();
			   
		   log.info  "::::::::::::::END::::::project By Expense:::::"+new Date()
		   
		   return tmpXls?.getAbsolutePath()
	   
		} catch(Exception e) {
		   e.printStackTrace()
	   }finally{
		
			if(out){
				out.close()
			}
			
			if(os){
				os.close()
			}
		
		}
	}
	/**
	 *
	 * @param zipfile the template file
	 * @param tmpfile the XML file with the sheet data
	 * @param entry the name of the sheet entry to substitute, e.g. xl/worksheets/sheet1.xml
	 * @param out the stream to write the result to
	 */
	private static void substitute(File zipfile, File tmpfile, String entry, OutputStream out) throws IOException {
		ZipFile zip = new ZipFile(zipfile);

		ZipOutputStream zos = new ZipOutputStream(out);

		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();
		while (en.hasMoreElements()) {
			ZipEntry ze = en.nextElement();
			if(!ze.getName().equals(entry)){
				zos.putNextEntry(new ZipEntry(ze.getName()));
				InputStream is = zip.getInputStream(ze);
				copyStream(is, zos);
				is.close();
			}
		}
		zos.putNextEntry(new ZipEntry(entry));
		InputStream is = new FileInputStream(tmpfile);
		copyStream(is, zos);
		is.close();

		zos.close();
	}

	private static void copyStream(InputStream inp, OutputStream out) throws IOException {
		byte[] chunk = new byte[1024];
		int count;
		while ((count = inp.read(chunk)) >=0 ) {
		  out.write(chunk,0,count);
		}
	}

	/**
	 * Writes spreadsheet data in a Writer.
	 * (YK: in future it may evolve in a full-featured API for streaming data in Excel)
	 */
	public static class SpreadsheetWriter {
		private final Writer _out;
		private int _rownum;

		public SpreadsheetWriter(Writer out){
			_out = out;
		}

		public void beginSheet() throws IOException {
			_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
					"<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">" );
			_out.write("<sheetData>\n");
		}

		public void endSheet() throws IOException {
			_out.write("</sheetData>");
			_out.write("</worksheet>");
		}

		/**
		 * Insert a new row
		 *
		 * @param rownum 0-based row number
		 */
		public void insertRow(int rownum) throws IOException {
			_out.write("<row r=\""+(rownum+1)+"\">\n");
			this._rownum = rownum;
		}

		/**
		 * Insert row end marker
		 */
		public void endRow() throws IOException {
			_out.write("</row>\n");
		}

		public void createCell(int columnIndex, String value, int styleIndex) throws IOException {
			String ref = new CellReference(_rownum, columnIndex).formatAsString();
			_out.write("<c r=\""+ref+"\" t=\"inlineStr\"");
			if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
			_out.write(">");
			_out.write("<is><t>"+value+"</t></is>");
			_out.write("</c>");
		}

		public void createCell(int columnIndex, String value) throws IOException {
			createCell(columnIndex, value, -1);
		}

		public void createCell(int columnIndex, double value, int styleIndex) throws IOException {
			String ref = new CellReference(_rownum, columnIndex).formatAsString();
			_out.write("<c r=\""+ref+"\" t=\"n\"");
			if(styleIndex != -1) _out.write(" s=\""+styleIndex+"\"");
			_out.write(">");
			_out.write("<v>"+value+"</v>");
			_out.write("</c>");
		}

		public void createCell(int columnIndex, double value) throws IOException {
			createCell(columnIndex, value, -1);
		}

		public void createCell(int columnIndex, Date value, int styleIndex) throws IOException {
			createCell(columnIndex, DateUtil.getExcelDate(value, false), styleIndex);
		}
	}
	
	def setFormualaForCell(def cell, def rowColumnReferenceList){
		
		cell.setCellType(XSSFCell.CELL_TYPE_FORMULA)
		
		def str = ""
		rowColumnReferenceList?.each {
			if(str){
				str = str +","+it
			}else{
				str = it
			}
		}
		cell.setCellType(XSSFCell.CELL_TYPE_FORMULA)
		cell.setCellFormula("Sum("+str+")")
	}
	
	private def generateCell( XSSFRow row, int cellNo ,XSSFCellStyle style, def cellValue){
		XSSFCell  cell = row.createCell(cellNo)
		if(cellValue!=null){
			cell.setCellValue(cellValue)
			cell.setCellStyle(style)
		}
		return cell
	}
	
}