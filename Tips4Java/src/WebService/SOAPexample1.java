// package WebService;

/*
 *  http://www.codeproject.com/KB/XML/WSfromJava.aspx
 * 
 */
import java.applet.Applet;
import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class SOAPexample1 extends Applet {

  private String response = "Nothing";

  public void init() {
    SoapRequestBuilder s = new SoapRequestBuilder();
    s.Server = getParameter("server");
    s.MethodName = getParameter("method");
    s.XmlNamespace = getParameter("xmlnamespace");
    s.WebServicePath = getParameter("webservicepath");
    s.SoapAction = s.XmlNamespace+s.MethodName;
    s.AddParameter("agentId", getParameter("string1"));
    s.AddParameter("lastVersion", getParameter("string2"));
    response = s.sendRequest();
    repaint();
  }

  public void paint(Graphics g) {
    g.setColor(Color.black);
    Font f = new Font("TimesRoman", 0, 20);
    g.setFont(f);
    g.drawString(response, 10, 20);
  }

  public void start() {
    repaint();
  }
}

class SoapRequestBuilder {
  String Server = "";
  String WebServicePath = "";
  String SoapAction = "";
  String MethodName = "";
  String XmlNamespace = "";
  private Vector ParamNames = new Vector();
  private Vector ParamData = new Vector();

  public void AddParameter(String Name, String Data) {
    ParamNames.addElement( (Object) Name);
    ParamData.addElement( (Object) Data);
  }

  public String sendRequest() {
    String retval = "";
    Socket socket = null;
    try {
      socket = new Socket(Server, 80);
    }
    catch (Exception ex1) {
      return ("Error: "+ex1.getMessage());
    }

    try {
      OutputStream os = socket.getOutputStream();
      boolean autoflush = true;
      PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.
          getInputStream()));

      int length = 295 + (MethodName.length() * 2) + XmlNamespace.length();
      for (int t = 0; t < ParamNames.size(); t++) {
        String name = (String) ParamNames.elementAt(t);
        String data = (String) ParamData.elementAt(t);
        length += name.length();
        length += data.length();
      }

      // send an HTTP request to the web service
      out.println("POST " + WebServicePath + " HTTP/1.1");
      out.println("Host: 192.168.61.28");
      out.println("Content-Type: text/xml; charset=utf-8");
      out.println("Content-Length: " + String.valueOf(length));
      out.println("SOAPAction: \"" + SoapAction + "\"");
      out.println("Connection: Close");
      out.println();

      out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      out.println("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
      out.println("<soap:Body>");
      out.println("<" + MethodName + " xmlns=\"" + XmlNamespace + "\">");
      //Parameters passed to the method are added here
      for (int t = 0; t < ParamNames.size(); t++) {
        String name = (String) ParamNames.elementAt(t);
        String data = (String) ParamData.elementAt(t);
        out.println("<" + name + ">" + data + "</" + name + ">");
      }
      out.println("</" + MethodName + ">");
      out.println("</soap:Body>");
      out.println("</soap:Envelope>");
      out.println();

      // Read the response from the server ... times out if the response takes
      // more than 3 seconds
      String inputLine;
      StringBuffer sb = new StringBuffer(1000);

      int wait_seconds = 3;
      boolean timeout = false;
      long m = System.currentTimeMillis();
      while ( (inputLine = in.readLine()) != null && !timeout) {
        sb.append(inputLine + "\n");
        if ( (System.currentTimeMillis() - m) > (1000 * wait_seconds)) timeout = true;
      }
      in.close();

      // The StringBuffer sb now contains the complete result from the
      // webservice in XML format.  You can parse this XML if you want to
      // get more complicated results than a single value.

      if (!timeout) {
        String returnparam = MethodName + "Result";
        int start = sb.toString().indexOf("<" + returnparam + ">") +
            returnparam.length() + 2;
        int end = sb.toString().indexOf("</" + returnparam + ">");

        //Extract a singe return parameter
        retval = sb.toString().substring(start, end);
      }
      else {
        retval="Error: response timed out.";
      }

      socket.close();
    }
    catch (Exception ex) {
      return ("Error: cannot communicate.");
    }

    return retval;
  }
}
