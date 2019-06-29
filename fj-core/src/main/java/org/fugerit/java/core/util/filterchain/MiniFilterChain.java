package org.fugerit.java.core.util.filterchain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	}

	private List<MiniFilter> filterChain;

	public List<MiniFilter> getFilterChain() {
		return filterChain;
	}

	@Override
	public int apply(MiniFilterContext context, MiniFilterData data) throws Exception {
		int res = this.getDefaultBehaviour();
		Iterator<MiniFilter> it = this.getFilterChain().iterator();
		boolean goOn = true;
		// now all the steps are always looked
		while ( it.hasNext() ) {
			MiniFilter filter = it.next();
			int stepResult = SKIP;
			// if previous status was continue apply this step
			if ( goOn ) {
				stepResult = filter.apply(context, data);
				goOn = ( SKIP != stepResult );
			// apply anyways if prevopus status was always but next step will not be affected
			} else if ( filter.getDefaultBehaviour() == ALWAYS ) {
				stepResult = filter.apply(context, data);
			}
			logger.debug( this.toString() +", step : "+filter.toString()+", continue? "+goOn+" stepResult : "+stepResult );
		}
		return res;
	}
	
}
