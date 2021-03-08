package org.nextprot.dataintegration.experiments.commpatterns.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

public class TransformConsumer {

    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();

        int count = 0;
        boolean end = false;
        Jedis jedis = new Jedis();
        while (!end) {
            String statement = jedis.lpop("1");
            if(statement == null) {
                continue;
            }

            if ("DIE".equals(statement)) {
                end = true;
                System.out.println("Poison pill");
            }
            System.out.println("Reading statement " + ++count + " " + statement);


        }

        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTime - startTime) + " milliseconds");
    }
}
