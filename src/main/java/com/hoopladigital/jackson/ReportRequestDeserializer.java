package com.hoopladigital.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hoopladigital.bean.ReportRequest;
import com.hoopladigital.bean.ReportType;

import java.io.IOException;

import static com.fasterxml.jackson.databind.type.SimpleType.constructUnsafe;

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
		final ReportType reportType = ReportType.valueOf(node.get("reportType").asText());
		final ReportRequest<?> request = new ReportRequest<>(reportType);

		// now we can get the payload...
		final JsonParser payloadParser = node.get("payload").traverse();

		// deserializing payload fails with "unexpected end of input" if we don't call nextToken() first
		payloadParser.nextToken();

		// ...and deserialize it
		request.setPayload(context.readValue(payloadParser, constructUnsafe(reportType.getPayloadClass())));

		return request;

	}

}
