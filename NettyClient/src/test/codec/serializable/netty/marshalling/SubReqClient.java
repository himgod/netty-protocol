package test.codec.serializable.netty.marshalling;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import test.codec.protobuf.*;
public class SubReqClient 
{
	  private final String host;
	  private final int port;
	
	
	  public SubReqClient(String host, int port)
	  {
		    this.host = host;
		    this.port = port;
	  }
	
	  public void start() throws Exception 
	  {
		    EventLoopGroup group = new NioEventLoopGroup();
		    try 
		    {
			      Bootstrap b = new Bootstrap();
			      b.group(group);
			      b.channel(NioSocketChannel.class);
			      b.remoteAddress(new InetSocketAddress(host, port));
			      b.handler(new ChannelInitializer<SocketChannel>() 
			      {
				        public void initChannel(SocketChannel ch) throws Exception
				        {
				        	ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
				        	ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
			            	ch.pipeline().addLast(new SubReqClientHandler());
				        }
			      });
			      ChannelFuture f = b.connect().sync();
			      f.addListener(new ChannelFutureListener() 
			      {
			        
			        public void operationComplete(ChannelFuture future) throws Exception {
			          if(future.isSuccess())
			          {
			        	  System.out.println("client connected");
			          }
			          else
			          {
			        	  System.out.println("server attemp failed");
			        	  future.cause().printStackTrace();
			          }
			          
			        }
			      });
			      f.channel().closeFuture().sync();
		    }
		    finally 
		    {
		    	group.shutdownGracefully().sync();
		    }
	  }
	
	  public static void main(String[] args) throws Exception 
	  {
		  new SubReqClient("127.0.0.1", 8989).start();
	  }
}
