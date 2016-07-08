package com.sjtu.ist.charge.Util;

public class TokenGenrerate {

	public static String getToken(String username) {
		String str = System.currentTimeMillis() + username;
		return MD5.GetMD5Code(str);
	}
}
