/**
 * 
 */
package com.cvirn.ferndaleforms2.cloud;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.cvirn.ferndaleforms2.constants.LMReport;

import android.util.Log;

/**
 * @author janko
 * 
 */
public class WebServClient implements Serializable {

	/**
	 * 
	 */
	private static final String API_URL="";
	private static final long serialVersionUID = -5161378112130927111L;
	protected String username;
	protected String password;
	protected String action;
	protected String report_nr;
	protected String report_type;
	protected HashMap<String, String> report_data;
	
	

	

	public String getReport_nr() {
		return report_nr;
	}

	public void setReport_nr(String report_nr) {
		this.report_nr = report_nr;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public HashMap<String, String> getReport_data() {
		return report_data;
	}

	public void setReport_data(HashMap<String, String> report_data) {
		this.report_data = report_data;
	}

	static final String KEY_ACCTION = "Action";
	static final String KEY_VALUE = "Value";
	
	

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
			pairs.add(new BasicNameValuePair("action", getAction()));
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
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				API_URL);
		String xml = "empty";
		try {
			// Add data to your post
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(2);
			pairs.add(new BasicNameValuePair("username", getUsername()));
			pairs.add(new BasicNameValuePair("password", getPassword()));
			pairs.add(new BasicNameValuePair("action", LMReport.API_Action));
			pairs.add(new BasicNameValuePair("reportnr", getReport_nr()));
			pairs.add(new BasicNameValuePair("reporttype", getReport_type()));
			
			pairs.add(new BasicNameValuePair(LMReport.LMR_Cutomer, getReport_data().get(LMReport.LMR_Cutomer)));
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
	

}
