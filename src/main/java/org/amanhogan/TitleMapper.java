package org.amanhogan;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TitleMapper extends Mapper<LongWritable, Text, Text, Text>
{

    private static final int TITLE_ID_INDEX = 0;
    private static final int TITLE_TYPE_INDEX = 1;
    private static final int TITLE_NAME_INDEX = 2;
    private static final int YEAR_INDEX = 5;
    private static final int GENRE_INDEX = 8;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {

        // Split the line into fields based on the tab separator
        String[] fields = value.toString().split("\t");

        // Ensure that the record has the expected number of fields
        if (fields.length >= GENRE_INDEX + 1)
        {
            String titleId = fields[TITLE_ID_INDEX];
            String titleType = fields[TITLE_TYPE_INDEX];
            String titleName = fields[TITLE_NAME_INDEX];
            String year = fields[YEAR_INDEX];
            String genre = fields[GENRE_INDEX];

            // Replace "\N" with a default value (e.g., "N/A")
            year = "\\N".equals(year) ? "N/A" : year;
            genre = "\\N".equals(genre) ? "N/A" : genre;

            // Check if the titleType is 'tvMovie' and the year is within the range 2010 to 2020
            if ("tvMovie".equals(titleType) && !year.equals("N/A"))
            {
                int movieYear = Integer.parseInt(year);
                if (movieYear >= 2010 && movieYear <= 2020)
                {
                    context.write(new Text(titleId), new Text("T" + titleType + "|" + year  + "|" + titleName + "|" + genre));
                }
            }
        }
    }
}