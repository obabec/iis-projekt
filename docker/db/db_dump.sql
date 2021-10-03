--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: autorstvi; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.autorstvi (
    id integer NOT NULL
);


ALTER TABLE public.autorstvi OWNER TO "compose-postgres";

--
-- Name: blokace; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.blokace (
    id integer NOT NULL,
    datum_od date,
    datum_do date,
    is_vypujcka boolean
);


ALTER TABLE public.blokace OWNER TO "compose-postgres";

--
-- Name: kniha; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.kniha (
    id integer NOT NULL,
    nazev character varying(50),
    vydani date,
    isbn character(13),
    vydavatel character varying(50),
    zanr character varying(30),
    hodnoceni smallint
);


ALTER TABLE public.kniha OWNER TO "compose-postgres";

--
-- Name: knihovna; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.knihovna (
    id integer NOT NULL,
    oznaceni character(5),
    ulice character varying(255),
    mesto character varying(50),
    cp character varying(20),
    from_time time without time zone,
    to_time time without time zone,
    popis character varying(500)
);


ALTER TABLE public.knihovna OWNER TO "compose-postgres";

--
-- Name: osoba; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.osoba (
    id integer NOT NULL,
    jmeno character varying(20),
    prijmeni character varying(50),
    narozeni date,
    role smallint,
    username character varying(50),
    password character varying(255)
);


ALTER TABLE public.osoba OWNER TO "compose-postgres";

--
-- Name: vlastnictvi; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.vlastnictvi (
    id integer NOT NULL,
    pocet_knih integer
);


ALTER TABLE public.vlastnictvi OWNER TO "compose-postgres";

--
-- Data for Name: autorstvi; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.autorstvi (id) FROM stdin;
\.


--
-- Data for Name: blokace; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.blokace (id, datum_od, datum_do, is_vypujcka) FROM stdin;
\.


--
-- Data for Name: kniha; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.kniha (id, nazev, vydani, isbn, vydavatel, zanr, hodnoceni) FROM stdin;
\.


--
-- Data for Name: knihovna; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.knihovna (id, oznaceni, ulice, mesto, cp, from_time, to_time, popis) FROM stdin;
\.


--
-- Data for Name: osoba; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.osoba (id, jmeno, prijmeni, narozeni, role, username, password) FROM stdin;
\.


--
-- Data for Name: vlastnictvi; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.vlastnictvi (id, pocet_knih) FROM stdin;
\.


--
-- Name: autorstvi autorstvi_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.autorstvi
    ADD CONSTRAINT autorstvi_pkey PRIMARY KEY (id);


--
-- Name: blokace blokace_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blokace
    ADD CONSTRAINT blokace_pkey PRIMARY KEY (id);


--
-- Name: kniha kniha_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.kniha
    ADD CONSTRAINT kniha_pkey PRIMARY KEY (id);


--
-- Name: knihovna knihovna_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.knihovna
    ADD CONSTRAINT knihovna_pkey PRIMARY KEY (id);


--
-- Name: osoba osoba_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.osoba
    ADD CONSTRAINT osoba_pkey PRIMARY KEY (id);


--
-- Name: vlastnictvi vlastnictvi_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.vlastnictvi
    ADD CONSTRAINT vlastnictvi_pkey PRIMARY KEY (id);


--
-- Name: blokace kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blokace
    ADD CONSTRAINT kniha FOREIGN KEY (id) REFERENCES public.kniha(id);


--
-- Name: autorstvi kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.autorstvi
    ADD CONSTRAINT kniha FOREIGN KEY (id) REFERENCES public.kniha(id);


--
-- Name: vlastnictvi kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.vlastnictvi
    ADD CONSTRAINT kniha FOREIGN KEY (id) REFERENCES public.kniha(id);


--
-- Name: blokace knihovna; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blokace
    ADD CONSTRAINT knihovna FOREIGN KEY (id) REFERENCES public.knihovna(id);


--
-- Name: vlastnictvi knihovna; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.vlastnictvi
    ADD CONSTRAINT knihovna FOREIGN KEY (id) REFERENCES public.knihovna(id);


--
-- Name: blokace osoba; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blokace
    ADD CONSTRAINT osoba FOREIGN KEY (id) REFERENCES public.osoba(id);


--
-- Name: autorstvi osoba; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.autorstvi
    ADD CONSTRAINT osoba FOREIGN KEY (id) REFERENCES public.osoba(id);


--
-- PostgreSQL database dump complete
--
