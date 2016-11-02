CREATE TABLE public.cervejas(
   id bigint, 
   nome character varying(80), 
   tipo character varying(60), 
   familia character varying(60), 
   amargor bigint, 
   cor bigint, 
   teor bigint, 
   observacao character varying(1000), 
   CONSTRAINT pk_cervejas PRIMARY KEY (id)
);

CREATE SEQUENCE public.seq_cervejas;

CREATE TABLE public.cervejarias (
   id bigint, 
   nome character varying(80),
   localizacao character varying(100), 
   estado character varying(50), 
   pais character varying(50), 
   CONSTRAINT pk_cervejarias PRIMARY KEY (id)
   
);

CREATE SEQUENCE public.seq_cervejarias;

