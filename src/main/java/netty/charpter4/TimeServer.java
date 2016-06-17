package netty.charpter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer {
	public void bind(int port){
		EventLoopGroup group=new NioEventLoopGroup();
		EventLoopGroup childGroup=new NioEventLoopGroup();
		ServerBootstrap bs=new ServerBootstrap();
		bs.group(group, childGroup).channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline().addLast(new LineBasedFrameDecoder(1024));//解决粘包问题
				ch.pipeline().addLast(new StringDecoder());//进行String解码
				ch.pipeline().addLast(new TimeServerHandler());
				
			}
		});
		try {
			ChannelFuture   futrue=bs.bind(port).sync();
			futrue.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			group.shutdownGracefully();
			childGroup.shutdownGracefully();
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new TimeServer().bind(8080);
	}
}
