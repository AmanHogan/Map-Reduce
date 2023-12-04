package org.amanhogan;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DirectorMapper extends Mapper<LongWritable, Text, Text, Text>
{

    private static final int TITLE_ID_INDEX = 0;
    private static final int DIRECTORS_INDEX = 1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {

        // Split the line into fields based on the tab separator
        String[] fields = value.toString().split("\t");

        // Ensure that the record has the expected number of fields
        if (fields.length >= DIRECTORS_INDEX + 1)
        {
            String titleId = fields[TITLE_ID_INDEX];
            String directors = fields[DIRECTORS_INDEX];

            context.write(new Text(titleId), new Text("D" + directors));
        }
    }
}