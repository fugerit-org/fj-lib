[Validator Catalog Home](index.md) | [Docs Home](../../index.md)

### BasicValidator reference <a name="top"/>

This is the [basic validator](https://www.fugerit.org/data/java/javadoc/fj-core/org/fugerit/java/core/validator/BasicValidator.html) implementation.
All other validators extends this one.

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
		<td><a href="#required">required</a></td>
		<td>If set to true or 1 if the validator check for the value to be not empty</td>
	</tr>	
	<tr>
		<td><a href="#info">info</a></td>
		<td>Custom info to be used in validator message</td>
	</tr>	
	<tr>
		<td><a href="#minLength">minLength</a></td>
		<td>If set checks for minimum length</td>
	</tr>	
	<tr>
		<td><a href="#maxLength">maxength</a></td>
		<td>If set checks for maximum length</td>
	</tr>	
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="required">required</a></caption>
	<tr>
		<th>Default</th>
		<td>false</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>true</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.required</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>0 - field label</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="info">info</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, optional)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>alternate text</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="minLength">minLength</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, optional)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>2</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.length.min</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - length<br/>
		2 - minLength
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="maxLength">maxLength</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, optional)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>10</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.length.max</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - length<br/>
		2 - maxLength
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

