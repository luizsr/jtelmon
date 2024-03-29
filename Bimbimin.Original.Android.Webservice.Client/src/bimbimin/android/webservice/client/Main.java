/*
 * http://bimbim.in/post/2010/10/08/Android-Calling-Web-Service-with-complex-types.aspx
 */

package bimbimin.android.webservice.client;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;

import bimbimin.android.webservice.dto.Person;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        ServiceCall call = new ServiceCall();

        String Result1 = call.CallHelloWorld();
        Toast.makeText(this, "Result: " + Result1 + "\n", Toast.LENGTH_LONG).show() ;
        System.out.println ("The Result is :"+Result1);
        Person out = call.CallGetSingle();
        String Result2 = out.get_name();
        
        String Result3 = new String();
        int result3 = out.get_age();
        Result3	= String.valueOf(result3);
        float salar = out.get_salary();
        String Salar = new String();
        Salar = String.valueOf(salar);
        Date DataNastere= new Date();
        
        Date date = new Date();
        DataNastere=out.get_dob();
        
        //String dtStart = "2010-10-15T09:27:37Z";
        //dTStart = String.valueOf(value);
        
        date = DataNastere;
        java.text.DateFormat dateFormat =
            android.text.format.DateFormat.getDateFormat(getApplicationContext());
        // mTimeText.setText("Time: " + dateFormat.format(date));
        String dob = new String();
        dob = dateFormat.format(date);
        
//        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");  
//        try {  
//            Date date = format.parse(dtStart);  
//            System.out.println(date);  
//        } catch (ParseException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        }

        String sText = 
        "Name:    " + Result2 + "\n" +
        "Age:     " + Result3 + "\n " +
        "Salar:   " + Salar + "\n" +
        "Data :   " + dob + "\n" ;
        
        
        //Toast.makeText(this, "Name:    " + Result2 + "\n", Toast.LENGTH_LONG).show(); 
        //Toast.makeText(this, "Age:     " + Result3 + "\n", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Salar:   " + Salar + "\n", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, "Data N.: " + dob + "\n", Toast.LENGTH_LONG).show();
        Toast.makeText(this, sText + "\n", Toast.LENGTH_LONG).show();
        
        // Boolean res = call.CallSetValue(out);
    }
}

/* HelloWorld.asmx ->
<%@ WebService Language="C#" class="HelloWorld"%>
using System.Web.Services;
public class HelloWorld : WebService
{
[WebMethod]
public string HelloWorldMethod()
{
return "Hello World";
}

[WebMethod]
    public Person GetSingle()
    {
        Person person = new Person();
        person.Name = "bimbim.in";
        person.Age = 30;
        person.Dob = new System.DateTime(1980, 01, 15);
        person.Salary = 50000f;
        return person;
    }
}
*/
