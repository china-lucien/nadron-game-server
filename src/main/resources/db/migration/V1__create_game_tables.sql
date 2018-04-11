-- Игрок
CREATE TABLE public.players
(
  id 		bigserial NOT NULL,
  mail      character varying,
  name	    character varying,
  password  character varying,
  vk_uid    character varying,
  vk_key    character varying,
  ref		character varying,
  sex		integer NOT NULL,
  money		integer NOT NULL,
  rating	integer NOT NULL,
  clan_id   bigint NOT NULL,
  created   timestamp without time zone NOT NULL,
  CONSTRAINT players_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE EXTENSION IF NOT EXISTS citext;
ALTER TABLE players ALTER COLUMN vk_uid TYPE citext; 
