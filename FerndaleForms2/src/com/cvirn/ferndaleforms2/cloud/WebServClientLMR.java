/**
 * 
 */
package com.cvirn.ferndaleforms2.cloud;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.cvirn.ferndaleforms2.bean.LMREquipmentBean;
import com.cvirn.ferndaleforms2.bean.LMRJobBean;
import com.cvirn.ferndaleforms2.bean.LMRLaborBean;
import com.cvirn.ferndaleforms2.bean.LMRMaterialBean;
import com.cvirn.ferndaleforms2.bean.LMRSubConBean;
import com.cvirn.ferndaleforms2.constants.LMReport;

import android.os.Environment;
import android.util.Log;

/**
 * @author janko
 * 
 */
public class WebServClientLMR implements Serializable {

	/**
	 * 
	 */
	private static final String API_URL="http://108.179.218.75/services.php";
	private static final long serialVersionUID = -5161378112130927111L;
	protected String username;
	protected String password;
	protected String action;
	
	protected LMRJobBean jobbean;
	protected LMRMaterialBean mat_bean;
	protected LMRLaborBean labor_bean;
	protected LMREquipmentBean equip_bean;
	protected LMRSubConBean sub_bean;
	
	final static String[] actions= {"setLMRJob","setLMRLabor","setLMRMat","setLMREqu","setLMRSub"};
	
	
	final static String[] parameters_job= {"custnr","fec","jobn",
		                                 "dat","work","jobl",
		                                 "job","cust"};

	
	final static String[] parameters_labor= {"w","st","ht","dt","jobnr"};
	
	final static String[] parameters_mat= {"n","a","jobnr"};
	
	final static String[] parameters_equip= {"n","a","jobnr"};
	
	final static String[] parameters_sub= {"n","a","jobnr"};
	

	
	static final String KEY_ACCTION = "Action";
	static final String KEY_VALUE = "Value";
	
	
	
	public LMRSubConBean getSub_bean() {
		return sub_bean;
	}

	public void setSub_bean(LMRSubConBean sub_bean) {
		this.sub_bean = sub_bean;
	}

	public LMRMaterialBean getMat_bean() {
		return mat_bean;
	}

	public void setMat_bean(LMRMaterialBean mat_bean) {
		this.mat_bean = mat_bean;
	}

	public LMRLaborBean getLabor_bean() {
		return labor_bean;
	}

	public void setLabor_bean(LMRLaborBean labor_bean) {
		this.labor_bean = labor_bean;
	}

	public LMREquipmentBean getEquip_bean() {
		return equip_bean;
	}

	public void setEquip_bean(LMREquipmentBean equip_bean) {
		this.equip_bean = equip_bean;
	}

	public void setJobbean(LMRJobBean jobbean) {
		this.jobbean = jobbean;
	}
	
	public LMRJobBean getJobbean() {
		return jobbean;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginResult() throws IllegalStateException, IOException {

		String xml = getLoginPost();
		Document doc = getDomElement(xml);
		NodeList nl = doc.getElementsByTagName(KEY_ACCTION);
		Log.d("XML 2", nl.toString());
		Element e = (Element) nl.item(0);
		String res = getValue(e, KEY_VALUE);

		Log.d("WS result", res);
		return res;

	}

	public String getLoginPost() {

		// To use these Internet methods, AndroidManifest.xml must have the
		// following permission:
		// <uses-permission android:name="android.permission.INTERNET"/>
		// Create the Apache HTTP client and post
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action", "getLogin"));
			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Finally, execute the request
			HttpResponse webServerAnswer = httpclient.execute(httppost);

			HttpEntity httpEntity = webServerAnswer.getEntity();

			if (httpEntity != null) {

				xml = EntityUtils.toString(httpEntity);
				Log.d("XML", xml);
			}

		} catch (ClientProtocolException e) {
			// Deal with it
		} catch (IOException e) {
			// Deal with it
		}

		return xml;

	}
	
	public String getPostNewReport() {

		// To use these Internet methods, AndroidManifest.xml must have the
		// following permission:tring
		// <uses-permission android:name="android.permission.INTERNET"/>
		// Create the Apache HTTP client and post
		//parameters= {"customernr","fecmanager","jobnumber",
        //			   "date","workperformed","joblocation",
        //			   "jobname","customer"};
		
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action",actions[0]));
			
			
			/// Specific data
			pairs.add(new BasicNameValuePair(parameters_job[0], getJobbean().getCustomernr()));
			pairs.add(new BasicNameValuePair(parameters_job[1], getJobbean().getFecmanager()));
			pairs.add(new BasicNameValuePair(parameters_job[2], getJobbean().getJobnumber()));
			pairs.add(new BasicNameValuePair(parameters_job[3], getJobbean().getDate()));
			pairs.add(new BasicNameValuePair(parameters_job[4], getJobbean().getWorkperformed()));
			pairs.add(new BasicNameValuePair(parameters_job[5], getJobbean().getJoblocation()));
			pairs.add(new BasicNameValuePair(parameters_job[6], getJobbean().getJobname()));
			pairs.add(new BasicNameValuePair(parameters_job[7], getJobbean().getCustomer()));
			
			Log.d("WSERV","sending "+parameters_job.length);
			
			//TODO add other parameters
			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Finally, execute the request
			HttpResponse webServerAnswer = httpclient.execute(httppost);

			HttpEntity httpEntity = webServerAnswer.getEntity();

			if (httpEntity != null) {

				xml = EntityUtils.toString(httpEntity);
				Log.d("XML", xml);
			}

		} catch (ClientProtocolException e) {
			Log.d("XML",e.getLocalizedMessage());
		} catch (IOException e) {
			Log.d("XML",e.getLocalizedMessage());
			
		}

		return xml;

	}
	public String getPostReportDetailsLabor() {

		// To use these Internet methods, AndroidManifest.xml must have the
		// following permission:tring
		// <uses-permission android:name="android.permission.INTERNET"/>
		// Create the Apache HTTP client and post
		
		
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action", actions[1]));
			
			
			/// Specific data
			
			pairs.add(new BasicNameValuePair(parameters_job[2], getJobbean().getJobnumber()));
			pairs.add(new BasicNameValuePair(parameters_labor[0], getLabor_bean().getWorker()));
			pairs.add(new BasicNameValuePair(parameters_labor[1], getLabor_bean().getStime()));
			pairs.add(new BasicNameValuePair(parameters_labor[2], getLabor_bean().getHtime()));
			pairs.add(new BasicNameValuePair(parameters_labor[3], getLabor_bean().getDtime()));
			
			
			//TODO add other parameters
			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Finally, execute the request
			HttpResponse webServerAnswer = httpclient.execute(httppost);

			HttpEntity httpEntity = webServerAnswer.getEntity();

			if (httpEntity != null) {

				xml = EntityUtils.toString(httpEntity);
				Log.d("XML", xml);
			}

		} catch (ClientProtocolException e) {
			// Deal with it
		} catch (IOException e) {
			// Deal with it
		}

		return xml;

	}
	
	
	public String getPostReportDetailsMat() {

		// To use these Internet methods, AndroidManifest.xml must have the
		// following permission:tring
		// <uses-permission android:name="android.permission.INTERNET"/>
		// Create the Apache HTTP client and post
		
		
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action", actions[2]));
			
			
			/// Specific data
			
			pairs.add(new BasicNameValuePair(parameters_job[2], getJobbean().getJobnumber()));
			pairs.add(new BasicNameValuePair(parameters_mat[0], getMat_bean().getName()));
			pairs.add(new BasicNameValuePair(parameters_mat[1], getMat_bean().getAmmount()));
			
			
			//TODO add other parameters
			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Finally, execute the request
			HttpResponse webServerAnswer = httpclient.execute(httppost);

			HttpEntity httpEntity = webServerAnswer.getEntity();

			if (httpEntity != null) {

				xml = EntityUtils.toString(httpEntity);
				Log.d("XML", xml);
			}

		} catch (ClientProtocolException e) {
			// Deal with it
		} catch (IOException e) {
			// Deal with it
		}

		return xml;

	}
	
	public String getPostReportDetailsEquip() {

		// To use these Internet methods, AndroidManifest.xml must have the
		// following permission:tring
		// <uses-permission android:name="android.permission.INTERNET"/>
		// Create the Apache HTTP client and post
		
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action", actions[3]));
			
			
			/// Specific data
			
			pairs.add(new BasicNameValuePair(parameters_job[2], getJobbean().getJobnumber()));
			pairs.add(new BasicNameValuePair(parameters_equip[0], getEquip_bean().getName()));
			pairs.add(new BasicNameValuePair(parameters_equip[1], getEquip_bean().getAmmount()));
			
			
			//TODO add other parameters
			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Finally, execute the request
			HttpResponse webServerAnswer = httpclient.execute(httppost);

			HttpEntity httpEntity = webServerAnswer.getEntity();

			if (httpEntity != null) {

				xml = EntityUtils.toString(httpEntity);
				Log.d("XML", xml);
			}

		} catch (ClientProtocolException e) {
			// Deal with it
		} catch (IOException e) {
			// Deal with it
		}

		return xml;

	}
	
	public String getPostReportDetailsSubCon() {

		// To use these Internet methods, AndroidManifest.xml must have the
		// following permission:tring
		// <uses-permission android:name="android.permission.INTERNET"/>
		// Create the Apache HTTP client and post
		
		
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action", actions[4]));
			
			
			/// Specific data
			
			pairs.add(new BasicNameValuePair(parameters_job[2], getJobbean().getJobnumber()));
			pairs.add(new BasicNameValuePair(parameters_equip[0], getSub_bean().getName()));
			pairs.add(new BasicNameValuePair(parameters_equip[1], getSub_bean().getAmmount()));
			
			
			//TODO add other parameters
			httppost.setEntity(new UrlEncodedFormEntity(pairs));

			// Finally, execute the request
			HttpResponse webServerAnswer = httpclient.execute(httppost);

			HttpEntity httpEntity = webServerAnswer.getEntity();

			if (httpEntity != null) {

				xml = EntityUtils.toString(httpEntity);
				Log.d("XML", xml);
			}

		} catch (ClientProtocolException e) {
			// Deal with it
		} catch (IOException e) {
			// Deal with it
		}

		return xml;

	}
	
	
	
	// /////////////////////////////////////////////////
	// XML PARSING PART
	public Document getDomElement(String xml) {

		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (Exception e) {
			Log.e("XML parser", e.getLocalizedMessage());
			return null;
		}
		return doc;

	}
	
	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	
public String DoFileUpload (String job_id) {
		
		String serverResponseMessage="";
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		//DataInputStream inputStream = null;
		Log.d("FILE",job_id);
		//TODO add webservice for upload
		String urlServer = "http://108.179.218.75/signature/upload.php";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;
		
		try
		{
		
			
			
			
		FileInputStream fileInputStream = new FileInputStream(new File(extStorageDirectory,job_id+"Signature.jpg") );
		Log.d("FILE",fileInputStream.getFD().toString());
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
		outputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\"" + job_id+"Signature.jpg" +"\"" + lineEnd);
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
