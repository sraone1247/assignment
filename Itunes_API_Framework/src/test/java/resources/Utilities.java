package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Utilities {
	public static RequestSpecification reqSpec;
	public static ResponseSpecification resSpec;
	
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop =new Properties();
		FileInputStream fis =new FileInputStream("src/test/java/resources/global.properties");
		prop.load(fis);
		return prop.getProperty(key);	
	}
	
	public RequestSpecification requestSpecification() throws IOException
	{
		if(reqSpec==null)
		{
		PrintStream log =new PrintStream(new FileOutputStream("logging.txt"));
		reqSpec=new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
				 .addFilter(RequestLoggingFilter.logRequestTo(log))
				 .addFilter(ResponseLoggingFilter.logResponseTo(log))
		.setContentType(ContentType.JSON).build();
		 return reqSpec;
		}
		return reqSpec;
	}
	
	public ResponseSpecification responseSpecification() throws IOException
	{
		
		resSpec=new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
		 return resSpec;
	}
}
