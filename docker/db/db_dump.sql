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
    id integer NOT NULL,
    book_id integer NOT NULL,
    person_id integer NOT NULL
);


ALTER TABLE public.authorship OWNER TO "compose-postgres";

--
-- Name: blokace; Type: TABLE; Schema: public; Owner: compose-postgres
--

CREATE TABLE public.blocking (
    id integer NOT NULL,
    book_id integer NOT NULL,
    library_id integer NOT NULL,
    person_id integer NOT NULL,
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
    library_id integer NOT NULL,
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
    name character varying(50),
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
-- Name: blokace kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT book FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: autorstvi kniha; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT book FOREIGN KEY (book_id) REFERENCES public.book(id);

--
-- Name: blokace knihovna; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT library FOREIGN KEY (library_id) REFERENCES public.library(id);

--
-- Name: blokace osoba; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.blocking
    ADD CONSTRAINT person FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: autorstvi osoba; Type: FK CONSTRAINT; Schema: public; Owner: compose-postgres
--

ALTER TABLE ONLY public.authorship
    ADD CONSTRAINT person FOREIGN KEY (person_id) REFERENCES public.person(id);


ALTER TABLE ONLY public.book
    ADD CONSTRAINT library FOREIGN KEY (library_id) REFERENCES public.library(id);
--
-- PostgreSQL database dump complete
--
