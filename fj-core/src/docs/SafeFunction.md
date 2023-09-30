# SafeFunction

[fj-core index](../../README.md)

SafeFunction provides API for handling exception without a try catch block.

Imagine, for instance, you are writing some code to append all the lines in a reader to one line, the classic code would look like :

```
  	public String testExampleToOneLineClassic() {
		StringBuilder builder = new StringBuilder();
		try ( BufferedReader reader = new BufferedReader( new StringReader( "test" ) ) ) {
			reader.lines().forEach( line -> builder.append( line+" " ) );
		} catch (IOException e) {
			throw new ConfigRuntimeException( e );
		}
		return builder.toString();
	}
```

With safe functions it look likes : 

```
	public String testExampleToOneLineSafeFunction() {
		return SafeFunction.get(() -> {
			StringBuilder builder = new StringBuilder();
			try ( BufferedReader reader = new BufferedReader( new StringReader( "test" ) ) ) {
				reader.lines().forEach( line -> builder.append( line+" " ) );
			}	
			return builder.toString();
		});
	}
```

The default behavior is that any thrown Exception will be wrapped by a ConfigRuntimeException.
It is possible to provide custom exception handlers.

Read the [SafeFunction javadoc](https://javadoc.io/doc/org.fugerit.java/fj-core/latest/org/fugerit/java/core/function/SafeFunction.html) too.