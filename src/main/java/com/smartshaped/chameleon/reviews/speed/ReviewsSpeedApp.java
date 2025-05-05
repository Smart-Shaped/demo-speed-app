package com.smartshaped.chameleon.reviews.speed;

import com.smartshaped.chameleon.common.utils.exception.CassandraException;
import com.smartshaped.chameleon.common.utils.exception.ConfigurationException;
import com.smartshaped.chameleon.common.utils.exception.KafkaConsumerException;
import com.smartshaped.chameleon.speed.exception.SpeedLayerException;
import com.smartshaped.chameleon.speed.exception.SpeedUpdaterException;

public class ReviewsSpeedApp {

  public static void main(String[] args)
      throws SpeedLayerException,
          ConfigurationException,
          NumberFormatException,
          SpeedUpdaterException,
          KafkaConsumerException,
          CassandraException {

    ReviewsSpeedLayer speedLayer = new ReviewsSpeedLayer();

    speedLayer.start();
  }
}
