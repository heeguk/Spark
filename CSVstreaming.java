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
		SparkSession spark = SparkSession
			  .builder()
			  .appName("JavaStructuredNetworkWordCount")
			  .getOrCreate();
		
		StructType MySchema = DataTypes.createStructType(new StructField[] {
	            DataTypes.createStructField("id",  DataTypes.StringType, true),
	            DataTypes.createStructField("name", DataTypes.StringType, true),
	            DataTypes.createStructField("password", DataTypes.StringType, true),
	            DataTypes.createStructField("age", DataTypes.IntegerType, true)
	    });
		
		
		Dataset<Row> df = spark
				  .readStream()
				  .format("csv")
				  .option("header", "true")
				  .schema(MySchema)
				  .csv("/home/heeguk/csvhere");

		
		
		StreamingQuery query = df.writeStream()
				  .outputMode("append")
				  .format("console")
				  .start();
		
		query.awaitTermination();

  }
}
