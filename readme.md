# Map Reduce

This project implements hadoop and the Map/Reduce algorithm as apart of the /5331 – Fall 2023 Project 3 description at UTA. Below is the project description:

*There are many instances when a person who acts in a movie, tvEpisode, or tvMovie also 
directs it. For example, Ben Affleck directed and starred in the 2012 movie Argo. You need 
to compute actors (or actresses) who also acted as directors for the parameters given to 
you. You will compute it for the given a 10-year period. You will be given one of 
{movie, tvEpisode, tvMovie}. Write a Map/Reduce program to list the names of people, 
who have directed and acted in the same IMDb title/category of any genre and output
title, director, actor, genre, and year. All datasets are needed for this.*

## Contents
- [Configurations](./Configurations) 
- [Log Files](./logFiles)



## Prerequisites
- A Windows 10 or Mac OS computer
- Internet access and administrator access
- Hadoop 3.2.1
- Java 
- Setup of Hadoop 
- Maven
- Setup the EXACT conifugration I used using: https://gist.github.com/vorpal56/5e2b67b6be3a827b85ac82a63a5b3b2e


## How to Run

1. Clone this repository
2. Follow Setup of Hadoop and JDK at: https://gist.github.com/vorpal56/5e2b67b6be3a827b85ac82a63a5b3b2e
3. Run in Intelij
4. Follow these steps:

Enter the Commands:
```
%HADOOP_HOME%\sbin\start-dfs.cmd
%HADOOP_HOME%\sbin\start-yarn.cmd
```

Check that the Namenode is running at:
`http://localhost:9870/dfshealth.html#tab-overview`

Navigate to the file system at that namenode in the browser and delete `tmp` and `ouputinter` if they exist.

Monitor the job at the yarn resource manager:
`http://localhost:8088`

Go to Intelij and do this to create jar file: 
``` 
mvn clean
mvn install
```

Run program: `hadoop jar target/ImdbProject-1.0-SNAPSHOT.jar org.amanhogan.IMDB_Fall20 hdfs://localhost:19000/input/title.basics.tsv hdfs://localhost:19000/input/imdb00-title-actors.csv hdfs://localhost:19000/input/title.crew.tsv %HDFS_LOC%/output
`



## Contributors
- Aman Hogan-Bailey

