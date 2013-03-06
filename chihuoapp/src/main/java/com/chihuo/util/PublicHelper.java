package com.chihuo.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;

public class PublicHelper {
	public static String encryptUser(Integer uid, String password, Integer utype) {
		String token = DigestUtils.shaHex(StringUtils.join(new String[] {
				uid.toString(), password }));
		return StringUtils.join(
				new String[] { uid.toString(), token, utype.toString() }, '|');
	}

	public static String saveImage(InputStream upImg) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = upImg.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		byte[] bs = buffer.toByteArray();

		if (bs.length > 0) {
			String id = UUID.randomUUID().toString();
			String image = id + ".png";

			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bs));

			String tmpPath;
			if (PublicConfig.isLocal()) {
				tmpPath = System.getProperty("java.io.tmpdir");
			} else {
				tmpPath = SaeUserInfo.getSaeTmpPath() + "/";
			}

			File file = new File(tmpPath + image);
			if (file.isDirectory()) {
				ImageIO.write(bi, "png", file);
			} else {
				file.mkdirs();
				ImageIO.write(bi, "png", file);
			}

			// 生成不同规格的图片
			File bigFile = saveScaledImage(bi, 700, 525, tmpPath + "big"
					+ image);
			File mediumFile = saveScaledImage(bi, 200, 150, tmpPath + "medium"
					+ image);
			File smallFile = saveScaledImage(bi, 100, 75, tmpPath + "small"
					+ image);
			
			// 将图片保存
			if (PublicConfig.isLocal()) {
				file.renameTo(new File(PublicConfig.getImagePath() + "origin"
						+ File.separator, file.getName()));
				bigFile.renameTo(new File(PublicConfig.getImagePath() + "big"
						+ File.separator, file.getName()));
				mediumFile.renameTo(new File(PublicConfig.getImagePath()
						+ "medium" + File.separator, file.getName()));
				smallFile.renameTo(new File(PublicConfig.getImagePath()
						+ "small" + File.separator, file.getName()));
			} else {
				SaeStorage ss = new SaeStorage();
				ss.upload("menuimages", file.getAbsolutePath(), "origin/"
						+ file.getName());
				ss.upload("menuimages", bigFile.getAbsolutePath(), "big/"
						+ file.getName());
				ss.upload("menuimages", mediumFile.getAbsolutePath(), "medium/"
						+ file.getName());
				ss.upload("menuimages", smallFile.getAbsolutePath(), "small/"
						+ file.getName());
			}

			return image;
		}
		return null;
	}

	private static File saveScaledImage(BufferedImage bi, int width,
			int height, String filePath) throws IOException {
		Image bigImage = bi.getScaledInstance(width, height,
				BufferedImage.SCALE_SMOOTH);
		BufferedImage big = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		big.createGraphics().drawImage(bigImage, 0, 0, null);
		File bigFile = new File(filePath);
		ImageIO.write(big, "png", bigFile);
		return bigFile;
	}

}