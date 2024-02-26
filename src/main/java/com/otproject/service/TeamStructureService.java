package com.otproject.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.otproject.dto.MainTeamDTO;
import com.otproject.dto.PositionDTO;
import com.otproject.dto.ProjectDTO;
import com.otproject.dto.TeamDTO;
import com.otproject.dto.TeamStructureDTO;
import com.otproject.repository.MainTeamRepository;
import com.otproject.repository.PositionRepository;
import com.otproject.repository.ProjectRepository;
import com.otproject.repository.TeamRepository;
import com.otproject.repository.TeamStructureRepository;

@Service
public class TeamStructureService {

	@Autowired
	private TeamStructureRepository teamStructureRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private PositionRepository positionRepo;
	
	@Autowired
	private MainTeamRepository mainTeamRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	 static String[] HEADERs = { "No", "Name", "StaffID", "Team","Project","Position" };
	 static String SHEET = "Sheet1";
	
	public void TeamStructureInsert(TeamStructureDTO teamStructure) {
		teamStructureRepo.save(teamStructure);
	}
	
	public List<TeamStructureDTO> selectAllTeamStructure(){
		return teamStructureRepo.findAll();
	}
	
	public void updateTeamStructure(TeamStructureDTO dto,Integer id) {
		teamStructureRepo.save(dto);
	}
	
	public void excelInsert(MultipartFile file) {
		try {
			List<TeamStructureDTO> oldTeamList = teamStructureRepo.findAll();
			List<TeamDTO> oldlist = teamRepo.findAll();
			List<ProjectDTO> oldProjectList = projectRepo.findAll();
			
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
		      Sheet sheet = workbook.getSheet(SHEET);
		      Iterator<Row> rows = sheet.iterator();
		      List<TeamStructureDTO> teamStructureList = new ArrayList<TeamStructureDTO>();
		      List<TeamDTO> teamList = new ArrayList<TeamDTO>();
		      List<ProjectDTO> projectList = new ArrayList<ProjectDTO>();
		      
		      int rowNumber = 0;
		      List<String> storeList = new ArrayList<String>();
		      while (rows.hasNext()) {
		    	  String teamStructureName=null;
		        Row currentRow = rows.next();
		        // skip header
		        if (rowNumber == 0) { 
		          rowNumber++;
		          continue;
		        }
		        Iterator<Cell> cellsInRow = currentRow.iterator();
		        TeamStructureDTO teamStructure = new TeamStructureDTO();
		        TeamDTO team = new TeamDTO();
		        ProjectDTO project = new ProjectDTO();
		        
		        int cellIdx = 0;
		        while (cellsInRow.hasNext()) {
		        	int count =0;
		        	int same=0;
		          Cell currentCell = cellsInRow.next();
		          switch (cellIdx) {
		         
		          case 1:
		        	  storeList.add(currentCell.getStringCellValue());
		        	  for(String store:storeList) {
		        		  if(store.equals(currentCell.getStringCellValue())) {
		        			  count++;
		        		  }
		        	  }
		        	  
		        	  for(TeamStructureDTO o:oldTeamList) {
		        		  if(o.getName().equals(currentCell.getStringCellValue())) {
		        			  same++;
		        		  }
		        	  }
		        	  
		        	  if(count==1 && same==0) {
		        		  teamStructure.setName(currentCell.getStringCellValue());
		        		  teamStructureName=currentCell.getStringCellValue();
		        		  teamStructure.setCheckDelete(0);
		        		  teamStructure.setPassword(passwordEncoder.encode("123dat"));
		        		  count=0;
		        	  }
		            break;
		          case 2:
		        	  for(String store:storeList) {
		        		  if(store.equals(teamStructureName)){
		        			  count++;
		        		  }
		        	  }
		        	  
		        	  for(TeamStructureDTO o:oldTeamList) {
		        		  if(o.getStaffId().equals(currentCell.getStringCellValue())) {
		        			  same++;
		        		  }
		        	  }
		        	  
		        	  if(count==1 && same==0) {
		        		  teamStructure.setStaffId(currentCell.getStringCellValue());
		        		  count=0;
		        	  }
		            break;
		          case 3:
		        	  storeList.add(currentCell.getStringCellValue());
		        	  for(String store:storeList) {
		        		  if(store.equals(currentCell.getStringCellValue())) {
		        			  count++;
		        		  }
		        	  }
		        	  
		        	  for(TeamDTO o:oldlist) {
		        		  if(o.getTeamName().equals(currentCell.getStringCellValue())) {
		        			  same++;
		        		  }
		        	  }
		        	  
		        	  if(count==1 && same==0) {
		        		  team.setTeamName(currentCell.getStringCellValue());
		        		  count=0;
		        	  }
		        	 break;
		          case 4:
		        	  storeList.add(currentCell.getStringCellValue());
		        	  for(String store:storeList) {
		        		  if(store.equals(currentCell.getStringCellValue())) {
		        			  count++;
		        		  }
		        	  }
		        	  
		        	  for(ProjectDTO o:oldProjectList) {
		        		  if(o.getProjectCode().equals(currentCell.getStringCellValue())) {
		        			  same++;
		        		  }
		        	  }
		        	  
		        	  if(count==1 && same==0 && currentCell.getStringCellValue()!="") {
		        		  project.setProjectCode(currentCell.getStringCellValue());
		        		  count=0;
		        	  }
		        	 break;
		          default:
		            break;
		          }
		          cellIdx++;
		        }
		        if(teamStructure.getName()!=null && teamStructure.getStaffId()!=null) {
		        	teamStructureList.add(teamStructure);
		        }
		        if(team.getTeamName()!=null) {
		        	teamList.add(team);
		        }
		        if(project.getProjectCode()!=null) {
		        	projectList.add(project);
		        }
		      }
		      workbook.close();
			
			teamStructureRepo.saveAll(teamStructureList);
			teamRepo.saveAll(teamList);
			projectRepo.saveAll(projectList);
			
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
			
	}
	
	public void mainTeamInsert(MultipartFile file) {
		try {
			List<TeamStructureDTO> oldTeamList = teamStructureRepo.findAll();
			List<TeamDTO> oldlist = teamRepo.findAll();
			List<ProjectDTO> oldProjectList = projectRepo.findAll();
			List<PositionDTO> oldPositionList = positionRepo.findAll();
			
		      Workbook workbookMain = new XSSFWorkbook(file.getInputStream());
		      Sheet sheetMain = workbookMain.getSheet(SHEET);
		      Iterator<Row> rowsMain = sheetMain.iterator();
		      List<MainTeamDTO> mainTeamList = new ArrayList<MainTeamDTO>();
		      int rowNumberMain = 0;
		      while (rowsMain.hasNext()) {
		        Row currentRow = rowsMain.next();
		        // skip header
		        if (rowNumberMain == 0) { 
		        	rowNumberMain++;
		          continue;
		        }
		        Iterator<Cell> cellsInRowMain = currentRow.iterator();
		        MainTeamDTO mainTeam = new MainTeamDTO();
		        int cellIdx = 0;
		        while (cellsInRowMain.hasNext()) {
		          Cell currentCell = cellsInRowMain.next();
		          switch (cellIdx) {
		         
		          case 2:
		        	  for(TeamStructureDTO t:oldTeamList) {
		        		  if(t.getStaffId().equals(currentCell.getStringCellValue())) {
		        			  mainTeam.setTeamStructureId(t.getId());
		        		  }
		        	  }
		            break;
		          case 3:
		        	  for(TeamDTO tt:oldlist) {
		        		  if(tt.getTeamName().equals(currentCell.getStringCellValue())) {
		        			  mainTeam.setTeamId(tt.getTeamId());
		        		  }
		        	  }
		            break;
		          case 4:
		        	  for(ProjectDTO p:oldProjectList) {
		        		  if(p.getProjectCode().equals(currentCell.getStringCellValue())) {
		        			  mainTeam.setProjectId(p.getProjectId());
		        		  }
		        	  }
		            break;
		          case 5:
		        	  for(PositionDTO po:oldPositionList) {
		        		  if(po.getPositionName().equals(currentCell.getStringCellValue())) {
		        			  mainTeam.setPositionId(po.getPositionId());
		        		  }
		        	  }
		            break;
		          default:
		            break;
		          }
		          cellIdx++;
		        }
		        mainTeamList.add(mainTeam);
		      }
		      workbookMain.close();
		      mainTeamRepo.saveAll(mainTeamList);
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		    }
		  }
	
	public String lastStaffId26() {
		TeamStructureDTO staffId = teamStructureRepo.lastStaffId26();
		String staffId26 = staffId.getStaffId();
		return staffId26;
	}
	
	public String lastStaffId25() {
		TeamStructureDTO staffId = teamStructureRepo.lastStaffId25();
		String staffId25 = staffId.getStaffId();
		return staffId25;
	}
	
	public Integer lastTeamStructureId() {
		TeamStructureDTO teamStructureDto = new TeamStructureDTO();
		teamStructureDto = teamStructureRepo.lastTeamStructureId();
		return teamStructureDto.getId();
	}
	
	public Optional<TeamStructureDTO> findUserInfoForUpdateSignature(String staffId) {
		Optional<TeamStructureDTO> teamStructureDto = teamStructureRepo.findUserByStaffId(staffId);
		return teamStructureDto;
	}
	
	public TeamStructureDTO findByStaffId(String staffId) {
		TeamStructureDTO teamStructureDto = teamStructureRepo.findByStaffId(staffId);
		return teamStructureDto;
	}
}
