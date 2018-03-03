package com.byderbeck.lotterydata.dao;

/**
 * Created by lyman on 10/12/2016.
 */

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadBuilder;
import com.dropbox.core.v2.files.FileMetadata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dropbox {

	final String ACCESS_TOKEN = "Bz9acwpVKiAAAAAAAAAAEGrAxQ1rCVCxnG9JFSUhVaGVrzYTBz5dDzURDw9qSpZD";
	final String FILE_PATH = "Apps/byderbeck";

	public Dropbox() {

	}

	public String getLottoDrawings() {

		DbxRequestConfig config = new DbxRequestConfig(FILE_PATH);
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		StringBuilder output = new StringBuilder();

		try {
			DownloadBuilder downloadBuilder = client.files().downloadBuilder("/lottoDrawings.csv");
			DbxDownloader<FileMetadata> downloader = downloadBuilder.start();
			InputStream in = null;
			in = downloader.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			in.close();
		} catch (DbxException dbe) {
			dbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public String getLottoTickets() {

		DbxRequestConfig config = new DbxRequestConfig(FILE_PATH);
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		StringBuilder output = new StringBuilder();

		try {
			DownloadBuilder downloadBuilder = client.files().downloadBuilder("/lottoTickets.csv");
			DbxDownloader<FileMetadata> downloader = downloadBuilder.start();
			InputStream in = null;
			in = downloader.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			in.close();
		} catch (DbxException dbe) {
			dbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public String getPowerballDrawings() {

		DbxRequestConfig config = new DbxRequestConfig(FILE_PATH);
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		StringBuilder output = new StringBuilder();

		try {
			DownloadBuilder downloadBuilder = client.files().downloadBuilder("/powerballDrawings.csv");
			DbxDownloader<FileMetadata> downloader = downloadBuilder.start();
			InputStream in = null;
			in = downloader.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			in.close();
		} catch (DbxException dbe) {
			dbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public String getPowerballTickets() {

		DbxRequestConfig config = new DbxRequestConfig(FILE_PATH);
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		StringBuilder output = new StringBuilder();

		try {
			DownloadBuilder downloadBuilder = client.files().downloadBuilder("/powerballTickets.csv");
			DbxDownloader<FileMetadata> downloader = downloadBuilder.start();
			InputStream in = null;
			in = downloader.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			in.close();
		} catch (DbxException dbe) {
			dbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public String getMegaMillionsDrawings() {

		DbxRequestConfig config = new DbxRequestConfig(FILE_PATH);
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		StringBuilder output = new StringBuilder();

		try {
			DownloadBuilder downloadBuilder = client.files().downloadBuilder("/megaMillionsDrawings.csv");
			DbxDownloader<FileMetadata> downloader = downloadBuilder.start();
			InputStream in = null;
			in = downloader.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			in.close();
		} catch (DbxException dbe) {
			dbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public String getMegaMillionsTickets() {

		DbxRequestConfig config = new DbxRequestConfig(FILE_PATH);
		DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
		StringBuilder output = new StringBuilder();

		try {
			DownloadBuilder downloadBuilder = client.files().downloadBuilder("/megaMillionsTickets.csv");
			DbxDownloader<FileMetadata> downloader = downloadBuilder.start();
			InputStream in = null;
			in = downloader.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			in.close();
		} catch (DbxException dbe) {
			dbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}
}
