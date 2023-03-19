[Validator Catalog Home](index.md) | [Docs Home](../../index.md)

### ValidatorRegex reference <a name="top"/>

This is the [regex validator](https://www.fugerit.org/data/java/javadoc/fj-core/org/fugerit/java/core/validator/ValidatorRegex.html) implementation.

Here are the [Default Validator Messages](../../fj-core/src/main/resources/core/validator/validator.properties)
  
<table width="100%">
	<tr>
		<th colspan="2">configuration entries reference</th>
	</tr>
	<tr>
		<th width="30%">Key</th>
		<th width="70%">Description</th>
	</tr>
	<tr>
		<td><a href="#regex">regex</a></td>
		<td>The regex used to check value.</td>
	</tr>	
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="regex">regex</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, required)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>^[a-zA-ZÀ-ž' \-\.,]*$</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.regex</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - info or regex
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>


