package com.unir.cliente.cliente_service.elastic;

import java.util.List;

public class FacetsResponse {

    public static class Bucket {
        private String key;
        private long count;

        public Bucket() {}
        public Bucket(String key, long count) {
            this.key = key;
            this.count = count;
        }

        public String getKey() { return key; }
        public long getCount() { return count; }
        public void setKey(String key) { this.key = key; }
        public void setCount(long count) { this.count = count; }
    }

    private List<Bucket> nombres;
    private List<Bucket> precios;

    public FacetsResponse() {}
    public FacetsResponse(List<Bucket> nombres, List<Bucket> precios) {
        this.nombres = nombres;
        this.precios = precios;
    }

    public List<Bucket> getNombres() { return nombres; }
    public List<Bucket> getPrecios() { return precios; }
    public void setNombres(List<Bucket> nombres) { this.nombres = nombres; }
    public void setPrecios(List<Bucket> precios) { this.precios = precios; }
}
