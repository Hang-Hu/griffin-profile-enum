uname -a
apt-get update
apt -y install git
mkdir /root/huhang
cd /root/huhang
git clone https://github.com/bhlx3lyx7/griffin-sample.git
cd griffin-sample/accuracy-sample/

hadoop fs -mkdir -p /griffin/data/enum-profiling-sample
#hadoop fs -mkdir -p /griffin/data/enum-profiling-sample/result
hadoop fs -put test_src.avro /griffin/data/enum-profiling-sample
#curl -O https://search.maven.org/remotecontent?filepath=org/apache/griffin/measure/0.1.5-incubating/measure-0.1.5-incubating.jar
spark-submit --class com.griffin.profile.ProfileApplication --master yarn-client \
             --num-executors 1 \
             --conf "spark.yarn.dist.files=$SPARK_HOME/conf/hive-site.xml" \
             griffin-profile-1.0-SNAPSHOT-jar-with-dependencies.jar \
	         enumProfile.json
