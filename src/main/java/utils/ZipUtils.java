package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	public static void fileSummary(File resultFile, List<File> list) {
		FileOutputStream resultOutputStream = null;
		BufferedWriter bWriter = null;
		try {
			resultOutputStream = new FileOutputStream(resultFile);
			bWriter = new BufferedWriter(new OutputStreamWriter(resultOutputStream));

			boolean headerWrited = false;
			for (File file : list) {

				if (!file.exists()) {
					continue;
				}

				BufferedReader reader = null;
				FileInputStream temp = null;
				try {

					temp = new FileInputStream(file);
					reader = new BufferedReader(new InputStreamReader(temp));
					String line = reader.readLine();
					if (headerWrited == false && line != null && !"".equals(line)) {

						bWriter.write(line + "\n");
						headerWrited = true;
					}
					while ((line = reader.readLine()) != null) {

						if (!"".equals(line)) {
							bWriter.write(line + "\n");
						}
					}

				} catch (FileNotFoundException e) {
					logger.error("文件不存在", e);
				} catch (IOException e) {
					logger.error("", e);
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							logger.error("", e);
						}
					}
				}

			}

		} catch (FileNotFoundException e) {
			logger.error("文件不存在", e);
		} finally {
			if (bWriter != null) {
				try {
					bWriter.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}

		}

	}

}
