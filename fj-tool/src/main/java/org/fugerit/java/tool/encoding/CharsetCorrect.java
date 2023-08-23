package org.fugerit.java.tool.encoding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.fugerit.java.core.charset.EncodingCheck;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.tool.Launcher;
import org.fugerit.java.tool.RunToolException;
import org.fugerit.java.tool.ToolHandlerHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Tool to correct character encoding.</p>
 * 
 * @author Fugerit
 *
 */
@Slf4j
public class CharsetCorrect extends ToolHandlerHelper {

	private CharsetCorrect() {}
	
	private static void handleFile( File input, File output, String sourceCharset, String targetCharset, boolean infoComment, boolean verbose ) throws RunToolException {
		try {
			FileInputStream is = new FileInputStream( input );
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			StreamIO.pipeStream( is , os , StreamIO.MODE_CLOSE_BOTH );
			byte[] sourceData = os.toByteArray();
			boolean hasErrors = ! EncodingCheck.checkEncoding( sourceData , targetCharset );
			if ( hasErrors ) {
				StringBuilder infoCommentBuffer = new StringBuilder();
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
				} else if ( infoComment && input.getName().endsWith( "properties" ) ) {
					String comment = "# "+infoCommentBuffer.toString();
					StreamIO.pipeStream( new ByteArrayInputStream( comment.getBytes( targetCharset ) ) , fos, StreamIO.MODE_CLOSE_IN_ONLY );
				}
				log.info( "input : {} , output : {} , comment : {}" + input.getAbsolutePath(), output.getAbsolutePath(), infoCommentBuffer );
				fos.close();
				log.debug( "written file -> {}", output.getAbsolutePath() );
			} else if ( verbose ) {
				log.info( "correct encoding, skipping : {}", input.getAbsolutePath() );
			}
		} catch ( Exception e ) {
			throw RunToolException.convertEx( e );
		}
	}
	
	private static void recurse( File current, FileFilter filter, String sourceCharset, String targetCharset, boolean infoComment, boolean verbose ) throws RunToolException {
		log.debug( "recurse "+current.getAbsolutePath() );
		if ( current.isFile() ) {
			handleFile( current , current, sourceCharset, targetCharset, infoComment, verbose );
		} else {
			File[] list = current.listFiles( filter );
			if ( list != null ) {
				for ( int k=0; k<list.length; k++ ) {
					recurse( list[k] , filter, sourceCharset, targetCharset, infoComment, verbose );	
				}	
			}
		}
	}
	
	/**
	 * Arg for input file
	 */
	public static final String PARAM_INPUT_FILE = "input-file";
	
	/**
	 * Arg for output file (default to input-file)
	 */
	public static final String PARAM_OUTPUT_FILE = "output-file";
	
	/**
	 * Arg for a folder to recurse on
	 */
	public static final String PARAM_FOLDER_RECURSE = "folder-recurse";
	
	/**
	 * Arg for a name filter to apply on the recursion
	 */
	public static final String PARAM_FOLDER_FILTER = "folder-filter";
	
	/**
	 * This Arg should be set to '1' in case you want to add comment to corrected files
	 */
	public static final String PARAM_APPEND_INFO_AS_COMMENT = "info-comment";
	
	/**
	 * Path to a report file for the whole operation
	 */
	public static final String PARAM_REPORT_FILE = "report-file";
	
	/**
	 * Arg for Target charset
	 */
	public static final String PARAM_TARGET_CHARSET = "target-charset";
	public static final String PARAM_TARGET_CHARSET_DEFAULT = "utf-8";
	
	/**
	 * Arg for Source charset
	 */
	public static final String PARAM_SOURCE_CHARSET = "source-charset";
	public static final String PARAM_SOURCE_CHARSET_DEFAULT = "Windows-1252";
	
	/**
	 * <p>The main method of this Class.</P>
	 * 
	 * 
	 * @param params		the arguments
	 * @throws Exception	if issues arise.
	 */
	public static void correct( Properties params ) throws RunToolException {
		String inputFile = params.getProperty( PARAM_INPUT_FILE );
		String outputFile = params.getProperty( PARAM_OUTPUT_FILE, inputFile );
		String folderRecurse = params.getProperty( PARAM_FOLDER_RECURSE );
		String folderFilter = params.getProperty( PARAM_FOLDER_FILTER, "." )+"*";
		String targetCharset = params.getProperty( PARAM_TARGET_CHARSET, PARAM_TARGET_CHARSET_DEFAULT );
		String sourceCharset = params.getProperty( PARAM_SOURCE_CHARSET, PARAM_SOURCE_CHARSET_DEFAULT );
		boolean infoComment = ( params.getProperty( PARAM_APPEND_INFO_AS_COMMENT ) != null );
		boolean verbose = ( params.getProperty( Launcher.ARG_VERBOSE ) != null );
		log.info( PARAM_TARGET_CHARSET+" -> "+targetCharset );
		if ( folderRecurse != null ) {
			log.info( LOG_PARAM_LITERAL, PARAM_FOLDER_RECURSE, folderRecurse );
			log.info( LOG_PARAM_LITERAL, PARAM_FOLDER_FILTER, folderFilter );
			File baseFolder = new File( folderRecurse );
			recurse( baseFolder, new RecurseFilter( folderFilter ), sourceCharset, targetCharset, infoComment, verbose );
		} else {
			log.info( LOG_PARAM_LITERAL, PARAM_INPUT_FILE, inputFile );
			log.info( LOG_PARAM_LITERAL, PARAM_OUTPUT_FILE, outputFile );
			File input = new File( inputFile );
			File output = new File( outputFile );
			handleFile( input , output, sourceCharset, targetCharset, infoComment, verbose );	
		}
	}

	@Override
	public int handleWorker(Properties params) throws RunToolException {
		correct( params );
		return EXIT_OK;
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
