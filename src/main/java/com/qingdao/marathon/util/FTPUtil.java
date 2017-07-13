package com.qingdao.marathon.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.qingdao.marathon.contants.Constants;

public class FTPUtil {

	/**
	 * * Description: 向FTP服务器上传文件 *
	 * 
	 * @param url
	 *            FTP服务器hostname *
	 * @param port
	 *            FTP服务器端口 * @param username FTP登录账号 *
	 * @param password
	 *            FTP登录密码 * @param path FTP服务器保存目录 *
	 * @param filename
	 *            上传到FTP服务器上的文件名 * @param input 输入流 *
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String path,String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(Constants.FTP_IP, Constants.FTP_PORT);
			ftp.login(Constants.USERNAME, Constants.PWD);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

}
