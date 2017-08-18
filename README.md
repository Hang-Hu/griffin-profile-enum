# Griffin enum profiling

## Function

Compute the ocurrence possibility of each enum in a column of a data set.

## Main business process
![Business_Process_image](doc/image/enum-profiling-proccess.svg)

## Usage

### Source data

Avro is used as source data, containing both schema and data.

[How to generate avro file](How-to-generate-avro-file.md)


### Configuration files

#### enumProfile.json

`source` is the `source` part of `config.json`. 

`envFile` is the args submitted to griffin on how and where to get `env.json`.

`enumValue`, `elasticSearch`, `persist` is the config file used by `griffin-profile-enum`.

`enumValue` is the key and all values of enum to be profiled in `test_src.json`.

`elasticSearch` indicates where to read single matched results persisted by griffin measure. `ip:port+endPoint` makes up the adress of single matched results in elasticsearch. `hitNamePrefix` adds a prefix to `name` of each single matched result in elasticsearch.

`persist` is the way `griffin-profile-enum` uses to store the final results, which is the combination of all single matched results.

```
{
  "source": {
    "type": <source type, either avro or hive>,
    "version": "1.7",
    "config": {
      "file.name": <avro file path, either in local or hdfs | hive table name>
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
    "type":<local or raw>,
    "config":{
      "file.name":<local path, applicable if type is local>,
      "file.content":<content of env.json, applicable if type is raw>
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
        "path": <hdfs path to store the combined result>
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

### Run sample

A sample has been provided to run the enum profiling.

```
curl -LO https://raw.githubusercontent.com/justACT/griffin-profile-enum/master/script/docker.sh
chmod +x docker.sh
nohup ./docker.sh > enum-profiling.log &
tail -f enum-profiling.log
```

The result of doing enum profiling on race:

```
{Amer-Indian-Eskimo=0.009551303706888609, OTHER=0.0, Asian-Pac-Islander=0.03190933939375326, White=0.8542735173981143, Black=0.0959429992936335, Other=0.008322840207610331}
```
