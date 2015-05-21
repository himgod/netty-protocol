package test.netty.ownprotocol.server;

import java.util.List;

import test.utils.ByteBufToBytes;
import test.utils.ByteObjConverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PersonDecoder extends ByteToMessageDecoder 
{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("decoding person from client...");
		ByteBufToBytes reader = new ByteBufToBytes((int)in.readableBytes());
		Object obj = ByteObjConverter.ByteToObject(reader.read(in));
		out.add(obj);
		//		ctx.write(obj);
//		ctx.flush();
	}

}
