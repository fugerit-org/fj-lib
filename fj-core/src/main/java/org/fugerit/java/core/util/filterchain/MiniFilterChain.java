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
		while ( it.hasNext() && goOn ) {
			MiniFilter filter = it.next();
			int stepResult = filter.apply(context, data);
			goOn = ( CONTINUE == stepResult );
			logger.debug( this.toString() +", step : "+filter.toString()+", continue? "+goOn );
		}
		return res;
	}
	
}
