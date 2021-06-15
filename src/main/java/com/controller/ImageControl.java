//$Id$
package com.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
public class ImageControl {

	@RequestMapping(value = "/imageservice", method = RequestMethod.POST)
	@ResponseBody
	public void userphoto(HttpServletRequest req, HttpServletResponse res) throws SQLException, IOException, ServletException, Exception {

		String responseString = null;
		System.out.println( "inside the writerspen image upload" );
		try
		{
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			SSLContext sslContext = SSLContext.getDefault();
			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			CloseableHttpClient httpclient = httpClientBuilder.setSSLSocketFactory(sslConnectionSocketFactory).build();
			
			URIBuilder uriBuilder = new URIBuilder("http://localhost:8080/FileServer/imageservice");
			
			HttpUriRequest requestObj = new HttpPost(uriBuilder.build());
			
			HttpEntityEnclosingRequestBase requestBase = (HttpEntityEnclosingRequestBase) requestObj;
			
			MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
	
			@SuppressWarnings("resource")
			InputStream stream = IOUtils.toInputStream( req.getParameter("encodedbase64") );
			
			
			byte[] buffer = new byte[8192];
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = stream.read(buffer)) != -1)
			{
			    output.write(buffer, 0, bytesRead);
			}
		
			multipartEntity.addPart("file", new ByteArrayBody(output.toByteArray(), "testingBalajiCode.jpg"));
			requestBase.setEntity(multipartEntity.build());
			HttpResponse response = httpclient.execute(requestObj);
			HttpEntity responseEntity = response.getEntity();
			Object responseObject = EntityUtils.toString(responseEntity);
			responseString = responseObject.toString();
			System.out.println(responseString);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		Gson g = new Gson();
		String gsonstring = g.toJson(responseString);

		res.setContentType("application/json");
		res.getWriter().write(gsonstring);
		return;
	}
}
