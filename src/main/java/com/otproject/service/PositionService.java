package com.otproject.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.otproject.dto.PositionDTO;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.repository.PositionRepository;
import com.otproject.repository.TeamStructureRepository;

@Service
public class PositionService {
	@Autowired
	private PositionRepository positionRepo;
	
	@Autowired
	private TeamStructureRepository teamStructureRepo;
	
	@Autowired
	TeamStructureService teamStructureService;
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	  static String[] HEADERs = { "No", "Name", "StaffID", "Position","Basic Pay" };
	  static String SHEET = "Sheet1";
	
	public void positionInsert(PositionDTO position) {
		positionRepo.save(position);
	}
	
	
	public void positionExcelInsert(MultipartFile file) {
		List<TeamStructureDTO> teamStructureList = teamStructureRepo.findAll();
		 try {
			 List<PositionDTO> oldPositionList = positionRepo.findAll();
		      Workbook workbook = new XSSFWorkbook(file.getInputStream());
		      Sheet sheet = workbook.getSheet(SHEET);
		      Iterator<Row> rows = sheet.iterator();
		      List<PositionDTO> positionList = new ArrayList<PositionDTO>();
		      int rowNumber = 0;
		      List<String> storeList = new ArrayList<String>();

		      while (rows.hasNext()) {
		        Row currentRow = rows.next();
		        // skip header
		        if (rowNumber == 0) {
		          rowNumber++;
		          continue;
		        }
		        Iterator<Cell> cellsInRow = currentRow.iterator();
		        PositionDTO position = new PositionDTO();
		        TeamStructureDTO teamDTO = new TeamStructureDTO();
		        int cellIdx = 0;
		        int teamId=0;
		        while (cellsInRow.hasNext()) {
		        	int count =0;
		        	int same=0;
		   
			          Cell currentCell = cellsInRow.next();
			          switch (cellIdx) {
			          case 2:
			        	  for(TeamStructureDTO t:teamStructureList) {
			        		  if(t.getStaffId().equals(currentCell.getStringCellValue())) {
			        			  teamId = t.getId();
			        			  teamDTO.setId(t.getId());
			        			  teamDTO.setName(t.getName());
			        			  teamDTO.setCheckDelete(t.getCheckDelete());
			        			  teamDTO.setStaffId(t.getStaffId());
			        		  }
			        	  }
			        	  break;
			          case 3:
			        	  storeList.add(currentCell.getStringCellValue());
			        	  for(String store:storeList) {
			        		  if(store.equals(currentCell.getStringCellValue())) {
			        			  count++;
			        		  }
			        	  }
			        	  
			        	  for(PositionDTO o:oldPositionList) {
			        		  if(o.getPositionName().equals(currentCell.getStringCellValue())) {
			        			  same++;
			        		  }
			        	  }
			        	  
			        	  if(count==1 && same==0) {
			        		  position.setPositionName(currentCell.getStringCellValue());
			        		  count=0;
			        		 
			        	  }
			            break;
			          case 4:
			        	  BigDecimal b = new BigDecimal(currentCell.getNumericCellValue());
			        	  teamDTO.setSalary(b);
			        	  teamStructureService.updateTeamStructure(teamDTO, teamId);
			            break;
			          default:
			            break;
			          }
			          cellIdx++;
		        }
		        if(position.getPositionName()!=null) {
		        	positionList.add(position);
		        }
		        
		      }
		      workbook.close();
		      positionRepo.saveAll(positionList);
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse Salary Excel file: " + e.getMessage());
		    }
		  }
	
	public Map<String,Integer> selectAllPositionList(){
		Map<String,Integer> map= new HashedMap<String, Integer>();
		List<PositionDTO> list = positionRepo.findAll();
		for(PositionDTO mapid:list) {
			map.put(mapid.getPositionName(), mapid.getPositionId());
		}
		return map;
	}
	
	public List<PositionDTO> selectPositions(){
		List<PositionDTO> list = positionRepo.findAllPositions();
		return list;
	}
	
	public PositionDTO position(Integer id) {
		return positionRepo.findByPositionId(id);
	}
	
	public void updatePosition(PositionDTO dto,Integer id) {
		positionRepo.save(dto);
	}
}
