package com.sl.api.salon.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zeasn.common.log.MyLog;
import com.zeasn.common.util.Common;

public class WePayUtil {
	private static final MyLog log = MyLog.getLog(WePayUtil.class);
	
	private WePayUtil(){}
	
	public static boolean isTenpaySign(SortedMap<String, String> packageParams, String apiKey) {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Entry<String, String>> it = packageParams.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			
			if (!"sign".equals(k) && null != v && v.length() > 0) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		sb.append("key=" + apiKey);
		String mysign = Common.md5(sb.toString()).toUpperCase();
		
		Object signValue = packageParams.get("sign");
		if(signValue != null){
			String tenpaySign = ((String) signValue).toUpperCase();	
			return tenpaySign.equals(mysign);
			
		}else{
			return false;
		}
	}
	
	public static String createSign(SortedMap<String, String> packageParams, String apiKey) {
		StringBuilder sb = new StringBuilder();
		
		Iterator<Entry<String, String>> it = packageParams.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			
			if (v != null && v.length() > 0 && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + apiKey);
		
		return Common.md5(sb.toString()).toUpperCase();
	}
	
	public static String getRequestXml(SortedMap<String, String> parameters) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		
		Iterator<Entry<String, String>> it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		
		return sb.toString();
	}
	
	public static String getRequestXmlTransparent(SortedMap<String, String> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			
			sb.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
		}
		sb.append("</xml>");
		
		return sb.toString();
	}
	
	public static String getNonceString(){
		SimpleDateFormat outFormat = new SimpleDateFormat("HHmmssSSS");
		String s = outFormat.format(new Date());
		
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		int  d = (int) ((random * 10000));
		
		return s + d;
	}
	
	/**
	 * postData
	 * @param urlStr
	 * @param data
	 * @return return null means fail
	 */
	public static String postData(String urlStr, String data) {
		HttpURLConnection conn = null;
		OutputStreamWriter osw = null;
		OutputStream os = null;

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		

		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);

			os = conn.getOutputStream();
				
			osw = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
			if (data == null){
				data = "";
			}
				
			osw.write(data);
			osw.flush();

			is = conn.getInputStream();
			isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			in = new BufferedReader(isr);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
			return sb.toString();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				close(os, osw, null);
				close(is, isr, in);
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return null;
	}
	
	public static SortedMap<String, String> getCallBackSignParams(HttpServletRequest request) {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader in = null;

		try {
			StringBuilder sb = new StringBuilder();
			is = request.getInputStream();
			String str;
			isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			in = new BufferedReader(isr);
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}

			Map<String, String> m = new HashMap<>();
			m = doXMLParse(sb.toString());
			// sort key nature
			SortedMap<String, String> packageParams = new TreeMap<>();
			Iterator<String> it = m.keySet().iterator();
			// remove null
			while (it.hasNext()) {
				String parameter = it.next();
				String parameterValue = m.get(parameter);
				String v = "";
				if (null != parameterValue) {
					v = parameterValue.trim();
				}
				packageParams.put(parameter, v);
			}
			return packageParams;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
		} finally {
			close(is, isr, in);
		}
		
		return null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static Map<String, String> doXMLParse(String strxml) {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, String> m = new HashMap<>();
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(strxml.getBytes(StandardCharsets.UTF_8));
			SAXReader reader = new SAXReader();

			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";

				List<Element> children = e.elements();
				if (children.isEmpty()) {
					v = e.getText();
				} else {
					v = getChildrenText(children);
				}
				m.put(k, v);
			}
			return m;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			Common.closeStream(is);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static String getChildrenText(List children) {
		StringBuilder sb = new StringBuilder();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getText();
				List list = e.elements();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}
	
	private static void close(InputStream is, InputStreamReader isr, BufferedReader in) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (isr != null) {
			try {
				isr.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	private static void close(OutputStream os, OutputStreamWriter osw, BufferedWriter out) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (osw != null) {
			try {
				osw.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
