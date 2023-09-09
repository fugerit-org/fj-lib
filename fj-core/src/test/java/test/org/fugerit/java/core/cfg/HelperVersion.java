package test.org.fugerit.java.core.cfg;

import java.util.Properties;

import org.fugerit.java.core.cfg.ModuleVersion;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class HelperVersion implements ModuleVersion {

	public static final String NAME = "test";
	
	public static final String VERSION = "1.0.0";
	
	public HelperVersion() {
		this(  NAME, VERSION, "2023-09-08", "2023-09-09", new Properties() );
	}
	
	@Getter private String name;
	
	@Getter private String version;
	
	@Getter private String date;
	
	@Getter private String loadTime;
	
	@Getter private Properties dependancies;
	
}
