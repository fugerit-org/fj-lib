[Validator Catalog Home](index.md) | [Docs Home](../../index.md)

### &lt;validator&gt; reference <a name="top"/>

This is the reference for 'validator' element configuration and its attributes.

<table width="100%">
	<tr>
		<th colspan="2">validator attributes quick reference</th>
	</tr>
	<tr>
		<th width="30%">Attribute</th>
		<th width="70%">Description</th>
	</tr>
	<tr>
		<td><a href="#id">id</a></td>
		<td>Id of the validator</td>
	</tr>	
	<tr>
		<td><a href="#type">type</a></td>
		<td>java fully qualified class name of a subclass of 
		<a href="https://www.fugerit.org/data/java/javadoc/fj-core/org/fugerit/java/core/validator/BasicValidator.html">BasicValidator</a></td>
	</tr>	
	<tr>
		<td><a href="#parent">parent</a></td>
		<td>Parent validator (must be set before in the same catalog)</td>
	</tr>	
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Attribute <a name="id">id</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, required)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>dateValidator</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Attribute <a name="type">type</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, required)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>org.fugerit.java.core.validator.ValidatorDate</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>

<table>
	<caption>Attribute <a name="parent">parent</a></caption>
	<tr>
		<th>Default</th>
		<td>(no default, optional)</td>
	</tr>
	<tr>
		<th>Example</th>
		<td>dateValidator</td>
	</tr>	
	<tr>
		<th>Since</th>
		<td>0.7.4.6</td>
	</tr>
</table>

<br/><a href="#top">top</a><br/>