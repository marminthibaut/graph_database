--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: blog_blog; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_blog (
    blog_id character varying(32) NOT NULL,
    blog_uid character varying(32) NOT NULL,
    blog_creadt timestamp without time zone DEFAULT now() NOT NULL,
    blog_upddt timestamp without time zone DEFAULT now() NOT NULL,
    blog_url character varying(255) NOT NULL,
    blog_name character varying(255) NOT NULL,
    blog_desc text,
    blog_status smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.blog_blog OWNER TO test;

--
-- Name: blog_category; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_category (
    cat_id bigint NOT NULL,
    blog_id character varying(32) NOT NULL,
    cat_title character varying(255) NOT NULL,
    cat_url character varying(255) NOT NULL,
    cat_desc text,
    cat_position integer DEFAULT 0,
    cat_lft integer,
    cat_rgt integer
);


ALTER TABLE public.blog_category OWNER TO test;

--
-- Name: blog_comment; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_comment (
    comment_id bigint NOT NULL,
    post_id bigint NOT NULL,
    comment_dt timestamp without time zone DEFAULT now() NOT NULL,
    comment_tz character varying(128) DEFAULT 'UTC'::character varying NOT NULL,
    comment_upddt timestamp without time zone DEFAULT now() NOT NULL,
    comment_author character varying(255) DEFAULT NULL::character varying,
    comment_email character varying(255) DEFAULT NULL::character varying,
    comment_site character varying(255) DEFAULT NULL::character varying,
    comment_content text,
    comment_words text,
    comment_ip character varying(39) DEFAULT NULL::character varying,
    comment_status smallint DEFAULT 0,
    comment_spam_status character varying(128) DEFAULT 0,
    comment_spam_filter character varying(32) DEFAULT NULL::character varying,
    comment_trackback smallint DEFAULT 0 NOT NULL
);


ALTER TABLE public.blog_comment OWNER TO test;

--
-- Name: blog_link; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_link (
    link_id bigint NOT NULL,
    blog_id character varying(32) NOT NULL,
    link_href character varying(255) NOT NULL,
    link_title character varying(255) NOT NULL,
    link_desc character varying(255),
    link_lang character varying(5),
    link_xfn character varying(255),
    link_position integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.blog_link OWNER TO test;

--
-- Name: blog_log; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_log (
    log_id bigint NOT NULL,
    user_id character varying(32),
    blog_id character varying(32),
    log_table character varying(255) NOT NULL,
    log_dt timestamp without time zone DEFAULT now() NOT NULL,
    log_ip character varying(39) NOT NULL,
    log_msg character varying(255) NOT NULL
);


ALTER TABLE public.blog_log OWNER TO test;

--
-- Name: blog_media; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_media (
    media_id bigint NOT NULL,
    user_id character varying(32) NOT NULL,
    media_path character varying(255) NOT NULL,
    media_title character varying(255) NOT NULL,
    media_file character varying(255) NOT NULL,
    media_dir character varying(255) DEFAULT '.'::character varying NOT NULL,
    media_meta text,
    media_dt timestamp without time zone DEFAULT now() NOT NULL,
    media_creadt timestamp without time zone DEFAULT now() NOT NULL,
    media_upddt timestamp without time zone DEFAULT now() NOT NULL,
    media_private smallint DEFAULT 0 NOT NULL
);


ALTER TABLE public.blog_media OWNER TO test;

--
-- Name: blog_menu; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_menu (
    link_id bigint NOT NULL,
    blog_id character varying(32) NOT NULL,
    link_href character varying(255) NOT NULL,
    link_title character varying(255) NOT NULL,
    link_desc character varying(255),
    link_lang character varying(5),
    link_xfn character varying(255),
    link_position integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.blog_menu OWNER TO test;

--
-- Name: blog_meta; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_meta (
    meta_id character varying(255) NOT NULL,
    meta_type character varying(64) NOT NULL,
    post_id bigint NOT NULL
);


ALTER TABLE public.blog_meta OWNER TO test;

--
-- Name: blog_permissions; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_permissions (
    user_id character varying(32) NOT NULL,
    blog_id character varying(32) NOT NULL,
    permissions text
);


ALTER TABLE public.blog_permissions OWNER TO test;

--
-- Name: blog_ping; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_ping (
    post_id bigint NOT NULL,
    ping_url character varying(255) NOT NULL,
    ping_dt timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.blog_ping OWNER TO test;

--
-- Name: blog_post; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_post (
    post_id bigint NOT NULL,
    blog_id character varying(32) NOT NULL,
    user_id character varying(32) NOT NULL,
    cat_id bigint,
    post_dt timestamp without time zone DEFAULT now() NOT NULL,
    post_tz character varying(128) DEFAULT 'UTC'::character varying NOT NULL,
    post_creadt timestamp without time zone DEFAULT now() NOT NULL,
    post_upddt timestamp without time zone DEFAULT now() NOT NULL,
    post_password character varying(32) DEFAULT NULL::character varying,
    post_type character varying(32) DEFAULT 'post'::character varying NOT NULL,
    post_format character varying(32) DEFAULT 'xhtml'::character varying NOT NULL,
    post_url character varying(255) NOT NULL,
    post_lang character varying(5) DEFAULT NULL::character varying,
    post_title character varying(255) DEFAULT NULL::character varying,
    post_excerpt text,
    post_excerpt_xhtml text,
    post_content text,
    post_content_xhtml text NOT NULL,
    post_notes text,
    post_meta text,
    post_words text,
    post_status smallint DEFAULT 0 NOT NULL,
    post_selected smallint DEFAULT 0 NOT NULL,
    post_position integer DEFAULT 0 NOT NULL,
    post_open_comment smallint DEFAULT 0 NOT NULL,
    post_open_tb smallint DEFAULT 0 NOT NULL,
    nb_comment integer DEFAULT 0 NOT NULL,
    nb_trackback integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.blog_post OWNER TO test;

--
-- Name: blog_post_media; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_post_media (
    media_id bigint NOT NULL,
    post_id bigint NOT NULL,
    link_type character varying(32) DEFAULT 'attachment'::character varying NOT NULL
);


ALTER TABLE public.blog_post_media OWNER TO test;

--
-- Name: blog_pref; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_pref (
    pref_id character varying(255) NOT NULL,
    user_id character varying(32),
    pref_ws character varying(32) DEFAULT 'system'::character varying NOT NULL,
    pref_value text,
    pref_type character varying(8) DEFAULT 'string'::character varying NOT NULL,
    pref_label text
);


ALTER TABLE public.blog_pref OWNER TO test;

--
-- Name: blog_session; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_session (
    ses_id character varying(40) NOT NULL,
    ses_time integer DEFAULT 0 NOT NULL,
    ses_start integer DEFAULT 0 NOT NULL,
    ses_value text NOT NULL
);


ALTER TABLE public.blog_session OWNER TO test;

--
-- Name: blog_setting; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_setting (
    setting_id character varying(255) NOT NULL,
    blog_id character varying(32),
    setting_ns character varying(32) DEFAULT 'system'::character varying NOT NULL,
    setting_value text,
    setting_type character varying(8) DEFAULT 'string'::character varying NOT NULL,
    setting_label text
);


ALTER TABLE public.blog_setting OWNER TO test;

--
-- Name: blog_spamrule; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_spamrule (
    rule_id bigint NOT NULL,
    blog_id character varying(32),
    rule_type character varying(16) DEFAULT 'word'::character varying NOT NULL,
    rule_content character varying(128) NOT NULL
);


ALTER TABLE public.blog_spamrule OWNER TO test;

--
-- Name: blog_user; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_user (
    user_id character varying(32) NOT NULL,
    user_super smallint,
    user_status smallint DEFAULT 1 NOT NULL,
    user_pwd character varying(40) NOT NULL,
    user_change_pwd smallint DEFAULT 0 NOT NULL,
    user_recover_key character varying(32) DEFAULT NULL::character varying,
    user_name character varying(255) DEFAULT NULL::character varying,
    user_firstname character varying(255) DEFAULT NULL::character varying,
    user_displayname character varying(255) DEFAULT NULL::character varying,
    user_email character varying(255) DEFAULT NULL::character varying,
    user_url character varying(255) DEFAULT NULL::character varying,
    user_desc text,
    user_default_blog character varying(32) DEFAULT NULL::character varying,
    user_options text,
    user_lang character varying(5) DEFAULT NULL::character varying,
    user_tz character varying(128) DEFAULT 'UTC'::character varying NOT NULL,
    user_post_status smallint DEFAULT (-2) NOT NULL,
    user_creadt timestamp without time zone DEFAULT now() NOT NULL,
    user_upddt timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE public.blog_user OWNER TO test;

--
-- Name: blog_version; Type: TABLE; Schema: public; Owner: test; Tablespace: 
--

CREATE TABLE blog_version (
    module character varying(64) NOT NULL,
    version character varying(32) NOT NULL
);


ALTER TABLE public.blog_version OWNER TO test;

--
-- Name: COLUMN tagger_term.original; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN tagger_term.original IS 'true => dico d''origine';


--
-- Name: blog_pk_blog; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_blog
    ADD CONSTRAINT blog_pk_blog PRIMARY KEY (blog_id);


--
-- Name: blog_pk_category; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_category
    ADD CONSTRAINT blog_pk_category PRIMARY KEY (cat_id);


--
-- Name: blog_pk_comment; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_comment
    ADD CONSTRAINT blog_pk_comment PRIMARY KEY (comment_id);


--
-- Name: blog_pk_link; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_link
    ADD CONSTRAINT blog_pk_link PRIMARY KEY (link_id);


--
-- Name: blog_pk_log; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_log
    ADD CONSTRAINT blog_pk_log PRIMARY KEY (log_id);


--
-- Name: blog_pk_media; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_media
    ADD CONSTRAINT blog_pk_media PRIMARY KEY (media_id);


--
-- Name: blog_pk_menu; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_menu
    ADD CONSTRAINT blog_pk_menu PRIMARY KEY (link_id);


--
-- Name: blog_pk_meta; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_meta
    ADD CONSTRAINT blog_pk_meta PRIMARY KEY (meta_id, meta_type, post_id);


--
-- Name: blog_pk_permissions; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_permissions
    ADD CONSTRAINT blog_pk_permissions PRIMARY KEY (user_id, blog_id);


--
-- Name: blog_pk_ping; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_ping
    ADD CONSTRAINT blog_pk_ping PRIMARY KEY (post_id, ping_url);


--
-- Name: blog_pk_post; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_post
    ADD CONSTRAINT blog_pk_post PRIMARY KEY (post_id);


--
-- Name: blog_pk_post_media; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_post_media
    ADD CONSTRAINT blog_pk_post_media PRIMARY KEY (media_id, post_id, link_type);


--
-- Name: blog_pk_session; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_session
    ADD CONSTRAINT blog_pk_session PRIMARY KEY (ses_id);


--
-- Name: blog_pk_spamrule; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_spamrule
    ADD CONSTRAINT blog_pk_spamrule PRIMARY KEY (rule_id);


--
-- Name: blog_pk_user; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_user
    ADD CONSTRAINT blog_pk_user PRIMARY KEY (user_id);


--
-- Name: blog_pk_version; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_version
    ADD CONSTRAINT blog_pk_version PRIMARY KEY (module);


--
-- Name: blog_uk_cat_url; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_category
    ADD CONSTRAINT blog_uk_cat_url UNIQUE (cat_url, blog_id);


--
-- Name: blog_uk_post_url; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_post
    ADD CONSTRAINT blog_uk_post_url UNIQUE (post_url, post_type, blog_id);


--
-- Name: blog_uk_pref; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_pref
    ADD CONSTRAINT blog_uk_pref UNIQUE (pref_ws, pref_id, user_id);


--
-- Name: blog_uk_setting; Type: CONSTRAINT; Schema: public; Owner: test; Tablespace: 
--

ALTER TABLE ONLY blog_setting
    ADD CONSTRAINT blog_uk_setting UNIQUE (setting_ns, setting_id, blog_id);


--
-- Name: blog_idx_blog_blog_upddt; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_blog_blog_upddt ON blog_blog USING btree (blog_upddt);


--
-- Name: blog_idx_blog_post_post_dt_post_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_blog_post_post_dt_post_id ON blog_post USING btree (blog_id, post_dt, post_id);


--
-- Name: blog_idx_blog_post_post_status; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_blog_post_post_status ON blog_post USING btree (blog_id, post_status);


--
-- Name: blog_idx_category_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_category_blog_id ON blog_category USING btree (blog_id);


--
-- Name: blog_idx_category_cat_lft_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_category_cat_lft_blog_id ON blog_category USING btree (blog_id, cat_lft);


--
-- Name: blog_idx_category_cat_rgt_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_category_cat_rgt_blog_id ON blog_category USING btree (blog_id, cat_rgt);


--
-- Name: blog_idx_comment_post_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_comment_post_id ON blog_comment USING btree (post_id);


--
-- Name: blog_idx_comment_post_id_dt_status; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_comment_post_id_dt_status ON blog_comment USING btree (post_id, comment_dt, comment_status);


--
-- Name: blog_idx_link_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_link_blog_id ON blog_link USING btree (blog_id);


--
-- Name: blog_idx_log_user_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_log_user_id ON blog_log USING btree (user_id);


--
-- Name: blog_idx_media_media_path; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_media_media_path ON blog_media USING btree (media_path, media_dir);


--
-- Name: blog_idx_media_user_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_media_user_id ON blog_media USING btree (user_id);


--
-- Name: blog_idx_menu_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_menu_blog_id ON blog_menu USING btree (blog_id);


--
-- Name: blog_idx_meta_meta_type; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_meta_meta_type ON blog_meta USING btree (meta_type);


--
-- Name: blog_idx_meta_post_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_meta_post_id ON blog_meta USING btree (post_id);


--
-- Name: blog_idx_permissions_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_permissions_blog_id ON blog_permissions USING btree (blog_id);


--
-- Name: blog_idx_post_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_blog_id ON blog_post USING btree (blog_id);


--
-- Name: blog_idx_post_cat_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_cat_id ON blog_post USING btree (cat_id);


--
-- Name: blog_idx_post_media_media_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_media_media_id ON blog_post_media USING btree (media_id);


--
-- Name: blog_idx_post_media_post_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_media_post_id ON blog_post_media USING btree (post_id);


--
-- Name: blog_idx_post_post_dt; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_post_dt ON blog_post USING btree (post_dt);


--
-- Name: blog_idx_post_post_dt_post_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_post_dt_post_id ON blog_post USING btree (post_dt, post_id);


--
-- Name: blog_idx_post_user_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_post_user_id ON blog_post USING btree (user_id);


--
-- Name: blog_idx_pref_user_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_pref_user_id ON blog_pref USING btree (user_id);


--
-- Name: blog_idx_pref_user_id_null; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_pref_user_id_null ON blog_pref USING btree (((user_id IS NULL)));


--
-- Name: blog_idx_setting_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_setting_blog_id ON blog_setting USING btree (blog_id);


--
-- Name: blog_idx_setting_blog_id_null; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_setting_blog_id_null ON blog_setting USING btree (((blog_id IS NULL)));


--
-- Name: blog_idx_spamrule_blog_id; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_spamrule_blog_id ON blog_spamrule USING btree (blog_id);


--
-- Name: blog_idx_spamrule_blog_id_null; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_spamrule_blog_id_null ON blog_spamrule USING btree (((blog_id IS NULL)));


--
-- Name: blog_idx_user_user_default_blog; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_user_user_default_blog ON blog_user USING btree (user_default_blog);


--
-- Name: blog_idx_user_user_super; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX blog_idx_user_user_super ON blog_user USING btree (user_super);


--
-- Name: term; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE INDEX term ON tagger_term USING btree (term);


--
-- Name: term_unique; Type: INDEX; Schema: public; Owner: test; Tablespace: 
--

CREATE UNIQUE INDEX term_unique ON tagger_term USING btree (term, lemme, forme);


--
-- Name: blog_fk_category_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_category
    ADD CONSTRAINT blog_fk_category_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_comment_post; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_comment
    ADD CONSTRAINT blog_fk_comment_post FOREIGN KEY (post_id) REFERENCES blog_post(post_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_link_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_link
    ADD CONSTRAINT blog_fk_link_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_log_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_log
    ADD CONSTRAINT blog_fk_log_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: blog_fk_media; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_post_media
    ADD CONSTRAINT blog_fk_media FOREIGN KEY (media_id) REFERENCES blog_media(media_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_media_post; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_post_media
    ADD CONSTRAINT blog_fk_media_post FOREIGN KEY (post_id) REFERENCES blog_post(post_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_media_user; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_media
    ADD CONSTRAINT blog_fk_media_user FOREIGN KEY (user_id) REFERENCES blog_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_menu_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_menu
    ADD CONSTRAINT blog_fk_menu_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_meta_post; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_meta
    ADD CONSTRAINT blog_fk_meta_post FOREIGN KEY (post_id) REFERENCES blog_post(post_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_permissions_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_permissions
    ADD CONSTRAINT blog_fk_permissions_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_permissions_user; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_permissions
    ADD CONSTRAINT blog_fk_permissions_user FOREIGN KEY (user_id) REFERENCES blog_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_ping_post; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_ping
    ADD CONSTRAINT blog_fk_ping_post FOREIGN KEY (post_id) REFERENCES blog_post(post_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_post_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_post
    ADD CONSTRAINT blog_fk_post_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_post_category; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_post
    ADD CONSTRAINT blog_fk_post_category FOREIGN KEY (cat_id) REFERENCES blog_category(cat_id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: blog_fk_post_user; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_post
    ADD CONSTRAINT blog_fk_post_user FOREIGN KEY (user_id) REFERENCES blog_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_pref_user; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_pref
    ADD CONSTRAINT blog_fk_pref_user FOREIGN KEY (user_id) REFERENCES blog_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_setting_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_setting
    ADD CONSTRAINT blog_fk_setting_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_spamrule_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_spamrule
    ADD CONSTRAINT blog_fk_spamrule_blog FOREIGN KEY (blog_id) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: blog_fk_user_default_blog; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY blog_user
    ADD CONSTRAINT blog_fk_user_default_blog FOREIGN KEY (user_default_blog) REFERENCES blog_blog(blog_id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO test WITH GRANT OPTION;


--
-- PostgreSQL database dump complete
--


