# griffin-profile-enum
## spec
## user guide

## spec
### define
enum value check in griffin means checking all value of the enum from avro file or hive table and return a calculate result to user.
### Basic structure
![Basic structure](doc/image/enumvalue-check-spec.png)

### Main business process
![Business_Process_image](doc/image/enumvalue-check-spec.png)

## Demo

### Data source

```

```


### Configuration files

#### enumProfile.json

`source` is the `source` part of `config.json`. 

`envFile` is the args submitted to griffin on how and where to get `env.json`.

`enumValue`, `elasticSearch`, `persist` is the config file used by `griffin-profile-enum`.

`enumValue` is the key and all values of enum to be profiled in `test_src.json`.

`elasticSearch` indicates where to read single matched results persisted by griffin measure. `ip:port+endPoint` makes up the adress of single matched results in elasticsearch.

`persist` is the way `griffin-profile-enum` uses to store the final results, which is the combination of all single matched results.

```
{
  "source": {
    "type": <source type, either avro or hive>,
    "version": "1.7",
    "config": {
      "file.name": <your avro file path, either in local or hdfs | your hive table name>
    }
  },

  "enumValue":{
    "name":"race",
    "valueSet":[
      "White",
      "Black",
      "Asian-Pac-Islander",
      "Amer-Indian-Eskimo",
      "Other"
    ]
  },

  "elasticSearch":{
    "ip":"10.149.247.156",
    "port":49200,
    "endPoint":"/griffin/accuracy/_search?pretty&filter_path=hits.hits._source&size=100",
    "hitNamePrefix":"profileEnum"
  },

  "envFile":{
    "type":"local",
    "config":{
      "file.name":"src/main/resources/env.json",
      "file.content":""
    }
  },

  "persist":[
    {
      "type":"log",
      "config":{
      }
    },
    {
      "type":"hdfs",
      "config":{
        "path": "hdfs://localhost:9000/griffin-profile/enum-value-check/"
      }
    }
  ]
}
```

#### env.json

`env.json` is to be submitted to griffin measure.

```
{
  "persist": [
    {
      "type": "log",
      "config": {
        "max.log.lines": <max lines to log>
      }
    },
    {
      "type": "hdfs",
      "config": {
        "path": <persist path in hdfs>,
        "max.persist.lines": <max lines to persist>,
        "max.lines.per.file": <max lines per file to persist>
      }
    },
    {
      "type": "http",
      "config": {
        "method": <http method>,
        "api": <url>
      }
    }
  ]
}
```

### Run

```
spark-submit --class com.griffin.profile.ProfileApplication --master yarn-client \
                    --num-executors 1 \
                    --conf "spark.yarn.dist.files=$SPARK_HOME/conf/hive-site.xml" \
                    griffin-profile-1.0-SNAPSHOT-jar-with-dependencies.jar \
                    /root/ttt/enumProfile.json
```

