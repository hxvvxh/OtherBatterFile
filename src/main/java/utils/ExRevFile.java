/**
 * ==================================================================
 * The Huateng Software License
 *
 * Copyright (c) 2007-2008 Huateng Software System.  All rights
 * reserved.
 * @author MAIK.CHEN
 * create time :2008-02-17 16:45:38
 * ==================================================================
 */
package utils;

/**
 * @author chenjz
 *
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator 文件操作类 TODO To change the template for this generated
 *         type comment go to Window - Preferences - Java - Code Style - Code
 *         Templates 20071208修改,写文件方式改为使用DataoutputStream
 */
public class ExRevFile {

	protected static Logger logger = LoggerFactory.getLogger(ExRevFile.class);
	private BufferedReader br = null;
	private BufferedWriter bw = null;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	private File f = null;
	private char ReadOrWrite;
	private static String sFtpdir = null;
	// private String sFtpdir = null;

	static {
		try {
			// TODO
			sFtpdir = "D:\\asset";
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public ExRevFile(String sFtpdir) {
		this.sFtpdir = sFtpdir;
		ReadOrWrite = 'r';
	}

	public String getsFtpdir() {
		return sFtpdir;
	}

	public void setsFtpdir(String sFtpdir) {
		this.sFtpdir = sFtpdir;
	}

	/**
	 *
	 * @param c
	 *  0-读文件,1-写文件
	 */
	public ExRevFile(char c) {
		ReadOrWrite = c;
	}

	public int openFile(String sFilename, String sPath) {
		return openFile(sPath + File.separator + sFilename, 1);
	}

	public int openFile(String sFilename) {
		return openFile(sFtpdir + File.separator + sFilename, 1);
	}

	/**
	 * 打开文件
	 *
	 * @param sFilename:
	 *            全路径文件名
	 * @param i:
	 *            0-使用默认目录,1-使用全路径
	 * @return
	 */
	private int openFile(String sFilename, int i) {
		if (i == 0) {
			// 使用系统默认的文件目录
			sFilename = sFtpdir + File.separator + sFilename;
		}

		logger.debug("文件全路径名称:" + sFilename);

		try {
			if (ReadOrWrite == 'r') {
				br = null;
				f = new File(sFilename);
				// FileInputStream fin = new FileInputStream(f);
				// din = new DataInputStream(new BufferedInputStream(fin));
				br = new BufferedReader(new FileReader(f));
			} else if (ReadOrWrite == 'w') {
				bw = null;
				f = new File(sFilename);
				f.getParentFile().mkdirs();
				bw = new BufferedWriter(new FileWriter(f));
				// DataOutputStream d = new DataOutputStream(new
				// BufferedOutputStream(new FileOutputStream(f)));
				// bw = new DataOutputStream(new BufferedOutputStream(new
				// FileOutputStream(f)));
			} else if (ReadOrWrite == 'a') {
				// File f = new File(sFilename);
				// bw = new BufferedWriter(new FileWriter(f));
				bw = null;
			}
			// 以下为流方式读写
			else if (ReadOrWrite == 'R') {
				dis = null;
				f = new File(sFilename);
				dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
			} else if (ReadOrWrite == 'W') {
				dos = null;
				f = new File(sFilename);
				f.getParentFile().mkdirs();
				dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			} else {
				return -2; /* 错误的标志 */
			}
		} catch (FileNotFoundException fe) {
			logger.error(fe.getMessage(), fe);
			return -1;
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
			return -1;
		}finally {

			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (dis != null){
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (dos != null){
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}

	public void closeFile() {
		try {
			if (ReadOrWrite == 'r') {
				br.close();
				br = null;
			} else if (ReadOrWrite == 'R') {
				dis.close();
				dis = null;
			} else if (ReadOrWrite == 'W') {
				dos.flush();
				dos.close();
				dos = null;
			} else {
				bw.flush();
				bw.close();
				bw = null;
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		}
	}

	public String readLine() {
		String s = null;

		if (br == null) {
			logger.debug("file not opened");
			return null;
		}

		try {
			s = br.readLine();
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		}

		return s;
	}

	/**
	 * 向文件中写入一行
	 *
	 * @param s
	 */
	public void writeLine(String s) {
		try {
			bw.write(s + "\n");
			bw.flush();
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		}
	}

	/**
	 * 将buf中的内容写入文件
	 *
	 * @param buf
	 */
	public void writeBytes(byte[] buf) {
		try {
			dos.write(buf);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		}
	}

	/**
	 * 从文件中读取字节
	 *
	 * @param buf
	 * 			@return, 读取的字节数
	 */
	public int readBytes(byte[] buf) {
		int i = 0;

		try {
			if (dis != null)
				i = dis.read(buf);
			else
				return -1;
		} catch (IOException io) {
			return 0;
		}

		return i;
	}

	public int getFileLength() {
		return (int) f.length();
	}

	public String[] readFileToArray(String sFilename) {
		String sFile = sFtpdir + File.separator + sFilename;
		// String sFile = "c:\\temp\\ftp\\" + sFilename;
		if (openFile(sFile) != 0) {
			String[] s = new String[1];
			s[0] = "打开文件失败!";
			return s;
		}

		/* 先将文件读取到数组中 */
		ArrayList al = new ArrayList();
		while (true) {
			String s = readLine();
			if (s == null)
				break;

			al.add(s);
		}

		String[] s;
		if (al.size() > 0) {
			s = new String[al.size()];
			for (int i = 0; i < al.size(); i++)
				s[i] = (String) al.get(i);
		} else {
			s = new String[1];
			s[0] = "文件为空!";
		}

		for (int i = 0; i < s.length; i++)
			logger.debug(s[i]);

		closeFile();

		return s;
	}

	public static void main(String[] args) {
		// ExFile ef = new ExFile();
		// String s[] = ef.ReadFileToArray("3197cz.117743");

		ExRevFile ef1 = new ExRevFile('W');
		ef1.openFile("ff3.txt");
		ef1.writeLine("write file test");
		ef1.writeLine("write file test, two");
		ef1.closeFile();
	}
}