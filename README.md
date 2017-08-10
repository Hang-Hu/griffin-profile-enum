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

```
{
  "source": {
    "type": "avro",
    "version": "1.7",
    "config": {
      "file.name": "src/main/resources/test_src.avro"
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

```
{
  "persist": [
    {
      "type": "log",
      "config": {
        "max.log.lines": 20
      }
    },
    {
      "type": "http",
      "config": {
        "method": "post",
        "api": "http://10.149.247.156:49200/griffin/accuracy"
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

