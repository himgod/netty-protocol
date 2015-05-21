package test.netty.encoderanddecoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUserInfo 
{
	public static void main(String[] args) throws IOException
	{
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("himgod");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(info);
		oos.flush();
		oos.close();
		byte[] b = bos.toByteArray();
		
		System.out.println("the jdk serializable length is: "+b.length);
		bos.close();
		
		System.out.println("the byte array serializable length is:"+ info.codeC().length);
	}
	

}
