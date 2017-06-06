/**
 * 打印输出
 */
package edu.csuft.chentao.util;

/**
 * @author csuft.chentao
 *
 * 2016年12月8日 下午9:45:32
 */
public class Logger {

	private static boolean isPrint = false;
	
	public static void log(String msg){
		if(isPrint){
			System.out.println(msg);	
		}
	}
}
