package me.deftware.client.framework.render.shader;

import com.google.common.base.Charsets;
import lombok.Getter;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.util.ResourceUtils;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL20;

import java.util.Objects;

/**
 * @author Deftware
 */
public class Shader {

	private @Getter int program, vs, fs;

	public Shader(String path, String vertex, String fragment, EMCMod mod) {
		try {
			program = GL20.glCreateProgram();
			// Vertex shader
			vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
			GL20.glShaderSource(vs, IOUtils.toString(Objects.requireNonNull(ResourceUtils.getStreamFromModResources(mod, path + vertex + ".vs")), Charsets.UTF_8));
			GL20.glCompileShader(vs);
			if (GL20.glGetShaderi(vs, GL20.GL_COMPILE_STATUS) != 1) {
				throw new RuntimeException(GL20.glGetShaderInfoLog(vs));
			}
			// Fragment shader
			fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			GL20.glShaderSource(fs, IOUtils.toString(Objects.requireNonNull(ResourceUtils.getStreamFromModResources(mod, path + fragment + ".fs")), Charsets.UTF_8));
			GL20.glCompileShader(fs);
			if (GL20.glGetShaderi(fs, GL20.GL_COMPILE_STATUS) != 1) {
				throw new RuntimeException(GL20.glGetShaderInfoLog(fs));
			}
			// Attach
			GL20.glAttachShader(program, vs);
			GL20.glAttachShader(program, fs);
			// Link to program
			GL20.glLinkProgram(program);
			if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != 1) {
				throw new RuntimeException(GL20.glGetShaderInfoLog(program));
			}
			GL20.glValidateProgram(program);
			if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != 1) {
				throw new RuntimeException(GL20.glGetShaderInfoLog(program));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void bind() {
		GL20.glUseProgram(program);
	}

	public void unbind() {
		GL20.glUseProgram(0);
	}

}
