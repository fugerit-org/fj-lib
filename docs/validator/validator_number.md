[Validator Catalog Home](index.md) | [Docs Home](../../index.md)

### BasicValidator reference <a name="top"/>

This is the [number validator](https://www.fugerit.org/data/java/javadoc/fj-core/org/fugerit/java/core/validator/ValidatorNumber.html) implementation.

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
		<td><a href="#maximumIntegerDigits">maximumIntegerDigits</a></td>
		<td>Maximum number of digits in integer part of the number</td>
	</tr>	
	<tr>
		<td><a href="#maximumFractionDigits">maximumFractionDigits</a></td>
		<td>Maximum number of digits in fraction part of the number</td>
	</tr>	
	<tr>
		<td><a href="#groupingUsed">groupingUsed</a></td>
		<td>Set to 'true' or '1' if the number has grouping (i.e. 1,000)</td>
	</tr>	
	<tr>
		<td><a href="#currency">currency</a></td>
		<td>Set to 'true' or '1' if the number is currency</td>
	</tr>	
	<tr>
		<td><a href="#minValue">minValue</a></td>
		<td>If set checks for minimum value</td>
	</tr>	
	<tr>
		<td><a href="#maxValue">maxValue</a></td>
		<td>If set checks for maximum value</td>
	</tr>	
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="maximumIntegerDigits">maximumIntegerDigits</a></caption>
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
		<td>error.number.maxinteger</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - info or maximumIntegerDigits
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.7</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="maximumFractionDigits">maximumFractionDigits</a></caption>
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
		<td>error.number.maxfraction</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - info or maximumFractionDigits
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.7</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="groupingUsed">groupingUsed</a></caption>
	<tr>
		<th>Default</th>
		<td>false</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>true</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.7</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="currency">currency</a></caption>
	<tr>
		<th>Default</th>
		<td>false</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>true</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.7</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="minValue">minValue</a></caption>
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
		<td>error.number.min</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - minValue
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.7</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Entry <a name="maxValue">maxValue</a></caption>
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
		<td>error.number.max</td>
	</tr>	
	<tr>
		<th>error message params</th>
		<td>
		0 - field label<br/>
		1 - value<br/>
		2 - maxValue
		</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.7</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

