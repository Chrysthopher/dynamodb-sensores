package com.utfpr.dynamodb;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;

@Service
public class SensorService {
    private final DynamoDbTable<SensorData> table;

    public SensorService(DynamoDbEnhancedClient enhancedClient) {
        this.table = enhancedClient.table("SensoresToledo", TableSchema.fromBean(SensorData.class));
    }

    public void salvar(SensorData data) {
        table.putItem(data);
    }

    public SensorData buscarPorIdETimestamp(String sensorId, Long timestamp) {
        return table.getItem(Key.builder().partitionValue(sensorId).sortValue(timestamp).build());
    }

    public List<SensorData> listarPorSensor(String sensorId) {
        List<SensorData> result = new ArrayList<>();
        table.query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(sensorId))))
                .items().forEach(result::add);
        return result;
    }

    public List<SensorData> listarPorLocalizacaoGSI(String localizacao) {
        List<SensorData> result = new ArrayList<>();
        DynamoDbIndex<SensorData> index = table.index("LocalizacaoIndex");
        index.query(r -> r.queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(localizacao))))
                .forEach(page -> page.items().forEach(result::add));
        return result;
    }

    public List<SensorData> scanTemperaturaMaior(Double tempMinima) {
        List<SensorData> result = new ArrayList<>();
        Expression filter = Expression.builder()
                .expression("Temperatura > :val")
                .putExpressionValue(":val", AttributeValue.builder().n(String.valueOf(tempMinima)).build())
                .build();
        table.scan(r -> r.filterExpression(filter)).items().forEach(result::add);
        return result;
    }
}
