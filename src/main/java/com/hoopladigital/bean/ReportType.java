package com.hoopladigital.bean;

public enum ReportType {

	ANOTHER_TYPE(SomeReport.class),
	ORDER_LINE_DOWNLOAD(OrderlineDownloadReport.class);

	private final Class<?> payloadClass;

	ReportType(final Class<?> payloadClass) {
		this.payloadClass = payloadClass;
	}

	public Class<?> getPayloadClass() {
		return payloadClass;
	}

}
