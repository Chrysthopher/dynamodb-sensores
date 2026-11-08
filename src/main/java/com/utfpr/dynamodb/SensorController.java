package com.utfpr.dynamodb;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {
    private final SensorService service;

    public SensorController(SensorService service) {
        this.service = service;
    }

    @PostMapping
    public String salvar(@RequestBody SensorData data) {
        service.salvar(data);
        return "Leitura salva com sucesso!";
    }

    @GetMapping("/{sensorId}/{timestamp}")
    public SensorData buscarUnico(@PathVariable String sensorId, @PathVariable Long timestamp) {
        return service.buscarPorIdETimestamp(sensorId, timestamp);
    }

    @GetMapping("/{sensorId}")
    public List<SensorData> listarPorSensor(@PathVariable String sensorId) {
        return service.listarPorSensor(sensorId);
    }

    @GetMapping("/localizacao/{localizacao}")
    public List<SensorData> listarPorLocalizacao(@PathVariable String localizacao) {
        return service.listarPorLocalizacaoGSI(localizacao);
    }

    @GetMapping("/scan/alerta-temperatura")
    public List<SensorData> alertaTemperatura(@RequestParam(defaultValue = "23.0") Double limite) {
        return service.scanTemperaturaMaior(limite);
    }
}
