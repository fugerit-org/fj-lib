package org.fugerit.java.core.util.filterchain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.lang.helpers.StringUtils;

public class MiniFilterChain extends MiniFilterBase {

	public MiniFilterChain() {
		this( CONTINUE );
	}

	public MiniFilterChain(int defaultBehaviour) {
		this( genKey(), defaultBehaviour );
	}

	public MiniFilterChain(String key, int defaultBehaviour) {
		super(key, defaultBehaviour);
		this.filterChain = new ArrayList<MiniFilter>();
		this.defaultConfig = new Properties();
	}

	private List<MiniFilter> filterChain;

	public List<MiniFilter> getFilterChain() {
		return filterChain;
	}

	private Properties defaultConfig;
	
	public Properties getDefaultConfig() {
		return defaultConfig;
	}
	
	@Override
	public int apply(MiniFilterContext context, MiniFilterData data) throws Exception {
		int res = this.getDefaultBehaviour();
		if ( StringUtils.isEmpty( context.getChainId() ) ) {
			context.setChainId( this.getChainId() );
		}
		Iterator<MiniFilter> it = this.getFilterChain().iterator();
		boolean goOn = true;
		context.getCustomConfig().putAll( this.getDefaultConfig() );
		Exception ex = null;
		// now all the steps are always looked
		while ( it.hasNext() ) {
			MiniFilter filter = it.next();
			int stepResult = SKIP;
			// if previous status was continue apply this step
			if ( goOn ) {
				try {
					stepResult = filter.apply(context, data);
					goOn = ( SKIP != stepResult );	
				} catch (Exception e) {
					logger.warn( "Error : "+e, e );
					goOn = false;
					ex = e;
					if ( context.getExHandler() != null ) {
						context.getExHandler().error( e );
					}
				}
			// apply anyways if prevopus status was always but next step will not be affected
			} else if ( filter.getDefaultBehaviour() == ALWAYS ) {
				stepResult = filter.apply(context, data);
			}
			if ( logger.isDebugEnabled() ) {
				String message = this.toString() +", step : "+filter.toString()+", continue? "+goOn+" stepResult : "+stepResult;
				logger.debug( message );
			}
		}
		if ( ex != null ) {
			throw ex;
		}
		return res;
	}
	
}
