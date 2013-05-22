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
	public static String encryptPassword(Integer uid, String password) {
		return DigestUtils.sha1Hex(StringUtils.join(new String[] {
				uid.toString(), password }));
	}

	public static String encryptUser(Integer uid, String password, Integer utype) {
		String token = encryptPassword(uid, password);
		return StringUtils.join(
				new String[] { uid.toString(), token, utype.toString() }, '|');
	}

	public static String saveImage(InputStream upImg, String oldName)
			throws IOException {
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

			File originFile = new File(tmpPath + image);
			if (originFile.isDirectory()) {
				ImageIO.write(bi, "png", originFile);
			} else {
				originFile.mkdirs();
				ImageIO.write(bi, "png", originFile);
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
				originFile.renameTo(new File(PublicConfig.getImagePath()
						+ "origin" + File.separator, image));
				bigFile.renameTo(new File(PublicConfig.getImagePath() + "big"
						+ File.separator, image));
				mediumFile.renameTo(new File(PublicConfig.getImagePath()
						+ "medium" + File.separator, image));
				smallFile.renameTo(new File(PublicConfig.getImagePath()
						+ "small" + File.separator, image));

				if (!StringUtils.isBlank(oldName)) {
					File oldoriginFile = new File(PublicConfig.getImagePath()
							+ "origin" + File.separator, oldName);
					File oldbigFile = new File(PublicConfig.getImagePath()
							+ "big" + File.separator, oldName);
					File oldmediumFile = new File(PublicConfig.getImagePath()
							+ "medium" + File.separator, oldName);
					File oldsmallFile = new File(PublicConfig.getImagePath()
							+ "small" + File.separator, oldName);
					oldoriginFile.delete();
					oldbigFile.delete();
					oldmediumFile.delete();
					oldsmallFile.delete();
				}

			} else {
				SaeStorage ss = new SaeStorage();
				ss.upload("menuimages", originFile.getAbsolutePath(), "origin/"
						+ originFile.getName());
				ss.upload("menuimages", bigFile.getAbsolutePath(), "big/"
						+ originFile.getName());
				ss.upload("menuimages", mediumFile.getAbsolutePath(), "medium/"
						+ originFile.getName());
				ss.upload("menuimages", smallFile.getAbsolutePath(), "small/"
						+ originFile.getName());

//				if (!StringUtils.isBlank(oldName)) {
//					ss.delete("menuimages", "origin/" + oldName);
//					ss.delete("menuimages", "big/" + oldName);
//					ss.delete("menuimages", "medium/" + oldName);
//					ss.delete("menuimages", "small/" + oldName);
//				}
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