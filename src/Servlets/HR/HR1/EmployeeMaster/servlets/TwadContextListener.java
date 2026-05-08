package Servlets.HR.HR1.EmployeeMaster.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

import javax.servlet.*;
import javax.servlet.http.*;

public class TwadContextListener implements ServletContextListener {
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        String path = "", pathdec = "";
        /*
       //path=context.getRealPath("/Servlet/Security/servlets/Config.enc");
       path=context.getRealPath("/WEB-INF/classes/Servlets/Security/servlets/EncConfig.properties");
        pathdec=context.getRealPath("/WEB-INF/classes/Servlets/Security/servlets/Config.properties");
        File srcFile = new File(path);
        System.out.println("Real path of enc:"+srcFile.getAbsoluteFile());
        File desFile = new File(pathdec);
        System.out.println("Real path of dec:"+desFile.getPath());
        FileInputStream fis=null;
        FileOutputStream fos=null;
        CipherInputStream cis=null;
        try{
        // Creation of Secret key
        byte key[] = "abcdEFGH".getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(key,"DES");

        // Creation of Cipher objects
        Cipher decrypt =
             Cipher.getInstance("DES/ECB/PKCS5Padding");
        decrypt.init(Cipher.DECRYPT_MODE, secretKey);

        // Open the Encrypted file
        try{
        fis = new FileInputStream(srcFile);
        }catch(Exception e){System.out.println("File 1 error:"+e);return;}

        cis = new CipherInputStream(fis, decrypt);

        // Write to the Decrypted file
         try{
        fos = new FileOutputStream(desFile);
            }catch(Exception e){System.out.println("File 2 error:"+e);}
        byte[] b = new byte[8];
        int i = cis.read(b);
        while (i != -1) {
             fos.write(b, 0, i);
             i = cis.read(b);
        }
        fos.flush();
        fos.close();
        cis.close();
        fis.close();
        }  catch(Exception e){
             e.printStackTrace();
        }

        */
        Connection connection = null;
        ResourceBundle rs =
            ResourceBundle.getBundle("Servlets.Security.servlets.Config");

        String ConnectionString = "";
        String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
        String strdsn = rs.getString("Config.DSN");
        String strhostname = rs.getString("Config.HOST_NAME");
        String strportno = rs.getString("Config.PORT_NUMBER");
        String strsid = rs.getString("Config.SID");
        String strdbusername = rs.getString("Config.USER_NAME");
        String strdbpassword = rs.getString("Config.PASSWORD");

        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

        System.out.println(" connection string : " + ConnectionString);
        try {
            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Connection e" + e);
            //RequestDispatcher rd=config.getServletContext().getNamedDispatcher("/index.jsp?message=yes");
            // RequestDispatcher rd=context.getRequestDispatcher("/index.jsp?message=dbnill");
            System.out.println("Context Initialize Error " + e);

            return;
        }
        System.out.println("--->Web Applicatin Started<---");
        if (connection != null) {
            context.setAttribute("Connection", connection);
        }
        //desFile.delete();

    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();
        System.out.println("--->Web Applicatin Stoped<---");
    }
}
