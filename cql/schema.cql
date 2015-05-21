// start clean:
DROP KEYSPACE IF EXISTS fake_data;

CREATE KEYSPACE fake_data
WITH replication = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
};

CREATE TABLE fake_data.messages (
	"messageId" text,
	"messageContent" text,
	timestamp timestamp,
	PRIMARY KEY ("messageId", timestamp)
);

CREATE TABLE fake_data.latest_message (
	"messageId" text,
	"messageContent" text,
	timestamp timestamp,
	PRIMARY KEY ("messageId")
);
