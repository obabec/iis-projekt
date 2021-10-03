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

CREATE TABLE public.authorship (
    id integer NOT NULL
);


ALTER TABLE public.authorship OWNER TO "compose-postgres";

--
-- Name: blokace; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.blocking (
    id integer NOT NULL,
    date_from date,
    date_to date,
    is_borrowed boolean
);


ALTER TABLE public.blocking OWNER TO "compose-postgres";

--
-- Name: kniha; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.book (
    id integer NOT NULL,
    name character varying(50),
    release date,
    isbn character(13),
    publisher character varying(50),
    genre character varying(30),
    rate smallint
);


ALTER TABLE public.book OWNER TO "compose-postgres";

--
-- Name: knihovna; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.library (
    id integer NOT NULL,
    tag character(5),
    street character varying(255),
    city character varying(50),
    street_number character varying(20),
    from_time time without time zone,
    to_time time without time zone,
    description character varying(500)
);


ALTER TABLE public.library OWNER TO "compose-postgres";

--
-- Name: osoba; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.person (
    id integer NOT NULL,
    name character varying(20),
    surname character varying(50),
    birth_date date,
    role smallint,
    username character varying(50),
    password character varying(255)
);


ALTER TABLE public.person OWNER TO "compose-postgres";

--
-- Name: vlastnictvi; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.ownership (
    id integer NOT NULL,
    book_count integer
);


ALTER TABLE public.ownership OWNER TO "compose-postgres";

--
-- Data for Name: autorstvi; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.ownership (id) FROM stdin;
\.


--
-- Data for Name: blokace; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.blocking (id, date_from, date_to, is_borrowed) FROM stdin;
\.


--
-- Data for Name: kniha; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.book (id, name, release, isbn, publisher, genre, rate) FROM stdin;
\.


--
-- Data for Name: knihovna; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.library (id, tag, street, city, street_number, from_time, to_time, description) FROM stdin;
\.


--
-- Data for Name: osoba; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.person (id, name, surname, birth_date, role, username, password) FROM stdin;
\.


--
-- Data for Name: vlastnictvi; Type: TABLE DATA; Schema: public; Owner: compose-postgres
--

COPY public.ownership (id, book_count) FROM stdin;
\.


--
-- Name: autorstvi autorstvi_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT authorship_pkey PRIMARY KEY (id);


--
-- Name: blokace blokace_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT blocking_pkey PRIMARY KEY (id);


--
-- Name: kniha kniha_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: knihovna knihovna_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.library
    ADD CONSTRAINT library_pkey PRIMARY KEY (id);


--
-- Name: osoba osoba_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: vlastnictvi vlastnictvi_pkey; Type: CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.ownership
    ADD CONSTRAINT ownership_pkey PRIMARY KEY (id);


--
-- Name: blokace kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT book FOREIGN KEY (id) REFERENCES public.book(id);


--
-- Name: autorstvi kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT book FOREIGN KEY (id) REFERENCES public.book(id);


--
-- Name: vlastnictvi kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.ownership
    ADD CONSTRAINT book FOREIGN KEY (id) REFERENCES public.book(id);


--
-- Name: blokace knihovna; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT library FOREIGN KEY (id) REFERENCES public.library(id);


--
-- Name: vlastnictvi knihovna; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.ownership
    ADD CONSTRAINT library FOREIGN KEY (id) REFERENCES public.library(id);


--
-- Name: blokace osoba; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT person FOREIGN KEY (id) REFERENCES public.person(id);


--
-- Name: autorstvi osoba; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT person FOREIGN KEY (id) REFERENCES public.person(id);


--
-- PostgreSQL database dump complete
--
