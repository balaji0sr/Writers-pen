//$Id$
package com.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;

@Controller
public class AttachmentControl {

	@RequestMapping(value = "/attachmentservice", method = RequestMethod.POST)
	@ResponseBody
	public void userphoto(@RequestParam("file") CommonsMultipartFile files, HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ServletException {

		System.out.println("Inside writerspen attachmentservice");

		HttpSession ses = req.getSession();
		int useridint = (int) ses.getAttribute("userid");
		long userid = Long.valueOf(useridint);
		String contenttype = files.getContentType();
		String type = contenttype.substring(contenttype.indexOf("/") + 1);

		long contentsize = files.getSize();

		String responseString = null;
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			SSLContext sslContext = SSLContext.getDefault();
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient httpclient = httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
			String uristring = "http://localhost:8080/FileServer/attachmentservice/" + userid + "/" + type + "/" + contentsize;
			System.out.println(uristring);
			URIBuilder uriBuilder = new URIBuilder(uristring);

			HttpUriRequest requestObj = new HttpPost(uriBuilder.build());

			HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) requestObj;

			MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();

			@SuppressWarnings("resource")
			InputStream stream = new ByteArrayInputStream(files.getBytes());

			byte[] buffer = new byte[8192];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = stream.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
			System.out.println("getOriginalFilename :::" + files.getOriginalFilename());
			System.out.println("getContentType :::" + files.getContentType());
			System.out.println("getSize :::" + files.getSize());

			multipartEntity.addPart(files.getOriginalFilename(), new ByteArrayBody(output.toByteArray(), files.getOriginalFilename()));
			requestBase.setEntity(multipartEntity.build());
			HttpResponse response = httpclient.execute(requestObj);
			HttpEntity responseEntity = response.getEntity();
			Object responseObject = EntityUtils.toString(responseEntity);
			responseString = responseObject.toString();
			System.out.println("responseString " + responseString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Gson g = new Gson();
		String gsonstring = g.toJson(responseString);

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}
}
/*
 * public class AttachmentControl {
 * 
 * @RequestMapping(value = "/attachmentservice", method = RequestMethod.POST)
 * 
 * @ResponseBody public void userphoto(@RequestParam("fd") CommonsMultipartFile files , HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ServletException {
 * 
 * System.out.println("Inside writerspen attachmentservice"); System.out.println(files.getOriginalFilename() + "  " + files.getName());
 * 
 * HttpSession ses = req.getSession(); int useridint = (int) ses.getAttribute("userid"); long userid = Long.valueOf(useridint);
 * 
 * String responseString = null; try { HttpClientBuilder httpClientBuilder = HttpClientBuilder.create(); SSLContext sslContext = SSLContext.getDefault(); SSLConnectionSocketFactory
 * sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE); CloseableHttpClient httpclient =
 * httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
 * 
 * URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/FileServer/attachmentservice/" + userid);
 * 
 * HttpUriRequest requestObj = new HttpPost(uriBuilder.build());
 * 
 * HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) requestObj;
 * 
 * MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
 * 
 * @SuppressWarnings("resource") InputStream stream = new ByteArrayInputStream(files.getBytes());
 * 
 * byte[] buffer = new byte[8192]; ByteArrayOutputStream output = new ByteArrayOutputStream(); int bytesRead; while ((bytesRead = stream.read(buffer)) != -1) { output.write(buffer, 0, bytesRead); }
 * System.out.println("getOriginalFilename :::" + files.getOriginalFilename()); System.out.println(files.getContentType()); System.out.println(files.getName()); System.out.println(files.getSize());
 * System.out.println(files.getStorageDescription());
 * 
 * 
 * multipartEntity.addPart(files.getOriginalFilename(), new ByteArrayBody(output.toByteArray(), files.getOriginalFilename())); requestBase.setEntity(multipartEntity.build()); HttpResponse response =
 * httpclient.execute(requestObj); HttpEntity responseEntity = response.getEntity(); Object responseObject = EntityUtils.toString(responseEntity); responseString = responseObject.toString();
 * System.out.println("responseString " + responseString); } catch(Exception ex) { ex.printStackTrace(); }
 * 
 * Gson g = new Gson(); String gsonstring = g.toJson(responseString);
 * 
 * res.setContentType("application/json"); res.getWriter().write(gsonstring); return; } }
 */
