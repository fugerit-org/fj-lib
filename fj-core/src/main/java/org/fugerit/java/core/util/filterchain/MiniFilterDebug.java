package org.fugerit.java.core.util.filterchain;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

public class MiniFilterDebug {

	public static void dumpConfig( PrintWriter writer, InputStream is ) throws Exception {
		MiniFilterConfig config = new MiniFilterConfig();
		MiniFilterConfig.loadConfig( is , config );	
		dumpConfig(writer, config);
	}
	
	public static void dumpConfig( PrintWriter writer, MiniFilterConfig config ) throws Exception {
		try {
			writer.println( "General config properties : "+config.getGeneralProps().toString() );
			Iterator<String> itConfig = config.getIdSet().iterator();
			while ( itConfig.hasNext() )  {
				String currentId = itConfig.next();
				writer.println( "***********************************************" );
				writer.println( "AnprOperationBase.getChain() id='"+currentId+"'" );
				writer.println( "***********************************************" );
				Iterator<MiniFilterConfigEntry> itEntry = config.getDataList( currentId ).iterator();
				while ( itEntry.hasNext() ) {
					MiniFilterConfigEntry entry = itEntry.next();
					writer.println( "entry chain : "+entry.getId()+" - "+entry.getType() );
				}
			}
		} catch (Exception e) {
			writer.println( "Error dumping config "+e );
			e.printStackTrace( writer );
			throw e;
		}
	}

}
