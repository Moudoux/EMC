package me.deftware.client.framework.render.batching;

/**
 * @author Deftware
 */
public interface VertexConstructor {

    VertexConstructor vertex(double x, double y, double z);

    VertexConstructor texture(float u, float v);

    VertexConstructor color(float r, float g, float b, float alpha);

    VertexConstructor normal(float x, float y, float z);

    void next();

}
