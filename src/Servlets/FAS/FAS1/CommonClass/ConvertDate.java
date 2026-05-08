package Servlets.FAS.FAS1.CommonClass;

import java.sql.Date;

public class ConvertDate {


    public java.sql.Date convert(String txtDrLUpdate) {
        java.sql.Date debitdate = null;
        java.util.GregorianCalendar c2;
        try {


            //  String txtDrLUpdate=request.getParameter("txtDrLUpdate");
            String[] deb_Date = txtDrLUpdate.split("/");
            c2 =
  new java.util.GregorianCalendar(Integer.parseInt(deb_Date[2]), Integer.parseInt(deb_Date[1]) -
                                  1, Integer.parseInt(deb_Date[0]));
            java.util.Date ddd = c2.getTime();
            debitdate = new Date(ddd.getTime());
        } catch (Exception e) {
            //System.out.println(e);

        }

        return debitdate;

    }

    public int ConvertInt(String Varible) {
        int x = Integer.parseInt(Varible);
        return x;

    }

    public Double ConvertDouble(String input) {
        Double y = Double.parseDouble(input);
        return y;

    }

}
