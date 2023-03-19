[Validator Catalog Home](index.md) | [Docs Home](../../index.md)

### BasicValidator reference <a name="top"/>

This is the [date validator](https://www.fugerit.org/data/java/javadoc/fj-core/org/fugerit/java/core/validator/ValidatorDate.html) implementation.

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
		<td><a href="#dateFormat">dateFormat</a></td>
		<td>The date format will be used to check if it's possible to convert the value</td>
	</tr>	
	<tr>
		<td><a href="#miDate">miDate</a></td>
		<td>If set checks for minimum date</td>
	</tr>	
	<tr>
		<td><a href="#maxDate">maxDate</a></td>
		<td>If set checks for maximum date</td>
	</tr>	
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="dateFormat">dateFormat</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, required)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>yyyy-MM-dd</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.date</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - info or dateFormat
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="minDate">minDate</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, optional)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>2020-01-01</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.date.min</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - minDate
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="maxDate">maxDate</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, optional)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>2020-12-31</td>
	</tr>	
	<tr>
		<th>error message key</th>
		<td>error.date.max</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - maxDate
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

