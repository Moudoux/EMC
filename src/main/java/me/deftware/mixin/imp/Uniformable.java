package me.deftware.mixin.imp;

import net.minecraft.client.gl.PostProcessShader;

import java.util.List;

public interface Uniformable {

    void registerUniformf(String name, float[] values);

    List<PostProcessShader> getPostShaders();

}
