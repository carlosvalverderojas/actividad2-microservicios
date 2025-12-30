package com.unir.cliente.cliente_service.elastic;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductoElasticRepository
        extends ElasticsearchRepository<ProductoDocument, Long> {

    @Query("""
    {
      "multi_match": {
        "query": "?0",
        "fields": ["nombre^3", "descripcion"],
        "operator": "or",
        "fuzziness": "AUTO"
      }
    }
    """)
    List<ProductoDocument> buscarFullText(String texto);
}
