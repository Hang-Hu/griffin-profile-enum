uname -a
apt-get update
apt -y install git
mkdir /root/
cd /root/
git clone --recursive https://github.com/Hang-Hu/griffin-sample.git
cd griffin-sample/enum-profiling-sample/sample
curl -LO https://github.com/justACT/griffin-profile-enum/releases/download/0.1.0/griffin-profile-1.0-SNAPSHOT-jar-with-dependencies.jar
hadoop fs -mkdir -p /griffin/data/enum-profiling-sample
#hadoop fs -mkdir -p /griffin/data/enum-profiling-sample/result
hadoop fs -put test_src.avro /griffin/data/enum-profiling-sample
#curl -O https://search.maven.org/remotecontent?filepath=org/apache/griffin/measure/0.1.5-incubating/measure-0.1.5-incubating.jar
spark-submit --class com.griffin.profile.ProfileApplication --master yarn-client \
             --num-executors 1 \
             --conf "spark.yarn.dist.files=$SPARK_HOME/conf/hive-site.xml" \
             griffin-profile-1.0-SNAPSHOT-jar-with-dependencies.jar \
	         ./enumProfile.json
