import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class Connect{
	public static void main(String[] args){
		new ConnectionTest();
	}
}

class ConnectionTest{
	String username = "root";
	String ipadress = "http://130.238.15.239/lvl2/phptest.php?";
	String password = "Fleron22#";
	String empID = "21";

	public ConnectionTest(){
		try{
			String data = URLEncoder.encode("name", "UTF-8")+ "="+URLEncoder.encode(username, "UTF-8");
			data += "&"+URLEncoder.encode("pwd", "UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
			data += "&"+URLEncoder.encode("emp", "UTF-8")+"="+URLEncoder.encode(empID,"UTF-8");
		
			URL url = new URL(ipadress+data);
			URLConnection conn = url.openConnection();

			conn.setDoOutput(true);
			/*
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			*/
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
			StringBuilder sb = new StringBuilder();
			String line = null;
			
			while((line = reader.readLine())!= null)
			{
				sb.append(line);
				break;
			}
			String theLine = sb.toString();
			System.out.println(theLine);
			/*
			JSONObject json = new JSONObject(sb.toString());
			System.out.println(json.getString("14"));
			*/
		}
		catch(Exception e){
			System.out.println("shit: "+e.getMessage());

		}
	}		
}
