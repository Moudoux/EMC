package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;
import me.deftware.client.framework.render.Shader;

import java.util.List;

/**
 * @author Deftware
 */
public class EventShader extends Event {

    private final List<Shader> shaders;

    public EventShader(List<Shader> shaders) {
        this.shaders = shaders;
    }

    public List<Shader> getShaders() {
        return shaders;
    }

    public EventShader register(Shader shader) {
        this.shaders.add(shader);
        return this;
    }

}
