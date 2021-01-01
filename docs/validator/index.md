[Docs Home](../../index.md)

## Validator Catalog configuration reference

&lt;validator-catalog/&gt; structure : 

```
+--validator-catalog
   +--custom-messages(*)
      +--entry(*)
   +--validator(*)
      +--entry(*)
```

Configuration reference : 
* [validator-catalog](tag_validator-catalog.md)
* [custom-messages](tag_validator-catalog_custom-messages.md)
* [validator](tag_validator-catalog_validator.md)
* [entry](tag_validator-catalog_entry.md)
  
Build in validators : 
* [BasicValidator (since 0.7.4.6)](validator_basic.md)
* [ValidatorRegex (since 0.7.4.6)](validator_regex.md)
* [ValidatorDate (since 0.7.4.6)](validator_date.md)
* [ValidatorNumber (since 0.7.4.7)](validator_number.md)
  
Other configurations : 
* [Default Validator Messages](../../fj-core/src/main/resources/core/validator/validator.properties)

Here is a complete [sample validator catalog](../../fj-core/src/test/resources/core/validator/validator-catalog-test.xml)