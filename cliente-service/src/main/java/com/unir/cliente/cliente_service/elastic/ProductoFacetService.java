package com.unir.cliente.cliente_service.elastic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoFacetService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // Local: http://localhost:9200
    // Docker: http://elasticsearch:9200
    // Cloud: https://xxxx.elastic.cloud:443
    @Value("${app.elasticsearch.url:http://localhost:9200}")
    private String elasticUrl;

    // En local estará vacío, en Cloud lo defines en variables de entorno
    @Value("${app.elasticsearch.apiKey:}")
    private String elasticApiKey;

    public FacetsResponse obtenerFacets() {
        String url = elasticUrl + "/productos/_search";

        String body = """
        {
          "size": 0,
          "aggs": {
            "top_nombres": {
              "terms": { "field": "nombre", "size": 10 }
            },
            "rangos_precio": {
              "range": {
                "field": "precio",
                "ranges": [
                  { "key": "0-1000", "from": 0, "to": 1000 },
                  { "key": "1000-2000", "from": 1000, "to": 2000 },
                  { "key": "2000+", "from": 2000 }
                ]
              }
            }
          }
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Si hay API Key, agregar Authorization (Cloud)
        // Elastic Cloud usa: Authorization: ApiKey <API_KEY>
        if (elasticApiKey != null && !elasticApiKey.isBlank()) {
            headers.set("Authorization", "ApiKey " + elasticApiKey.trim());
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        try {
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode aggs = root.path("aggregations");

            // nombres
            List<FacetsResponse.Bucket> nombres = new ArrayList<>();
            for (JsonNode b : aggs.path("top_nombres").path("buckets")) {
                nombres.add(new FacetsResponse.Bucket(
                        b.path("key").asText(),
                        b.path("doc_count").asLong()
                ));
            }

            // precios
            List<FacetsResponse.Bucket> precios = new ArrayList<>();
            for (JsonNode b : aggs.path("rangos_precio").path("buckets")) {
                precios.add(new FacetsResponse.Bucket(
                        b.path("key").asText(),
                        b.path("doc_count").asLong()
                ));
            }

            return new FacetsResponse(nombres, precios);

        } catch (Exception e) {
            throw new RuntimeException("Error parseando facets de Elasticsearch", e);
        }
    }
}
