package it.freax.fpm.core.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class FtpDownload extends AbstractDownload
{

	public FtpDownload(URL url, String path)
	{
		super(url, path);
		download();
	}

	public FtpDownload(URL url, String path, String proxyUrl, int port)
	{
		super(url, path, proxyUrl, port);
		download();
	}

	public FtpDownload(URL url, String path, String proxyUrl, int port, String userName, String password)
	{
		super(url, path, proxyUrl, port, userName, password);
		download();
	}

	@Override
	public void run()
	{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try
		{
			if (useProxy)
			{
				Properties systemProperties = System.getProperties();
				systemProperties.setProperty("ftp.proxyHost", proxyUrl);
				systemProperties.setProperty("ftp.proxyPort", String.valueOf(port));

				if (useAuthentication)
				{
					Authenticator.setDefault(new SimpleAuthenticator(userName, password));
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append(path);
			if (!path.endsWith(System.getProperty("file.separator")))
			{
				sb.append(System.getProperty("file.separator"));
			}
			sb.append(getFileName(url));

			status = DOWNLOADING;

			URLConnection urlc = url.openConnection();

			bis = new BufferedInputStream(urlc.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(sb.toString()));

			while (status == DOWNLOADING)
			{
				byte[] buffer = new byte[MAX_BUFFER_SIZE];

				// Read from server into buffer.
				int read = bis.read(buffer);
				if (read == -1)
				{
					break;
				}

				// Write buffer to file.
				bos.write(buffer, 0, read);
				downloaded += read;
			}
			status = COMPLETE;
			stateChanged();
		}
		catch (MalformedURLException e)
		{
			if (debug)
			{
				e.printStackTrace();
			}
			setDebugMessage(e.toString(), true);
			error();
		}
		catch (IOException e)
		{
			if (debug)
			{
				e.printStackTrace();
			}
			setDebugMessage(e.toString(), true);
			error();
		}
		finally
		{
			if (bis != null)
			{
				try
				{
					bis.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
			if (bos != null)
			{
				try
				{
					bos.close();
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			}
		}
	}
}