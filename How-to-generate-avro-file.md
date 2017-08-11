# How to generate avro file

Reference on how to use avsc and json files to generate the avro file: [https://avro.apache.org/docs/1.8.2/gettingstartedjava.html](https://avro.apache.org/docs/1.8.2/gettingstartedjava.html).

The demo is based on dataset from [https://archive.ics.uci.edu/ml/datasets/Adult](https://archive.ics.uci.edu/ml/datasets/Adult).

#### test_src.avsc

```
{
   "type":"record",
   "name":"Record",
   "namespace":"org.apache.griffin",
   "fields":[
      {
         "name":"age",
         "type":"string"
      },
      {
         "name":"workclass",
         "type":"string"
      },
      {
         "name":"fnlwgt",
         "type":"string"
      },
      {
         "name":"education",
         "type":"string"
      },
      {
         "name":"educationNum",
         "type":"string"
      },
      {
         "name":"martialStatus",
         "type":"string"
      },
      {
         "name":"occupation",
         "type":"string"
      },
      {
         "name":"relationships",
         "type":"string"
      },
      {
         "name":"race",
         "type":"string"
      },
      {
         "name":"sex",
         "type":"string"
      },
      {
         "name":"capitalGain",
         "type":"string"
      },
      {
         "name":"capitialLoss",
         "type":"string"
      },
      {
         "name":"hoursPerWeek",
         "type":"string"
      },
      {
         "name":"nativeCountry",
         "type":"string"
      }
   ]
}
```

#### test_src.json

This is only one instance, full content is in folder sample

```
{"age":"39","workclass":"State-gov","fnlwgt":"77516","education":"Bachelors","educationNum":"13","martialStatus":"Never-married","occupation":"Adm-clerical","relationships":"Not-in-family","race":"White","sex":"Male","capitalGain":"2174","capitialLoss":"0","hoursPerWeek":"40","nativeCountry":"United-States"}
```


Convert `test_src.avsc` and `test_src.json` to `test_src.avro`:

```
wget http://www.namesdir.com/mirrors/apache/avro/avro-1.8.2/java/avro-tools-1.8.2.jar
java -jar ./avro-tools-1.8.2.jar fromjson --schema-file test_src.avsc test_src.json > test_src.avro
```
