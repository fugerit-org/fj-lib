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
* [BasicValidator](validator_basic.md)
* [ValidatorRegex](validator_regex.md)
* [ValidatorDate](validator_date.md)
  
Other configurations : 
* [Default Validator Messages](../../fj-core/src/main/resources/core/validator/validator.properties)

Here is a complete [sample validator catalog](../../fj-core/src/test/resources/core/validator/validator-catalog-test.xml)