function makeArray0() 
{
	for (i = 0; i<makeArray0.arguments.length; i++)
	this[i] = makeArray0.arguments[i];
}

var numbers = new makeArray0('','one','two','three','four','five','six','seven','eight','nine','ten','eleven','twelve','thirteen','fourteen','fifthteen','sixteen','seventeen','eighteen','nineteen');
	
var numbers10 = new makeArray0('','ten','twenty','thirty','fourty','fifty','sixty','seventy','eighty','ninety');

function chequeAmount(input) 
{
	var rupees = Math.floor(input);
	var paise = Math.round((input*100 - rupees*100));
 
	var crores = (rupees - rupees % 10000000) / 10000000;
	rupees -= crores * 10000000;
 
	var lakhs = (rupees - rupees % 100000) / 100000;
	rupees -= lakhs * 100000;
  
	var thousands = (rupees - rupees % 1000) / 1000;
	rupees -= thousands * 1000;
  
	var hundreds = (rupees - rupees % 100) / 100;
	rupees -= hundreds * 100;

	var output = '';
  
	output += (crores > 0 ? fN(crores) + ' crore ' : '') +
  
	(lakhs > 0 ? fN(lakhs) + ' Lakh ' : '') +
  
	(thousands > 0 ? fN(thousands) + ' Thousand ' : '') +
   
    (hundreds > 0 ? fN(hundreds) + ' Hundred ' : '') +
            
    (rupees > 0 ? fN(rupees) + ' ' : '') +
            
//    ((crores>0 || lakhs > 0 || thousands > 0 || hundreds > 0 || rupees > 0) ? '' : '') +

    ((crores>0 || lakhs > 0 || thousands > 0 || hundreds > 0 || rupees > 0) ? '' : '') +            

    ((Math.floor(input) > 0 && paise > 0) ? 'and ' : '') +
    (paise > 0 ? fN(paise) + ' Paise' : '');
	return output.substring(0,1).toUpperCase() + output.substring(1);
}
function fN(i) 
{
	if (i<20) return numbers[i];
	var tens = (i - i % 10) / 10, units = i - (i - i % 10);
	return numbers10[tens] + ((tens > 0 && units > 0) ? '-' : '') + numbers[units];
}
function Call_Text()
{
	var X = "Rupees " + chequeAmount(document.Frm_RFQPI.txt_grand.value - 0) + " Only" ;
	GrandAmt.innerHTML=X;
	//window.print();
}