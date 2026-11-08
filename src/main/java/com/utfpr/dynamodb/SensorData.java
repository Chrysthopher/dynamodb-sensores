package com.utfpr.dynamodb;

import com.fasterxml.jackson.annotation.JsonProperty;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class SensorData {
    private String sensorId;
    private Long timestamp;
    private Double temperatura;
    private Double umidade;
    private String localizacao;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("SensorID")
    @JsonProperty("sensorID")
    public String getSensorID() { return sensorId; }
    public void setSensorID(String sensorId) { this.sensorId = sensorId; }

    @DynamoDbSortKey
    @DynamoDbSecondarySortKey(indexNames = "LocalizacaoIndex")
    @DynamoDbAttribute("Timestamp")
    @JsonProperty("timestamp")
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    @DynamoDbAttribute("Temperatura")
    @JsonProperty("temperatura")
    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    @DynamoDbAttribute("Umidade")
    @JsonProperty("umidade")
    public Double getUmidade() { return umidade; }
    public void setUmidade(Double umidade) { this.umidade = umidade; }

    @DynamoDbSecondaryPartitionKey(indexNames = "LocalizacaoIndex")
    @DynamoDbAttribute("Localizacao")
    @JsonProperty("localizacao")
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}
