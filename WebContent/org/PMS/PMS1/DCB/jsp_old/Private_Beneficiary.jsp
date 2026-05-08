<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Dcb_Mst_Private</title>
    <link href='../../../../../css/Sample3.css' rel='stylesheet' media='screen'/>
     <script type="text/javascript" src="../scripts/Private_Beneficiary.js"></script>
     <script type="text/javascript" src="../scripts/Basic.js"></script>
  </head>
  <body onload="callServer('Get'); callServer('Type'); callServer('District');" >
  <form name="mstprivate" action="">
  <table border="1" width="80%" align="center">
    <tr class="tdH" align="center" style="color:black">
        <td colspan="2"> <div align="center"><strong>Private Beneficiaries</strong></div></td>
    </tr>
    <tr class="table">
        <td>Private Beneficiary Code</td>
        <td><input type="text" name="sno" id="sno" maxlength="5" size="5" readonly="readonly" style="background-color: #ececec"/><small>(Auto generated)</small></td>
    </tr>
           
        
    <tr class="table">
        <td>District</td>
        <td>
        	<select name="dis" id="dis" />
        		<option value="">--Select District--</option>
        	</select>
        </td>
    </tr>
    
    
    <tr class="table">
        <td>Private Beneficiary Type</td>
        <td>
        	<select name="type" id="type" onchange="callServer('Get');" />
        		<option value="">--Select Beneficiary Type--</option>
        	</select>
        </td>
    </tr>
 
    
    <tr class="table">
        <td>Private Beneficiary Name</td>
        <td><input type="text" name="desc" maxlength="35" size="35" id="desc" /></td> <!-- style="TEXT-TRANSFORM:UPPERCASE"  -->
    </tr>
    
           
        
    <tr class="table">
        <td>Billing Name</td>
        <td><input type="text" name="adr" maxlength="35" size="35" id="adr" /> (Eg: &nbsp; Ms/Mr XYZ, Designation)</td> 
        <!-- style="TEXT-TRANSFORM:UPPERCASE"  -->
    </tr>
    
    <!-- <tr class="table">
        <td>Private Group Reference</td>
        <td><input type="text" name="Private_Group_Reference" maxlength="35" size="35" id="Private_Group_Reference" style="TEXT-TRANSFORM:UPPERCASE" />
        <select id ="Private_Group_Reference">
            <option>- - -Select - - -</option>
        </select>
        </td>
    </tr>-->


    <tr class="tdH" align="center">
        <td colspan="2" >               
                <input type="button" name="Add" value="Add" id="Add" onclick="callServer('Add')"/>
                <input type="button" name="Update" value="Update" id="Update" onclick="callServer('Update')" disabled/>
                <input type="button" name="Delete" value="Delete" id="Delete" onclick="callServer('Delete')" disabled/>
                <input type="reset" name="Clear" value="Clear"  id="Clear" onclick="clearAll(); callServer('Get');"/>
                <input type="button" name="Exit" value="Exit"  id="Exit" onclick="self.close()"/>              
        </td>
    </tr>
   
    </table>
     </form>
    <table id="existing" border="1" width="80%" align="center">
        <tr>
            <th class="tdH">Select
            <th class="tdH">Private Beneficiary</th>
            <th class="tdH">Beneficiary Type</th>
            <th class="tdH">District</th>
            <th class="tdH">Billing Name</th>
          <!--   <th class="tdH">Private Group Reference</th>-->
        </tr>
        <tbody id="tblList" name="tblList" class="table">
        </tbody>
    </table>
  </body>
</html>