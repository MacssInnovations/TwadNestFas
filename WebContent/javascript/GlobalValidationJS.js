function IsNumeric(Object)
{
var strValidChars = "0123456789.-";
var strChar;
var flag = true;
var strString=Object.value;
var ids=Object.id;


if (strString.length == 0) return false;

for (var i = 0; i < strString.length && flag == true; i++)
   {
   strChar = strString.charAt(i);
   if (strValidChars.indexOf(strChar) == -1)
      {
      flag = false;
      alert('Please Enter numeric value only');
      Object.value="";

	  return flag;
      }
   }
return flag;
}
