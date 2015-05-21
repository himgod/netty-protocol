package test.netty.protocol.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import test.netty.protocol.decoder.NettyMessageDecoder;
import test.netty.protocol.encoder.NettyMessageEncoder;
import test.netty.protocol.handler.HeartBeatReqHandler;
import test.netty.protocol.handler.LoginAuthReqHandler;
import test.netty.protocol.message.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyClient 
{
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	public void connect(String remoteServer,int port) throws InterruptedException
	{
		try
		{
			Bootstrap boot = new Bootstrap();
		
			boot.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			//.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChildChannelHandler());
		
			// bind local port,to repeated login 
			ChannelFuture future = boot.connect(new InetSocketAddress(remoteServer,port),new InetSocketAddress(NettyConstant.LOCAL_IP,NettyConstant.LOCAL_PORT)).sync();
			System.out.println("Netty time Client connected at port:"+port);
		
			
			future.addListener(new ChannelFutureListener() 
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
			future.channel().closeFuture().sync();
		}
		finally
		{
			//Util release resource,clear all resources,reconnected
			//workerGroup.shutdownGracefully();
			executor.execute(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try 
					{
						TimeUnit.SECONDS.sleep(5);
						System.out.println(" try to reconnect");
						connect(NettyConstant.REMOTE_IP,NettyConstant.REMOTE_PORT);
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			});
		}
	}
	
	public static class ChildChannelHandler extends ChannelInitializer<SocketChannel>
	{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4,-8,0));
			ch.pipeline().addLast(new NettyMessageEncoder());
			ch.pipeline().addLast(new ReadTimeoutHandler(50));
			
			ch.pipeline().addLast(new LoginAuthReqHandler());
			ch.pipeline().addLast(new HeartBeatReqHandler());
		}
		
	}
	
	public static void main(String[] args)
	{
		try 
		{
			new NettyClient().connect(NettyConstant.REMOTE_IP, NettyConstant.REMOTE_PORT);
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	

