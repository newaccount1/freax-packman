package it.freax.fpm.core.util;

public abstract class Constants
{
	private static boolean systemResource = true;
	private static String confdir = "conf";
	private static String archives_conf = "Archives.conf";
	private static String sourcediscoverconf = "source-detect.xml";

	public static String getDirPrefix()
	{
		String ret = "";
		if (!isSystemResource())
		{
			ret = System.getProperty("user.dir");
		}
		return ret;
	}

	public static void setSystemResource(boolean systemResource)
	{
		Constants.systemResource = systemResource;
	}

	public static boolean isSystemResource()
	{
		return systemResource;
	}

	public static void setArchivesConf(String archives_conf)
	{
		Constants.archives_conf = archives_conf;
	}

	public static String getArchivesConf()
	{
		return CoreUtils.concatPaths(getDirPrefix(), getConfdir(), archives_conf);
	}

	public static void setSourceDiscoverConf(String sourcediscoverconf)
	{
		Constants.sourcediscoverconf = sourcediscoverconf;
	}

	public static String getSourceDiscoverConf()
	{
		return sourcediscoverconf;
	}

	public static String getConfdir()
	{
		return confdir;
	}

	public static void setConfdir(String confdir)
	{
		Constants.confdir = confdir;
	}
}
