package org.amanhogan;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ActorMapper extends Mapper<LongWritable, Text, Text, Text>
{

    private static final int TITLE_ID_INDEX = 0;
    private static final int ACTOR_ID_INDEX = 1;
    private static final int ACTOR_NAME_INDEX = 2;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {

        // Split the line into fields based on the comma separator
        String[] fields = value.toString().split(",");

        // Ensure that the record has the expected number of fields
        if (fields.length >= ACTOR_NAME_INDEX + 1)
        {
            String titleId = fields[TITLE_ID_INDEX];
            String actorId = fields[ACTOR_ID_INDEX];
            String actorName = fields[ACTOR_NAME_INDEX];

            context.write(new Text(titleId), new Text("A" + actorId + "|" + actorName));

        }
    }
}