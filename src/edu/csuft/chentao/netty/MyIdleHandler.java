package edu.csuft.chentao.netty;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
 
public class MyIdleHandler extends ChannelHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
    	super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
 
            if (e.state() == IdleState.READER_IDLE) {
                System.out.println("--- Reader Idle ---");
                ctx.writeAndFlush(String.valueOf("Server Reader Idle"));
                // ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                System.out.println("--- Write Idle ---");
                ctx.writeAndFlush(String.valueOf("Server Write Idle"));
                // ctx.close();
            } else if (e.state() == IdleState.ALL_IDLE) {
                System.out.println("--- All_IDLE ---");
                ctx.writeAndFlush(String.valueOf("Server All Idle"));
            }
        }
    }
}