package test.org.fugerit.java.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.FileFun;
import org.fugerit.java.core.io.FileIO;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.io.file.AbstractFileFun;
import org.fugerit.java.core.util.PropsIO;

public class ArgHelper {

	
	public static void deleteDir( File file ) {
		if ( file.exists() ) {
			if ( file.isDirectory() ) {
				for ( File f : file.listFiles() ) {
					deleteDir( f );
				}
			}
			file.delete();
		}
	}
	
	public static void copyDir( File folderFrom, File folderTo ) {
		folderTo.mkdirs();
		FileFun copyFun = AbstractFileFun.newFileFun( f -> {
			try {
				String relPath = f.getCanonicalPath().substring( folderFrom.getCanonicalPath().length() );
				File newFile = new File( folderTo, relPath );
				if ( f.isDirectory() ) {
					newFile.mkdir();
				} else {
					StreamIO.pipeStream( new FileInputStream( f ) , new FileOutputStream( newFile ),  StreamIO.MODE_CLOSE_BOTH );
				}	
			} catch (IOException e) {
				throw new ConfigRuntimeException( e );
			}
		} );
		SafeFunction.apply( () -> FileIO.recurseDirSecure( folderFrom , copyFun ) );
	}
	
	public static String[] argTestParams( String id ) {
		return propertiesToArgs( readTestParams(id) );
	}
	
	public static Properties readTestParams( String id ) {
		return PropsIO.loadFromClassLoaderSafe( "tool/params/"+id+".properties" );
	}
	
	public static String[] propertiesToArgs( Properties props ) {
		List<String> args = new ArrayList<String>();
		props.entrySet().forEach( e -> {
			args.add( ArgUtils.getArgString( e.getKey().toString() ) );
			args.add( e.getValue().toString()  );
		} );
		return args.toArray( new String[0] );
	}
	
}
