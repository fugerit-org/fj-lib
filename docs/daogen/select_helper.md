[DaoGen API Home](index.md) | [Docs Home](../../index.md)

# SelectHelper

The SelectHelper class is basically a wrapper for parameters needed in a `java.sql.PreparedStatement`


Here is a sample code : 


```
			// SelectHelper setup
			BasicDAOHelper<String> daoHelper = new BasicDAOHelper<>(context);
			SelectHelper selectHelper = daoHelper.newSelectHelper( "fugerit.address" );
			// select helper is basically a query builder, it is possible to add functions, instead of the simple column name
			String column = String.format( "UPPER(%s)" , COL_INFO );		// will add 'UPPER(INFO)'
			// it is possible to transform upper case the value to be compared
			String value = "test address 01".toUpperCase();
			// this will perform an ignore case comparison, as both the column and the value are now UPPERCASE
			selectHelper.andEqualParam( column , value );
			logger.info( "sql -> {}", selectHelper.getQueryContent() );		// method getQueryContent() provides access to current query buffer
			// the result
			String res = daoHelper.loadOneHelper( selectHelper, RSE_ADDRESS_COL_INFO );
			logger.info( "res -> {}", res );
```

The method `selectHelper.getQueryContent()` gives access to the current query buffer.

You can find some examples as Junit too : 
* [Simple select sample](../../fj-core/src/test/java/test/org/fugerit/java/core/db/dao/daogen/TestSelectHelperSimple.java)
* [Ignore case select sample](../../fj-core/src/test/java/test/org/fugerit/java/core/db/dao/daogen/TestSelectHelperUpper.java)
