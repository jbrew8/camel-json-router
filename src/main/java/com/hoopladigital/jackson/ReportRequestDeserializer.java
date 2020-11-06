package com.hoopladigital.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoopladigital.bean.ReportRequest;
import com.hoopladigital.bean.ReportType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.fasterxml.jackson.databind.type.SimpleType.constructUnsafe;

@Slf4j
public class ReportRequestDeserializer extends StdDeserializer<ReportRequest<?>> {

	public ReportRequestDeserializer(final Class<?> vc) {
		super(vc);
	}

	@Override
	public ReportRequest<?> deserialize(
		final JsonParser jsonParser,
		final DeserializationContext context
	) throws IOException {

		// read the json tree
		final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

		// extract the report type and create our request
		final JsonNode jsonNode = node.get("reportType");
		final ReportType reportType;
		if (jsonNode != null) {
			reportType = ReportType.valueOf(jsonNode.asText());
			log.debug("using report type {}", reportType);
		} else {
			log.warn("no report type found - deserializing will not be correct for this json");
			reportType = null;
		}
		final ReportRequest<?> request = new ReportRequest<>(reportType);

		if (null != reportType) {

			// since we know the type, we can get and parse the payload...
			final JsonNode payload = node.get("payload");
			if (null != payload) {

				final JsonParser payloadParser = payload.traverse();

				// deserializing payload fails with "unexpected end of input" if we don't call nextToken() first
				payloadParser.nextToken();

				// ...and deserialize it
				request.setPayload(context.readValue(payloadParser, constructUnsafe(reportType.getPayloadClass())));

			}

		}

		return request;

	}

}
