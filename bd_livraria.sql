--
-- PostgreSQL database dump
--

-- Dumped from database version 16.8 (Ubuntu 16.8-0ubuntu0.24.04.1)
-- Dumped by pg_dump version 16.8 (Ubuntu 16.8-0ubuntu0.24.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id integer NOT NULL,
    nome character varying(150) NOT NULL,
    endereco character varying(255),
    identificador character varying(14) NOT NULL,
    tipo_pessoa character varying(2) NOT NULL,
    telefone character varying(20),
    ativo boolean DEFAULT true NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_id_seq OWNER TO postgres;

--
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id;


--
-- Name: editora; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.editora (
    id integer NOT NULL,
    nome character varying(100) NOT NULL,
    categoria character varying(50),
    endereco character varying(200),
    telefone character varying(20),
    gerente character varying(100)
);


ALTER TABLE public.editora OWNER TO postgres;

--
-- Name: editora_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.editora_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.editora_id_seq OWNER TO postgres;

--
-- Name: editora_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.editora_id_seq OWNED BY public.editora.id;


--
-- Name: livros; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livros (
    id integer NOT NULL,
    id_editora integer,
    nome character varying(150) NOT NULL,
    autor character varying(100),
    preco numeric(10,2),
    categoria character varying(50),
    isbn character varying(20) NOT NULL,
    quantidade integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.livros OWNER TO postgres;

--
-- Name: livros_comprados; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livros_comprados (
    id_cliente integer NOT NULL,
    id_livro integer NOT NULL,
    data_compra timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


ALTER TABLE public.livros_comprados OWNER TO postgres;

--
-- Name: livros_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.livros_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.livros_id_seq OWNER TO postgres;

--
-- Name: livros_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.livros_id_seq OWNED BY public.livros.id;


--
-- Name: cliente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id SET DEFAULT nextval('public.cliente_id_seq'::regclass);


--
-- Name: editora id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.editora ALTER COLUMN id SET DEFAULT nextval('public.editora_id_seq'::regclass);


--
-- Name: livros id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros ALTER COLUMN id SET DEFAULT nextval('public.livros_id_seq'::regclass);


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cliente (id, nome, endereco, identificador, tipo_pessoa, telefone, ativo) FROM stdin;
1	Diego Amorim	we 75 n 1262	18617271272	PF	91993621494	t
\.


--
-- Data for Name: editora; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.editora (id, nome, categoria, endereco, telefone, gerente) FROM stdin;
1	Editora 1	Romance	we 75 1262	8198283618	Diegao
\.


--
-- Data for Name: livros; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.livros (id, id_editora, nome, autor, preco, categoria, isbn, quantidade) FROM stdin;
1	1	biblia	Deus	39.00	naosei	182738	19
\.


--
-- Data for Name: livros_comprados; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.livros_comprados (id_cliente, id_livro, data_compra) FROM stdin;
1	1	2025-05-26 00:27:58.573731
\.


--
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_seq', 1, true);


--
-- Name: editora_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.editora_id_seq', 1, true);


--
-- Name: livros_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.livros_id_seq', 1, true);


--
-- Name: cliente cliente_identificador_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_identificador_key UNIQUE (identificador);


--
-- Name: cliente cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pk PRIMARY KEY (id);


--
-- Name: editora editora_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.editora
    ADD CONSTRAINT editora_pk PRIMARY KEY (id);


--
-- Name: livros_comprados livros_comprados_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros_comprados
    ADD CONSTRAINT livros_comprados_pkey PRIMARY KEY (id_cliente, id_livro, data_compra);


--
-- Name: livros livros_isbn_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros
    ADD CONSTRAINT livros_isbn_key UNIQUE (isbn);


--
-- Name: livros livros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros
    ADD CONSTRAINT livros_pkey PRIMARY KEY (id);


--
-- Name: livros_comprados fk_livros_comprados_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros_comprados
    ADD CONSTRAINT fk_livros_comprados_cliente FOREIGN KEY (id_cliente) REFERENCES public.cliente(id) ON DELETE CASCADE;


--
-- Name: livros_comprados fk_livros_comprados_livro; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros_comprados
    ADD CONSTRAINT fk_livros_comprados_livro FOREIGN KEY (id_livro) REFERENCES public.livros(id) ON DELETE RESTRICT;


--
-- Name: livros fk_livros_editora; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livros
    ADD CONSTRAINT fk_livros_editora FOREIGN KEY (id_editora) REFERENCES public.editora(id) ON DELETE SET NULL;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;


--
-- PostgreSQL database dump complete
--

