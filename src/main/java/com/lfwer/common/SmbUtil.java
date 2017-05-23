package com.lfwer.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

/**
 * 文件共享目录.
 * 
 * @author Administrator
 *
 */
public class SmbUtil {
	/**
	 * 上传文件到共享目录.
	 *
	 * @author liufangwei@sinosoft.com.cn
	 * @param smbUrl
	 *            共享目录地址
	 * @param FileItem
	 *            通过uploadServlet获取的FileItem文件对象
	 * @param fileName
	 *            文件名
	 */
	public static void smbPut(String smbUrl, File file)
			throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
			// 增加如下配置，则读取速度会有质的飞跃
			System.setProperty("jcifs.smb.client.dfs.disabled", "true");

			SmbFile remoteFile = new SmbFile(smbUrl);
			if (!remoteFile.exists()) {
				remoteFile.mkdirs();// 注意给共享目录写入权限哦~
			}
			remoteFile = new SmbFile(smbUrl + "/" + file.getName());
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				out.write(tempbyte);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (file != null) {
					file.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void smbGet(String smbUrl, HttpServletResponse response)
			throws Exception {
		SmbFileInputStream in = null;
		OutputStream out = null;
		try {
			// 增加如下配置，则读取速度会有质的飞跃
			System.setProperty("jcifs.smb.client.dfs.disabled", "true");
			SmbFile remoteFile = new SmbFile(smbUrl);
			if (!remoteFile.exists()) {// 文件不存在
				response.setStatus(404);
				return;
			}
			if (!remoteFile.isFile()) {// 非文件
				response.setStatus(404);
				return;
			}
			String fileName = remoteFile.getName();
//			fileName = fileName.substring(0, fileName.lastIndexOf("_uuid_"))
//					+ fileName.substring(fileName.lastIndexOf("."));
//			fileName = URLEncoder.encode(fileName, "UTF-8");// 转码
			fileName = fileName.replace("+", "%20");
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Length", "" + remoteFile.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			in = new SmbFileInputStream(remoteFile);
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
				out.flush();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
