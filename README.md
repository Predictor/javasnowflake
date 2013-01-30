javasnowflake
=============

Java implementation of twitter snowflake.
64 bit sequential ID generator based on twitter snowflake ID generation algorithm.
id is composed of:
time - 41 bits (millisecond precision w/ a custom epoch gives us 69 years); 
configured machine id - 10 bits - gives us up to 1024 machines; 
sequence number - 12 bits - rolls over every 4096 per machine (with protection to avoid rollover in the same ms).
