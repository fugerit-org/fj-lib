package org.fugerit.java.tool.encoding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Properties;

import org.fugerit.java.core.charset.EncodingCheck;
import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.io.StreamIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharsetCorrect {

	private static final Logger logger = LoggerFactory.getLogger(CharsetCorrect.class);

//	private static List<Point> checkEncodingErrors( final byte[] bytes, final String encoding ) {
//		List<Point> errors = new ArrayList<Point>();
//		int line = 1;
//		int col = 0;
//		for ( int k=0; k<bytes.length; k++ ) {
//			col++;
//			byte[] current = { bytes[k] };
//			String s = null;
//			try {
//				 s = new String( current, encoding );
//				 if ( !checkEncoding( current, encoding) ) {
//					 Point p = new Point( line, col );
//					 errors.add( p );	 
//				 }
//			} catch (UnsupportedEncodingException e) {
//				logger.debug( "Unmappable character at line : "+line+" column : "+col );
//				Point p = new Point( line, col );
//				errors.add( p );
//			}
//			if ( s != null ) {
//				char c = s.charAt( 0 );
//				if ( c == '\n' || c == '\r' ) {
//					line++;
//					col = 0;
//				}
//			}
//		}
//		return errors;
//    }
	

	
	private static void handleFile( File input, File output, String sourceCharset, String targetCharset, boolean infoComment ) throws Exception {
		FileInputStream is = new FileInputStream( input );
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		StreamIO.pipeStream( is , os , StreamIO.MODE_CLOSE_BOTH );
		byte[] sourceData = os.toByteArray();
		boolean hasErrors = ! EncodingCheck.checkEncoding( sourceData , targetCharset );
		if ( hasErrors ) {
			StringBuffer infoCommentBuffer = new StringBuffer();
			infoCommentBuffer.append( "/* " );
			infoCommentBuffer.append( CharsetCorrect.class.getSimpleName() );
			infoCommentBuffer.append( " found non " );
			infoCommentBuffer.append( targetCharset );
			infoCommentBuffer.append( " characters on " );
			infoCommentBuffer.append( new java.sql.Timestamp( System.currentTimeMillis() ) );
			infoCommentBuffer.append( " */" );
			String source = new String( sourceData , sourceCharset );
			byte[] destData = source.getBytes( targetCharset );
			FileOutputStream fos = new FileOutputStream( output );
			StreamIO.pipeStream( new ByteArrayInputStream( destData ) , fos, StreamIO.MODE_CLOSE_IN_ONLY );
			if ( infoComment && input.getName().endsWith( "java" ) ) {
				StreamIO.pipeStream( new ByteArrayInputStream( infoCommentBuffer.toString().getBytes( targetCharset ) ) , fos, StreamIO.MODE_CLOSE_IN_ONLY );
			}
			logger.info( "input : " + input.getAbsolutePath()+ " , output : " +output.getAbsolutePath() + " , comment : " + infoCommentBuffer+"" );
			fos.close();
			logger.debug( "written file -> "+output.getAbsolutePath() );
		}		
	}
	
	private static void recurse( File current, FileFilter filter, String sourceCharset, String targetCharset, boolean infoComment ) throws Exception {
		logger.debug( "recurse "+current.getAbsolutePath() );
		if ( current.isFile() ) {
			handleFile( current , current, sourceCharset, targetCharset, infoComment);
		} else {
			File[] list = current.listFiles( filter );
			if ( list != null ) {
				for ( int k=0; k<list.length; k++ ) {
					recurse( list[k] , filter, sourceCharset, targetCharset, infoComment);	
				}	
			}
		}
	}
	
	public static final String PARAM_INPUT_FILE = "input-file";
	public static final String PARAM_OUTPUT_FILE = "output-file";
	
	public static final String PARAM_FOLDER_RECURSE = "folder-recurse";
	public static final String PARAM_FOLDER_FILTER = "folder-filter";
	
	public static final String PARAM_APPEND_INFO_AS_COMMENT = "info-comment";
	
	public static final String PARAM_TARGET_CHARSET = "target-charset";
	public static final String PARAM_TARGET_CHARSET_DEFAULT = "utf-8";
	
	public static final String PARAM_SOURCE_CHARSET = "source-charset";
	public static final String PARAM_SOURCE_CHARSET_DEFAULT = "Windows-1252";
	
	public static void handle( Properties params ) throws Exception {
		String inputFile = params.getProperty( PARAM_INPUT_FILE );
		String outputFile = params.getProperty( PARAM_OUTPUT_FILE, inputFile );
		String folderRecurse = params.getProperty( PARAM_FOLDER_RECURSE );
		String folderFilter = params.getProperty( PARAM_FOLDER_FILTER, "." );
		String targetCharset = params.getProperty( PARAM_TARGET_CHARSET, PARAM_TARGET_CHARSET_DEFAULT );
		String sourceCharset = params.getProperty( PARAM_SOURCE_CHARSET, PARAM_SOURCE_CHARSET_DEFAULT );
		boolean infoComment = ( params.getProperty( PARAM_APPEND_INFO_AS_COMMENT ) != null );
		logger.info( PARAM_TARGET_CHARSET+" -> "+targetCharset );
		if ( folderRecurse != null ) {
			logger.info( PARAM_FOLDER_RECURSE+" -> "+folderRecurse );
			logger.info( PARAM_FOLDER_FILTER+" -> "+folderFilter );
			File baseFolder = new File( folderRecurse );
			recurse( baseFolder, new RecurseFilter( folderFilter ), sourceCharset, targetCharset, infoComment );
		} else {
			logger.info( PARAM_INPUT_FILE+" -> "+inputFile );
			logger.info( PARAM_OUTPUT_FILE+" -> "+outputFile );
			File input = new File( inputFile );
			File output = new File( outputFile );
			handleFile( input , output, sourceCharset, targetCharset, infoComment );	
		}
		
		
	}
	
	public static void main( String[] args ) {
		try {
			Properties params = ArgUtils.getArgs( args );
			handle( params );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
}

class RecurseFilter implements FileFilter {

	private String pattern;
	
	public RecurseFilter(String pattern) {
		super();
		this.pattern = pattern;
	}

	@Override
	public boolean accept( File pathname ) {
		return pathname.isDirectory() || pathname.getName().matches( pattern );
	}
	
}
