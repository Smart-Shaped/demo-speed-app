package com.smartshaped.chameleon.reviews.speed.components;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;

import com.smartshaped.chameleon.common.preprocessing.Preprocessor;
import com.smartshaped.chameleon.common.preprocessing.exception.PreprocessorException;
import com.smartshaped.chameleon.common.utils.exception.CassandraException;
import com.smartshaped.chameleon.common.utils.exception.ConfigurationException;
import com.smartshaped.chameleon.speed.SpeedUpdater;
import com.smartshaped.chameleon.speed.exception.SpeedUpdaterException;

public class TrendingReviewsUpdater extends SpeedUpdater {

  private Preprocessor preprocessor;

  private static final String WATERMARK = "1 seconds";
  private static final String WINDOW_DURATION = "1 minute";
  private static final String SLIDE_DURATION = "1 minute";

  public TrendingReviewsUpdater() throws ConfigurationException, CassandraException {
    super();
    this.preprocessor = new ReviewPreprocessor();
  }

  @Override
  public Dataset<Row> updateSpeed(Dataset<Row> df) throws SpeedUpdaterException {

    try {
      df = preprocessor.preprocess(df);
    } catch (PreprocessorException e) {
      throw new SpeedUpdaterException("Error preprocessig datastream", e);
    }

    df =
        df.withWatermark("timestamp", WATERMARK)
            .groupBy(
                functions.window(functions.col("timestamp"), WINDOW_DURATION, SLIDE_DURATION),
                functions.col("asin"))
            .agg(functions.avg("rating").alias("meanrating"));

    df = df.where(functions.col("meanrating").$greater$eq(4.5));

    df =
        df.withColumn("windowstart", functions.col("window.start"))
            .withColumn("windowend", functions.col("window.end"))
            .drop("window");

    df.printSchema();

    return df;
  }
}
