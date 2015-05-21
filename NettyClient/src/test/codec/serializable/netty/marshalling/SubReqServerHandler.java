package test.codec.serializable.netty.marshalling;


import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;
import test.codec.protobuf.*;
/**
 * Sharable表示此对象在channel间共享
 * handler类是我们的具体业务类
 * */
@Sharable//注解@Sharable可以让它在channels间共享
public class SubReqServerHandler extends ChannelInboundHandlerAdapter
{
	int counter = 0;
	  public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException 
	  { 
		  SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
		  System.out.println("Service accept client subscribe req: ["+req.toString() +"]");
		  ctx.writeAndFlush(resp(req.getSubReqID()));
	  } 
	  public void channelReadComplete(ChannelHandlerContext ctx) 
	  { 
		   //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER); //flush掉所有写回的数据
		    //.addListener(ChannelFutureListener.CLOSE); //当flush完成后关闭channel
	  } 
	  
	  private SubscribeRespProto.SubscribeResp resp(int subRespID)
	  {
		  SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		  builder.setSubReqID(subRespID);
		  builder.setRespCode(0);
		  builder.setDesc("Netty book order succeed,3 days later,sent to the designated address");
		  return builder.build();
	  }
	  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) 
	  { 
		    cause.printStackTrace();//捕捉异常信息
		    ctx.close();//出现异常时关闭channel 
	  } 	
}


