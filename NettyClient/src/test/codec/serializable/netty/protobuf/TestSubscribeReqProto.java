package test.codec.serializable.netty.protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

import test.codec.protobuf.*;
public class TestSubscribeReqProto 
{
	private static byte[] encode(SubscribeReqProto.SubscribeReq req)
	{
		return req.toByteArray();
	}
	
	private static SubscribeReqProto.SubscribeReq decode(byte[] body)
	{
		try 
		{
			return SubscribeReqProto.SubscribeReq.parseFrom(body);
		} 
		catch (InvalidProtocolBufferException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static SubscribeReqProto.SubscribeReq createSubscribeReq()
	{
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("himgod");
		builder.setProductName("Netty book");
		List<String> list = new ArrayList<String>();
		list.add("NanJing");
		list.add("xian");
		list.add("beijing");
		builder.addAllAddress(list);
		
		return builder.build();
	}
	
	public static void main(String[] args) throws Exception
	{
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("before encode :" +req.toString());
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("After dncode :" +req.toString());
		System.out.println("Insert equal: -->"+req2.equals(req));
	}
	
	
}
