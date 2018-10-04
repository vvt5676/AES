import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
public class Aes{
	private static SecretKeySpec skey;
	private static byte[]key;

	public static void setKey(String myKey)
	{System.out.println("encrypted: "+encrypted );
		MessageDigest sha = null;
		try
		{
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			skey = new SecretKeySpec(key, "AES");
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	public  static String encrypt(String str, String key)
	{
		try
		{
			setKey(key);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5padding");
		cipher.init(Cipher.ENCRYPT_MODE, skey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static String decrept(String str, String key)
	{
		try
		{
			setKey(key);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
class Driver
{
	public static void main(String[]args) throws Exception
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("sample.txt"));
			String input = br.readLine();
			String key = br.readLine();
			BufferedWriter bw = new BufferedWriter(new FileWriter("encrypt1.txt"));
			String encrypted = Aes.encrypt(input, key);
			BufferedWriter bw_de = new BufferedWriter(new FileWriter("decrypt2.txt"));
			bw.write(encrypted);
			// bw.write("vvt");
			bw.close();
			String decrypted = Aes.decrept(encrypted, key);
			System.out.println("encrypted: "+encrypted );
			System.out.println("decrypted: "+decrypted );
			bw_de.write(decrypted);
			bw_de.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}