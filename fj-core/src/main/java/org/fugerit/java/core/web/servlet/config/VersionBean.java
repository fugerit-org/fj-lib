package org.fugerit.java.core.web.servlet.config;

public class VersionBean {

	public int hashCode() {
		return this.getAppName().hashCode();
	}

	public boolean equals(Object obj) {
		return this.getAppName().equals( ((VersionBean)obj).getAppName() );
	}

	private String appName;
	
	private String appVersion;
	
	private String appDate;
	
	private String lastStartup;
	
	private String releaseName;

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public VersionBean(String appName, String appVersion, String appDate,
			String lastStartup) {
		super();
		this.appName = appName;
		this.appVersion = appVersion;
		this.appDate = appDate;
		this.lastStartup = lastStartup;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getAppDate() {
		return appDate;
	}

	public String getLastStartup() {
		return lastStartup;
	}
	
}
