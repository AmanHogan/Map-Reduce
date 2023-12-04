package org.amanhogan;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;


import org.apache.hadoop.mapreduce.Reducer;
public class DirActComb extends Reducer<Text, Text, Text, NullWritable>
{

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
    {

        // Lists to store directors, actors, and titles
        List<String> directors = new ArrayList<>();
        List<String> actors = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        // Iterate through values to separate directors, actors, and titles
        for (Text value : values)
        {
            String val = value.toString();
            if (val.startsWith("D"))
            {
                directors.add(val.substring(1));
            }

            else if (val.startsWith("A"))
            {
                actors.add(val.substring(1));
            }

            else if (val.startsWith("T"))
            {
                titles.add(val.substring(1));
            }
        }

        // Emit the combined information for each title, director, and actor
        for (String title : titles)
        {
            for (String director : directors)
            {
                for (String actor : actors)
                {

                    // Emit the result: (title, director, actor)
                    String[] actor_parts = actor.split("\\|");
                    // Extract individual values
                    String actorId = actor_parts[0];
                    String actorName = actor_parts[1];


                    // Emit the result: (title, director, actor)
                    String[] title_parts = title.split("\\|");
                    // Extract individual values
                    String titleType = title_parts[0];
                    String year = title_parts[1];
                    String titleName = title_parts[2];
                    String genre = title_parts[3];



                    if (director.contains(actorId))
                    {
                        context.write(new Text(titleType + "|" + year + "|" + actorName + "|" + actorId + "|" + titleName + "|" + director + "|" + genre), NullWritable.get());
                    }
                }
            }
        }
    }
}
