package com.objectfrontier.training.web.application.util;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

/**
 * @author keerthanar
 * @since  Oct 03, 2016
 */
public class JsonUtil {

	private static ObjectMapper mapper = null;
	private static ObjectMapper getObjectMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		}
		return mapper;
	}

	public static String toJson(Object o) {

		try {
			return getObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public static <T> T toObject(String jsonString, Class<? extends T> type) {

		try {
			return getObjectMapper().readValue(jsonString, type);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	public static <T> List<T> toList(String json, Class<T> elementType) {

		try {

			ObjectMapper mapper = getObjectMapper();

			CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, elementType);

			return mapper.readValue(json, listType);

		} catch (Exception e) {
			throw new AppException(e);
		}
	}
}
