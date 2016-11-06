/* Tabela de usuários */
CREATE TABLE public.usuarios(
   id bigint, 
   nome character varying(80), 
   idade bigint, 
   
CONSTRAINT pk_usuarios PRIMARY KEY (id)
);
CREATE SEQUENCE public.seq_usuarios;

ALTER TABLE public.usuarios OWNER TO postgres;
COMMENT ON COLUMN public.usuarios.id IS 'Identificador do registro';
COMMENT ON COLUMN public.usuarios.nome IS 'Nome do usuário';
COMMENT ON COLUMN public.usuarios.idade IS 'Idade do usuário';



/* Tabela de acessos */
CREATE TABLE public.acessos(
   id bigint, 
   i_usuario bigint, 
   login character varying(20),
   senha character varying(20),

   CONSTRAINT pk_acessos PRIMARY KEY (id),
   CONSTRAINT fk_acesso_usuario FOREIGN KEY (i_usuario)
      REFERENCES public.usuarios (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE SEQUENCE public.seq_acessos;

ALTER TABLE public.acessos OWNER TO postgres;
COMMENT ON COLUMN public.acessos.id IS 'Identificador do registro';
COMMENT ON COLUMN public.acessos.i_usuario IS 'Identificador do usuário';
COMMENT ON COLUMN public.acessos.login IS 'Login do usuário';
COMMENT ON COLUMN public.acessos.senha IS 'Senha do usuário';



/* Tabela de cervejas */
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

ALTER TABLE public.cervejas OWNER TO postgres;
COMMENT ON COLUMN public.cervejas.id IS 'Identificador do registro';
COMMENT ON COLUMN public.cervejas.nome IS 'Nome da cerveja';
COMMENT ON COLUMN public.cervejas.tipo IS 'Tipo da cerveja';
COMMENT ON COLUMN public.cervejas.familia IS 'Família da cerveja';
COMMENT ON COLUMN public.cervejas.amargor IS 'Amargor da cerveja';
COMMENT ON COLUMN public.cervejas.cor IS 'Cor da cerveja';
COMMENT ON COLUMN public.cervejas.teor IS 'Teor ancoólico da cerveja';
COMMENT ON COLUMN public.cervejas.observacao IS 'Observações';



/* Tabela de cervejarias */
CREATE TABLE public.cervejarias (
   id bigint, 
   nome character varying(80),
   localizacao character varying(100), 
   estado character varying(50), 
   pais character varying(50),
 
   CONSTRAINT pk_cervejarias PRIMARY KEY (id)
   
);
CREATE SEQUENCE public.seq_cervejarias;

ALTER TABLE public.cervejarias OWNER TO postgres;
COMMENT ON COLUMN public.cervejarias.id IS 'Identificador do registro';
COMMENT ON COLUMN public.cervejarias.nome IS 'Nome da cervejaria';
COMMENT ON COLUMN public.cervejarias.localizacao IS 'Localização da cervejaria';
COMMENT ON COLUMN public.cervejarias.estado IS 'Estado da cervejaria';
COMMENT ON COLUMN public.cervejarias.pais IS 'País da cervejaria';


/* Tabela de cervejas favoritas */
CREATE TABLE public.favoritas (
  id bigint NOT NULL,
  i_usuario bigint,
  i_cerveja bigint NOT NULL,
  pontuacao bigint,
  curtida character varying(1),
  comentario character varying(500),

  CONSTRAINT pk_favoritas PRIMARY KEY (id),
  CONSTRAINT fk_favorita_cerveja FOREIGN KEY (i_cerveja)
      REFERENCES public.cervejas (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);
CREATE SEQUENCE public.seq_favoritas;

ALTER TABLE public.favoritas OWNER TO postgres;

COMMENT ON COLUMN public.favoritas.id IS 'Identificador do registro';
COMMENT ON COLUMN public.favoritas.i_usuario IS 'Identificador do usuário';
COMMENT ON COLUMN public.favoritas.i_cerveja IS 'Identificador da cerveja';
COMMENT ON COLUMN public.favoritas.pontuacao IS 'Pontuação';
COMMENT ON COLUMN public.favoritas.curtida IS 'Cerveja curtida';
COMMENT ON COLUMN public.favoritas.comentario IS 'Comentário';