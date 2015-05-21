package test.netty.encoderanddecoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class PerformTestUserInfo
{
	public static void main(String[] args) throws IOException
	{
		UserInfo info = new UserInfo();
		
		info.buildUserID(100).buildUserName("Welcome to Netty.");
		int loop = 1000000;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		
		long starttime = System.currentTimeMillis();
		
		for(int i = 0 ; i < loop; i++)
		{
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(info);
			oos.flush();
			oos.close();
			byte[] b = bos.toByteArray();
			bos.close();
		}
		
		long endtime = System.currentTimeMillis();
		
		System.out.println("The jdk serializable cost time is "+(endtime -starttime) +" ms");
		
		System.out.println("____________________________________");
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		starttime = System.currentTimeMillis();
		for(int i = 0 ; i  < loop;i++)
		{
			byte[] b = info.codeC(buffer);
		}
		endtime = System.currentTimeMillis();
		
		System.out.println("The byte array serializable cost time is "+(endtime -starttime) +" ms");
		
	}
}
