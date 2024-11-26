package com.smartshaped.chameleon.reviews.speed.utils;

import java.time.Instant;

import com.smartshaped.chameleon.common.utils.TableModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrendingModel extends TableModel {

	private String asin;

	private Double meanRating;

	private Instant windowStart;

	private Instant windowEnd;

	@Override
	protected String choosePrimaryKey() {
		return "";
	}

}
