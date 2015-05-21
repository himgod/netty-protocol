package test.netty.ownprotocol.client;

import test.netty.ownprotocol.Person;
import test.utils.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PersonEncoder extends MessageToByteEncoder
{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf out)
			throws Exception {
		
		System.out.println("encoding perosn...");
		Person person = (Person)obj;
		byte[] datas = ByteObjConverter.ObjectToByte(person);
		out.writeBytes(datas);
		ctx.flush();
	}
	

}
