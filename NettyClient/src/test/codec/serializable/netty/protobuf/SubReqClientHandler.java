package test.codec.serializable.netty.protobuf;


import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.CharsetUtil;
import test.codec.protobuf.*;

@Sharable
public class SubReqClientHandler extends ChannelInboundHandlerAdapter
{
	
	   /*此方法会在连接到服务器后被调用 
	   * */
	  public void channelActive(ChannelHandlerContext ctx) 
	  {
		  for(int i = 0; i < 10; i++ )
		  {
			  ctx.write(subReq(i));
			  
		  }
		  ctx.flush();
	  }
	  /**
	   *此方法会在接收到服务器数据后调用 
	   * */
	  public void channelRead(ChannelHandlerContext ctx, Object msg) 
	  {
		  System.out.println("Receive Server response: ["+msg+"]");
//		  System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
	  }
	 
	  private SubscribeReqProto.SubscribeReq subReq(int i)
	  {
		  SubscribeReqProto.SubscribeReq.Builder builder= SubscribeReqProto.SubscribeReq.newBuilder();
		  builder.setSubReqID(i);
		  builder.setUserName("himgod");
		  builder.setProductName("Netty book for Protobuf");
		  List<String> address = new ArrayList<String>();
		  address.add("Nanjing YuHuatai");
		  address.add("Beijing LiuliChang");
		  address.add("Shenzhen HongShulin");
		  builder.addAllAddress(address);
		  return builder.build();
	  }
	  
	  @Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
//		super.channelReadComplete(ctx);
		 ctx.flush();
	}


	/**
	   *捕捉到异常 
	   * */
	  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	  {
		    cause.printStackTrace();
		    ctx.close();
	  }

}

