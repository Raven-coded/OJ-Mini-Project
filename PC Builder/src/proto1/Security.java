package proto1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Security {
	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(password.getBytes());
		byte[] b = md.digest();
		StringBuffer sb = new StringBuffer();
		for(byte b1 : b){
			sb.append(Integer.toHexString(b1 & 0xff).toString());
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
}
