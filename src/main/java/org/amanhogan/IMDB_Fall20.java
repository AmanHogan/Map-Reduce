package org.amanhogan;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class IMDB_Fall20
{
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException
    {

        Configuration conf = new Configuration();

        // Shard count
        int split = 700 * 1024 * 1024;
        String splitsize = Integer.toString(split);

        // RAM configurations
        conf.set("mapreduce.input.fileinputformat.split.minsize", splitsize);
        conf.set("mapreduce.map.memory.mb", "2048");
        conf.set("mapreduce.reduce.memory.mb", "2048");

        Job job1 = Job.getInstance(conf, "actor-director gig");
        job1.setJarByClass(IMDB_Fall20.class);
        // job1.setNumReduceTasks(2); // <--- CHANGE this to increase the number of reducers


        // Set up multiple inputs with different mappers
        MultipleInputs.addInputPath(job1, new Path(args[0]), TextInputFormat.class, TitleMapper.class);
        MultipleInputs.addInputPath(job1, new Path(args[1]), TextInputFormat.class, ActorMapper.class);
        MultipleInputs.addInputPath(job1, new Path(args[2]), TextInputFormat.class, DirectorMapper.class);

        // Set the reducer and output key-value classes
        job1.setReducerClass(DirActComb.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);
        job1.setOutputKeyClass(Text.class);

        // Set the output path
        FileOutputFormat.setOutputPath(job1, new Path(args[3] + "inter"));

        // Wait for job completion
        job1.waitForCompletion(true);

        // Exit the program
        System.exit(0);
    }
}