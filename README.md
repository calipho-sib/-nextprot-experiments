# nextprot-experiments

Set of experiments/POCs useful to make design decisions when developing data integration pipelines

## Setup

Install nextprot-commons dependency locally by
mvn install:install-file    -Dfile=./src/lib/nextprot-commons.jar    -DgroupId=org.nextprot    -DartifactId=nextprot-commons    -Dversion=3.24.0    -Dpackaging=jar 


## Experiment Async Communication Patter for ETL

ETL often has multiple component written in different languages. These component could perform some transformation/computational tasks and generates output to be processed by the subsequent component in the pipeline.
This experiment compares different possiblities for such inter component communication/data exchange.

1. I/O (read/writing to hard disk)

2. Kafka Pub/Sub Messaging

3. Redis Producer Consumer List 

