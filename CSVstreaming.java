import java.util.Arrays;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;


public final class CSVstreaming {
public static void main(String[] args) throws Exception {
		//구조적 스트리밍 사용하기 위해 스파크세션 생성
		SparkSession spark = SparkSession
			  .builder()
			  .appName("JavaStructuredNetworkWordCount")
			  .getOrCreate();
		
		// .csv 파일을 받아오기 전에 Dataset의 스키마를 만들어야함
		StructType MySchema = DataTypes.createStructType(new StructField[] {
	            DataTypes.createStructField("id",  DataTypes.StringType, true),
	            DataTypes.createStructField("name", DataTypes.StringType, true),
	            DataTypes.createStructField("password", DataTypes.StringType, true),
	            DataTypes.createStructField("age", DataTypes.IntegerType, true)
	    });
		
		// 해당 경로에 있는 .csv 파일을 모두 가져옴. 파일의 스키마는 기존에 정의한 스키마를 따름
		Dataset<Row> df = spark
				  .readStream()
				  .format("csv")
				  .option("header", "true")
				  .schema(MySchema)
				  .csv("/home/heeguk/csvhere");

		
		//콘솔에 
		StreamingQuery query = df.writeStream()
				  .outputMode("append")
				  .format("console")
				  .start();
		
		query.awaitTermination();

  }
}
