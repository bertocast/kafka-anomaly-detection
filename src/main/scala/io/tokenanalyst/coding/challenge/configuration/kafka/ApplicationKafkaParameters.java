package io.tokenanalyst.coding.challenge.configuration.kafka;

public class ApplicationKafkaParameters {

    private ApplicationKafkaParameters(){}

    public static final String KAFKA_BROKER = "kafka:9092";


    public static final String DATA_TOPIC = "full-transactions";
    public static final String MODELS_TOPIC = "models";

    public static final String DATA_GROUP = "tokensRecordsGroup";

    public static final String STORE_NAME = "ModelStore";
    public static final int STORE_ID = 42;




}