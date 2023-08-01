package com.byneuron.azure.header;

final public class Headers {

	private Headers() {}
	
	public static final String correlationId = "x-correlation-id"; 
	
	public static final String retryableTransactionId = "x-retryable-transaction-id";
	public static final String parentTransactionId = "x-parent-transaction-id";
	public static final String transactionId = "transaction-id";
	public static final String traceId = "trace-id";
	
	public static final String tenantId = "x-tenant-id";
	public static final String userId = "x-user-id";
}
