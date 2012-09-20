package com.cvirn.ferndaleforms2.cloud;

//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

//import android.net.Uri;
import android.os.Environment;

public class WebServClientSignature implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public String DoFileUpload (String job_id) throws Exception{
		
		String serverResponseMessage="";
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		//DataInputStream inputStream = null;

		//TODO add webservice for upload
		String urlServer = "http://appcloud.esd-strompartner.de/service2.php";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;
		
		try
		{
		FileInputStream fileInputStream = new FileInputStream(new File(extStorageDirectory,job_id+"Signature.jpeg") );

		URL url = new URL(urlServer);
		connection = (HttpURLConnection) url.openConnection();

		// Allow Inputs & Outputs
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		// Enable POST method
		connection.setRequestMethod("POST");

		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

		outputStream = new DataOutputStream( connection.getOutputStream() );
		outputStream.writeBytes(twoHyphens + boundary + lineEnd);
		outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + job_id+"Signature.jpeg" +"\"" + lineEnd);
		outputStream.writeBytes(lineEnd);

		bytesAvailable = fileInputStream.available();
		bufferSize = Math.min(bytesAvailable, maxBufferSize);
		buffer = new byte[bufferSize];

		// Read file
		bytesRead = fileInputStream.read(buffer, 0, bufferSize);

		while (bytesRead > 0)
		{
		outputStream.write(buffer, 0, bufferSize);
		bytesAvailable = fileInputStream.available();
		bufferSize = Math.min(bytesAvailable, maxBufferSize);
		bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		}

		outputStream.writeBytes(lineEnd);
		outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

		// Responses from the server (code and message)
		//int serverResponseCode = connection.getResponseCode();
		serverResponseMessage = connection.getResponseMessage();
		
		fileInputStream.close();
		outputStream.flush();
		outputStream.close();
		}
		catch (Exception ex)
		{
		//Exception handling
		}
		return serverResponseMessage;
	
	}
}
