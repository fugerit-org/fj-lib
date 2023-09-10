package org.fugerit.java.core.cfg.store.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.store.ConfigStore;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigStoreIO {

    private static final Logger logger = LoggerFactory.getLogger( ConfigStoreIO.class );

    public static final String DEF_CONFIG_CL_PATH = "core/cfg/store/default-conf.properties";

    public static final String DEF_CONFIG_STORE_MAIN = ".fugerit-conf";

    public static final String DEF_CONFIG_STORE_NAME = "default-conf";

    private String storeApp;

    private String storeMain;

    private String storeNameDefault;

    private String defaultConfigClPath;

    public ConfigStoreIO(String storeApp, String storeMain, String storeNameDefault, String defaultConfigClPath) {
        this.storeApp = storeApp;
        this.storeMain = storeMain;
        this.storeNameDefault = storeNameDefault;
        this.defaultConfigClPath = defaultConfigClPath;
    }

    public ConfigStoreIO(String storeApp, String storeMain, String storeNameDefault) {
        this( storeApp, storeMain, storeNameDefault, DEF_CONFIG_CL_PATH );
    }

    public ConfigStoreIO(String storeApp, String storeMain) {
        this( storeApp, storeMain, DEF_CONFIG_STORE_NAME );
    }

    public ConfigStoreIO(String storeApp) {
        this( storeApp, DEF_CONFIG_STORE_MAIN );
    }

    private void createDir(File baseDir ) {
        if ( !baseDir.exists() ) {
            logger.info( "baseDir {} -> mkdir? {}", baseDir, baseDir.mkdir() );
        }
        logger.info( "baseDir {} -> exists? {}", baseDir, baseDir.exists() );
    }

    protected File getStoreMain() {
        File baseDir = new File( System.getProperty( "user.home" ), this.storeMain );
        createDir(baseDir);
        return baseDir;
    }

    protected File getStoreApp() {
        File baseDir = new File( getStoreMain(), this.storeApp );
        createDir(baseDir);
        return baseDir;
    }

    protected File getConfigStoreFile( String configName ) {
        File configFile = new File( getStoreApp(), configName+".properties" );
        logger.info( "config file {}", configFile );
        return configFile;
    }

    public void saveConfig(ConfigStore configStore ) {
        this.saveConfig( configStore, this.storeNameDefault );
    }

    public void saveConfig(ConfigStore configStore, String configName ) {
        File configFile = this.getConfigStoreFile( configName );
        try (FileOutputStream fos = new FileOutputStream( configFile )) {
            ConfigStoreProps.write( configStore, fos );
        } catch (IOException e) {
            throw new ConfigRuntimeException("Error loading configuration "+e, e);
        }
    }

    public ConfigStoreDefault loadConfig() {
        return this.loadConfig( this.storeNameDefault );
    }

    public ConfigStoreDefault loadConfig( String configName ) {
        ConfigStoreDefault configStore;
        File configFile = this.getConfigStoreFile( configName );
        if ( configFile.exists() ) {
            try (FileInputStream fis = new FileInputStream( configFile )) {
                configStore = ConfigStoreProps.read( fis );
            } catch (IOException e) {
                throw new ConfigRuntimeException("Error loading configuration "+e, e);
            }
        } else {
            try (InputStream is = ClassHelper.loadFromDefaultClassLoader( this.defaultConfigClPath )) {
                configStore = ConfigStoreProps.read( is );
            } catch (Exception e) {
                throw new ConfigRuntimeException("Error loading default configuration "+e, e);
            }
        }
        return configStore;
    }

}
