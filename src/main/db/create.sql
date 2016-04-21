
-- DROP TABLE account;

CREATE TABLE account
(
  id bigint NOT NULL,
  created timestamp without time zone,
  email character varying(255),
  password character varying(255),
  role character varying(255),
  CONSTRAINT account_pkey PRIMARY KEY (id),
  CONSTRAINT uk_q0uja26qgu1atulenwup9rxyr UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO jambtughzaugiv;
  
  -- DROP TABLE conversionquery;

CREATE TABLE conversionquery
(
  id bigint NOT NULL,
  amount double precision NOT NULL,
  creationtimestamp bigint NOT NULL,
  fromcurrency character varying(255),
  ratestimestamp bigint NOT NULL,
  result double precision NOT NULL,
  tocurrency character varying(255),
  user_id bigint NOT NULL,
  CONSTRAINT conversionquery_pkey PRIMARY KEY (id),
  CONSTRAINT fk_b9gnmgbab2jryehgg5lm9khio FOREIGN KEY (user_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE conversionquery
  OWNER TO jambtughzaugiv;
