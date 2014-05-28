package com.eugenefe.util;

import groovyjarjarantlr.debug.Event;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.jgroups.jmx.protocols.pbcast.FLUSH;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.eugenefe.entity.MarketVariable;
import com.eugenefe.entity.MarketVariableJoin;
import com.eugenefe.entity.Scenario;
import com.eugenefe.entity.ScenarioDetail;
import com.eugenefe.entity.ScenarioDetailId;
import com.eugenefe.enums.EShockType;
import com.eugenefe.util.RiskMeasureGroup;

@Name("fileUploadController")
@Scope(ScopeType.CONVERSATION)
public class FileUploadController {
	
	@Logger	private Log log;
	@In		private StatusMessages statusMessages;
	@In		private Session session;
	
	private byte[] byteData;
	public byte[] getByteData() {
		return byteData;
	}
	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}

	private String contType;
	public String getContType() {
		return contType;
	}
	public void setContType(String contType) {
		this.contType = contType;
	}

	private UploadedFile file;
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	private List<ScenarioUpload> scenarioUploadList;
	
	public List<ScenarioUpload> getScenarioUploadList() {
		return scenarioUploadList;
	}

	public void setScenarioUploadList(List<ScenarioUpload> scenarioUploadList) {
		this.scenarioUploadList = scenarioUploadList;
	}

	public FileUploadController() {
	
	}
	public void upload(){
		log.info("in fileup:#0");
//		uploadHandle(file);
	}

	public void uploadbefore(){
//		log.info("in fileup:#0", file.getFileName());
		log.info("in fileup:#0", byteData.length);
		InputStream infile;
		File tempfile = new File("C:\\text.xls");
		HSSFWorkbook workbook = null;
		
		try {
			infile = new ByteArrayInputStream(byteData);
			workbook = new HSSFWorkbook(infile);

			log.info("in fileup1:#0", workbook.getBytes());
			HSSFSheet sheet = workbook.getSheetAt(0);
			log.info("in fileup11:#0", sheet.getRow(1).getCell(1).getStringCellValue());
			setScenarioFile(sheet);
		} catch (Exception e) {
			// TODO: handle exception
		}
		for(ScenarioUpload aa : scenarioUploadList){
			log.info("in fileup2:#0,#1", scenarioUploadList.size(), aa.getScenarioId());
		}
	}
	
	private void uploadHandle(InputStream infile){
//	private void uploadHandle(UploadedFile file){
//		log.info("in fileup:#0");
//		statusMessages.addFromResourceBundle("Successful", file.getFileName());
		
//		if(file ==null){
//			statusMessages.addFromResourceBundle(Severity.ERROR, "scenarioUpload.noFileError");
//		}
//		InputStream infile;
		HSSFWorkbook workbook = null;
		try{
//			infile = file.getInputstream();
			log.info("in fileup111:#0");
			workbook = new HSSFWorkbook(infile);
			log.info("in fileup111:#0");
		}catch(IOException e){
			statusMessages.addFromResourceBundle(Severity.ERROR, "scenarioUpload.readFileError");
		}

		HSSFSheet sheet = workbook.getSheetAt(1);
		setScenarioFile(sheet);
	}
	
		

	private void setScenarioFile(HSSFSheet sheet){
		log.info("Sheet: #0", sheet.getRow(1).getCell(1).getStringCellValue());
		scenarioUploadList = new ArrayList<ScenarioUpload>();
		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			if(!row.getCell(0).getStringCellValue().startsWith("#")){
				ScenarioUpload temp = new ScenarioUpload();
				temp.setScenarioId(row.getCell(0).getStringCellValue());
				temp.setScenarioName(row.getCell(1).getStringCellValue());
				temp.setScenarioType(row.getCell(2).getStringCellValue());
				temp.setScenarioSet(row.getCell(3).getStringCellValue());
				temp.setMvId(row.getCell(4).getStringCellValue());
				temp.setShockType(row.getCell(5).getStringCellValue());
				temp.setShockValue(row.getCell(6).getNumericCellValue());
				scenarioUploadList.add(temp);
			}
		}
	}
	
	
	public void handle(FileUploadEvent event){
		log.info("in fileup:#0");
		statusMessages.addFromResourceBundle("Successful", event.getFile().getFileName());
		
		if(event.getFile().equals(null)){
			statusMessages.addFromResourceBundle(Severity.ERROR, "scenarioUpload.noFileError");
		}
		InputStream file;
		HSSFWorkbook workbook = null;
		try{
			file = event.getFile().getInputstream();
			workbook = new HSSFWorkbook(file);
		}catch(IOException e){
			statusMessages.addFromResourceBundle(Severity.ERROR, "scenarioUpload.readFileError");
		}
		HSSFSheet sheet = workbook.getSheetAt(1);
		
		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			int index =0;
			ScenarioUpload temp = new ScenarioUpload();
			while(cellIterator.hasNext()){
				index = index+1;
				Cell cell = cellIterator.next();
				if(cell.getStringCellValue().startsWith("#")){
					break;
				}else{
					switch (index) {
					case 1:
						temp.setScenarioId(cell.getStringCellValue());
						break;
					case 2:
						temp.setScenarioName(cell.getStringCellValue());
						break;
					case 3:
						temp.setScenarioType(cell.getStringCellValue());
						break;
					case 4:
						temp.setScenarioSet(cell.getStringCellValue());
						break;
					case 5:
						temp.setMvId(cell.getStringCellValue());
						break;
					case 6:
						temp.setShockType(cell.getStringCellValue());
						break;	
					case 7:
						temp.setShockValue(cell.getNumericCellValue());
						break;	
					default:
						break;
					}
				}
				scenarioUploadList.add(temp);
				log.info("in fileup1:#0");
			}
		}
	}
	public void addNew(){
		if(scenarioUploadList == null){
			scenarioUploadList= new ArrayList<ScenarioUpload>();
		}
		scenarioUploadList.add(new ScenarioUpload());
	}
	
	public void save(){
		Scenario sce ;
		ScenarioDetail sceDetail;
		
		for(ScenarioUpload aa : scenarioUploadList){

			sce = (Scenario)session.get(Scenario.class, aa.getScenarioId());
			if( sce == null ){
				sce = new Scenario();
				sce.setScenarioId(aa.getScenarioId());
				sce.setScenarioName(aa.getScenarioName());
				sce.setScenarioType(aa.getScenarioType());
				sce.setScenarioSet(aa.getScenarioSet());
				
//				scenarioSet.add(sce);
				session.saveOrUpdate(sce);
			}
			
			sceDetail = (ScenarioDetail)session.get(ScenarioDetail.class, new ScenarioDetailId(aa.getScenarioId(), aa.getMvId()));
			if(sceDetail ==null){
				sceDetail = new ScenarioDetail(new ScenarioDetailId(aa.getScenarioId(), aa.getMvId()));
				sceDetail.setMarketVariable((MarketVariableJoin)session.get(MarketVariableJoin.class, aa.getMvId()));
				sceDetail.setScenario(sce);
				sce.getSceDetailList().add(sceDetail);
			}
			sceDetail.setShockType(EShockType.valueOf(aa.getShockType()));
			sceDetail.setShockValue(new BigDecimal(aa.getShockValue()));

			session.saveOrUpdate(sceDetail);
		}
		session.flush();
		
		Events.instance().raiseEvent("evtUpdateScenario");
	}	
}
