#apt-get update
#apt -y install git
mkdir -p /root/griffin/enum-profiling
cd /root/griffin/enum-profiling
curl -LO https://raw.githubusercontent.com/justACT/griffin-profile-enum/master/sample/enumProfile.json
curl -LO https://raw.githubusercontent.com/justACT/griffin-profile-enum/master/sample/env.json
curl -LO https://raw.githubusercontent.com/justACT/griffin-profile-enum/master/sample/test_src.avro
#git clone --recursive https://github.com/Hang-Hu/griffin-sample.git
#cd griffin-sample/enum-profiling-sample/sample
curl -LO https://github.com/justACT/griffin-profile-enum/releases/download/0.1.0/griffin-profile-1.0-SNAPSHOT-jar-with-dependencies.jar
hadoop fs -mkdir -p /griffin/data/enum-profiling-sample
hadoop fs -put test_src.avro /griffin/data/enum-profiling-sample
spark-submit --class com.griffin.profile.ProfileApplication --master yarn-client \
             --num-executors 1 \
             --conf "spark.yarn.dist.files=$SPARK_HOME/conf/hive-site.xml" \
             griffin-profile-1.0-SNAPSHOT-jar-with-dependencies.jar \
	         ./enumProfile.json
