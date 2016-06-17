package netty.charpter5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer_FixedLength {
public void bind(int port){
	EventLoopGroup group=new NioEventLoopGroup();
	EventLoopGroup childGroup=new NioEventLoopGroup();
	ServerBootstrap sb=new ServerBootstrap();
	sb.group(group, childGroup).channel(NioServerSocketChannel.class)
	.option(ChannelOption.SO_BACKLOG, 100)
	.handler(new LoggingHandler(LogLevel.INFO))
	.childHandler(new ChannelInitializer<SocketChannel>() {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new EchoServerHandler_FixedLength());
			
		}
	});
	try {
		ChannelFuture f=sb.bind("192.168.23.63",port).sync();
		f.channel().closeFuture().sync();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		group.shutdownGracefully();
		childGroup.shutdownGracefully();
		e.printStackTrace();
	}
}
public static void main(String[] args) {
	new EchoServer_FixedLength().bind(8080);
}
}
