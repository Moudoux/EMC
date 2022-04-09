package me.deftware.client.framework.world.chunk;

import java.util.random.RandomGenerator;

public interface Randomizer extends RandomGenerator {

    int _nextInt(int bound);

    float _nextFloat();

    double _nextDouble();

}
