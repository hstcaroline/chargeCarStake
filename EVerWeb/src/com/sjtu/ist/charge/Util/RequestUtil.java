package com.sjtu.ist.charge.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;


public class RequestUtil {

	public static String getRequestBody(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader bf = null;
		try {
			is = request.getInputStream();
			isr = new InputStreamReader(is);
			bf = new BufferedReader(isr);
			char[] charBuffer = new char[128];
			int readSize = -1;
			while ((readSize = bf.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, readSize);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bf.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String requestContent = sb.toString();
		return requestContent;
	}
}
