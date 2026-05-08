package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Building_Details_Create extends HttpServlet 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
    {
    	System.out.println("\n\n*******************************************************\n");
        Connection connection=null;
        try
          {
			 ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
			 String ConnectionString="";
			
			 String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
			 String strdsn=rs.getString("Config.DSN");
			 String strhostname=rs.getString("Config.HOST_NAME");
			 String strportno=rs.getString("Config.PORT_NUMBER");
			 String strsid=rs.getString("Config.SID");
			 String strdbusername=rs.getString("Config.USER_NAME");
			 String strdbpassword=rs.getString("Config.PASSWORD");
			   
			    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			
			 Class.forName(strDriver.trim());
			 connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  
          }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
        response.setContentType(CONTENT_TYPE);
 
        
        String msg = null;
        String strCommand = ""; 

       
        int accounting_unit_id = 0;
        int accounting_unit_office_id = 0;
        String financial_year = null;
        int asset_code = 0;
        

                
        String trans_date = null;
        String building_name = null;
        int owner_code = 0;
        int survey_no = 0;
        String door_no = null;
        String village = null;
        int year_of_construction = 0;
        String type_of_building = null;
        int no_of_floors = 0;
        String type_of_foundation = null;
        String structured_bldg_elements = null;
        int total_civil_cost = 0;
        int total_electrical_cost = 0;
        int total_external_cost = 0;
        int total_additions_cost = 0;
        int total_book_value = 0;
        int account_head_code = 0;
        String remarks = null;
        
/* cmbAcc_UnitCode cmbOffice_code txtDate cmbFinyr txtAsset txtBuilding cmbOwner txtSurvey txtDoorNo txtVillage txtBuidConsYear txtBuildType txtFloors txtFoundationType txtStructuralElements txtCivilCost txtElectricalCost txtExternalServCost txtAdditionCost txtBookValue cmbAcHeadCode txtRemarks cmdAddBuild cmdUpdateBuild cmdDeleteBuild cmdCancel cmdListBuild cmdClearBuild    */

        
    	//txtFloorNo txtFloorConsYear txtFloorHeight txtPlinthArea txtFloorCivilCost txtFloorElectricalCost 
    	//txtFloorExternalServCost txtFloorAdditionCost txtFloorBookValue txtBPNo txtFloorAcHeadCode
        
        int floor_no = 0;
        int floor_year = 0;
        int floor_height = 0;
        int floor_area = 0;
        int floor_civilCost = 0;
        int floor_electricalCost = 0;
        int floor_externalCost = 0;
        int floor_addCost = 0;
        int floor_bookVal = 0;
        int floor_BPNo = 0;
        int floor_AcHead = 0;
        String floor_Remarks = "-";
                
        String flr_no[] = new String[20];
        String flr_year[] = new String[20];
        String flr_height[] = new String[20];
        String flr_area[] = new String[20];
        String flr_civilCost[] = new String[20];
        String flr_electricalCost[] = new String[20];
        String flr_externalCost[] = new String[20];
        String flr_addCost[] = new String[20];
        String flr_bookVal[] = new String[20];
        String flr_BPNo[] = new String[20];
        String flr_AcHead[] = new String[20];
        String flr_Remarks[] = new String[20];
      
        
        
        int office_flrNo = 0;
        String office = null;
        String office_TN = "T";
        String office_Othr = null;
        String hidOfficeType = "T";
        String office_Remarks = "-";
                
        String off_flrNo[] = new String[50];
        String off[] = new String[50];
        String off_TN[] = new String[50];
        String off_Remarks[] = new String[50];
        
        
        
        
        HttpSession session=request.getSession(false);
        try
        {
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
               
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
         String userid=(String)session.getAttribute("UserId");
         System.out.println("session id is:"+userid);
        
        
        response.setContentType("text/html");
        response.setHeader("Cache-Control","no-cache");

        
        
        try
        {
        	strCommand = request.getParameter("command");
        	System.out.println("strCommand : " + strCommand);
          
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'accounting_unit_id' parameter ==> "+ e);
        }        

        try
        {
        	accounting_unit_id= Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	System.out.println("accounting_unit_id : " + accounting_unit_id);
          
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'accounting_unit_id' parameter ==> "+ e);
        }        

           
        try
        {
        	accounting_unit_office_id= Integer.parseInt(request.getParameter("cmbOffice_code"));
        	System.out.println("accounting_unit_office_id : " + accounting_unit_office_id);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'accounting_unit_office_id' parameter ==> "+ e);
        }        

        try
        {
        	trans_date= request.getParameter("txtDate");
        	System.out.println("trans_date : " + trans_date);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'trans_date' parameter ==> "+ e);
        }        

        try
        {
        	financial_year= request.getParameter("cmbFinyr");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'financial_year' parameter ==> "+ e);
        }        

        try
        {
        	asset_code= Integer.parseInt(request.getParameter("txtAsset"));
        	System.out.println("asset_code : " + asset_code);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'asset_code' parameter ==> "+ e);
        }        

        try
        {
        	building_name  = request.getParameter("txtBuilding");
        	System.out.println("building_name : " + building_name);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'building_name' parameter ==> "+ e);
        }        

        try
        {
        	owner_code = Integer.parseInt(request.getParameter("cmbOwner"));
        	System.out.println("owner_code : " + owner_code);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'owner_code' parameter ==> "+ e);
        }        

        try
        {
        	survey_no = Integer.parseInt(request.getParameter("txtSurvey"));
        	System.out.println("survey_no : " + survey_no);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'survey_no' parameter ==> "+ e);
        }        
        
        try
        {
        	door_no = request.getParameter("txtDoorNo");
        	System.out.println("door_no : " + door_no);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'door_no' parameter ==> "+ e);
        }        

        try
        {
        	village = request.getParameter("txtVillage");
        	System.out.println("village : " + village);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'village' parameter ==> "+ e);
        }        

        try
        {
        	year_of_construction = Integer.parseInt(request.getParameter("txtBuidConsYear"));
        	System.out.println("year_of_construction : " + year_of_construction);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'year_of_construction' parameter ==> "+ e);
        }        

        try
        {
        	type_of_building = request.getParameter("txtBuildType");
        	System.out.println("type_of_building : " + type_of_building);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'type_of_building' parameter ==> "+ e);
        }        

        try
        {
        	no_of_floors = Integer.parseInt(request.getParameter("txtFloors"));
        	System.out.println("no_of_floors : " + no_of_floors);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'no_of_floors' parameter ==> "+ e);
        }      

        try
        {
        	type_of_foundation = request.getParameter("txtFoundationType");
        	System.out.println("type_of_foundation : " + type_of_foundation);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'type_of_foundation' parameter ==> "+ e);
        }      
        
        try
        {
        	structured_bldg_elements = request.getParameter("txtStructuralElements");
        	System.out.println("structured_bldg_elements : " + structured_bldg_elements);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'structured_bldg_elements' parameter ==> "+ e);
        }    
        
        try
        {
        	total_civil_cost = Integer.parseInt(request.getParameter("txtCivilCost"));
        	System.out.println("total_civil_cost : " + total_civil_cost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'total_civil_cost' parameter ==> "+ e);
        }   
        
        try
        {
        	total_electrical_cost = Integer.parseInt(request.getParameter("txtElectricalCost"));
        	System.out.println("total_electrical_cost : " + total_electrical_cost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'total_electrical_cost' parameter ==> "+ e);
        }       

        try
        {
        	total_external_cost = Integer.parseInt(request.getParameter("txtExternalServCost"));
        	System.out.println("total_external_cost : " + total_external_cost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'total_external_cost' parameter ==> "+ e);
        }
        
        try
        {
        	total_additions_cost = Integer.parseInt(request.getParameter("txtAdditionCost"));
        	System.out.println("total_additions_cost : " + total_additions_cost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'total_additions_cost' parameter ==> "+ e);
        }   
        
        try
        {
        	total_book_value = Integer.parseInt(request.getParameter("txtBookValue"));
        	System.out.println("total_book_value : " + total_book_value);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'total_book_value' parameter ==> "+ e);
        }   
        
        try
        {
        	account_head_code = Integer.parseInt(request.getParameter("txtAcHeadCode"));
        	System.out.println("account_head_code : " + account_head_code);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'account_head_code' parameter ==> "+ e);
        }   
        
        try
        {
        	remarks = request.getParameter("txtRemarks");
        	System.out.println("remarks : " + remarks);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'remarks' parameter ==> "+ e);
        }   

        

        
        
        
        
        
       	//txtFloorNo txtFloorConsYear txtFloorHeight txtPlinthArea txtFloorCivilCost txtFloorElectricalCost 
    	//txtFloorExternalServCost txtFloorAdditionCost txtFloorBookValue txtBPNo txtFloorAcHeadCode txtFloorRemarks

        try
        {
        	floor_no = Integer.parseInt(request.getParameter("txtFloorNo"));
        	System.out.println("floor_no : " + floor_no);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_no' parameter ==> "+ e);
        }   
        
        try
        {
        	floor_year = Integer.parseInt(request.getParameter("txtFloorConsYear"));
        	System.out.println("floor_year : " + floor_year);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_year' parameter ==> "+ e);
        }   
        
      
        try
        {
        	floor_height = Integer.parseInt(request.getParameter("txtFloorHeight"));
        	System.out.println("floor_height : " + floor_height);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_height' parameter ==> "+ e);
        }   
        
        try
        {
        	floor_area = Integer.parseInt(request.getParameter("txtPlinthArea"));
        	System.out.println("floor_area : " + floor_area);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_area' parameter ==> "+ e);
        }   
        
        
        try
        {
        	floor_civilCost = Integer.parseInt(request.getParameter("txtFloorCivilCost"));
        	System.out.println("floor_civilCost : " + floor_civilCost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_civilCost' parameter ==> "+ e);
        }   
        
        try
        {
        	floor_electricalCost = Integer.parseInt(request.getParameter("txtFloorElectricalCost"));
        	System.out.println("floor_electricalCost : " + floor_electricalCost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_electricalCost' parameter ==> "+ e);
        }   
        
        try
        {
        	floor_externalCost = Integer.parseInt(request.getParameter("txtFloorExternalServCost"));
        	System.out.println("floor_externalCost : " + floor_externalCost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_externalCost' parameter ==> "+ e);
        }   
        
        try
        {
        	floor_addCost = Integer.parseInt(request.getParameter("txtFloorAdditionCost"));
        	System.out.println("floor_addCost : " + floor_addCost);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_addCost' parameter ==> "+ e);
        }   
        
      
        try
        {
        	floor_bookVal = Integer.parseInt(request.getParameter("txtFloorBookValue"));
        	System.out.println("floor_bookVal : " + floor_bookVal);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_bookVal' parameter ==> "+ e);
        }   


        try
        {
        	floor_BPNo = Integer.parseInt(request.getParameter("txtBPNo"));
        	System.out.println("floor_BPNo : " + floor_BPNo);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_BPNo' parameter ==> "+ e);
        }   
        
 
        
        try
        {
        	floor_AcHead = Integer.parseInt(request.getParameter("txtFloorAcHeadCode"));
        	System.out.println("floor_AcHead : " + floor_AcHead);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_AcHead' parameter ==> "+ e);
        }   
                
        
        try
        {
        	floor_Remarks = request.getParameter("txtFloorRemarks");
        	System.out.println("floor_Remarks : " + floor_Remarks);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'floor_Remarks' parameter ==> "+ e);
        } 
        
        
        //txtOfficeFloorNo cmbOffice radOffice

        try
        {
        	office_flrNo = Integer.parseInt(request.getParameter("txtOfficeFloorNo"));
        	System.out.println("office_flrNo : " + office_flrNo);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'office_flrNo' parameter ==> "+ e);
        }   
        
       
        try
        {
        	office = request.getParameter("cmbOffice");
        	System.out.println("office : " + office);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'office' parameter ==> "+ e);
        }   
 
        
        try
        {
        	office_TN = request.getParameter("radOffice");
        	System.out.println("office_TN : " + office_TN);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'office_TN' parameter ==> "+ e);
        }   
        
        
        try
        {
        	office_Othr = request.getParameter("txtOffice");
        	System.out.println("office_Othr : " + office_Othr);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'office_Othr' parameter ==> "+ e);
        }   
        
        
        try
        {
        	hidOfficeType = request.getParameter("hidOfficeType");
        	System.out.println("hidOfficeType : " + hidOfficeType);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'hidOfficeType' parameter ==> "+ e);
        }   
        
        
        try
        {
        	office_Remarks = request.getParameter("txtOfficeRemarks");
        	System.out.println("office_Remarks : " + office_Remarks);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'office_Remarks' parameter ==> "+ e);
        } 
 
        
        //sendMessage(response,"WELCOME","ok");
        
        
        /**************************************************************************************************
         * 									Building Details - Insert
         **************************************************************************************************/
        
        try
        {
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO fas_building_details " +
											                		"( accounting_unit_id, " +
											                		"  accounting_unit_office_id, " +
											                		"  trans_date, " +
											                		"  financial_year, " +
											                		"  asset_code, " +
											                		"  building_name, " +
											                		"  owner_code, " +
											                		"  survey_no, " +
											                		"  door_no, " +
											                		"  village, " +
											                		"  year_of_construction, " +
											                		"  type_of_building, " +
											                		"  no_of_floors, " +
											                		"  type_of_foundation, " +
											                		"  structured_bldg_elements, " +
											                		"  total_civil_cost, " +
											                		"  total_electrical_cost, " +
											                		"  total_external_cost, " +
											                		"  total_additions_cost, " +
											                		"  total_book_value, " +
											                		"  account_head_code, " +
											                		"  remarks, " +
											                		"  updated_by_user_id, " +
											                		"  updated_date, " +
											                		"  STATUS ) " +
											                		"VALUES(?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,systimestamp,'Y')");
            System.out.println(ps1);
            ps1.setInt(1,accounting_unit_id);
            ps1.setInt(2,accounting_unit_office_id);
            ps1.setString(3,trans_date);
            ps1.setString(4,financial_year);
            ps1.setInt(5,asset_code);
            ps1.setString(6,building_name);
            ps1.setInt(7,owner_code);
            ps1.setInt(8,survey_no);
            ps1.setString(9,door_no);
            ps1.setString(10,village);
            ps1.setInt(11,year_of_construction);
            ps1.setString(12,type_of_building);
            ps1.setInt(13,no_of_floors);
            ps1.setString(14,type_of_foundation);
            ps1.setString(15,structured_bldg_elements);
            ps1.setInt(16,total_civil_cost);
            ps1.setInt(17,total_electrical_cost);
            ps1.setInt(18,total_external_cost);
            ps1.setInt(19,total_additions_cost);
            ps1.setInt(20,total_book_value);
            ps1.setInt(21,account_head_code);
            ps1.setString(22,remarks);
            ps1.setString(23,userid);
            
            ps1.executeUpdate();
            ps1.close();
            
            msg="Building Details Inserted Successfully!";
            msg=msg+"<br><br>";
            
            
        }
        catch(SQLException e) {
            System.out.println("Exception executing 'Add Building Query' ==> "+e);
            try
            {connection.rollback();}catch(Exception e1){System.out.println("Exception in Catch"+e1);}
            msg="<br><br>Exception in adding Building ==> "+e+"<br>";
            sendMessage(response,msg,"ok");
        }
        
        
        
        
        /**************************************************************************************************
         * 									Floor Details - Insert
         **************************************************************************************************/
        
        
       	//txtFloorNo txtFloorConsYear txtFloorHeight txtPlinthArea txtFloorCivilCost txtFloorElectricalCost 
    	//txtFloorExternalServCost txtFloorAdditionCost txtFloorBookValue txtBPNo txtFloorAcHeadCode HIDtxtFloorRemarks
        
        
        try
        {
	        flr_no = request.getParameterValues("HIDtxtFloorNo");
	        System.out.println("flr_no : " + flr_no[0]);
	        flr_year = request.getParameterValues("HIDtxtFloorConsYear");
	        System.out.println("flr_year : " + flr_year[0]);
	        flr_height = request.getParameterValues("HIDtxtFloorHeight");
	        System.out.println("flr_height : " + flr_height[0]);
	        flr_area = request.getParameterValues("HIDtxtPlinthArea");
	        System.out.println("flr_area : " + flr_area[0]);
	        flr_civilCost = request.getParameterValues("HIDtxtFloorCivilCost");
	        System.out.println("flr_civilCost : " + flr_civilCost[0]);
	        flr_electricalCost = request.getParameterValues("HIDtxtFloorElectricalCost");
	        System.out.println("flr_electricalCost : " + flr_electricalCost[0]);
	        flr_externalCost = request.getParameterValues("HIDtxtFloorExternalServCost");
	        System.out.println("flr_externalCost : " + flr_externalCost[0]);
	        flr_addCost = request.getParameterValues("HIDtxtFloorAdditionCost");
	        System.out.println("flr_addCost : " + flr_addCost[0]);
	        flr_bookVal = request.getParameterValues("HIDtxtFloorBookValue");
	        System.out.println("flr_bookVal : " + flr_bookVal[0]);
	        flr_BPNo = request.getParameterValues("HIDtxtBPNo");
	        System.out.println("flr_BPNo : " + flr_BPNo[0]);
	        flr_AcHead = request.getParameterValues("HIDtxtFloorAcHeadCode");
	        System.out.println("flr_AcHead : " + flr_AcHead[0]);
	        flr_Remarks = request.getParameterValues("HIDtxtFloorRemarks");
	        System.out.println("flr_Remarks : " + flr_Remarks[0]);
	        
        }catch(Exception e)
        {
        	System.out.println("Exception fetching Floor parameters ==> " + e);
        }
        
        try
        {
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO FAS_BUILDING_FLOOR_DETAILS " +
																"( " +
																"  ACCOUNTING_UNIT_ID, " +
																"  ACCOUNTING_UNIT_OFFICE_ID, " +
																"  TRANS_DATE, " +
																"  FINANCIAL_YEAR, " +
																"  ASSET_CODE, " +
																"  FLOOR_NO, " +
																"  YEAR_OF_CONSTRUCTION, " +
																"  FLOOR_HEIGHT, " +
																"  PLINTH_AREA, " +
																"  CIVIL_COST, " +
																"  ELECTRICAL_COST, " +
																"  EXTERNAL_COST, " +
																"  ADDITIONAL_COST, " +
																"  BOOK_VALUE, " +
																"  BP_NO_FOR_CONSTRUCTION, " +
																"  ACCOUNT_HEAD_CODE, " +
																"  REMARKS, " +
																"  UPDATED_BY_USER_ID, " +
																"  UPDATED_DATE, " +
																"  STATUS " +
																") " +
																"VALUES " +
																"(?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,systimestamp,'Y') ");
            System.out.println(ps2);
            for(int i=0; i<flr_no.length; i++)
            {
	            ps2.setInt(1,accounting_unit_id);
	            ps2.setInt(2,accounting_unit_office_id);
	            ps2.setString(3,trans_date);
	            ps2.setString(4,financial_year);
	            ps2.setInt(5,asset_code);
	            ps2.setInt(6,Integer.parseInt(flr_no[i]));
	            ps2.setInt(7,Integer.parseInt(flr_year[i]));
	            ps2.setInt(8,Integer.parseInt(flr_height[i]));
	            ps2.setInt(9,Integer.parseInt(flr_area[i]));
	            ps2.setInt(10,Integer.parseInt(flr_civilCost[i]));
	            ps2.setInt(11,Integer.parseInt(flr_electricalCost[i]));
	            ps2.setInt(12,Integer.parseInt(flr_externalCost[i]));
	            ps2.setInt(13,Integer.parseInt(flr_addCost[i]));
	            ps2.setInt(14,Integer.parseInt(flr_bookVal[i]));
	            ps2.setInt(15,Integer.parseInt(flr_BPNo[i]));
	            ps2.setInt(16,Integer.parseInt(flr_AcHead[i]));
	            ps2.setString(17,flr_Remarks[i]);
	            ps2.setString(18,userid);
	        
	            ps2.executeUpdate();
            }
            ps2.close();
            
            msg+="Floor Details Inserted Successfully!";
            msg=msg+"<br><br>";
            
            
        }
        catch(SQLException e) {
            System.out.println("Exception executing 'Add Floor Query' ==> "+e);
            try
            {connection.rollback();}catch(Exception e1){System.out.println("Exception in Catch"+e1);}
            msg+="<br><br>Exception in adding Floor ==> "+e+"<br>";
            sendMessage(response,msg,"ok");
        }
        
        
        
        
        
        /**************************************************************************************************
         * 									Office Details - Insert
         **************************************************************************************************/
        
        //txtOfficeFloorNo cmbOffice radOffice hidOfficeType HIDtxtFloorRemarks
        
        try
        {
        	off_flrNo = request.getParameterValues("HIDtxtOfficeFloorNo");
	        System.out.println("off_flrNo : " + off_flrNo[0]);
	        off = request.getParameterValues("HIDcmbOffice");
	        System.out.println("off : " + off[0]);
	        off_TN = request.getParameterValues("HIDhidOfficeType");
	        System.out.println("off_TN : " + off_TN[0]);
	        off_Remarks = request.getParameterValues("HIDtxtOfficeRemarks");
	        System.out.println("off_Remarks : " + off_Remarks[0]);
	    }catch(Exception e)
        {
        	System.out.println("Exception fetching Office parameters ==> " + e);
        }
        
	    
        try
        {
            PreparedStatement ps2 = connection.prepareStatement("INSERT INTO FAS_FLOOR_OFFICE_DETAILS " +
																"( ACCOUNTING_UNIT_ID, " +
																"  ACCOUNTING_UNIT_OFFICE_ID, " +
																"  TRANS_DATE, " +
																"  FINANCIAL_YEAR, " +
																"  ASSET_CODE, " +
																"  FLOOR_NO, " +
																"  TYPE_OF_OCCUPYING_OFFICE, " +
																"  TWAD_OFFICE_ID, " +
																"  NONTWAD_OFFICE_NAME, " +
																"  REMARKS, " +
																"  UPDATED_BY_USER_ID, " +
																"  UPDATED_DATE, " +
																"  STATUS " +
																") " +
																"VALUES " +
																"(?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,SYSTIMESTAMP,'Y')");
            System.out.println(ps2);
            
            int TWAD_Office_id = 0;
            String NonTWAD_Office_name = "-";
            for(int i=0; i<off_flrNo.length; i++)
            {
	            ps2.setInt(1,accounting_unit_id);
	            ps2.setInt(2,accounting_unit_office_id);
	            ps2.setString(3,trans_date);
	            ps2.setString(4,financial_year);
	            ps2.setInt(5,asset_code);
	            ps2.setInt(6,Integer.parseInt(off_flrNo[i]));
	            ps2.setString(7,off_TN[i]);
	            
	            TWAD_Office_id = 0;
	            NonTWAD_Office_name = off[i];
	            if(off[i].matches("\\d*"))
	            {
	            	TWAD_Office_id = Integer.parseInt(off[i]);
	            	NonTWAD_Office_name = "-";
	            }
	            System.out.println(TWAD_Office_id);
	            System.out.println(NonTWAD_Office_name);
	            
	            ps2.setInt(8,TWAD_Office_id);
	            ps2.setString(9,NonTWAD_Office_name);
	            ps2.setString(10,off_Remarks[i]);
	            ps2.setString(11,userid);
	        
	            ps2.executeUpdate();
            }
            ps2.close();
            
            msg+="Office Details Inserted Successfully!";
            msg=msg+"<br><br>";
            
            
        }
        catch(SQLException e) {
            System.out.println("Exception executing 'Add Office Query' ==> "+e);
            try
            {connection.rollback();}catch(Exception e1){System.out.println("Exception in Catch"+e1);}
            msg+="<br><br>Exception in adding Office ==> "+e+"<br>";
            sendMessage(response,msg,"ok");
        }
        
        
        

        
        
        
        sendMessage(response,msg,"ok");
    }

    
    
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException 
    {}
    
    
    
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);          
        }
        catch(IOException e)
        {}
    }  

}
